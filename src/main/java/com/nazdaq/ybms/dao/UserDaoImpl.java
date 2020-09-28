package com.nazdaq.ybms.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nazdaq.ybms.model.User;



/**
 * @author nasrin.akter
 *
 */
@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public void addUser(User user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
	}

	@SuppressWarnings("unchecked")
	public List<User> listUser() {
		return (List<User>) sessionFactory.getCurrentSession().createCriteria(User.class).list();
	}

	public User getUser(int id) {
		return (User) sessionFactory.getCurrentSession().get(User.class, id);
	}

	public void deleteUser(User user) {
		sessionFactory.getCurrentSession().createQuery("DELETE FROM User WHERE id = "+user.getId()).executeUpdate();
	}


	public User getUser(String username) {
		
			Query query = sessionFactory.getCurrentSession().createQuery(
					"from User where userName = :userName");
			query.setParameter("userName", username);
			return (User) query.list().get(0);
	}
	
	
	public String getUserNameById(int uId){
		Query query=null;
		String hql = "select userName from User where id = "+uId; 
		List<String> userName = query.list();
		return userName.get(0);
	}
	
	public User getUserByEmpId(Short empId){
		String hql = "from User where empId = "+empId; 
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
		List<User> uList = query.list();
		return uList.get(0);
	}
	
	
	public boolean isExistsUser(String username) {
		boolean exists=false;
		String hql = null;
	
		if(username!=null){
			
		 hql = "from User where userName='"+username+"'"; 
		 }
			
		Query query=sessionFactory.getCurrentSession().createQuery(hql);
			
		List<User> listUser = query.list();
		if(listUser.size()>0){
			exists=true;
		}
		return exists;
	}

}
