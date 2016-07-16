package org.llh.utils.db.jdbc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.llh.utils.db.jdbc.annotation.Column;
import org.llh.utils.db.jdbc.annotation.Entity;

public class Mapping {
	private Class<?> className;
	private String tableName;
	private String id;
	
	private final Map<String, Field> columnMap = new HashMap<>();
	
	public Mapping(Class<?> entityClass){
		if(entityClass == null){
			return;
		}
		Entity e = entityClass.getAnnotation(Entity.class);
		if(e == null){
			return;
		}
		tableName = e.value();
		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			Column column = field.getDeclaredAnnotation(Column.class);
			if(column != null){
				columnMap.put(column.value(), field);
			}
		}
	}
	
	public Field getFeild(String columnName){
		return columnMap.get(columnName);
	}
}
