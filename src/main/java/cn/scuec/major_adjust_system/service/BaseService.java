package cn.scuec.major_adjust_system.service;

import java.util.List;

public interface BaseService<T> {

	//��ɾ�Ĳ�
	public void add(T t);
	
	public void delete(String majorCode);
	
	public void update(T t);
	
	public T selectOne(String majorCode);
	
	public List<T> selectAll();
	
}
