package cn.sucec.major_adjust_system.service;

import java.util.List;

import cn.sucec.major_adjust_system.model.DetailwarningTable;

public interface DetailwarningTableService extends BaseService<DetailwarningTable> {

	public List<DetailwarningTable> getWarningMajorByYear(int year);

	public List<DetailwarningTable> getWarningMajorByMajorCode(String majoeCode);
	
	// 把五年内累计三次列入预警名单的专业代码选出来
	public String getcauseMajorCode();


}
