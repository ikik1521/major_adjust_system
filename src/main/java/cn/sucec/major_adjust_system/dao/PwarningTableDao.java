package cn.sucec.major_adjust_system.dao;

import org.apache.ibatis.annotations.Param;

import cn.sucec.major_adjust_system.model.PwarningTable;

public interface PwarningTableDao extends BaseDao {
	
	public void addOne(PwarningTable pwarningTable);
	
}
