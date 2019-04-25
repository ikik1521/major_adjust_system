package cn.sucec.major_adjust_system.dao;

import java.util.List;
import java.util.Map;

import cn.sucec.major_adjust_system.model.WarningTable;

public interface WarningTableDao extends BaseDao{
	
	public List<WarningTable> getAll();
	
}
