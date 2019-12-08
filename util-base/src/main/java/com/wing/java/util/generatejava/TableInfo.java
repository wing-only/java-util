package com.wing.java.util.generatejava;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TableInfo {
	private String tableName;
	private List<String> colNames = new ArrayList<>();
	private List<String> colTypes = new ArrayList<>();
	private List<Integer> colSizes = new ArrayList<>(); // 列名大小
	private List<Integer> colScale = new ArrayList<>(); // 列名小数精度


	/**
	 * 引入哪些包
	 */
	private boolean importSql;
	private boolean importMath;
}
