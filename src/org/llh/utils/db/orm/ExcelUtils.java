package org.llh.utils.db.orm;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.concurrent.Executors;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelUtils {

	public static void main(String[] args) throws IOException, RowsExceededException, WriteException, ClassNotFoundException, SQLException {
		Executors.newFixedThreadPool(3).execute(null);
		
		
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "123456");
		PreparedStatement pstm = conn.prepareStatement("select * from help_category");
		ResultSet result = pstm.executeQuery();
		ResultSetMetaData metaData = result.getMetaData();
		int columnCount = metaData.getColumnCount();
		
		WritableWorkbook book = Workbook.createWorkbook(new File("D://test.xls"));
		WritableSheet sheet = book.createSheet("sheet1", 0);
		int row = 0;
		for (int i = 0; i < columnCount; i++) {
			sheet.addCell(new Label(i, row, String.valueOf(metaData.getColumnName(i+1))));
		}
		while (result.next()) {
			row++;
			for (int i = 0; i < columnCount; i++) {
				sheet.addCell(new Label(i, row, String.valueOf(result.getObject(i+1))));
			}
		}
		WritableCellFormat wf = new WritableCellFormat(new WritableFont(WritableFont.ARIAL));
        wf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
		  
//		sheet.mergeCells(0, 0, 0, 1);
//		sheet.addCell(new Label(0,0,"aaa0",wcf_title1));
		book.write();
		book.close();
	}
	
	
}

