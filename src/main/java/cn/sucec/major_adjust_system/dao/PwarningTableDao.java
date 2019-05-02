package cn.sucec.major_adjust_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sucec.major_adjust_system.model.PwarningTable;

public interface PwarningTableDao extends BaseDao {
	
	public void addOne(PwarningTable pwarningTable);
	
	public List<PwarningTable> getWarningMajor();
	
	public List<PwarningTable> getPwarningTableByMajorCode(@Param("majorCode") String majorCode);
	
	public void clearDate();
	
}
