package com.wing.java.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.*;
import java.util.Iterator;
import java.util.Objects;

/**
 * 图片处理工具类
 * 功能：
 * 缩放图像、
 * 切割图像、
 * 图像类型转换、
 * 图像色彩转换、
 * 图片水印
 */
public class ImageUtil {

    /**
     * 几种常见的图片格式
     */
    public static String IMAGE_TYPE_JPG = "jpg";        // 联合照片专家组
    public static String IMAGE_TYPE_JPEG = "jpeg";        // 联合照片专家组
    public static String IMAGE_TYPE_PNG = "png";        // 可移植网络图形
    public static String IMAGE_TYPE_GIF = "gif";        // 图形交换格式
    public static String IMAGE_TYPE_BMP = "bmp";        // 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
    public static String IMAGE_TYPE_PSD = "psd";        // Photoshop的专用格式Photoshop


//    ============================ 缩放图像 ============================

    /**
     * 缩放图像（按比例缩放）
     *
     * @param src   源图像文件地址
     * @param dest  缩放后的图像地址
     * @param scale 缩放系数，缩小时小于1，放大时大于1
     */
    public static void scale(String src, String dest, float scale) {
        try {
            BufferedImage srcImg = ImageIO.read(new File(src)); // 读入文件
            int width = srcImg.getWidth();                        // 得到源图宽
            int height = srcImg.getHeight();                    // 得到源图长
            //计算新的宽度和高度
            width = Integer.valueOf(new Float(width * scale).intValue());
            height = Integer.valueOf(new Float(height * scale).intValue());

            Image image = srcImg.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage destImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = destImg.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            ImageIO.write(destImg, "JPEG", new File(dest));// 输出到文件流
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 缩放图像（按高度和宽度缩放）
     *
     * @param src      源图像文件地址
     * @param dest     缩放后的图像地址
     * @param height   缩放后的高度
     * @param width    缩放后的宽度
     * @param isFiller 比例不对时是否需要补白：true为补白; false为不补白;
     */
    public static void scale(String src, String dest, int width, int height, boolean isFiller) {
        try {
            double ratio = 0.0; // 缩放比例
            BufferedImage bi = ImageIO.read(new File(src));
            Image destImg = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue()
                            / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform
                        .getScaleInstance(ratio, ratio), null);
                destImg = op.filter(bi, null);
            }
            if (isFiller) {//补白
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == destImg.getWidth(null))
                    g.drawImage(destImg, 0, (height - destImg.getHeight(null)) / 2,
                            destImg.getWidth(null), destImg.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(destImg, (width - destImg.getWidth(null)) / 2, 0,
                            destImg.getWidth(null), destImg.getHeight(null),
                            Color.white, null);
                g.dispose();
                destImg = image;
            }
            ImageIO.write((BufferedImage) destImg, "JPEG", new File(dest));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据期望值缩放图像（且按比例缩放）
     *
     * @param src    源图像文件地址
     * @param result 缩放后的图像地址
     * @param height 期望最大高度
     * @param width  期望最大宽度
     */
    public static void decrease(String src, String result, int height, int width) {
        try {
            BufferedImage srcImg = ImageIO.read(new File(src)); // 读入文件
            int srcWidth = srcImg.getWidth();      //得到源图宽
            int srcHeight = srcImg.getHeight();    //得到源图长
            float scale = 0.0f;                 //缩小倍数
            //如果图片是横向，并且源图片大小比期望值要大
            if (srcWidth > srcHeight && (srcWidth > width || srcHeight > height)) {
                scale = Float.valueOf(width) / Float.valueOf(srcWidth);
                ImageUtil.scale(src, result, scale);
            }
            //如果图片是纵向，并且源图片大小比期望值要大(参数的宽高要对调一下)
            else if (srcWidth < srcHeight && (srcWidth > height || srcHeight > width)) {
                int temp = height;
                height = width;
                width = temp;
                scale = Float.valueOf(width) / Float.valueOf(srcWidth);
                ImageUtil.scale(src, result, scale);
            }
            //其他情况，按缩小倍数为1处理
            else {
                ImageUtil.scale(src, result, 1f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * 图片缩放
     */
    public static void zoomImage(String src, String dest, int w, int h) throws Exception {
        double wr = 0, hr = 0;
        File srcFile = new File(src);
        File destFile = new File(dest);
        BufferedImage bufImg = ImageIO.read(srcFile);
        Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);
        wr = w * 1.0 / bufImg.getWidth();
        hr = h * 1.0 / bufImg.getHeight();
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(bufImg, null);
        try {
            ImageIO.write((BufferedImage) Itemp, dest.substring(dest.lastIndexOf(".") + 1), destFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //    ============================ 图像切割 ============================

    /**
     * 图像切割(按指定起点坐标和宽高切割)
     *
     * @param src    源图像地址
     * @param dest   切片后的图像地址
     * @param x      目标切片起点坐标X
     * @param y      目标切片起点坐标Y
     * @param width  目标切片宽度
     * @param height 目标切片高度
     */
    public static void cut(String src, String dest, int x, int y, int width, int height) {
        try {
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(src));
            int srcWidth = bi.getWidth();        // 源图宽度
            int srcHeight = bi.getHeight();    // 源图高度
            if (srcWidth > 0 && srcHeight > 0) {
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                // 四个参数分别为图像起点坐标和宽高
                // 即: CropImageFilter(int x,int y,int width,int height)
                ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
                Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
                BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
                g.dispose();
                // 输出为文件
                ImageIO.write(tag, "JPEG", new File(dest));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像切割（指定切片的行数和列数）
     *
     * @param src     源图像地址
     * @param destDir 切片目标文件夹
     * @param rows    目标切片行数。默认2，必须是范围 [1, 20] 之内
     * @param cols    目标切片列数。默认2，必须是范围 [1, 20] 之内
     */
    public static void cutByRank(String src, String destDir, int rows, int cols) {
        try {
            if (rows <= 0 || rows > 20) rows = 2; // 切片行数
            if (cols <= 0 || cols > 20) cols = 2; // 切片列数
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(src));
            int srcWidth = bi.getWidth(); // 源图宽度
            int srcHeight = bi.getHeight(); // 源图高度
            if (srcWidth > 0 && srcHeight > 0) {
                Image img;
                ImageFilter cropFilter;
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                int destWidth = srcWidth; // 每张切片的宽度
                int destHeight = srcHeight; // 每张切片的高度
                // 计算切片的宽度和高度
                if (srcWidth % cols == 0) {
                    destWidth = srcWidth / cols;
                } else {
                    destWidth = (int) Math.floor(srcWidth / cols) + 1;
                }
                if (srcHeight % rows == 0) {
                    destHeight = srcHeight / rows;
                } else {
                    destHeight = (int) Math.floor(srcWidth / rows) + 1;
                }
                // 循环建立切片
                // 改进的想法:是否可用多线程加快切割速度
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        // 四个参数分别为图像起点坐标和宽高
                        // 即: CropImageFilter(int x,int y,int width,int height)
                        cropFilter = new CropImageFilter(j * destWidth, i * destHeight,
                                destWidth, destHeight);
                        img = Toolkit.getDefaultToolkit().createImage(
                                new FilteredImageSource(image.getSource(),
                                        cropFilter));
                        BufferedImage tag = new BufferedImage(destWidth,
                                destHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics g = tag.getGraphics();
                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                        g.dispose();
                        // 输出为文件
                        ImageIO.write(tag, "JPEG", new File(destDir + "_r" + i + "_c" + j + ".jpg"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图像切割（指定切片的宽度和高度）
     *
     * @param src        源图像地址
     * @param destDir    切片目标文件夹
     * @param destWidth  目标切片宽度。默认200
     * @param destHeight 目标切片高度。默认150
     */
    public static void cutBySquare(String src, String destDir, int destWidth, int destHeight) {
        try {
            if (destWidth <= 0) destWidth = 200; // 切片宽度
            if (destHeight <= 0) destHeight = 150; // 切片高度
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(src));
            int srcWidth = bi.getWidth(); // 源图宽度
            int srcHeight = bi.getHeight(); // 源图高度
            if (srcWidth > destWidth && srcHeight > destHeight) {
                Image img;
                ImageFilter cropFilter;
                Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
                int cols = 0; // 切片横向数量
                int rows = 0; // 切片纵向数量
                // 计算切片的横向和纵向数量
                if (srcWidth % destWidth == 0) {
                    cols = srcWidth / destWidth;
                } else {
                    cols = (int) Math.floor(srcWidth / destWidth) + 1;
                }
                if (srcHeight % destHeight == 0) {
                    rows = srcHeight / destHeight;
                } else {
                    rows = (int) Math.floor(srcHeight / destHeight) + 1;
                }
                // 循环建立切片
                // 改进的想法:是否可用多线程加快切割速度
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        // 四个参数分别为图像起点坐标和宽高
                        // 即: CropImageFilter(int x,int y,int width,int height)
                        cropFilter = new CropImageFilter(j * destWidth, i * destHeight,
                                destWidth, destHeight);
                        img = Toolkit.getDefaultToolkit().createImage(
                                new FilteredImageSource(image.getSource(),
                                        cropFilter));
                        BufferedImage tag = new BufferedImage(destWidth,
                                destHeight, BufferedImage.TYPE_INT_RGB);
                        Graphics g = tag.getGraphics();
                        g.drawImage(img, 0, 0, null); // 绘制缩小后的图
                        g.dispose();
                        // 输出为文件
                        ImageIO.write(tag, "JPEG", new File(destDir
                                + "_r" + i + "_c" + j + ".jpg"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    ============================ 图像类型转换 ============================

    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     *
     * @param src        源图像地址
     * @param formatName 包含格式非正式名称的 String：如JPG、JPEG、GIF等
     * @param dest       目标图像地址
     */
    public static void convert(String src, String formatName, String dest) {
        try {
            File f = new File(src);
            f.canRead();
            f.canWrite();
            BufferedImage srcImg = ImageIO.read(f);
            ImageIO.write(srcImg, formatName, new File(dest));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 本地图片转换Base64的方法
     *
     * @param imagePath     
     */
    public static String image2Base64(String imagePath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imagePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(Objects.requireNonNull(data));
    }

    /**
     * 将base64字符串，生成图片
     */
    public static File base64ToImage(String base64String, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                dir.mkdirs();
            }

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bfile = decoder.decodeBuffer(base64String);

            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    //    ============================ 图像色彩转换 ============================

    /**
     * 彩色转为黑白
     *
     * @param src  源图像地址
     * @param dest 目标图像地址
     */
    public static void gray(String src, String dest) {
        try {
            BufferedImage srcImg = ImageIO.read(new File(src));
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            srcImg = op.filter(srcImg, null);
            ImageIO.write(srcImg, "JPEG", new File(dest));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    ============================ 图像文字水印 ============================

    /**
     * 给图片添加文字水印
     *
     * @param pressText 水印文字
     * @param src       源图像地址
     * @param dest      目标图像地址
     * @param fontName  水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color     水印的字体颜色
     * @param fontSize  水印的字体大小
     * @param x         修正值
     * @param y         修正值
     * @param alpha     透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressText(String src, String dest, String pressText, String fontName, int fontStyle, Color color, int fontSize,
                                 int x, int y, float alpha) {
        try {
            Image srcImg = ImageIO.read(new File(src));
            int width = srcImg.getWidth(null);
            int height = srcImg.getHeight(null);
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(srcImg, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));
            // 在指定坐标绘制水印文字
            g.drawString(pressText, (width - (getLength(pressText) * fontSize))
                    / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(dest));// 输出到文件流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片添加文字水印
     *
     * @param pressText 水印文字
     * @param src       源图像地址
     * @param dest      目标图像地址
     * @param fontName  字体名称
     * @param fontStyle 字体样式
     * @param color     字体颜色
     * @param fontSize  字体大小
     * @param x         修正值
     * @param y         修正值
     * @param alpha     透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressText2(String src, String dest, String pressText, String fontName,
                                  int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
        try {
            Image srcImg = ImageIO.read(new File(src));
            int width = srcImg.getWidth(null);
            int height = srcImg.getHeight(null);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(srcImg, 0, 0, width, height, null);
            g.setColor(color);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            // 在指定坐标绘制水印文字
            g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(dest));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 给图片添加图片水印
     *
     * @param pressImg 水印图片
     * @param src      源图像地址
     * @param dest     目标图像地址
     * @param x        修正值。 默认在中间
     * @param y        修正值。 默认在中间
     * @param alpha    透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public static void pressImage(String src, String dest, String pressImg, int x, int y, float alpha) {
        try {
            Image srcImg = ImageIO.read(new File(src));
            int wideth = srcImg.getWidth(null);
            int height = srcImg.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.drawImage(srcImg, 0, 0, wideth, height, null);
            // 水印文件
            Image src_biao = ImageIO.read(new File(pressImg));
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
            // 水印文件结束
            g.dispose();
            ImageIO.write((BufferedImage) image, "JPEG", new File(dest));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 计算text的长度（一个中文算两个字符）
     *
     * @param text
     * @return
     */
    private static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }

    //    ============================ 图像裁剪 ============================

    /*
     * 根据尺寸图片居中裁剪
     */
    public static void cutCenter(String src, String dest, int width, int height) throws IOException {
        Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader) iterator.next();
        InputStream in = new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        int imageIndex = 0;
        Rectangle rect = new Rectangle((reader.getWidth(imageIndex) - width) / 2, (reader.getHeight(imageIndex) - height) / 2, width, height);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, "jpg", new File(dest));
    }

    /*
     * 根据尺寸图片居中裁剪
     */
    public static void cutCenter(byte[] btyes, String dest, int width, int height) throws IOException {
        Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader) iterator.next();
        InputStream in = new ByteArrayInputStream(btyes);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        int imageIndex = 0;
        Rectangle rect = new Rectangle((reader.getWidth(imageIndex) - width) / 2, (reader.getHeight(imageIndex) - height) / 2, width, height);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, "jpg", new File(dest));
    }

    /*
     * 根据尺寸图片居中裁剪一个正方形
     */
    public static byte[] cutSquare(byte[] btyes) throws IOException {
        InputStream in = new ByteArrayInputStream(btyes);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        Iterator iterator = ImageIO.getImageReaders(iis);
//    	 Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader) iterator.next();
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        int imageIndex = 0;
        Rectangle rect = new Rectangle((reader.getWidth(imageIndex) - reader.getHeight(imageIndex)) / 2, 0, reader.getHeight(imageIndex), reader.getHeight(imageIndex));
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(bi, "jpg", out);
        byte[] data = out.toByteArray();
        return data;
    }


    /*
     * 图片裁剪二分之一
     */
    public static void cutHalf(String src, String dest) throws IOException {
        Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader) iterator.next();
        InputStream in = new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        int imageIndex = 0;
        int width = reader.getWidth(imageIndex) / 2;
        int height = reader.getHeight(imageIndex) / 2;
        Rectangle rect = new Rectangle(width / 2, height / 2, width, height);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, "jpg", new File(dest));
    }

    /*
     * 图片裁剪通用接口
     */
    public static void cutImage(String src, String dest, int x, int y, int w, int h) throws IOException {
        Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = (ImageReader) iterator.next();
        InputStream in = new FileInputStream(src);
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(x, y, w, h);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ImageIO.write(bi, "jpg", new File(dest));
    }


    //    ============================ 测试 ============================

    public static void main(String[] args) throws IOException {

//        cutCenter("e:/abc.jpg", "e:/abc_cut.jpg", 512, 320);

        // 1-缩放图像：
        // 方法一：按比例缩放
//        ImageUtil.scale("e:/abc.jpg", "e:/abc_scale.jpg", 1.2F);
        // 方法二：按高度和宽度缩放
//        ImageUtil.scale("e:/abc.jpg", "e:/abc_scale2.jpg", 900, 300, true);

        // 2-切割图像：
        // 方法一：按指定起点坐标和宽高切割
//        ImageUtil.cut("e:/abc.jpg", "e:/abc_cut.jpg", 0, 0, 450, 300 );
        // 方法二：指定切片的行数和列数
//        ImageUtil.cut("e:/abc.jpg", "e:/", 2, 2 );
        // 方法三：指定切片的宽度和高度
//        ImageUtil.cut3("e:/abc.jpg", "e:/", 300, 300 );

        // 3-图像类型转换：
//        ImageUtil.convert("e:/abc.jpg", "GIF", "e:/abc_convert.gif");

        // 4-彩色转黑白：
//        ImageUtil.gray("e:/abc.jpg", "e:/abc_gray.jpg");

        // 5-给图片添加文字水印：
        // 方法一：
//        ImageUtil.pressText("我是水印文字","e:/abc.jpg","e:/abc_pressText.jpg","宋体",Font.BOLD,Color.white,80, 0, 0, 0.5f);
        // 方法二：
//        ImageUtil.pressText2("我也是水印文字", "e:/abc.jpg","e:/abc_pressText2.jpg", "黑体", 36, Color.white, 80, 0, 0, 0.5f);

        // 6-给图片添加图片水印：
//        ImageUtil.pressImage("e:/abc2.jpg", "e:/abc.jpg", "e:/abc_pressImage.jpg", 0, 0, 0.5f);


        String base64String = image2Base64("G:/3620.jpg");
        System.out.println(base64String);
        base64ToImage(base64String, "G:", "3621.jpg");
    }
}