package com.nazdaq.ybms.dao;

import java.util.List;

import com.nazdaq.ybms.model.UserRole;





/**
 * @author abu.taleb
 *
 */
public interface UserRoleDao {
	
	public void addUserRole(UserRole userRole);

	public List<UserRole> listUserRole();
	
	public UserRole getUserRole(int userroleid);
	
	public void deleteUserRole(UserRole userRole);
	
	public List<String> getUserRoleName();
}
