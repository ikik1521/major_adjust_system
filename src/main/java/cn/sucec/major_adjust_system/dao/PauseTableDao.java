package cn.sucec.major_adjust_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sucec.major_adjust_system.model.PauseTable;

public interface PauseTableDao extends BaseDao {

	public void clearDate();
	
	/**
	 * 根据年份和专业代码删除某一条数据
	 * @param year
	 * @param majorCode
	 */
	public void deleteByYearAndMajorCode(@Param("year") int year, @Param("majorCode") String majorCode);
	
	public List<PauseTable> getAll();
}
