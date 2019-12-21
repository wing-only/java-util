package com.wing.java.util.codegenerator;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.*;
import java.util.Properties;

import static com.wing.java.util.codegenerator.InnerUtil.initcap;


@Mojo(name = "generate")
public class CodeGenerator extends AbstractMojo {

    @Parameter(property = "project")
    private MavenProject project;

    @Parameter(property = "configFile",
            defaultValue = "${project.basedir}\\src\\main\\resources\\codeGenerator.properties")
    private String configFile;

    @Parameter(property = "baseOutputDirectory",
            defaultValue = "${project.basedir}\\src\\main\\java\\")
    private String baseOutputDirectory;
    
    @Parameter(property = "baseOutputResourcesDirectory",
    		defaultValue = "${project.basedir}\\src\\main\\resources\\")
    private String baseOutputResourcesDirectory;


    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("[codeGenerate:configFile]" + configFile);
        System.out.println("[codeGenerate:baseOutputDirectory]" + baseOutputDirectory);

        Properties properties = loadProperties();

        CodeUtil codeUtil = new CodeUtil();
        codeUtil.tableName = properties.getProperty("tableName");
        codeUtil.modelName = properties.getProperty("entityName");
        codeUtil.entityName = ((null == codeUtil.modelName) || ("".equals(codeUtil.modelName.trim()))) ? initcap(codeUtil.getFieldName(codeUtil.tableName)) : codeUtil.modelName;

        codeUtil.controllerPackage = properties.getProperty("controllerPackage");
        codeUtil.modelPackage = properties.getProperty("entityPackage");
        codeUtil.voPackage = properties.getProperty("voPackage");
        codeUtil.daoPackage = properties.getProperty("daoPackage");
        codeUtil.mapperPackage = properties.getProperty("mapperPackage");
        codeUtil.iservicePackage = properties.getProperty("iservicePackage");
        codeUtil.serviceImplPackage = properties.getProperty("serviceImplPackage");
        codeUtil.dubboConfig = properties.getProperty("dubboConfig");

        codeUtil.driver = properties.getProperty("driver");
        codeUtil.url = properties.getProperty("url");
        codeUtil.username = properties.getProperty("username");
        codeUtil.password = properties.getProperty("password");
        codeUtil.dbName = properties.getProperty("dbName");

        codeUtil.baseOutputPath = baseOutputDirectory;
        codeUtil.baseOutputResourcesPath = baseOutputResourcesDirectory;

        codeUtil.generatorCode();
    }

    /**
     * 加载属性文件
     *
     * @return
     */
    private Properties loadProperties() {
        Properties properties = new Properties();

//        InputStream inputStream = CodeGenerate.class.getClassLoader().getResourceAsStream("codeGenerate.properties");
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(new File(configFile));
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[codeGenerate:properties]" + properties);
        return properties;
    }
}