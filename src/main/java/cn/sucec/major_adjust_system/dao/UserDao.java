package cn.sucec.major_adjust_system.dao;

import org.apache.ibatis.annotations.Param;

import cn.sucec.major_adjust_system.model.User;

public interface UserDao {
	
	public User queryuser();

	public User getUserByUserName(@Param("username") String username);

}
