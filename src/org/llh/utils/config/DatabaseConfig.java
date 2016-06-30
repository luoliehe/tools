package org.llh.utils.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.llh.utils.config.exception.ConfigException;
import org.llh.utils.db.jdbc.JdbcUtils;

public class DatabaseConfig extends Config {
	
	private String sql;
	private Mapper mapper;
	private DataSource dataSource;
	private final Map<String, String> cacheMap = new HashMap<>();
	
	/**
	 * 
	 * @param sql
	 * @param dataSource
	 * @param columnKeyName 数据库 作为key的列名称
	 * @param columnValueName 数据库 作为value的列名称
	 * @throws ConfigException
	 */
	public DatabaseConfig(String sql, DataSource dataSource, String columnKeyName, String columnValueName) throws ConfigException {
		this(sql, dataSource, getDefaultMapper(columnKeyName, columnValueName));
	}
	
	public DatabaseConfig(String sql, DataSource dataSource, Mapper mapper) throws ConfigException {
		this.sql = sql;
		this.mapper = mapper;
		this.dataSource = dataSource;
		sync(false);
	}
	
	public void setSql(String sql) {
		this.sql = sql;
	}
	public void setMapper(Mapper mapper) {
		this.mapper = mapper;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	String get(String key) {
		return cacheMap.get(key);
	}
	
	/**
	 * 重新加载数据库数据
	 * @throws ConfigException
	 */
	public void sync() throws ConfigException{
		sync(false);
	}
	
	/**
	 * 重新加载数据库数据
	 * @param clearOld 是否清除缓存
	 * @throws ConfigException
	 */
	public void sync(boolean clearOld) throws ConfigException {
		if(clearOld){
			cacheMap.clear();
		}
		if(dataSource == null){
			throw new IllegalArgumentException("the DataSource is null");
		}
		if(StringUtils.isBlank(sql)){
			throw new IllegalArgumentException("the sql is null");
		}
		
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = dataSource.getConnection();
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();
			
			while (rs.next()) {
				Pair p = mapper.map(rs);
				if (p != null) {
					cacheMap.put(p.getKey(), p.getValue());
				}
			}
		} catch (SQLException e) {
			throw new ConfigException(e);
		} finally {
			JdbcUtils.close(conn, pstm, rs);
		}
	}

	public void add(String key, String value) {
		cacheMap.put(key, value);
	}
	
	/**
	 * 获得默认的Mapper
	 * @param columnKeyName 数据库 作为key的列名称
	 * @param columnValueName 数据库 作为value的列名称
	 * @return
	 */
	public static Mapper getDefaultMapper(final String columnKeyName, final String columnValueName){
		return new Mapper(){
			@Override
			public Pair map(ResultSet rs) throws SQLException {
				return new Pair(rs.getString(columnKeyName), rs.getString(columnValueName));
			}
		};
	}
	
	public interface Mapper {
		Pair map(ResultSet rs) throws SQLException;
	}
	
	/**
	 * 键值对类，对应 getX 方法的 key,value
	 * @author victor
	 */
	public static class Pair {
		private String key;
		private String value;

		public Pair(String s, String value) {
			this.key = s;
			this.value = value;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
}
