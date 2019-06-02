package cn.scuec.major_adjust_system.service;

import java.util.List;

import cn.scuec.major_adjust_system.model.DetailwarningTable;
import cn.scuec.major_adjust_system.model.PauseTable;

public interface PauseTableService extends BaseService<PauseTable> {

	public void fenXiZanTingZhuanYe(int year);
	
	public void clearDate();

	public void deleteByYearAndMajorCode(int year, String majorCode);
	
	public List<PauseTable> getAll();
}
