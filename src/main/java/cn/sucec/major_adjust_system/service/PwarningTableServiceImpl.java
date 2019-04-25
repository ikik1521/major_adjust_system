package cn.sucec.major_adjust_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sucec.major_adjust_system.dao.BaseDao;
import cn.sucec.major_adjust_system.dao.PwarningTableDao;
import cn.sucec.major_adjust_system.model.PwarningTable;

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
		System.out.println("======pwarningTableDao:" + pwarningTableDao);
		pwarningTableDao.addOne(pwarningTable);
	}

}
