package cn.scuec.major_adjust_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.scuec.major_adjust_system.model.WarningTable;

public interface WarningTableDao extends BaseDao{
	
	public List<WarningTable> getAll();
	
	public void deleteWarningReasonNull();
	
	public List<WarningTable> chongfuWarningMajor();
	
	public List<WarningTable> getWarningTableByMajorCode(@Param("majorCode") String majorCode);

	public void clearDate();
}
