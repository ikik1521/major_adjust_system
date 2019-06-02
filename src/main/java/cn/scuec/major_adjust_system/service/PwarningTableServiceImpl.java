package cn.scuec.major_adjust_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.scuec.major_adjust_system.dao.BaseDao;
import cn.scuec.major_adjust_system.dao.PwarningTableDao;
import cn.scuec.major_adjust_system.model.PwarningTable;

@Service("pwarningTableService")
public class PwarningTableServiceImpl extends BaseServiceImpl<PwarningTable> implements PwarningTableService {

	@Autowired
	private PwarningTableDao pwarningTableDao;
	
	@Override
	public BaseDao getBaseDao() {
		return pwarningTableDao;
	}

	@Override
	public void addOne(PwarningTable pwarningTable) {
		//System.out.println("======pwarningTableDao:" + pwarningTableDao);
		pwarningTableDao.addOne(pwarningTable);
	}

	@Override
	public List<PwarningTable> getWarningMajor() {
		return pwarningTableDao.getWarningMajor();
	}

	@Override
	public List<PwarningTable> getPwarningTableByMajorCode(String majorCode) {
		return pwarningTableDao.getPwarningTableByMajorCode(majorCode);
	}

	@Override
	public void clearDate() {
		pwarningTableDao.clearDate();
	}

}
