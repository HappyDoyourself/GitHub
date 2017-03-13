package cn.com.jiuyao.pay.common.util;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

/**
 * 用来记录相关的日志信息
 *
 */
public class ClassInfo {
	
	private static final Logger logger = Logger.getLogger(ClassInfo.class);
	
	/*
	 * 展示类的属性及属性值
	 * 
	 */
	public static void show(Object obj) {
		
		try {
			Class clas = Class.forName(obj.getClass().getName());
			Field[] fields = clas.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				logger.error(field.getName()+ " : " + field.get(obj) + ", ");
				System.out.print(field.getName()+ " : " + field.get(obj) + ", "); // 取得属性名
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
