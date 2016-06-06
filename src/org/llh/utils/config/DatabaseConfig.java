package org.llh.utils.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.llh.utils.config.DatabaseConfig.Mapper.Pair;

public class DatabaseConfig extends Config {

	private String sql;
	private Mapper mapper;
	private final Map<String, String> cacheMap;

	public DatabaseConfig(String sql, Connection conn, Mapper mapper) {
		this.sql = sql;
		this.mapper = mapper;

		cacheMap = new HashMap<>();

		load(conn);
	}

	private void load(Connection conn) {
		if (conn != null && sql != null && mapper != null) {
			PreparedStatement pstm = null;
			ResultSet rs = null;
			try {
				pstm = conn.prepareStatement(sql);
				rs = pstm.executeQuery();
				Pair p = null;
				while (rs.next()) {
					p = mapper.map(rs);
					if (p != null) {
						cacheMap.put(p.getKey(), p.getValue());
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
					}
				}
				if (pstm != null) {
					try {
						pstm.close();
					} catch (SQLException e) {
					}
				}
			}

		}
	}

	@Override
	String get(String key) {
		return cacheMap.get(key);
	}

	public interface Mapper {
		/**
		 * 键值对类，对应 getX 方法的 key,value
		 * @author victor
		 *
		 */
		class Pair {
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

		Pair map(ResultSet rs) throws SQLException;
	}

	public void reload(Connection conn) {
		cacheMap.clear();
		load(conn);
	}

	public void add(String key, String value) {
		cacheMap.put(key, value);
	}
}
