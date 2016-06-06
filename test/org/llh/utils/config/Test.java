package org.llh.utils.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.llh.utils.config.DatabaseConfig.Mapper;

public class Test {

	public static void main(String[] args) {
		
		Config config = new PropertiesConfig("test/com/llh/utils/config/test.properties");
		System.out.println(config.getBoolean("b"));
		System.out.println(config.getString("s"));
		System.out.println(config.getFloat("n"));
		System.out.println(config.getInt("n"));
		System.out.println(config.getLong("n"));
		
		Config dbconfig = new DatabaseConfig("", null, new Mapper() {
			@Override
			public Pair map(ResultSet rs) throws SQLException {
				return new Pair(rs.getString("key"), rs.getString("value"));
			}

		});
		
		((DatabaseConfig)dbconfig).add("a", "1");
		System.out.println(dbconfig.getBoolean("a"));;
	}

}
