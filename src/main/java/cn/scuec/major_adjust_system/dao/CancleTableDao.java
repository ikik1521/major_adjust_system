package cn.scuec.major_adjust_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.scuec.major_adjust_system.model.CancleTable;

public interface CancleTableDao extends BaseDao {
	
	public void clearDate();
	
	/**
	 * ������ݺ�רҵ����ɾ��ĳһ������
	 * @param year
	 * @param majorCode
	 */
	public void deleteByYearAndMajorCode(@Param("year") int year, @Param("majorCode") String majorCode);
	
	public List<CancleTable> getAll();
	
}