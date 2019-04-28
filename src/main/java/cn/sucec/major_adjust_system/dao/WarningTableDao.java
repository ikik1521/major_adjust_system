package cn.sucec.major_adjust_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.sucec.major_adjust_system.model.WarningTable;

public interface WarningTableDao extends BaseDao{
	
	public List<WarningTable> getAll();
	
	public void deleteWarningReasonNull();
	
	public List<WarningTable> chongfuWarningMajor();
	
	public List<WarningTable> getWarningTableByMajorCode(@Param("majorCode") String majorCode);

	
}
