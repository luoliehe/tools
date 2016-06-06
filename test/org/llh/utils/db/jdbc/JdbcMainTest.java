package org.llh.utils.db.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

public class JdbcMainTest {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws SQLException {
		Connection conn = MysqlJdbcUtils.getConnection();
		Statement stm = conn.createStatement();
		ResultSet rs = stm.executeQuery("select * from jc_channel");
		while(rs.next()){
			//System.out.println(Arrays.asList(JdbcUtils.getRowData(rs)));
		}
		
		for (String str : Arrays.asList(MysqlJdbcUtils.getColumnCountNames(rs))) {
			if(StringUtils.isBlank(str))
				continue;
			int index = str.indexOf("_");
			String filed = str;
			if( index != -1){
				String[] ss = str.split("_");
				filed = ss[0] + str.substring(index + 1, index + 2).toUpperCase() + str.substring(index + 2);
			}
		}
		System.out.println(MysqlJdbcUtils.getFieldName(rs));
		System.out.println(MysqlJdbcUtils.getColumnCountNames(rs));
	}

}

