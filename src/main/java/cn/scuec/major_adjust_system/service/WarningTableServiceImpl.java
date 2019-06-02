package cn.scuec.major_adjust_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.scuec.major_adjust_system.dao.BaseDao;
import cn.scuec.major_adjust_system.dao.WarningTableDao;
import cn.scuec.major_adjust_system.model.WarningTable;

@Service("warningTableService")
public class WarningTableServiceImpl extends BaseServiceImpl<WarningTable> implements WarningTableService {

	@Autowired
	private WarningTableDao warningTableDao;
	
	@Override
	public BaseDao getBaseDao() {
		return warningTableDao;
	}

	@Override
	public void deleteWarningReasonNull() {
		warningTableDao.deleteWarningReasonNull();
	}

	@Override
	public List<WarningTable> getChongFu() {
		return warningTableDao.chongfuWarningMajor();
	}

	@Override
	public List<WarningTable> getWarningTableByMajorCode(String majorCode) {
		return warningTableDao.getWarningTableByMajorCode(majorCode);
	}

	@Override
	public void clearDate() {
		warningTableDao.clearDate();
	}

}
