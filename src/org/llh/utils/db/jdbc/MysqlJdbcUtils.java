package org.llh.utils.db.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

final public class MysqlJdbcUtils {
	
	public static Connection getConnection() throws SQLException{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jee-cms?characterEncoding=UTF-8", "root", "123456");
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SQLException(e);
		}
	}
	
	public static String[] getColumnCountNames(ResultSet rs) throws SQLException {
		return rs == null ? null : getColumnCountNames(rs.getMetaData());
	}
	
	public static String[] getColumnCountNames(ResultSetMetaData rmd) throws SQLException{
		String[] cNames = null;
		if (rmd != null){
			int cc = rmd.getColumnCount();
			cNames = new String[cc];
			for (int c = 0; c < rmd.getColumnCount(); c++) {
				cNames[c] = rmd.getColumnName(c + 1);
			}
		}
		return cNames;
	}
	
	public static String[] getRowData(ResultSet rs) throws SQLException{
		String[] datas = null;
		if(rs != null){
			int cc = getRowColumnCount(rs);
			datas = new String[cc];
			for (int i = 0; i < cc; i++) {
				Object obj = rs.getObject(i + 1);
				if(obj == null)
					continue;
				datas[i] = rs.wasNull() ? null : String.valueOf(obj);
			}
		}
		return datas;
	}
	
	public static int getRowColumnCount(ResultSet rs) throws SQLException{
		if(rs != null){
			ResultSetMetaData md = rs.getMetaData();
			return md.getColumnCount();
		}
		return 0;
	}

	public static List<String> getFieldName(ResultSet rs) throws SQLException {
		List<String> classFields = null;

		String[] dbFields = MysqlJdbcUtils.getColumnCountNames(rs);
		if (dbFields != null) {
			classFields = new ArrayList<>();
			
			for (String str : dbFields) {
				if (StringUtils.isBlank(str))
					continue;

				int index = str.indexOf("_");
				String filed = null;
				
				if (index != -1) {
					String[] ss = str.split("_");
					filed = ss[0] + str.substring(index + 1, index + 2).toUpperCase() + str.substring(index + 2);
				}else{
					filed = str;
				}
				classFields.add(filed);
			}
		}
		return classFields;
	}
}