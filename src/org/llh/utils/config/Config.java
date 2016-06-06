package org.llh.utils.config;

import java.lang.reflect.Constructor;

/**
 * 配置类抽象类，提供获得配置信息<br>
 * 获取常用类型值:<br>
 * {@link java.lang.String}, <br>
 * {@link java.lang.Integer}, <br>
 * {@link java.lang.Long},<br>
 * {@link java.lang.Float}, <br>
 * {@link java.lang.Byte}, <br>
 * {@link java.lang.Double},<br>
 * {@link java.lang.Boolean},<br>
 * 
 * @author victor
 */
public abstract class Config {

	public Byte getByte(String key, Byte defaultValue) {
		return get(Byte.class, key, defaultValue);
	}

	public Byte getByte(String key) {
		return get(Byte.class, key);
	}

	public Integer getInt(String key, Integer defaultValue) {
		return get(Integer.class, key, defaultValue);
	}

	public Integer getInt(String key) {
		return get(Integer.class, key);
	}

	public Short getShort(String key, Short defaultValue) {
		return get(Short.class, key, defaultValue);
	}

	public Short getShort(String key) {
		return get(Short.class, key);
	}

	public Long getLong(String key) {
		return get(Long.class, key);
	}

	public Long getLong(String key, Long defaultValue) {
		return get(Long.class, key, defaultValue);
	}

	public Double getDouble(String key) {
		return get(Double.class, key);
	}

	public Double getDouble(String key, Double defaultValue) {
		return get(Double.class, key, defaultValue);
	}

	public Float getFloat(String key) {
		return get(Float.class, key);
	}

	public Float getFloat(String key, Float defaultValue) {
		return get(Float.class, key, defaultValue);
	}
	
	/**
	 * 只有值为true 返回才为 true,其他值为 false
	 * @param key
	 */
	public Boolean getBoolean(String key) {
		return get(Boolean.class, key);
	}
	
	/**
	 * {@link #getBoolean(String)}
	 * @param key
	 * @param defaultValue 默认值
	 * @return
	 */
	public Boolean getBoolean(String key, Boolean defaultValue) {
		return get(Boolean.class, key, defaultValue);
	}

	public String getString(String key) {
		return get(String.class, key);
	}

	public String getString(String key, String def) {
		return get(String.class, key, def);
	}

	private <T> T get(Class<T> type, String key, T defaultValue) {
		T v = get(type, key);
		return v == null ? defaultValue : v;
	}

	@SuppressWarnings("unchecked")
	private <T> T get(Class<T> c, String key) {
		String v = get(key);

		if (v == null) {
			return null;
		}

		T obj = null;

		if (String.class == c) {
			obj = (T) v;

		} else if (Byte.class == c || Short.class == c || Integer.class == c 
				|| Long.class == c || Float.class == c
				|| Double.class == c || Boolean.class == c) {

			Constructor<T> constructor;

			try {
				constructor = c.getConstructor(String.class);
				obj = constructor.newInstance(v);

				return obj;
			} catch (Exception e) {
				String err = String.format("请确保{%s}值为{%s}类型", key, c.getName());
				throw new UnsupportedOperationException(err, e);
			}
		} else {
			throw new UnsupportedOperationException("不支撑转换为类型:{%s}");
		}

		return obj;
	}

	/**
	 * 通过key获得一个value
	 * 
	 * @param key
	 * @return
	 */
	abstract String get(String key);

}
