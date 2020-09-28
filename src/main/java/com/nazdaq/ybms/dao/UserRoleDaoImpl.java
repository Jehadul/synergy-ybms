package com.nazdaq.ybms.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nazdaq.ybms.model.UserRole;




/**
 * @author nasrin.akter
 *
 */
@Repository("userRoleDao")
@Transactional
public class UserRoleDaoImpl implements UserRoleDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addUserRole(UserRole userRole) {
		sessionFactory.getCurrentSession().saveOrUpdate(userRole);
	}

	@SuppressWarnings("unchecked")
	public List<UserRole> listUserRole() {
		return (List<UserRole>) sessionFactory.getCurrentSession().createCriteria(UserRole.class).list();
	}

	public UserRole getUserRole(int userroleid) {
		return (UserRole) sessionFactory.getCurrentSession().get(UserRole.class, userroleid);
	}

	public void deleteUserRole(UserRole userRole) {
		sessionFactory.getCurrentSession().createQuery("DELETE FROM UserRole WHERE userroleid = "+userRole.getUserRoleId()).executeUpdate();
	}

	public List<String> getUserRoleName(){
		String hql = "select DISTINCT authority from UserRole"; 
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		List<String> urList = query.list();
		return urList;
	}

}
