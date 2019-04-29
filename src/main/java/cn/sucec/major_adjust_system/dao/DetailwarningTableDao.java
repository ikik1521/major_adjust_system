package cn.sucec.major_adjust_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sucec.major_adjust_system.model.DetailwarningTable;

public interface DetailwarningTableDao extends BaseDao {

	public List<DetailwarningTable> getWarningMajorByYear(@Param("year") int year);

	public List<DetailwarningTable> getWarningMajorByMajorCode(@Param("majorCode") String majorCode);

	
}
