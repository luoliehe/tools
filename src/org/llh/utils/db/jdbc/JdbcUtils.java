package org.llh.utils.db.jdbc;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * jdbc相关操作工具类
 * @author victor.luo
 */
public class JdbcUtils {
	
	public static void closeConnection(Connection con) {
		if (con != null) {
			try {
				con.close();
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
			catch (Throwable ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
			catch (Throwable ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
			catch (Throwable ex) {
				ex.printStackTrace(); 
			}
		}
	}
	
	public static void close(Connection con, Statement stmt, ResultSet res){
		closeConnection(con);
		closeStatement(stmt);
		closeResultSet(res);
	}
	
	/**
	 * 获得数据的列数
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public static int getRowColumnCount(ResultSet rs) throws SQLException{
		if(rs == null) {
			throw new RuntimeException("ResultSet must not be null");
		}
		ResultSetMetaData md = rs.getMetaData();
		return md.getColumnCount();
	}
	
	public static List<String> getColumnName(ResultSetMetaData rmd)
			throws SQLException{
		if (rmd == null) {
			throw new RuntimeException("ResultSetMetaData must not be null!");
		}
		List<String> names = new ArrayList<>();
		for (int c = 0, len = rmd.getColumnCount(); c < len; c++) {
			names.add(getColumnName(rmd, c + 1));
		}
		return names;
	}
	
	/**
	 * 获得指定列的名称
	 * @param rmd
	 * @param index 第一列是1, 第二列是2, ...
	 * @return column name
	 * @throws SQLException
	 */
	public static String getColumnName(ResultSetMetaData rmd, int index) 
			throws SQLException {
		if (rmd == null) {
			throw new RuntimeException("ResultSetMetaData must not be null!");
		}
		if(index < 1){
			throw new RuntimeException("index must be equal or greater than!");
		}
		return rmd.getColumnName(index);
	}
	
	/**
	 * 
	 * @param rs ResultSet 不能为空
	 * @return 返回值一定不为 null
	 * @throws SQLException
	 */
	public static List<String> getRowData(ResultSet rs) throws SQLException{
		if(rs == null) {
			throw new RuntimeException("ResultSet must not be null");
		}
		List<String> list = new ArrayList<>();
		int cc = getRowColumnCount(rs);
		for (int i = 0; i < cc; i++) {
			Object obj = rs.getObject(i + 1);
			if(obj == null){
				continue;
			}
			list.add(rs.wasNull() ? null : String.valueOf(obj));
		}
		return list;
	}
	
	/**
	 * 将带下划线的命名转换为驼峰命名。
	 * <br>
	 * 如：user_name 转换为驼峰命名  userName
	 * @param name
	 * @return
	 */
	public static String convertUnderscoreNameToPropertyName(String name) {
		StringBuilder result = new StringBuilder();
		boolean nextIsUpper = false;
		if (name != null && name.length() > 0) {
			if (name.length() > 1 && name.substring(1,2).equals("_")) {
				result.append(name.substring(0, 1).toUpperCase());
			}
			else {
				result.append(name.substring(0, 1).toLowerCase());
			}
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				if (s.equals("_")) {
					nextIsUpper = true;
				}
				else {
					if (nextIsUpper) {
						result.append(s.toUpperCase());
						nextIsUpper = false;
					}
					else {
						result.append(s.toLowerCase());
					}
				}
			}
		}
		return result.toString();
	}
	
	public static String lookupColumnName(ResultSetMetaData resultSetMetaData, int columnIndex) throws SQLException {
		String name = resultSetMetaData.getColumnLabel(columnIndex);
		if (name == null || name.length() < 1) {
			name = resultSetMetaData.getColumnName(columnIndex);
		}
		return name;
	}
	
	public static int lookupColumnIndex(ResultSetMetaData rmd, String name) throws SQLException{
		for(int i=1; i <= rmd.getColumnCount(); i++){
			if(name != null && name.equals(lookupColumnName(rmd, i))){
				return i;
			}
		}
		return 0;
	}
	
	public static <T> T getResultSetValue(ResultSet rs, String columnName, Class<T> requiredType) throws SQLException {
		return getResultSetValue(rs, lookupColumnIndex(rs.getMetaData(), columnName), requiredType);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getResultSetValue(ResultSet rs, int index, Class<T> requiredType) throws SQLException {

		if (requiredType == null) {
			Object obj =  getResultSetValue(rs, index);
			return obj == null ? null :(T)obj;
		}

		Object value = null;
		boolean wasNullCheck = false;

		// Explicitly extract typed value, as far as possible.
		if (String.class.equals(requiredType)) {
			value = rs.getString(index);
		}
		else if (boolean.class.equals(requiredType) || Boolean.class.equals(requiredType)) {
			value = rs.getBoolean(index);
			wasNullCheck = true;
		}
		else if (byte.class.equals(requiredType) || Byte.class.equals(requiredType)) {
			value = rs.getByte(index);
			wasNullCheck = true;
		}
		else if (short.class.equals(requiredType) || Short.class.equals(requiredType)) {
			value = rs.getShort(index);
			wasNullCheck = true;
		}
		else if (int.class.equals(requiredType) || Integer.class.equals(requiredType)) {
			value = rs.getInt(index);
			wasNullCheck = true;
		}
		else if (long.class.equals(requiredType) || Long.class.equals(requiredType)) {
			value = rs.getLong(index);
			wasNullCheck = true;
		}
		else if (float.class.equals(requiredType) || Float.class.equals(requiredType)) {
			value = rs.getFloat(index);
			wasNullCheck = true;
		}
		else if (double.class.equals(requiredType) || Double.class.equals(requiredType) ||
				Number.class.equals(requiredType)) {
			value = rs.getDouble(index);
			wasNullCheck = true;
		}
		else if (byte[].class.equals(requiredType)) {
			value = rs.getBytes(index);
		}
		else if (java.sql.Date.class.equals(requiredType)) {
			value = rs.getDate(index);
		}
		else if (java.sql.Time.class.equals(requiredType)) {
			value = rs.getTime(index);
		}
		else if (java.sql.Timestamp.class.equals(requiredType) || java.util.Date.class.equals(requiredType)) {
			value = rs.getTimestamp(index);
		}
		else if (BigDecimal.class.equals(requiredType)) {
			value = rs.getBigDecimal(index);
		}
		else if (Blob.class.equals(requiredType)) {
			value = rs.getBlob(index);
		}
		else if (Clob.class.equals(requiredType)) {
			value = rs.getClob(index);
		}
		else {
			// Some unknown type desired -> rely on getObject.
			value = getResultSetValue(rs, index);
		}

		// Perform was-null check if demanded (for results that the
		// JDBC driver returns as primitives).
		if (wasNullCheck && value != null && rs.wasNull()) {
			value = null;
		}
		return value == null ? null : (T)value;
	
	}
	
	public static Object getResultSetValue(ResultSet rs, int index) throws SQLException {

		Object obj = rs.getObject(index);
		String className = null;
		if (obj != null) {
			className = obj.getClass().getName();
		}
		if (obj instanceof Blob) {
			obj = rs.getBytes(index);
		}
		else if (obj instanceof Clob) {
			obj = rs.getString(index);
		}
		else if (className != null &&
				("oracle.sql.TIMESTAMP".equals(className) ||
				"oracle.sql.TIMESTAMPTZ".equals(className))) {
			obj = rs.getTimestamp(index);
		}
		else if (className != null && className.startsWith("oracle.sql.DATE")) {
			String metaDataClassName = rs.getMetaData().getColumnClassName(index);
			if ("java.sql.Timestamp".equals(metaDataClassName) ||
					"oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
				obj = rs.getTimestamp(index);
			}
			else {
				obj = rs.getDate(index);
			}
		}
		else if (obj != null && obj instanceof java.sql.Date) {
			if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
				obj = rs.getTimestamp(index);
			}
		}
		return obj;
	
	}
}
