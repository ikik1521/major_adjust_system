package cn.sucec.major_adjust_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sucec.major_adjust_system.dao.BaseDao;
import cn.sucec.major_adjust_system.dao.DetailwarningTableDao;
import cn.sucec.major_adjust_system.model.DetailwarningTable;

@Service("detailwarningTableService")
public class DetailwarningTableServiceImpl extends BaseServiceImpl<DetailwarningTable> implements DetailwarningTableService {

	@Autowired
	private DetailwarningTableDao detailwarningTableDao;
	
	@Override
	public BaseDao getBaseDao() {
		return detailwarningTableDao;
	}

}
