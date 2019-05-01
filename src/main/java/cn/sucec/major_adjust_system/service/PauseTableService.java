package cn.sucec.major_adjust_system.service;

import cn.sucec.major_adjust_system.model.PauseTable;

public interface PauseTableService extends BaseService<PauseTable> {

	public void fenXiZanTingZhuanYe(int year);
	
	public void clearDate();

}
