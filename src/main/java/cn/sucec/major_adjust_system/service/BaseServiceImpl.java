package cn.sucec.major_adjust_system.service;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.sucec.major_adjust_system.dao.BaseDao;
import cn.sucec.major_adjust_system.tools.MapToEntityTool;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

	/**
	 * 获取BaseDao对象的解决方案
	 * 
	 * @return
	 */
	public abstract BaseDao getBaseDao();

	/**
	 * 在增删改查方法中需要知道T到底是什么Class，即为了知道父类的泛型是什么
	 */
	public Class<?> clazz;
	// 数据表的名字
	public String tableName;

	public BaseServiceImpl() {
		// 这是基本的写法，在高级代码中，都是一句话搞定的，这里只是解释一下
//		Type type = this.getClass().getGenericSuperclass();
//		ParameterizedType pt = (ParameterizedType) type;
//		Type[] types = pt.getActualTypeArguments();
//		clazz = (Class<?>) types[0];
		// 获取数据表的名字
		clazz = (Class<?>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments())[0];
		tableName = clazz.getSimpleName().toLowerCase();
		tableName = tableName.substring(0,(tableName.length()-5)) + "_table";
		//得到majortable toLowerCase()作用是变成小写字母
		//数据表的名字获取要改，因为得到的是MajorTable这样，要把大写变小写，然后中间加上"_"
	}

	@Override
	public void add(T t) {
		// 将参数放入数组中
		List<Object> list = new ArrayList<Object>();
		for (Field field : t.getClass().getDeclaredFields()) {
			field.setAccessible(true);// 打开获取private修饰的属性权限
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
		// 将参数放入数组中
		List<Object> list = new ArrayList<Object>();
		String majorCode=null;
		for (Field field : t.getClass().getDeclaredFields()) {
			field.setAccessible(true);// 打开获取private修饰的属性权限
			try {
				if (field.get(t) == null) {
					continue;
				}
				if ("majorCode".equals(field.getName())) {
					majorCode.equals(field.get(t));
					continue;
				}
				// 剩下的字段才是要修改到数据表里的字段值
				// 构造update中的set后面的“字段名=修改后的字段值”
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
		//这里存在一个问题，查询到的结果是一个map类型的数据，需要转变成一个实实在在的clazz类的对象
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
			//也需要把list集合里的Map转变成T
			t = (T) MapToEntityTool.map2entity(map, clazz);
			list.add(t);
		}
		return list;
	}

}
