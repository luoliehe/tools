package com.llh.utils.lrc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 歌词解析工具类
 * 
 * @author victor.luo
 */
public final class LrcUtil {

	public static LrcEntity analysisLrc(File lrcFile) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(lrcFile));
		LrcEntity entity = analysisLrc(reader);
		IOUtils.closeQuietly(reader);
		return entity;
	}
	
	public static LrcEntity analysisLrc(BufferedReader reader) throws IOException{
		
		LrcEntity entity = new LrcEntity();
		
		for (String line = null; (line=reader.readLine())!= null;) {
			if(StringUtils.isBlank(line)){
				continue;
			}
			
			line = line.replaceAll("\\s", "");
			 
			if(line.startsWith("﻿[ti:")){
				entity.setTitle(line.replaceAll("^[ti:|]$", ""));
				
			}else if(line.startsWith("[ar:")){
				entity.setAlbum(line.replaceAll("^[ar:|]$", ""));
				
			}else if(line.startsWith("[al:")){
				entity.setAlbum(line.replaceAll("^[al:|]$", ""));
				
			}else if(line.startsWith("[by:")){
				entity.setBy(line.replaceAll("^[by:|]$", ""));
				
			}else if(line.matches("^(\\[\\d{2}:\\d{2}.\\d{2}\\])+.*")){

				Pattern p = Pattern.compile("(\\[\\d{2}:\\d{2}.\\d{2}\\])");
				Matcher m = p.matcher(line);
				
				String lrc  = line.replaceAll("\\[\\d{2}:\\d{2}.\\d{2}\\]", "");

				while (m.find()) {
					String time = m.group();
					String[] t = time.replaceAll("\\[|\\]", "").split(":");
					int value = (int) (Float.parseFloat(t[0]) * 60 + Float.parseFloat(t[1]));
					entity.putLrc(value, lrc);
				}
			}
		}
		return entity;
	}
	
}

