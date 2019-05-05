package cn.sucec.major_adjust_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sucec.major_adjust_system.model.DetailwarningTable;

public interface DetailwarningTableDao extends BaseDao {

	public List<DetailwarningTable> getWarningMajorByYear(@Param("year") int year);

	public List<DetailwarningTable> getWarningMajorByMajorCode(@Param("majorCode") String majorCode);
	
	public List<DetailwarningTable> getAll();
	// 把五年内累计三次列入预警名单的专业代码选出来
	public String getcauseMajorCode();
	
	/**
	 * 根据年份和专业代码删除某一条数据
	 * @param year
	 * @param majorCode
	 */
	public void deleteByYearAndMajorCode(@Param("year") int year, @Param("majorCode") String majorCode);
	
	/* public List<DetailwarningTable> getAll(); */
	
}
