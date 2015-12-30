package com.llh.utils.qr;

import java.io.File;
import java.util.Date;

public class TestQR {
	   
    /**
     * @param args
     * @throws Exception 
     */ 
    public static void main(String[] args) throws Exception {
    	
    	
        String text = "luoliehe"; 
        int width = 300; 
        int height = 300; 
        
        //生成二维码 
        File file = new File("d:"+File.separator+(new Date().getTime())+".png");
        
        QRUtil.generateQR(text, width, height, file);
    } 
}
