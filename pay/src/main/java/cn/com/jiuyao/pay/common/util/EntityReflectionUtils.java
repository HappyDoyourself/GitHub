package cn.com.jiuyao.pay.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * @description 通过反射读取对象一些属性值
 * @author x.zl
 * @date 2012-11-22
 */
public class EntityReflectionUtils {
	
	private static final Log logger = LogFactory.getLog(EntityReflectionUtils.class);

	/**
	 * @description 得到对象属性名list
	 * @author x.zl
	 * @param o
	 * @return
	 */
	public static List<String> getFiledNameList(Object o)
	{
		List<String> filedNameList=new ArrayList<String>();
		if(o!=null)
		{
			Field[] fields=o.getClass().getDeclaredFields();
			for(int i=0;i<fields.length;i++)
			{
				filedNameList.add(fields[i].getName());
			}
		}
		return filedNameList;
	}
	
	/**
	 *  @description 获取属性值
	 * @author x.zl
	 * @param entity  目标实体
	 * @param propertyName  属性名称
	 */
	public static Object getFieldValue(Object entity, String propertyName)
	{
		if (entity == null || propertyName == null || "".equals(propertyName))
		{
			return "";
		}
		// 先通过getXxx()方法获取类属性值
		String methodname = "get"+ Character.toUpperCase(propertyName.charAt(0))+ propertyName.substring(1);
		Method method = null;
		for (Class <?> clazz1 = entity.getClass(); clazz1 != Object.class; clazz1 = clazz1
				.getSuperclass())
		{
			try
			{
				method = clazz1.getDeclaredMethod(methodname);
			}
			catch (Exception e)
			{				
			}
		}
		if(method != null){
			if (!Modifier.isPublic(method.getModifiers()))
			{ // 设置非共有方法权限
				method.setAccessible(true);
			}
			try
			{
				return method.invoke(entity);
			}
			catch (Exception e)
			{
				logger.equals(e.getMessage());
				return "";
			}
		}else{
			return "";
		}
	}
	/**
	 *  @description 设置属性值
	 * @author x.zl
	 * @param entity  目标实体
	 * @param propertyName  属性名称
	 */
	public static void setFieldValue(Object entity, String propertyName,
			Class propertyDataType, Object propertyValue) throws Exception
	{
		if (entity == null
				|| propertyName == null
				|| "".equals(propertyName)
				|| (propertyValue != null && !propertyDataType
						.isAssignableFrom(propertyValue.getClass())))
		{// 如果类型不匹配，直接退出
			return;
		}
		try
		{ // 先通过setXxx()方法设置类属性值
			String methodname = "set"
					+ Character.toUpperCase(propertyName.charAt(0))
					+ propertyName.substring(1);
			Method method = null;
			for (Class <?> clazz1 = entity.getClass(); clazz1 != Object.class; clazz1 = clazz1
					.getSuperclass())
			{
				try
				{
					method = clazz1.getDeclaredMethod(methodname,
							propertyDataType);

				}
				catch (Exception e)
				{
					// 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
					// 如果这里的异常打印或者往外抛，则就不会执行clazz =
					// clazz.getSuperclass(),最后就不会进入到父类中了

				}
			}
			if (!Modifier.isPublic(method.getModifiers()))
			{ // 设置非共有方法权限
				method.setAccessible(true);
			}
			method.invoke(entity, propertyValue); // 执行方法回调
		}
		catch (Exception me)
		{// 如果set方法不存在，则直接设置类属性值
			if (logger.isDebugEnabled())
			{
				logger.debug(me);
			}
		}
	}
	
	
}
