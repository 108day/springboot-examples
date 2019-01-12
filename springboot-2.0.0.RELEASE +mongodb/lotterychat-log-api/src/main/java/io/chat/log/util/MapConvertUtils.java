package io.chat.log.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体对象与Map之间的转换
 * @author Kevin zhaosheji.kevin@gmail.com
 * @date 2019年1月5日
 */
public class MapConvertUtils {

	/**
	 * 将接收到的参数 Map 转为 对象
	 * @Description: TODO(用一句话描述该文件做什么)
	 * @author Kevin zhaosheji.kevin@gmail.com
	 * @date 2019年1月5日
	 */
    public static Object map2Entity(Map<String,Object> map,Class<?> clazz) throws Exception {
    	if(map!=null) {
    		Constructor<?> constructor = clazz.getConstructor(); //默认的构造函数
    		Object t = constructor.newInstance(); //实例化一个对象
    		map.forEach((key,value)->{
    			Field declaredField = null;
				try {
					declaredField = clazz.getDeclaredField(key);
					if(declaredField!=null){
						declaredField.setAccessible(true);
						Type type = declaredField.getType();
						if(type == String.class){
							declaredField.set(t,value.toString());
						}else if(type == Date.class){
							declaredField.set(t,DateUtil.parse(value));
						}else if(type == Timestamp.class){
							declaredField.set(t,DateUtil.parse(value));
						}else if(type == Double.class){
							declaredField.set(t,new Double(value.toString()));
						}else if(type == Float.class){
							declaredField.set(t,new Float(value.toString()));
						}else if(type == Integer.class){
							declaredField.set(t,new Integer(value.toString()));
						}else if(type == BigDecimal.class){
							declaredField.set(t,new BigDecimal(value.toString()));
						}
					}
				} catch (Exception e) {
				}
    		});
    		return t;
    	}
    	return null;
    }
   
    /**
     * 
     * @param t
     * @return
     * @throws Exception 
     */
    public static<T> Map<String,Object> entity2Map(T t) throws Exception {
    	Map<String,Object> map = new HashMap<String,Object>();
    	try {
			if(t!=null) {
				throw new NullPointerException("The parameter must not be null .");
			}
			Field [] field=t.getClass().getDeclaredFields(); //获取属性列表
			for (int i = 0 ; i< field.length; i++) {
				Field declaredField = field[i];
				Object value = declaredField.get(t); //获取这个属性的值
				if(value == null || "".equals(value)) {
					map.put(declaredField.getName(), value);
				 }else if(value instanceof String){
				    	map.put(declaredField.getName(), value);
				}else if(value instanceof Boolean){
						map.put(declaredField.getName(), (boolean)value);
				}else if(value instanceof Integer){
						map.put(declaredField.getName(), (int)value);
				}else if(value instanceof Long){
						map.put(declaredField.getName(), (long)value);
				}else if(value instanceof Short){
						map.put(declaredField.getName(), (short)value);
				}else if(value instanceof Character){
						map.put(declaredField.getName(), (char)value);
				}else if(value instanceof Double){
						map.put(declaredField.getName(),(double)value);
				}else if(value instanceof Float){
						map.put(declaredField.getName(), (float)value);
				}else if(value instanceof Date){
						map.put(declaredField.getName(),(Date)value);
				}else if(value instanceof Timestamp){
						map.put(declaredField.getName(),(Timestamp)value);
				}else if(value instanceof BigDecimal){
						map.put(declaredField.getName(), (BigDecimal)value);
				}
			}
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage(),e);
		} 
    	return map;
    }
}
