package cn.scuec.major_adjust_system.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapToEntityTool {
	
	//����һ�������map���ϣ��������������Ϣ
	private static Map<String, EntityCacheItem> convertItemCache = new HashMap<>();
	
	/**
	 * map to eneity
	 * @param map
	 * @param entityClass
	 * @return
	 */ 
	public static <T> T map2entity(Map<Object, Object> map,Class<T> entityClass) {
		//һ������ֱ�ӳ��Դӻ����л�ȡEntityCacheItem����
		EntityCacheItem entityCacheItem = convertItemCache.get(entityClass.getName());
		if (entityCacheItem == null) {//��һ�ν����������п϶�û�У����Ծʹ���һ��
			entityCacheItem = EntityCacheItem.createEntityCacheItem(entityClass);
			convertItemCache.put(entityClass.getName(), entityCacheItem);   
		}
		
		//ͨ��entityClass��������ȡ������ߵ��������Ƽ���
		List<String> fieldNameList = entityCacheItem.getFieldNameList();
		//ͨ��entityClass��������ȡ������ߵ�set������map����,key����������value�Ƿ�����װ�Ķ���
		Map<String, Method> setMethodMap = entityCacheItem.getSetMethodMap();
		
		//System.out.println("���ݿ��в�ѯ�����Ľ������" + map);
		//{password=060402, add_date=2019-04-17 03:08:37.0, phone=989898989, enable=0, 
		// id=6, email=789465@qq.com, username=С��}
		//System.out.println("ʵ��������е��������֣�" + fieldNameList);
		//[id, username, password, email, phone, enable, addDate] ���Կ�����addDate���Ʋ�һ��������Ҫת��һ�� 
		
		//��add_date���ݱ��е������ֶα��ģ�����е�addDate
		Map<Object, Object> targetMap = new HashMap<>();
		String key;
		String key1;
		String key2;
				
		for (Map.Entry<Object, Object> entry:map.entrySet()) {
			key = entry.getKey().toString();
			while (key.contains("_")) {
				//�ҵ���add_date ��ֹ��a_b_c_d�����ģ����԰�if����while
				key1 = key.substring(0,key.indexOf("_"));//�õ�add
				key2 = key.substring(key.indexOf("_")+1);//�õ�data
				key = key1 + key2.substring(0,1).toUpperCase() + key2.substring(1);//�õ�addDate
			    }
			targetMap.put(key, entry.getValue());
		}
		
		
		T entity = null;
		try {
			entity = entityClass.newInstance();//ͨ������ķ�������ȡ������͵�һ������ʵ��
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Object mapFieldValue = null;
		Method setMethod1 = null;
		Class<?>[] parameterTypes = null; 
		for (String fieldName1 : fieldNameList) {
			mapFieldValue = targetMap.get(fieldName1);
			//���ݿ���map����key:add_date
			//ȡ������ݵ�ʱ����get(key):addDate
			//System.out.println("=========" + mapFieldValue);
			if (mapFieldValue == null) continue;
			setMethod1 = setMethodMap.get(fieldName1);
			//System.out.println("=========" + setMethod1);
			if (fieldName1 == null) continue;
			
			//��ȡset�����в������͵Ķ���
			parameterTypes = setMethod1.getParameterTypes();
			if (parameterTypes == null || parameterTypes.length>1) {
				continue;
			}
			
			if (parameterTypes[0].isAssignableFrom(mapFieldValue.getClass())) {
				//������map�д���������ֵ���ͺ�set�����в���������һ��
				try {
					setMethod1.invoke(entity, mapFieldValue);//����set�����set����������ֵע��
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
				field.setAccessible(true);//��ȡprivate���ε�����
				fieldName = field.getName();//�õ����Զ����Ӧ������
				fieldNameList.add(fieldName);
				setMethName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				//�õ���setMethName���Ϳ����õ���Ӧ�ķ�������
				try {
					//������һ����������
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
