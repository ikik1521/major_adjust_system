package cn.scuec.major_adjust_system.service;

import java.util.List;

import cn.scuec.major_adjust_system.model.CancleTable;

public interface CancleTableService extends BaseService<CancleTable> {
	
	// 对预警专业表和majorTaxble表进行分析，挑出要进行专业撤销的专业
	public void fenXiCheXiaoZhuanYe(int year);
	
	public void clearDate();
	
	public void deleteByYearAndMajorCode(int year, String majorCode);
	
	public List<CancleTable> getAll();
}
