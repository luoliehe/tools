package com.llh.utils.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipException;

import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * zip 工具类
 * @author luolh
 */
final public class ZipUtil {
	
	public static void main(String[] args) throws ZipException, IOException {
		
		File zip = new File("application_v10.zip");
		File target = new File("target1");
		
		//unzip
		unzip(zip, target);
		
		//zip
		zip(target);
		
		System.out.println("ok");
	}
	
	/**
	 * @param zip zip文件
	 * @param target 解压放置的目录
	 * @throws ZipException
	 * @throws IOException
	 */
	public static void unzip(File zip, File target) throws ZipException, IOException{
		
		ZipFile zipfile = new ZipFile(zip);
		zipfile.getEntries();
		for(Enumeration<?> e = zipfile.getEntries(); e.hasMoreElements();){
			ZipEntry zipEntry = (ZipEntry) e.nextElement();
			if(zipEntry.isDirectory()){
				File dir = new File(target, zipEntry.getName());
				if(!dir.exists()){
					dir.mkdirs();
				}
			}else{
				File file = new File(target, zipEntry.getName());
				InputStream is = zipfile.getInputStream(zipEntry);
				FileOutputStream fos = new FileOutputStream(file);
				IOUtils.copy(is, fos);
				is.close();
				fos.close();
			}
		}
		
		zipfile.close();
	}
	
	/**
	 * zip 文件名称和 要压缩的文件名称一致，且在统一目录下
	 * @param file
	 * @throws IOException
	 */
	public static void zip(File file) throws IOException{
		file = file.getAbsoluteFile();
		zip(file, new File(file.getParentFile(), file.getName()+".zip"));
	}
	
	public static void zip(File file, File target) throws IOException{
		
		if(!file.isAbsolute()){
			file = file.getAbsoluteFile();
		}
		if(!target.isAbsolute()){
			target = target.getAbsoluteFile();
		}
		FileOutputStream fos = new FileOutputStream(target);
		ZipOutputStream out = new ZipOutputStream(fos);
		loopZip(file, file.getParentFile(), out);
		out.flush();
		out.close();
		fos.close();
	}
	
	private static void loopZip(File file, File root, ZipOutputStream out)
			throws IOException {
		
		if (file.isDirectory()) {
			for(File f : file.listFiles()){
				loopZip(f, root, out);
			}
			
		}else{
			String relative = file.getAbsolutePath().replace(
					(root == null ? "" : root.getAbsolutePath())
							+ File.separator, "");
			
			ZipEntry zipEntry = new ZipEntry(relative);
			out.putNextEntry(zipEntry);
			FileInputStream fis = new FileInputStream(file);
			IOUtils.copy(fis, out);
			fis.close();
			
		}
	}
	
}
