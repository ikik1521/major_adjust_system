package cn.scuec.major_adjust_system.service;

import java.util.List;

import cn.scuec.major_adjust_system.model.WarningTable;

public interface WarningTableService extends BaseService<WarningTable>{
	
	public void deleteWarningReasonNull();
	
	public List<WarningTable> getChongFu();

	public List<WarningTable> getWarningTableByMajorCode(String majorCode);
	
	public void clearDate();
	
}
