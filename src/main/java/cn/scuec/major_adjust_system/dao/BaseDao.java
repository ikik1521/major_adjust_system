package cn.scuec.major_adjust_system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 通用的dao，里面有通用的增删改查，其中查分为查所有和查单个
 * 但是文件规定那个数据表不用去继承basedao，它是特殊的
 * @author WangChuo
 *
 */
public interface BaseDao {
	
	//增删改查
	public void add(@Param("tableName") String tableName,@Param("objects") Object[] objects);
	
	public void delete(@Param("tableName") String tableName,@Param("majorCode") String majorCode);
	
	public void update(@Param("tableName") String tableName,@Param("majorCode") String majorCode,@Param("objects") Object[] objects);
	
	//根据专业代码查询一条记录
	public Map<Object, Object> selectOne(@Param("tableName") String tableName,@Param("majorCode") String majorCode);
	
	//查询所有记录
	public List<Map<Object, Object>> selectAll(@Param("tableName") String tableName);
	
}
