package cn.scuec.major_adjust_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.scuec.major_adjust_system.dao.UserDao;
import cn.scuec.major_adjust_system.model.User;

@Service("userService")
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public User getAdmin() {
		
		User admin = userDao.queryuser();
		return admin;
	}
	
	public User getUserByUserName(String username) {
		
		User user = userDao.getUserByUserName(username);
		return user;
	}

}
