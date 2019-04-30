package cn.sucec.major_adjust_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.sucec.major_adjust_system.dao.UserDao;
import cn.sucec.major_adjust_system.model.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public User getAdmin() {
		
		User admin=userDao.queryuser();
		return admin;
		
	}

}
