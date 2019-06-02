package cn.scuec.major_adjust_system.service;

import java.util.List;

import cn.scuec.major_adjust_system.model.CancleTable;

public interface CancleTableService extends BaseService<CancleTable> {
	
	// ��Ԥ��רҵ���majorTaxble����з���������Ҫ����רҵ������רҵ
	public void fenXiCheXiaoZhuanYe(int year);
	
	public void clearDate();
	
	public void deleteByYearAndMajorCode(int year, String majorCode);
	
	public List<CancleTable> getAll();
}
