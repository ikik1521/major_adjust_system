package cn.sucec.major_adjust_system.service;

import java.util.List;

import cn.sucec.major_adjust_system.model.WarningTable;

public interface WarningTableService extends BaseService<WarningTable>{
	
	public void deleteWarningReasonNull();
	
	public List<WarningTable> getChongFu();

	public List<WarningTable> getWarningTableByMajorCode(String majorCode);
	
}
