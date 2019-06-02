package cn.scuec.major_adjust_system.service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.scuec.major_adjust_system.dao.BaseDao;
import cn.scuec.major_adjust_system.tools.MapToEntityTool;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

	/**
	 * ��ȡBaseDao����Ľ������
	 * 
	 * @return
	 */
	public abstract BaseDao getBaseDao();

	/**
	 * ����ɾ�Ĳ鷽������Ҫ֪��T������ʲôClass����Ϊ��֪������ķ�����ʲô
	 */
	public Class<?> clazz;
	// ���ݱ������
	public String tableName;

	public BaseServiceImpl() {
		// ���ǻ�����д�����ڸ߼������У�����һ�仰�㶨�ģ�����ֻ�ǽ���һ��
//		Type type = this.getClass().getGenericSuperclass();
//		ParameterizedType pt = (ParameterizedType) type;
//		Type[] types = pt.getActualTypeArguments();
//		clazz = (Class<?>) types[0];
		// ��ȡ���ݱ������
		clazz = (Class<?>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments())[0];
		tableName = clazz.getSimpleName().toLowerCase();
		tableName = tableName.substring(0,(tableName.length()-5)) + "_table";
		//�õ�majortable toLowerCase()�����Ǳ��Сд��ĸ
		//���ݱ�����ֻ�ȡҪ�ģ���Ϊ�õ�����MajorTable������Ҫ�Ѵ�д��Сд��Ȼ���м����"_"
	}

	@Override
	public void add(T t) {
		// ����������������
		List<Object> list = new ArrayList<Object>();
		for (Field field : t.getClass().getDeclaredFields()) {
			field.setAccessible(true);// �򿪻�ȡprivate���ε�����Ȩ��
			try {
				list.add(field.get(t));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//System.out.println("*******" + getBaseDao());
		//System.out.println("******" + tableName);
		getBaseDao().add(tableName, list.toArray());
	}

	@Override
	public void delete(String majorCode) {
		getBaseDao().delete(tableName, majorCode);
	}

	@Override
	public void update(T t) {
		// ����������������
		List<Object> list = new ArrayList<Object>();
		String majorCode=null;
		for (Field field : t.getClass().getDeclaredFields()) {
			field.setAccessible(true);// �򿪻�ȡprivate���ε�����Ȩ��
			try {
				if (field.get(t) == null) {
					continue;
				}
				if ("majorCode".equals(field.getName())) {
					majorCode.equals(field.get(t));
					continue;
				}
				// ʣ�µ��ֶβ���Ҫ�޸ĵ����ݱ�����ֶ�ֵ
				// ����update�е�set����ġ��ֶ���=�޸ĺ���ֶ�ֵ��
				list.add(field.getName() + "='" + field.get(t) + "'");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		getBaseDao().update(tableName, majorCode, list.toArray());

	}

	@Override
	public T selectOne(String majorCode) {
		Map<Object, Object> rsMap = getBaseDao().selectOne(tableName, majorCode);
		//�������һ�����⣬��ѯ���Ľ����һ��map���͵����ݣ���Ҫת���һ��ʵʵ���ڵ�clazz��Ķ���
		@SuppressWarnings("unchecked")
		T t = (T) MapToEntityTool.map2entity(rsMap, clazz);
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectAll() {
		List<Map<Object, Object>> rsList = getBaseDao().selectAll(tableName);
		//System.out.println(rsList);
		List<T> list = new ArrayList<>();
		T t = null;
		for (Map<Object,Object> map : rsList) {
			//Ҳ��Ҫ��list�������Mapת���T
			t = (T) MapToEntityTool.map2entity(map, clazz);
			list.add(t);
		}
		return list;
	}

}
