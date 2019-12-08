package com.wing.java.util;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;

import java.io.*;
import java.util.*;

public class SftpUtil {

    /**
     * 获取ChannelSftp对象
     * @param host
     * @param port
     * @param username
     * @param password
     * @return
     */
    public static ChannelSftp getChannelSftp(String host, int port, String username, String password) {
        Channel channel = null;
        try {
            JSch jsch = new JSch(); 												// 创建JSch对象
            Session session = jsch.getSession(username, host, port);	 			// 根据用户名，主机ip，端口获取一个Session对象
            session.setPassword(password); 											// 设置密码
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config); 												// 为Session对象设置properties
            //		session.setTimeout(timeout); 											// 设置timeout时间
            session.connect(); 														// 通过Session建立链接
            channel = session.openChannel("sftp"); 									// 打开SFTP通道
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        } 																			// 建立SFTP通道的连接

        return (ChannelSftp) channel;
    }

    /**
     * 关闭
     */
    public static void closeChannelSftp(ChannelSftp sftp){
        if (sftp != null) {
            try {
                if(sftp.getSession().isConnected()){
                    sftp.getSession().disconnect();
                }
            } catch (JSchException e) {
                e.printStackTrace();
            }
            sftp.disconnect();
            sftp.exit();
        }
    }

    /**
     * 上传文件
     * @param sftp
     * @param src			源文件
     * @param directory 	目标目录
     * @param dstFile		目标文件
     * @param mode	采用默认的传输模式：ChannelSftp.OVERWRITE
     * mode可选值为：ChannelSftp.OVERWRITE，ChannelSftp.RESUME，ChannelSftp.APPEND
     * 将本地文件名为src的文件上传到目标服务器，目标文件名为dstFile，若dstFile为目录，则目标文件名将与src文件名相同
     */
    public static void upload(ChannelSftp sftp, String src, String directory, String dstFile) {
        boolean exist = isExist(sftp, directory);
        if(!exist){
            mkDirs(sftp, directory);
        }

        try {
            sftp.put(src, directory + dstFile);
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     * @param sftp
     * @param src			源文件路径，包含文件
     * @param directory 	目标目录
     * @param dstFile		目标文件
     * @param mode
     */
    public static void upload(ChannelSftp sftp, String src, String directory, String dstFile, int mode) {
        boolean exist = isExist(sftp, directory);
        if(!exist){
            mkDirs(sftp, directory);
        }

        try {
            sftp.put(src, directory + dstFile, mode);
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     * @param sftp
     * @param in
     * @param directory 目标目录
     * @param dst		目标文件
     * 将本地的input stream对象src上传到目标服务器，目标文件名为dst，dst不能为目录。采用默认的传输模式：OVERWRITE
     */
    public static void upload(ChannelSftp sftp, InputStream in, String directory, String dstFile) {
        boolean exist = isExist(sftp, directory);
        if(!exist){
            mkDirs(sftp, directory);
        }

        try {
            sftp.put(in, directory + dstFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 上传文件
     * @param directory 上传的目录
     * @param uploadFile 要上传的文件
     * @param sftp
     */
    public static void upload(ChannelSftp sftp, String directory, String uploadFile) {
        try {
            sftp.cd(directory);
            File file=new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     * @param sftp
     * @param btyes
     * @param directory 目标目录
     * @param dstFile	目标文件
     * 采用默认的传输模式：OVERWRITE
     */
    public static void upload(ChannelSftp sftp, byte[] btyes, String directory, String dstFile) {
        boolean exist = isExist(sftp, directory);
        if(!exist){
            mkDirs(sftp, directory);
        }

        OutputStream out = null;
        try {
            out = sftp.put(directory + dstFile);
            out.write(btyes);
            out.flush();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * upload all the files to the server
     */
    public static void uploadFiles(ChannelSftp sftp, String directory, String uploadFile) {
        File[] files = new File(uploadFile).listFiles();
        try {
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    String localFile = uploadFile + "/" + fileName;
                    File temFile = new File(localFile);
                    String remoteFile = directory + "/" + fileName;
                    if (temFile.isFile()) {
                        File rfile = new File(remoteFile);
                        String rpath = rfile.getParent();
                        try {
                            sftp.mkdir(rpath);
                        } catch (Exception e) {
                        }
                        sftp.put(new FileInputStream(file), file.getName());
                    } else {
                        try {
                            sftp.mkdir(remoteFile);
                        } catch (Exception e) {
                        }
                        uploadFiles(sftp, remoteFile, localFile);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    /**
     * get all the files need to be upload or download
     * @param file
     * @return
     */
    public static List<String> getFileEntryList(String file) {
        ArrayList<String> fileList = new ArrayList<String>();
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            InputStreamReader inreader = new InputStreamReader(in);

            LineNumberReader linreader = new LineNumberReader(inreader);
            String filepath = linreader.readLine();
            while (filepath != null) {
                fileList.add(filepath);
                filepath = linreader.readLine();
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in = null;
            }
        }
        return fileList;
    }

    /**
     * 下载文件
     * @param directory  	  下载目录
     * @param downloadFile   下载的文件
     * @param saveFile		    存在本地的路径
     * @param sftp
     */
    public void download(ChannelSftp sftp, String directory, String downloadFile, String saveFile) {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 列出目录下的文件
     * @param directory     要列出的目录
     * @param sftp
     * @throws SftpException
     * @throws SftpException
     */
    // public Vector listFiles(String directory, ChannelSftp sftp) throws
    // SftpException{
    // return sftp.ls(directory);
    // }
    public static List<Map<String, String>> listFiles(String directory, ChannelSftp sftp) throws SftpException {
        List<Map<String, String>> fileList = new ArrayList<Map<String, String>>();
        Vector<LsEntry> files = sftp.ls(directory);
        int count = 0;
        for (LsEntry file : files) {
            Map<String, String> map = new HashMap<String, String>();
            count++;
            if (file.getAttrs().isDir()) {
                // 目录
                map.put("isParent", "true");
            } else {
                map.put("isParent", "false");
            }
            map.put("id", count + "");
            map.put("pId", "0");
            map.put("name", file.getFilename());
            map.put("path", directory);
            fileList.add(map);
        }
        return fileList;
    }

    /**
     * 判断目录是否存在
     * @param sftp
     * @param path  要判断的路径
     * @return
     */
    public static boolean isExist(ChannelSftp sftp, String path) {
        path = path.replace("\\", "/");
        try {
            sftp.ls(path);
        } catch (SftpException e) {
            return false;
        }
        return true;
    }

    /**
     * 创建多级目录 *注意：仅适用于Linux环境 格式为：/home/yuanyl/picture_test/temp/
     * @param sftp
     * @param path    要创建的目录
     * @throws SftpException
     */
    public static void mkDirs(ChannelSftp sftp, String path) {
        path = path.replace("\\", "/");
        String[] arr = path.split("/"); 						// arr[0] = ""; 空字符串
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null || "".equals(arr[i])) {
                continue;
            }
            String dir = "/";
            for (int j = 0; j <= i; j++) {
                if (arr[j] == null || "".equals(arr[j])) {
                    continue;
                }
                dir = dir + arr[j] + "/";
            }
            try {
                sftp.ls(dir);
            } catch (SftpException e) {
                try {
                    sftp.mkdir(dir);
                } catch (SftpException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     * @param sftp
     * @param path   文件全路径
     * @return
     */
    public static boolean delete(ChannelSftp sftp, String path) {
        try {
            path = path.replace("\\", "/");
            sftp.rm(path);
        } catch (SftpException e) {
            return false;
        }
        return true;
    }

    /**
     * 删除文件
     * @param directory   要删除文件所在目录
     * @param deleteFile  要删除的文件
     * @param sftp
     */
    public static boolean delete(ChannelSftp sftp, String directory, String deleteFile) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
