package com.nazdaq.ybms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nazdaq.ybms.dao.UserDao;
import com.nazdaq.ybms.model.User;




/**
 * @author abu.taleb
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	
	public void addUser(User user) {
		userDao.addUser(user);
	}
	
	public List<User> listUser() {
		return userDao.listUser();
	}

	public User getUser(int id) {
		return userDao.getUser(id);
	}
	
	public void deleteUser(User user) {
		userDao.deleteUser(user);
	}

	public User getUser(String username) {
		return userDao.getUser(username);
	}

	public String getUserNameById(int uId) {
		return userDao.getUserNameById(uId);
	}
	
	public boolean isExistsUser(String username) {
		return userDao.isExistsUser(username);
	}
	
	public User getUserByEmpId(Short empId){
		return userDao.getUserByEmpId(empId);
	}
	
}
