package cn.scuec.major_adjust_system.service;

import java.util.List;

import cn.scuec.major_adjust_system.model.DetailwarningTable;

public interface DetailwarningTableService extends BaseService<DetailwarningTable> {

	public List<DetailwarningTable> getWarningMajorByYear(int year);

	public List<DetailwarningTable> getWarningMajorByMajorCode(String majoeCode);
	
	// ���������ۼ���������Ԥ��������רҵ����ѡ����
	public String getcauseMajorCode();
	
	public void deleteByYearAndMajorCode(int year, String majorCode);
	
	public List<DetailwarningTable> getAll();


}
