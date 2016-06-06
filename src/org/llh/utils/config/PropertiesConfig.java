package org.llh.utils.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * properties文件的配置信息
 * <br>
 * <br>
 * 文件内容修改此类会重新加载配置文件
 * @author victor
 *
 */
public class PropertiesConfig extends Config {

	private final File file;

	private final Properties properties;

	private long lastModified;

	/**
	 * @param pathname 配置文件所文件路径
	 */
	public PropertiesConfig(String pathname) {
		this.file = new File(pathname);
		properties = new Properties();
		tryReload();
	}

	@Override
	String get(String key) {
		tryReload();
		return properties.getProperty(key);
	}
	
	private void tryReload() {
		if (file.lastModified() > lastModified) {
			synchronized (PropertiesConfig.this) {
				if (file.lastModified() > lastModified) {
					lastModified = file.lastModified();
					try {
						FileInputStream is = new FileInputStream(file);
						properties.load(is);
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
