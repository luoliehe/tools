package org.llh.utils.db.jdbc;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	@SuppressWarnings("unchecked")
	public static <T> T getResultSetValue(ResultSet rs, int index, Class<T> requiredType) throws SQLException {
		
		Object value = null;
		boolean isCheckNull = false;
		
		if (String.class.equals(requiredType)) {
			value = rs.getString(index);
		}
		else if (boolean.class.equals(requiredType) || Boolean.class.equals(requiredType)) {
			value = rs.getBoolean(index);
			isCheckNull = true;
		}
		else if (byte.class.equals(requiredType) || Byte.class.equals(requiredType)) {
			value = rs.getByte(index);
			isCheckNull = true;
		}
		else if (short.class.equals(requiredType) || Short.class.equals(requiredType)) {
			value = rs.getShort(index);
			isCheckNull = true;
		}
		else if (int.class.equals(requiredType) || Integer.class.equals(requiredType)) {
			value = rs.getInt(index);
			isCheckNull = true;
		}
		else if (long.class.equals(requiredType) || Long.class.equals(requiredType)) {
			value = rs.getLong(index);
			isCheckNull = true;
		}
		else if (float.class.equals(requiredType) || Float.class.equals(requiredType)) {
			value = rs.getFloat(index);
			isCheckNull = true;
		}
		else if (double.class.equals(requiredType) || Double.class.equals(requiredType) ||
				Number.class.equals(requiredType)) {
			value = rs.getDouble(index);
			isCheckNull = true;
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
			String ex = String.format("unsupported the type %s", requiredType.getName());
			throw new UnsupportedOperationException(ex);
		}
		
		if (isCheckNull && value != null && rs.wasNull()) {
			value = null;
		}
		return value == null ? null : (T)value;
	}
	
}
