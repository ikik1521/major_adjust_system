package cn.scuec.major_adjust_system.service;

import java.util.List;

import cn.scuec.major_adjust_system.model.PwarningTable;

public interface PwarningTableService extends BaseService<PwarningTable> {
	
	public void addOne(PwarningTable pwarningTable);
	
	public List<PwarningTable> getWarningMajor(); 
	
	public List<PwarningTable> getPwarningTableByMajorCode(String majorCode);
	
	public void clearDate();
	
}
