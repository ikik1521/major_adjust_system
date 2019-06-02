package cn.scuec.major_adjust_system.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.scuec.major_adjust_system.dao.BaseDao;
import cn.scuec.major_adjust_system.dao.DetailwarningTableDao;
import cn.scuec.major_adjust_system.model.DetailwarningTable;

@Service("detailwarningTableService")
public class DetailwarningTableServiceImpl extends BaseServiceImpl<DetailwarningTable> implements DetailwarningTableService {

	@Autowired
	private DetailwarningTableDao detailwarningTableDao;
	
	@Override
	public BaseDao getBaseDao() {
		return detailwarningTableDao;
	}

	@Override
	public List<DetailwarningTable> getWarningMajorByYear(int year) {
		return detailwarningTableDao.getWarningMajorByYear(year);
	}

	@Override
	public List<DetailwarningTable> getWarningMajorByMajorCode(String majoeCode) {
		return detailwarningTableDao.getWarningMajorByMajorCode(majoeCode);
	}

	@Override
	public String getcauseMajorCode() {
		return detailwarningTableDao.getcauseMajorCode();
	}

	@Override
	public void deleteByYearAndMajorCode(int year, String majorCode) {
		detailwarningTableDao.deleteByYearAndMajorCode(year, majorCode);
	}

	@Override
	public List<DetailwarningTable> getAll() {
		return detailwarningTableDao.getAll();
	}

}
