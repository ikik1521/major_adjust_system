package cn.scuec.major_adjust_system.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapToEntityTool {
	
	//建立一个缓存的map集合，缓存类的属性信息
	private static Map<String, EntityCacheItem> convertItemCache = new HashMap<>();
	
	/**
	 * map to eneity
	 * @param map
	 * @param entityClass
	 * @return
	 */ 
	public static <T> T map2entity(Map<Object, Object> map,Class<T> entityClass) {
		//一进来，直接尝试从缓存中获取EntityCacheItem对象
		EntityCacheItem entityCacheItem = convertItemCache.get(entityClass.getName());
		if (entityCacheItem == null) {//第一次进来，缓存中肯定没有，所以就创建一个
			entityCacheItem = EntityCacheItem.createEntityCacheItem(entityClass);
			convertItemCache.put(entityClass.getName(), entityCacheItem);   
		}
		
		//通过entityClass参数，获取类型里边的属性名称集合
		List<String> fieldNameList = entityCacheItem.getFieldNameList();
		//通过entityClass参数，获取类型里边的set方法的map集合,key是属性名，value是方法封装的对象
		Map<String, Method> setMethodMap = entityCacheItem.getSetMethodMap();
		
		//System.out.println("数据库中查询出来的结果集：" + map);
		//{password=060402, add_date=2019-04-17 03:08:37.0, phone=989898989, enable=0, 
		// id=6, email=789465@qq.com, username=小黑}
		//System.out.println("实体类对象中的属性名字：" + fieldNameList);
		//[id, username, password, email, phone, enable, addDate] 可以看见，addDate名称不一样，所以要转换一下 
		
		//把add_date数据表中的这种字段变成模型类中的addDate
		Map<Object, Object> targetMap = new HashMap<>();
		String key;
		String key1;
		String key2;
				
		for (Map.Entry<Object, Object> entry:map.entrySet()) {
			key = entry.getKey().toString();
			while (key.contains("_")) {
				//找到了add_date 防止有a_b_c_d这样的，所以把if换成while
				key1 = key.substring(0,key.indexOf("_"));//得到add
				key2 = key.substring(key.indexOf("_")+1);//得到data
				key = key1 + key2.substring(0,1).toUpperCase() + key2.substring(1);//得到addDate
			    }
			targetMap.put(key, entry.getValue());
		}
		
		
		T entity = null;
		try {
			entity = entityClass.newInstance();//通过反射的方法，获取这个类型的一个对象实例
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Object mapFieldValue = null;
		Method setMethod1 = null;
		Class<?>[] parameterTypes = null; 
		for (String fieldName1 : fieldNameList) {
			mapFieldValue = targetMap.get(fieldName1);
			//数据库中map集合key:add_date
			//取这个数据的时候用get(key):addDate
			//System.out.println("=========" + mapFieldValue);
			if (mapFieldValue == null) continue;
			setMethod1 = setMethodMap.get(fieldName1);
			//System.out.println("=========" + setMethod1);
			if (fieldName1 == null) continue;
			
			//获取set方法中参数类型的对象
			parameterTypes = setMethod1.getParameterTypes();
			if (parameterTypes == null || parameterTypes.length>1) {
				continue;
			}
			
			if (parameterTypes[0].isAssignableFrom(mapFieldValue.getClass())) {
				//若参数map中传来的属性值类型和set方法中参数的类型一致
				try {
					setMethod1.invoke(entity, mapFieldValue);//调用set对象的set方法把属性值注入
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}  
			}
			
		}
		return entity;
	}
	
	static class EntityCacheItem{
		
		private EntityCacheItem() {}
		
		private List<String> fieldNameList = new ArrayList<>();
		private Map<String, Method> setMethodMap = new HashMap<>();
		
		public List<String> getFieldNameList() {
			return fieldNameList;
		}
		
		public Map<String, Method> getSetMethodMap() {
			return setMethodMap;
		}
		
		public void parseEntity(Class<?> entityClass) {
			Field[] fields = entityClass.getDeclaredFields();
			String fieldName;
			String setMethName;
			Method setMethod = null;
			for (Field field : fields) {
				field.setAccessible(true);//获取private修饰的属性
				fieldName = field.getName();//拿到属性对象对应的名称
				fieldNameList.add(fieldName);
				setMethName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				//拿到了setMethName，就可以拿到对应的方法对象
				try {
					//返回了一个方法对象
					setMethod = entityClass.getDeclaredMethod(setMethName, field.getType());
				} catch (Exception e) {
					e.printStackTrace();
				}
				setMethodMap.put(fieldName, setMethod);
			}
		}
		
		public static EntityCacheItem createEntityCacheItem(Class<?> entityClass) {
			EntityCacheItem ci = new EntityCacheItem();
			ci.parseEntity(entityClass);
			return ci;
		}
		
	}
	
}
