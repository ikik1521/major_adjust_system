package cn.scuec.major_adjust_system.dao;

import org.apache.ibatis.annotations.Param;

import cn.scuec.major_adjust_system.model.User;

public interface UserDao {
	
	public User queryuser();

	public User getUserByUserName(@Param("username") String username);

}
