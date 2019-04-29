package cn.sucec.major_adjust_system.service;

import java.util.List;

import cn.sucec.major_adjust_system.model.DetailwarningTable;

public interface DetailwarningTableService extends BaseService<DetailwarningTable> {

	public List<DetailwarningTable> getWarningMajorByYear(int year);

	public List<DetailwarningTable> getWarningMajorByMajorCode(String majoeCode);


}
