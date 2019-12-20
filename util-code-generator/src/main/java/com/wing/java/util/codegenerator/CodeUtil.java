package com.wing.java.util.codegenerator;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wing.java.util.codegenerator.InnerUtil.*;

@SuppressWarnings("all")
public class CodeUtil {

	/**
	 * 需要指定的参数
	 */
	String tableName;
	String modelName;

	//代码生成位置，包路径
	String controllerPackage;
	String iservicePackage;
	String serviceImplPackage;
	String daoPackage;
	String mapperPackage;
	String modelPackage;
	String voPackage;
	String dubboConfig;
	
	//代码输出基本路径
	String baseOutputPath;
	String baseOutputResourcesPath;

	/**
	 * 数据库连接属性
	 */
	String driver;
	String url;
	String username;
	String password;
	String dbName;

	//	========================= 以下为默认配置 =========================

	// controller模板文件
	String ControllerTemplateFile = "ControllerTemplate.java";
	//service模板
	String iserviceTemplateFile = "IServceTemplate.java";
	//serviceImpl模板
	String serviceImplTemplateFile = "ServiecImplTemplate.java";
    // dao模板文件
	String DaoTemplateFile = "DaoTemplate.java";
	// mapper模板文件
	String MapperTemplateFile = "MapperTemplate.xml";
	// model模板文件
	String ModelTemplateFile = "ModelTemplate.java";
	// req模板文件  时间条件
	String QryReqVoTemplateFile = "QryReqVoTemplate.java";
	// resp模板文件  枚举
	String QryRespVoTemplateFile = "QryRespVoTemplate.java";

	//dubbo模板
	String dubboConfigFile = "dubbo.txt";
	
	//临时变量
    String controllerTemplate = "";
	String IserviceTemplate = "";
	String serviceImplTemplate = "";
	String daoTemplate = "";
	String modelTemplate = "";
	String tableComment="";
	String entityName = "";
	String mappingTemplate="";
	String reqvoTemplate="";
	String respvoTemplate="";

	String dubboConfigTemplate = "";

	//数据库字段类型与Java类型映射
	Map<String, String> jdbc2javatypes = new HashMap<String, String>();
	Map<String, String> sql2jdbctypes = new HashMap<String, String>();
	Map<String, String> javatypesfullname = new HashMap<String, String>();
	
	// model属性
	List<Entitys> entityslist = new ArrayList<Entitys>();
	// req属性
	List<Entitys> reqentityslist = new ArrayList<Entitys>();
	// resp属性
	List<Entitys> respentityslist = new ArrayList<Entitys>();
	
	List<String> datetypes = new ArrayList<String>();
	Entitys pk = null;
	String enter = getSeparator();
    String currenttime = getNow();
	
