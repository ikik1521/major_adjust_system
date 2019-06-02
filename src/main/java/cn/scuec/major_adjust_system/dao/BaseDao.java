package cn.scuec.major_adjust_system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * ͨ�õ�dao��������ͨ�õ���ɾ�Ĳ飬���в��Ϊ�����кͲ鵥��
 * �����ļ��涨�Ǹ����ݱ���ȥ�̳�basedao�����������
 * @author WangChuo
 *
 */
public interface BaseDao {
	
	//��ɾ�Ĳ�
	public void add(@Param("tableName") String tableName,@Param("objects") Object[] objects);
	
	public void delete(@Param("tableName") String tableName,@Param("majorCode") String majorCode);
	
	public void update(@Param("tableName") String tableName,@Param("majorCode") String majorCode,@Param("objects") Object[] objects);
	
	//����רҵ�����ѯһ����¼
	public Map<Object, Object> selectOne(@Param("tableName") String tableName,@Param("majorCode") String majorCode);
	
	//��ѯ���м�¼
	public List<Map<Object, Object>> selectAll(@Param("tableName") String tableName);
	
}