	void generatorCode() {
		try {
			init();
			readTableInfo();
			//
			getController();
			getService();
			getServiceImpl();
			getDao();
			getMapping();
			getModel();
			getVo();
			getDubboConfig();
			// 写文件
			writeFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化
	 * 1、表字段类型与Java类型映射
	 * 2、加载controlle、mapper目标
	 * @throws Exception
	 */
	void init() throws Exception {

		sql2jdbctypes.put("BIGINT", "BIGINT");
		sql2jdbctypes.put("CHAR", "CHAR");
		sql2jdbctypes.put("DATE", "DATE");
		sql2jdbctypes.put("DATETIME", "TIMESTAMP");
		sql2jdbctypes.put("TIMESTAMP", "TIMESTAMP");
		sql2jdbctypes.put("DECIMAL", "DECIMAL");
		sql2jdbctypes.put("DOUBLE", "DOUBLE");
		sql2jdbctypes.put("ENUM", "CHAR");
		sql2jdbctypes.put("FLOAT", "REAL");
		sql2jdbctypes.put("INT", "INTEGER");
		sql2jdbctypes.put("TINYINT", "TINYINT");
		sql2jdbctypes.put("TEXT", "LONGVARCHAR");
		sql2jdbctypes.put("TIME", "TIME");
		sql2jdbctypes.put("VARCHAR", "VARCHAR");
		sql2jdbctypes.put("YEAR", "DATE");

		jdbc2javatypes.put("CHAR", "String");
		jdbc2javatypes.put("VARCHAR", "String");
		jdbc2javatypes.put("BIGINT", "Long");
		jdbc2javatypes.put("TINYINT", "Byte");
		jdbc2javatypes.put("DATE", "Date");
		jdbc2javatypes.put("DOUBLE", "Double");
		jdbc2javatypes.put("INTEGER", "Integer");
		jdbc2javatypes.put("TIMESTAMP", "Date");
		jdbc2javatypes.put("DECIMAL", "BigDecimal");
		jdbc2javatypes.put("ENUM", "String");
		jdbc2javatypes.put("FLOAT", "Float");
		jdbc2javatypes.put("LONGVARCHAR", "String");
		jdbc2javatypes.put("BLOB", "byte[]");
		jdbc2javatypes.put("LONGBLOB", "byte[]");
		jdbc2javatypes.put("LONGVARBINARY", "byte[]");
		jdbc2javatypes.put("VARBINARY", "byte[]");

		javatypesfullname.put("String", "java.lang.String");
		javatypesfullname.put("Integer", "java.lang.Integer");
		javatypesfullname.put("Long", "java.lang.Long");
		javatypesfullname.put("Double", "java.lang.Double");
		javatypesfullname.put("Float", "java.lang.Float");

		datetypes.add("DATE");
		datetypes.add("DATETIME");
		datetypes.add("TIMESTAMP");

		StringBuilder sb =null;

		sb = new StringBuilder("");
		BufferedReader bReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(ControllerTemplateFile)));
		String line = null;
		while ((line = bReader.readLine()) != null) {
			sb.append(line).append(enter);
		}
		controllerTemplate = sb.toString();
		bReader.close();


		sb = new StringBuilder("");
		bReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(MapperTemplateFile)));
		line = null;
		while ((line = bReader.readLine()) != null) {
			sb.append(line).append(enter);
		}
		mappingTemplate = sb.toString();
		bReader.close();


		sb = new StringBuilder("");
		bReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(DaoTemplateFile)));
		line = null;
		while ((line = bReader.readLine()) != null) {
			sb.append(line).append(enter);
		}
		daoTemplate = sb.toString();
		bReader.close();

		sb = new StringBuilder("");
		bReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(ModelTemplateFile)));
		line = null;
		while ((line = bReader.readLine()) != null) {
			sb.append(line).append(enter);
		}
		modelTemplate = sb.toString();
		bReader.close();


		sb = new StringBuilder("");
		bReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(QryReqVoTemplateFile)));
		line = null;
		while ((line = bReader.readLine()) != null) {
			sb.append(line).append(enter);
		}
		reqvoTemplate = sb.toString();
		bReader.close();


		sb = new StringBuilder("");
		bReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(QryRespVoTemplateFile)));
		line = null;
		while ((line = bReader.readLine()) != null) {
			sb.append(line).append(enter);
		}
		respvoTemplate = sb.toString();
		bReader.close();

		sb = new StringBuilder("");
		bReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(serviceImplTemplateFile)));
		line = null;
		while ((line = bReader.readLine()) != null) {
			sb.append(line).append(enter);
		}
		serviceImplTemplate = sb.toString();
		bReader.close();

		sb = new StringBuilder("");
		bReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(iserviceTemplateFile)));
		line = null;
		while ((line = bReader.readLine()) != null) {
			sb.append(line).append(enter);
		}
		IserviceTemplate = sb.toString();
		bReader.close();

		sb = new StringBuilder("");
		bReader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(dubboConfigFile)));
		line = null;
		while ((line = bReader.readLine()) != null) {
			sb.append(line).append(enter);
		}
		dubboConfigTemplate = sb.toString();
		bReader.close();

		File file = new File(baseOutputPath);
		if(!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 从数据读取表结构信息
	 */
	private void readTableInfo() throws Exception {
		String sql = "SELECT lower(column_name) column_name,UPPER(data_type) data_type,column_comment,column_key,is_nullable ,character_maximum_length  ,(SELECT table_comment FROM information_schema.TABLES WHERE table_schema = '%s' AND table_name = '%s') table_comment FROM information_schema.columns WHERE table_schema = '%s' AND table_name = '%s'" ;
		
		sql = String.format(sql, dbName, tableName, dbName, tableName);
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, username, password);
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			String column_name = rs.getString("column_name");
			String data_type = rs.getString("data_type");
			String column_comment = rs.getString("column_comment");
			String column_key = rs.getString("column_key");
			String is_nullable = rs.getString("is_nullable");
			String character_maximum_length = rs.getString("character_maximum_length");
			tableComment = rs.getString("table_comment");
			
			Entitys en = new Entitys();
			en.setColumnName(column_name);
			en.setDataType(data_type);
			en.setComment(column_comment);
			if(character_maximum_length!=null && !"longblob".equalsIgnoreCase(data_type)){
				en.setMaxlength(Long.parseLong(character_maximum_length));
			}
			if ( column_key.contains("PRI")) {
				en.setPk(true);
				pk = en;
			}
			
			if("NO".equals(is_nullable)){
				en.setIsnullable(false);
			}
			
			entityslist.add(en);
			if (datetypes.contains(en.getDataType())) {
				en = new Entitys();
				en.setComment(column_comment + "_开始时间");
				en.setColumnName(column_name + "_start");
				en.setDataType(data_type);
				en.setIsadd(true);
				en.setOrgcolumnName(column_name);
				reqentityslist.add(en);
				en = new Entitys();
				en.setComment(column_comment + "_结束时间");
				en.setColumnName(column_name + "_end");
				en.setDataType(data_type);
				en.setOrgcolumnName(column_name);
				en.setIsadd(true);
				reqentityslist.add(en);
			}

			if ( en.getColumnName().endsWith("_enum") ) {
				en = new Entitys();
				en.setComment(column_comment + "_name");
				en.setColumnName(column_name + "_name");
				en.setDataType(data_type);
				en.setOrgcolumnName(column_name);
				en.setIsadd(true);
				respentityslist.add(en);
			}
		}

		for (Entitys entitys : entityslist) {
			entitys.setFieldName(getFieldName(entitys.getColumnName()));
			entitys.setDataType(covertSqltype2JdbcType(entitys.getDataType()));
			entitys.setFieldType(getFieldType(entitys.getDataType()));
		}
		if(reqentityslist != null && reqentityslist.size() > 0) {
			for (Entitys entitys : reqentityslist) {
				entitys.setFieldName(getFieldName(entitys.getColumnName()));
				entitys.setDataType(covertSqltype2JdbcType(entitys.getDataType()));
				entitys.setFieldType(getFieldType(entitys.getDataType()));
			}
		}
		if(respentityslist != null && respentityslist.size() > 0) {
			for (Entitys entitys : respentityslist) {
				entitys.setFieldName(getFieldName(entitys.getColumnName()));
				entitys.setDataType(covertSqltype2JdbcType(entitys.getDataType()));
				entitys.setFieldType(getFieldType(entitys.getDataType()));
			}
		}
		checkEntityslist();
	}

	/**
	 * 生成文件
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	private void writeFile() throws FileNotFoundException, UnsupportedEncodingException {
		
		PrintWriter pw = null;
		
		pw = new PrintWriter(createFile((baseOutputPath + modelPackage + ".").replaceAll("\\.", "\\\\")+ this.entityName + ".java"), "utf-8");
		pw.write(modelTemplate);
		pw.flush();
		
		pw = new PrintWriter(createFile((baseOutputPath + daoPackage + ".").replaceAll("\\.", "\\\\") + this.entityName + "Dao.java"), "utf-8");
		pw.write(daoTemplate);
		pw.flush();
		
		pw = new PrintWriter(createFile((baseOutputPath + mapperPackage + ".").replaceAll("\\.", "\\\\")+ this.entityName + "Dao.xml"), "utf-8");
		pw.write(mappingTemplate);
		pw.flush();
		pw = new PrintWriter(createFile((baseOutputPath + controllerPackage + ".").replaceAll("\\.", "\\\\") + this.entityName + "Controller.java"), "utf-8");
		pw.write(controllerTemplate);
		pw.flush();
		pw = new PrintWriter(createFile((baseOutputPath + voPackage + ".").replaceAll("\\.", "\\\\")+ this.entityName + "QryReqVo.java"), "utf-8");
		pw.write(reqvoTemplate);
		pw.flush();
		pw = new PrintWriter(createFile((baseOutputPath + voPackage + ".").replaceAll("\\.", "\\\\")+ this.entityName + "QryRespVo.java"), "utf-8");
		pw.write(respvoTemplate);
		pw.flush();
		
		pw = new PrintWriter(createFile((baseOutputPath + iservicePackage + ".").replaceAll("\\.", "\\\\")+"I"+ this.entityName + "Service.java"), "utf-8");
		pw.write(IserviceTemplate);
		pw.flush();
		pw = new PrintWriter(createFile((baseOutputPath + serviceImplPackage + ".").replaceAll("\\.", "\\\\")+ this.entityName + "ServiceImpl.java"), "utf-8");
		pw.write(serviceImplTemplate);
		pw.flush();
		pw.close();
		
		pw = new PrintWriter(createFile((baseOutputResourcesPath + dubboConfig + ".").replaceAll("\\.", "\\\\")+ "dubbo.txt"), "utf-8");
		pw.write(dubboConfigTemplate);
		pw.flush();
		pw.close();
	}

	private void getDao() {
		
		daoTemplate = daoTemplate.replaceAll("@daoPackage@", daoPackage);
		daoTemplate = daoTemplate.replaceAll("@modelPackage@", modelPackage);
		daoTemplate = daoTemplate.replaceAll("@entityName@", entityName);
		daoTemplate = daoTemplate.replaceAll("@voPackage@", voPackage);
		
	}

	/**
	 * 生成reqVo和respVo
	 */
	private void getVo() {
        String qryReqVoContents = processReqVoAttrs();
        
        reqvoTemplate = reqvoTemplate.replaceAll("@qryReqVoContent@", qryReqVoContents);
        reqvoTemplate = reqvoTemplate.replaceAll("@tableComment@", tableComment);
        reqvoTemplate = reqvoTemplate.replaceAll("@modelName@", entityName);
        reqvoTemplate = reqvoTemplate.replaceAll("@voPackage@", voPackage);
        reqvoTemplate = reqvoTemplate.replaceAll("@modelPackage@", modelPackage);
        
        String qryRespVoContents = processRespVoAttrs();
        
        respvoTemplate = respvoTemplate.replaceAll("@qryRespVoContent@", qryRespVoContents);
        respvoTemplate = respvoTemplate.replaceAll("@tableComment@", tableComment);
        respvoTemplate = respvoTemplate.replaceAll("@modelName@", entityName);
        respvoTemplate = respvoTemplate.replaceAll("@voPackage@", voPackage);
        respvoTemplate = respvoTemplate.replaceAll("@modelPackage@", modelPackage);
		
	}
	
    /**
     * 功能：生成reqVo所有属性
     */
    private String processReqVoAttrs() {
    	StringBuffer sb = new StringBuffer();
    	
    	if(reqentityslist != null && reqentityslist.size() > 0) {
    		for (Entitys entitys : reqentityslist) {
    			sb.append("\t/**\r\n");
    			sb.append("\t* ").append(entitys.getComment()).append("\r\n");
    			sb.append("\t*/ \r\n");
    			sb.append("\t@ApiModelProperty(name=\""+entitys.getFieldName()+"\", value=\""+entitys.getComment()+"\", required="+ !entitys.isIsnullable()   +") \r\n");
    			sb.append("\tprivate " + entitys.getFieldType() + " " + entitys.getFieldName() + ";\r\n\r\n");
    		}
    	}
        
        return sb.toString();
    }
    /**
     * 功能：生成respVo所有属性
     */
    private String processRespVoAttrs() {
    	StringBuffer sb = new StringBuffer();
    	
    	if(respentityslist != null && respentityslist.size() > 0) {
    		for (Entitys entitys : respentityslist) {
    			sb.append("\t/**\r\n");
    			sb.append("\t* ").append(entitys.getComment()).append("\r\n");
    			sb.append("\t*/ \r\n");
    			sb.append("\t@ApiModelProperty(name=\""+entitys.getFieldName()+"\", value=\""+entitys.getComment()+"\", required="+ !entitys.isIsnullable()   +") \r\n");
    			sb.append("\tprivate " + entitys.getFieldType() + " " + entitys.getFieldName() + ";\r\n\r\n");
    		}
    	}
    	
    	return sb.toString();
    }

	private void getService() {
	    
	    IserviceTemplate = IserviceTemplate.replaceAll("@ModelName@", entityName);
	    IserviceTemplate = IserviceTemplate.replaceAll("@modelPackage@", modelPackage);
	    
//	    IserviceTemplate = IserviceTemplate.replaceAll("@pkType@", pk.getFieldType());
	    IserviceTemplate = IserviceTemplate.replaceAll("@IServicePackage@", iservicePackage);
	    IserviceTemplate = IserviceTemplate.replaceAll("@voPackage@", voPackage);
	    IserviceTemplate = IserviceTemplate.replaceAll("@modelName@", modelName);
	}

	private void getServiceImpl() {
	    
	    serviceImplTemplate = serviceImplTemplate.replaceAll("@ModelName@", entityName);
	    serviceImplTemplate = serviceImplTemplate.replaceAll("@modelPackage@", modelPackage);
	    serviceImplTemplate = serviceImplTemplate.replaceAll("@voPackage@", voPackage);
	    serviceImplTemplate = serviceImplTemplate.replaceAll("@daoPackage@", daoPackage);
//	    serviceImplTemplate = serviceImplTemplate.replaceAll("@pkType@", pk.getFieldType());
	    serviceImplTemplate = serviceImplTemplate.replaceAll("@ServiceImplPackage@", serviceImplPackage);
	    serviceImplTemplate = serviceImplTemplate.replaceAll("@modelName@", modelName);
	    serviceImplTemplate = serviceImplTemplate.replaceAll("@variableName@", toLowerCaseFirstOne(modelName));
	    serviceImplTemplate= serviceImplTemplate.replaceAll("@IServicePackage@", iservicePackage);
	}
	
	private void getDubboConfig() {
		dubboConfigTemplate = dubboConfigTemplate.replaceAll("@ModelName@", entityName);
		dubboConfigTemplate = dubboConfigTemplate.replaceAll("@ServiceImplPackage@", serviceImplPackage);
		dubboConfigTemplate = dubboConfigTemplate.replaceAll("@variableName@", toLowerCaseFirstOne(modelName));
		dubboConfigTemplate= dubboConfigTemplate.replaceAll("@IServicePackage@", iservicePackage);
	}

    private void getController() {
        String  serviceComment = tableComment;
        if(tableComment.endsWith("表")) {
            serviceComment=tableComment.substring(0,tableComment.length()-1)+"服务";
        }
        
        controllerTemplate = controllerTemplate.replaceAll("@serviceComment@", serviceComment);
        controllerTemplate = controllerTemplate.replaceAll("@pkType@", pk.getFieldType());
        controllerTemplate = controllerTemplate.replaceAll("@controllerPackage@", controllerPackage);
        controllerTemplate = controllerTemplate.replaceAll("@tableComment@", tableComment);
        controllerTemplate = controllerTemplate.replaceAll("@ModelName@", entityName);
        controllerTemplate = controllerTemplate.replaceAll("@modelName@", toLowerCaseFirstOne(entityName));
        controllerTemplate = controllerTemplate.replaceAll("@modelPackage@", modelPackage);
        controllerTemplate = controllerTemplate.replaceAll("@voPackage@", voPackage);
        controllerTemplate = controllerTemplate.replaceAll("@IService@", "I"+ entityName +"Service");
        controllerTemplate = controllerTemplate.replaceAll("@IServicePackage@", iservicePackage);
        controllerTemplate = controllerTemplate.replaceAll("@iService@", toLowerCaseFirstOne(entityName +"Service"));
	}


	private void getMapping() {
		String BaseResultMapline = "\t\t<result column=\"%s\" property=\"%s\" jdbcType=\"%s\" />";
		String BaseResultMapline2 = "\t\t<result column=\"%s\" property=\"%s\" jdbcType=\"%s\" javaType=\"byte[]\" typeHandler=\"org.apache.ibatis.type.BlobTypeHandler\" />";

		//针对字符串
		String whereline = "\t\t\t<if test=\"%s != null and %s !='' \"> \r\n " + "\t\t\t\tAND %s  \r\n" + "\t\t\t</if> ";
		String wherelineOfPlatAmdCompany = "\t\t\tAND %s  \r\n";
		String insertDyline = "\t\t\t<if test=\"%s != null and %s !='' \"> \r\n " + "\t\t\t\t%s,  \r\n" + "\t\t\t</if> ";
		String wherelineForParam = "\t\t\t\t<if test=\"param.%s != null and param.%s !='' \"> \r\n " + "\t\t\t\t\tand %s  \r\n" + "\t\t\t\t</if> ";
		String whereForParamOfPlatAmdCompany = "\t\t\t\tand %s  \r\n";
		//针对非字符串
		String whereline2 = "\t\t\t<if test=\"%s != null \"> \r\n " + "\t\t\t\tAND %s  \r\n" + "\t\t\t</if> ";
		String insertDyline2 = "\t\t\t<if test=\"%s != null \"> \r\n " + "\t\t\t\t%s,  \r\n" + "\t\t\t</if> ";
		String wherelineForParam2 = "\t\t\t\t<if test=\"param.%s != null \"> \r\n " + "\t\t\t\t\tAND %s  \r\n" + "\t\t\t\t</if> ";
		String insertline = "\t\t#{%s,jdbcType=%s},";
		//更新全部列
		String updateline = "\t\t\t%s = #{%s,jdbcType=%s},";
		String updatelineForAllColumn = "\t\t\t%s = #{entity.%s,jdbcType=%s},";
		//更新部分列（字符串）
		String updateDynamicColumnsline = "\t\t\t<if test=\"%s != null and %s !='' \"> %s = #{%s,jdbcType=%s}, </if>";
		String insertDynamicColumnsline = "\t\t\t<if test=\"%s != null and %s !='' \"> #{%s,jdbcType=%s}, </if>";
		String updateDynamicEntityColumnsline = "\t\t\t<if test=\"entity.%s != null and entity.%s !='' \"> %s = #{entity.%s,jdbcType=%s}, </if>";
		//更新部分列（非字符串）
		String updateDynamicColumnsline2 = "\t\t\t<if test=\"%s != null \"> %s = #{%s,jdbcType=%s}, </if>";
		String insertDynamicColumnsline2 = "\t\t\t<if test=\"%s != null \"> #{%s,jdbcType=%s}, </if>";
		String updateDynamicEntityColumnsline2 = "\t\t\t<if test=\"entity.%s != null \"> %s = #{entity.%s,jdbcType=%s}, </if>";
		String ByIdline = "where %s = #{%s,jdbcType=%s}";
		String ById = String.format(ByIdline, pk.getColumnName(), pk.getFieldName(), pk.getDataType());

		
		String entityClasspath = modelPackage.concat(".").concat(this.entityName);
		String modeclasspath = daoPackage.concat(".").concat(this.entityName).concat("Dao");
		String reqModeclasspath = voPackage.concat(".").concat(this.entityName).concat("QryReqVo");
		String respModeclasspath = voPackage.concat(".").concat(this.entityName).concat("QryRespVo");
		StringBuilder columns = new StringBuilder();
		StringBuilder insertAllColumns = new StringBuilder();
		StringBuilder BaseResultMapColumns = new StringBuilder();
		StringBuilder wherecolumns = new StringBuilder();
		StringBuilder whereParamColumns = new StringBuilder();
		StringBuilder insert = new StringBuilder();
		StringBuilder insertColumns = new StringBuilder();
		StringBuilder insertDynamicColumns = new StringBuilder();
		
		StringBuilder updateColumns = new StringBuilder();
		StringBuilder updateAllColumnEntity = new StringBuilder();
		StringBuilder updateDynamicColumns = new StringBuilder();
		StringBuilder updateColumnEntity = new StringBuilder();
		
		for (Entitys entitys : entityslist) {
			String columnName = entitys.getColumnName();
			String dataType = entitys.getDataType();
			String fieldName = entitys.getFieldName();
			String fieldType = entitys.getFieldType();
			String orgcolumnName = entitys.getOrgcolumnName();
			//
			String wherecolumnscompare = "=";

			if(!"Date".equals(fieldType)){
				String wcs3 = columnName + wherecolumnscompare + "#{" + fieldName + ",jdbcType="+dataType+"}";
				String wcs4 = columnName + wherecolumnscompare + "#{param." + fieldName + ",jdbcType="+dataType+"}";
				if("String".equals(fieldType)) {
					if("platformid".equalsIgnoreCase(fieldName) || "companycode".equalsIgnoreCase(fieldName)) {
						wherecolumns.append(String.format(wherelineOfPlatAmdCompany,wcs3)).append(enter);
						whereParamColumns.append(String.format(whereForParamOfPlatAmdCompany, wcs4)).append(enter);
					}else {
						wherecolumns.append(String.format(whereline,fieldName, fieldName, wcs3)).append(enter);
						whereParamColumns.append(String.format(wherelineForParam,fieldName, fieldName, wcs4)).append(enter);
					}
				}else {
					wherecolumns.append(String.format(whereline2,fieldName, wcs3)).append(enter);
					whereParamColumns.append(String.format(wherelineForParam2,fieldName, wcs4)).append(enter);
				}
			}else{
//			    if (entitys.isadd) {
//			        if ("Date".equals(fieldType)) {
//	                    wherecolumnscompare = " <![CDATA[ >= ]]> ";
//	                }
//	                if ("Date".equals(fieldType)) {
//	                    wherecolumnscompare = " <![CDATA[ <= ]]> ";
//	                }
					wherecolumns.append("\t\t\t<if test=\""+fieldName+" != null \"> \r\n " + "\t\t\t\tAND "+columnName+" =  #{"+fieldName+",jdbcType="+dataType+"}  \r\n" + "\t\t\t</if>  \r\n");
                    wherecolumns.append("\t\t\t<if test=\""+fieldName+"Start != null \"> \r\n " + "\t\t\t\tAND "+columnName+" <![CDATA[ >= ]]> #{"+fieldName+"Start,jdbcType="+dataType+"}  \r\n" + "\t\t\t</if>  \r\n");
                    wherecolumns.append("\t\t\t<if test=\""+fieldName+"End != null \"> \r\n " + "\t\t\t\tAND "+columnName+" <![CDATA[ <= ]]> #{"+fieldName+"End,jdbcType="+dataType+"}  \r\n" + "\t\t\t</if>  \r\n");
                    whereParamColumns.append("\t\t\t\t<if test=\"param."+fieldName+" != null \"> \r\n " + "\t\t\t\t\tAND "+columnName+" = #{param."+fieldName+",jdbcType="+dataType+"}  \r\n" + "\t\t\t\t</if>  \r\n");
                    whereParamColumns.append("\t\t\t\t<if test=\"param."+fieldName+"Start != null \"> \r\n " + "\t\t\t\t\tAND "+columnName+" <![CDATA[ >= ]]> #{param."+fieldName+"Start,jdbcType="+dataType+"}  \r\n" + "\t\t\t\t</if>  \r\n");
                    whereParamColumns.append("\t\t\t\t<if test=\"param."+fieldName+"End != null \"> \r\n " + "\t\t\t\t\tAND "+columnName+" <![CDATA[ <= ]]> #{param."+fieldName+"End,jdbcType="+dataType+"}  \r\n" + "\t\t\t\t</if>  \r\n");
	                //"\t\t<if test=\"%s != null\"> \r\n " + "\t     and %s  \r\n" + "\t\t</if> ";
//	                String wcs3 = orgcolumnName + wherecolumnscompare + "#{" + fieldName + ",jdbcType="+dataType+"}";
//	                String wcs4 = columnName + wherecolumnscompare + "#{param." + fieldName + ",jdbcType="+dataType+"}";
//	                if("String".equals(fieldType)) {
//						wherecolumns.append(String.format(whereline,fieldName, fieldName, wcs3)).append(enter);
//						whereParamColumns.append(String.format(wherelineForParam,fieldName, fieldName, wcs4)).append(enter);
//					}else {
//						wherecolumns.append(String.format(whereline2,fieldName, wcs3)).append(enter);
//						whereParamColumns.append(String.format(wherelineForParam2,fieldName, wcs4)).append(enter);
//					}
//			    }else {
//			        String wcs3 = columnName + wherecolumnscompare + "#{" + fieldName + ",jdbcType="+dataType+"}";
//			        String wcs4 = columnName + wherecolumnscompare + "#{param." + fieldName + ",jdbcType="+dataType+"}";
//			        if("String".equals(fieldType)) {
//						wherecolumns.append(String.format(whereline,fieldName, fieldName, wcs3)).append(enter);
//						whereParamColumns.append(String.format(wherelineForParam,fieldName, fieldName, wcs4)).append(enter);
//					}else {
//						wherecolumns.append(String.format(whereline2,fieldName, wcs3)).append(enter);
//						whereParamColumns.append(String.format(wherelineForParam2,fieldName, wcs4)).append(enter);
//					}
//			    }
			}

			//
			if (!entitys.isadd){
				//
				if(!entitys.isIsadd()){
				    //jdbc
				    if(entitys.getDataType().equalsIgnoreCase("date")) {
				        columns.append("DATE_FORMAT("+columnName+",'%Y-%m-%d') "+columnName).append(",");
				    }else if(entitys.getDataType().equalsIgnoreCase("timestamp")) {
				        columns.append("DATE_FORMAT("+columnName+",'%Y-%m-%d %H:%i:%s') "+columnName).append(",");
				    }else if(entitys.getDataType().equalsIgnoreCase("time")) {
				    	columns.append("DATE_FORMAT("+columnName+",'%H:%i:%s') "+columnName).append(",");
				    }else {
				        columns.append(columnName).append(",");
				    }
				    insertAllColumns.append(columnName).append(",");
				}
				//
				String basemapline = BaseResultMapline;
				if(entitys.getDataType().equals("BLOB")){
					basemapline =BaseResultMapline2;
				}
				BaseResultMapColumns.append(String.format(basemapline, columnName, fieldName, dataType)).append(enter);
				
				if("c_date".equalsIgnoreCase(entitys.getColumnName()) || "c_time".equalsIgnoreCase(entitys.getColumnName())){
					insert.append("\t\tnow(),").append(enter);
					insertColumns.append(String.format(insertDyline2,fieldName,columnName)).append(enter);
					insertDynamicColumns.append("\t\t\tnow(),").append(enter);
				}else if("version".equalsIgnoreCase(entitys.getColumnName())){
					insert.append("\t\t1,").append(enter);
					insertColumns.append(String.format(insertDyline2,fieldName,columnName)).append(enter);
					insertDynamicColumns.append("\t\t\t1,").append(enter);
			    }
				else if("u_date".equalsIgnoreCase(entitys.getColumnName()) || "u_time".equalsIgnoreCase(entitys.getColumnName())){
			    	insert.append("\t\tnull,").append(enter);
			    	insertColumns.append(String.format(insertDyline2,fieldName,columnName)).append(enter);
			    	insertDynamicColumns.append("\t\t\tnull,").append(enter);
			    }else{
			    	insert.append(String.format(insertline, fieldName, dataType)).append(enter);
	                if("String".equals(fieldType)) {
	                	insertColumns.append(String.format(insertDyline,fieldName, fieldName,columnName)).append(enter);
	                	insertDynamicColumns.append(String.format(insertDynamicColumnsline, fieldName,fieldName,fieldName,dataType)).append(enter);
					}else {
						insertColumns.append(String.format(insertDyline2,fieldName,columnName)).append(enter);
						insertDynamicColumns.append(String.format(insertDynamicColumnsline2, fieldName,fieldName,dataType)).append(enter);
					}
			    }
				
				//
				if(!entitys.getColumnName().equalsIgnoreCase("id")) {
					if("u_date".equalsIgnoreCase(entitys.getColumnName())){
						updateColumns.append("\t\t\tu_date = now(),").append(enter);
						updateAllColumnEntity.append("\t\t\tu_date = now(),").append(enter);
					}else if("u_time".equalsIgnoreCase(entitys.getColumnName())){
						updateColumns.append("\t\t\tu_time = now(),").append(enter);
						updateAllColumnEntity.append("\t\t\tu_time = now(),").append(enter);
					}
/*					else if("version".equalsIgnoreCase(entitys.getColumnName())){
				    	updateColumns.append("\t\t\tversion = #{versionPlus,jdbcType=INTEGER},").append(enter);
				    	updateAllColumnEntity.append("\t\t\tversion = #{versionPlus,jdbcType=INTEGER},").append(enter);
					}*/
					else{
						updateColumns.append(String.format(updateline, columnName, fieldName, dataType)).append(enter);
						updateAllColumnEntity.append(String.format(updatelineForAllColumn, columnName, fieldName, dataType)).append(enter);
					}
				}
			    
			    /*if(fieldName.equalsIgnoreCase("version")) {
			        updateDynamicColumns.append("\t\t\tversion = #{versionPlus,jdbcType=INTEGER},").append(enter);
			        updateColumnEntity.append("\t\t\tversion = #{versionPlus,jdbcType=INTEGER},").append(enter);
			    }else */
			    if(!fieldName.equalsIgnoreCase("id")) {
			    	if(fieldName.equals("udate")) {
			    		updateDynamicColumns.append("\t\t\tu_date = now(),").append(enter);
			    		updateColumnEntity.append("\t\t\tu_date = now(),").append(enter);
			    	}else if(fieldName.equals("utime")) {
			    		updateDynamicColumns.append("\t\t\tu_time = now(),").append(enter);
			    		updateColumnEntity.append("\t\t\tu_time = now(),").append(enter);
			    	}else {
			    		if("String".equals(fieldType)) {
			    			updateDynamicColumns.append(String .format(updateDynamicColumnsline, fieldName,fieldName,columnName,fieldName,dataType)).append(enter);
			    			updateColumnEntity.append(String .format(updateDynamicEntityColumnsline, fieldName,fieldName,columnName,fieldName,dataType)).append(enter);
			    		}else {
			    			updateDynamicColumns.append(String .format(updateDynamicColumnsline2, fieldName,columnName,fieldName,dataType)).append(enter);
			    			updateColumnEntity.append(String .format(updateDynamicEntityColumnsline2, fieldName,columnName,fieldName,dataType)).append(enter);
			    		}
			    	}
			    }
			}
		}
		columns.deleteCharAt(columns.length() - 1);
		insertAllColumns.deleteCharAt(insertAllColumns.length() - 1);
		insert.deleteCharAt(insert.length() - 3);
		insertColumns.deleteCharAt(insertColumns.length() - 3);
//		insertDynamicColumns.deleteCharAt(insert.length() - 3);
//		if(",".equals(updateDynamicColumns.substring(0, updateDynamicColumns.length() - 3))) {//最后一个是逗号
//			updateDynamicColumns.deleteCharAt(updateDynamicColumns.length()-3);
//		}
//		if(",".equals(insertDynamicColumns.substring(0, insertDynamicColumns.length() - 3))) {//最后一个是逗号
//			insertDynamicColumns.deleteCharAt(insertDynamicColumns.length()-3);
//		}
//		if(",".equals(updateColumnEntity.substring(0, updateColumnEntity.length() - 3))) {//最后一个是逗号
//			updateColumnEntity.deleteCharAt(updateColumnEntity.length()-3);
//		}
		updateColumns.deleteCharAt(updateColumns.length() - 3);
		updateAllColumnEntity.deleteCharAt(updateAllColumnEntity.length() - 3);
		
		mappingTemplate = mappingTemplate.replaceAll("@pkJavaType@", getPkJavaFullname(pk));
		mappingTemplate = mappingTemplate.replaceAll("@ById@", ById);
		mappingTemplate = mappingTemplate.replaceAll("@updateColumns@", updateColumns.toString());
		mappingTemplate = mappingTemplate.replaceAll("@tableName@", tableName);
//		mappingTemplate = mappingTemplate.replaceAll("@BaseResultMapColumns@", BaseResultMapColumns.toString());
		mappingTemplate = mappingTemplate.replaceAll("@columns@", columns.toString());
		mappingTemplate = mappingTemplate.replaceAll("@insertAllColumns@", insertAllColumns.toString());
		mappingTemplate = mappingTemplate.replaceAll("@modeclasspath@", modeclasspath);
		mappingTemplate = mappingTemplate.replaceAll("@entityClasspath@", entityClasspath);
		mappingTemplate = mappingTemplate.replaceAll("@reqModeclasspath@", reqModeclasspath);
		mappingTemplate = mappingTemplate.replaceAll("@respModeclasspath@", respModeclasspath);
		mappingTemplate = mappingTemplate.replaceAll("@wherecolumns@", wherecolumns.toString());
		mappingTemplate = mappingTemplate.replaceAll("@whereParamColumns@", whereParamColumns.toString());
		mappingTemplate = mappingTemplate.replaceAll("@insert@", insert.toString());
		mappingTemplate = mappingTemplate.replaceAll("@insertColumns@", insertColumns.toString());
		mappingTemplate = mappingTemplate.replaceAll("@insertDynamicColumns@", insertDynamicColumns.toString());
		mappingTemplate = mappingTemplate.replaceAll("@updateDynamicColumns@", updateDynamicColumns.toString());
		mappingTemplate = mappingTemplate.replaceAll("@updateColumnEntity@", updateColumnEntity.toString());
		mappingTemplate = mappingTemplate.replaceAll("@updateAllColumnEntity@", updateAllColumnEntity.toString());
	}

	private String getPkJavaFullname(Entitys pk2) {
	    if (!javatypesfullname.containsKey(pk2.getFieldType())) {
	        System.out.println(pk2);
	        throw new RuntimeException("666");
	    }
        return javatypesfullname.get(pk2.getFieldType());
    }

	/**
     * 功能：生成实体类主体代码
     * @return
     */
    private void getModel() {
        
        String modelContents = processAllAttrs();
        
        modelTemplate = modelTemplate.replaceAll("@modelContents@", modelContents);
        modelTemplate = modelTemplate.replaceAll("@tableName@", tableName);
        modelTemplate = modelTemplate.replaceAll("@tableComment@", tableComment);
        modelTemplate = modelTemplate.replaceAll("@currenttime@", currenttime);
        modelTemplate = modelTemplate.replaceAll("@entityName@", entityName);
        modelTemplate = modelTemplate.replaceAll("@modelPackage@", modelPackage);
        
    }
    /**
     * 功能：生成所有属性
     */
    private String processAllAttrs() {
    	StringBuffer sb = new StringBuffer();
       
    	for (Entitys entitys : entityslist) {
            sb.append("\t/**\r\n");
            sb.append("\t* ").append(entitys.getComment()).append("\r\n");
            sb.append("\t*/ \r\n");
            
            if (entitys.isPk) {
            	sb.append("\t@Null(message =\"ID必须为空\",groups={AddGroup.class}) \r\n");
            	sb.append("\t@NotNull(message =\"ID不能为空\",groups={UpdateGroup.class}) \r\n");
            }else if (!entitys.isnullable && !"ctime".equalsIgnoreCase(entitys.getFieldName()) && !"cdate".equalsIgnoreCase(entitys.getFieldName())  && !"version".equalsIgnoreCase(entitys.getFieldName())) {
            	sb.append("\t@NotNull(message =\""+entitys.getComment()+"不能为空"+"\",groups=AddGroup.class) \r\n");
            	 if (entitys.getFieldType().equalsIgnoreCase("string")) {
            		 sb.append("\t@NotEmpty(message =\""+entitys.getComment()+"不能为空"+"\",groups=AddGroup.class) \r\n");
            	 }
            }
            
            if(entitys.getMaxlength()!=null){
                sb.append("\t@Size(max="+entitys.getMaxlength()+" ,message =\""+entitys.getComment()+"最多"+entitys.getMaxlength()+"个字符\",groups=AddGroup.class) \r\n");
            }
            sb.append("\t@ApiModelProperty(value=\""+entitys.getComment()+"\",name=\""+entitys.getFieldName()+"\",required="+ !entitys.isIsnullable()   +") \r\n");
            if("time".equalsIgnoreCase(entitys.getDataType())){//数据库类型为time时，需要单独转换一下
            	sb.append("\tprotected Date " + entitys.getFieldName() + ";\r\n\r\n");
            }else {
            	sb.append("\tprotected " + entitys.getFieldType() + " " + entitys.getFieldName() + ";\r\n\r\n");
            }
        }
        
        return sb.toString();
    }

	/**
	 * 功能：生成所有方法
	 */
	private void processAllMethod(StringBuffer sb) {
		for (Entitys entitys : entityslist) {
			String fieldName = entitys.getFieldName();
			String fieldType = entitys.getFieldType();
			sb.append("\tpublic void set" + initcap(fieldName) + "(" + fieldType + " " + fieldName + "){\r\n");
			sb.append("\t\tthis." + fieldName + "=" + fieldName + ";\r\n");
			sb.append("\t}\r\n\r\n");
			sb.append("\tpublic " + fieldType + " get" + initcap(fieldName) + "(){\r\n");
			sb.append("\t\treturn " + fieldName + ";\r\n");
			sb.append("\t}\r\n");
		}
		
		sb.append(enter);
		sb.append("\t@Override").append(enter);
		sb.append("\tpublic String toString() {").append(enter);
		sb.append("\t   return \""+this.entityName +" [");
		for (Entitys entitys : entityslist) {
			sb.append(entitys.getFieldName()).append("=\" + ").append(entitys.getFieldName())
			.append("  + \",");
		}
		
		sb.deleteCharAt(sb.length()-1);
		sb.append(" ]\";").append(enter);
		
		sb.append("\t}").append(enter);
	}

	
	
	String getFieldName(String columnName) {
		if (columnName.indexOf("_") == -1) {
			return columnName;
		}
		String[] strs = columnName.split("_");
		for (int i = 0; i < strs.length; i++) {
			if (i > 0) {
				strs[i] = initcap(strs[i]);
			}
		}
		return String.join("", strs);
	}

	String getFieldType(String dataType) {
		return jdbc2javatypes.get(dataType);
	}



	private void checkEntityslist() {
	    for (Entitys entitys : entityslist) {
	        if( null == entitys.getDataType()) {
	            throw new RuntimeException("getDataType为空：" + entitys);
	        }
        }
		if(reqentityslist != null && reqentityslist.size() > 0) {
			for (Entitys entitys : reqentityslist) {
		        if( null == entitys.getDataType()) {
		            throw new RuntimeException("getDataType为空：" + entitys);
		        }
			}
		}
		if(respentityslist != null && respentityslist.size() > 0) {
			for (Entitys entitys : respentityslist) {
		        if( null == entitys.getDataType()) {
		            throw new RuntimeException("getDataType为空：" + entitys);
		        }
			}
		}
    }

    private String covertSqltype2JdbcType(String dataType) {
        return sql2jdbctypes.get(dataType);
    }

	
}
