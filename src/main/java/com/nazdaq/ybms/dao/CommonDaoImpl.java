package com.nazdaq.ybms.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.StandardBasicTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository("CommonDao")
@Transactional
public class CommonDaoImpl implements CommonDao {

	private static final Logger logger = LoggerFactory
			.getLogger(CommonDaoImpl.class);

	// sessionFactory for get Current Session and comes form servlet-context.xml
	@Autowired
	private SessionFactory sessionFactory;


	@SuppressWarnings("rawtypes")
	@Override
	public Object getMaxValueByObjectAndColumn(String objectName,
			String columnName) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("select max(" + columnName
				+ ") from " + objectName);

		Integer max = 0;

		for (Iterator it = query.iterate(); it.hasNext();) {
			Object obj = it.next();
			if (obj != null) {
				max = (Integer) obj;

			}

			System.out.println("MAX ID : " + max);

		}

		return max;
	}

	@Override
	public void saveOrUpdateModelObjectToDB(Object object) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(object);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}
	
	@Override
	public Integer saveWithReturnId(Object object) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Integer id = (Integer) session.save(object);
			return id;

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;

	}

	@Override
	public Object getAnObjectByAnyUniqueColumn(String objectName,
			String columnName, String columnValue) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ columnName + " = '" + columnValue + "'");

			return query.list().get(0);

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByAnyColumnValueList(String objectName,
			String columnName, List<String> columnValues) {
		try {
			String q = "";
			for (String s : columnValues) {
				q = q + "'" + s + "', ";
			}
			q = q.trim();
			q = q.substring(0, q.length() - 1);

			Session session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from " + objectName + " where "
					+ columnName + " in (" + q + ") order by id desc");

			/*
			 * Criteria criteria = session.createCriteria(objectName);
			 * criteria.add(Restrictions.in(columnName, columnValues));
			 * List<Object> g=criteria.list();
			 */

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByAnyColumnValueListAndOneColumn(
			String objectName, String columnName, List<String> columnValues,
			String columnName1, String columnValue1) {
		try {
			String q = "";
			for (String s : columnValues) {
				q = q + "'" + s + "', ";
			}
			q = q.trim();
			q = q.substring(0, q.length() - 1);

			Session session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from " + objectName + " where "
					+ columnName + " in (" + q + ") and " + columnName1 + " = '"
					+ columnValue1 + "' order by id desc");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByAnyColumnValueNotInListAndOneColumn(
			String objectName, String columnName, List<String> columnValues,
			String columnName1, String columnValue1) {
		try {
			String q = "";
			for (String s : columnValues) {
				q = q + "'" + s + "', ";
			}
			q = q.trim();
			q = q.substring(0, q.length() - 1);

			Session session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from " + objectName + " where "
					+ columnName + " not in (" + q + ") and " + columnName1 + " = "
					+ columnValue1 + " order by id desc");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByAnyColumn(String objectName,
			String columnName, String columnValue) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ columnName + " = '" + columnValue + "'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getListBySqlQuery(String queryString) {
		try {
			Session session = sessionFactory.getCurrentSession();
			//session.beginTransaction().commit();
			//return session.createSQLQuery(query).list();
			Query query = session.createQuery(queryString);
			return query.list();
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListOrderByAnyColumn(
			String objectName,
			String orderBy, String orderedFormat) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " order by "
					+ orderBy + " " + orderedFormat);
			
			return query.list();
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByAnyColumnOrderByAnyColumn(
			String objectName, String columnName, String columnValue,
			String orderBy, String orderedFormat) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ columnName + " = '" + columnValue + "'" + " order by "
					+ orderBy + " " + orderedFormat);

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAllObjectList(String objectName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.getCurrentSession();
		try {

			Query query = session.createQuery("from " + objectName);

			return query.list();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByTwoColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and " + column2
					+ " = '" + columnValue2 + "'");
			List<Object> list=query.list();
			// return query.list();
			return list;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByTwoColumnOrderByColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2,String orderingColumn, String orderType) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and " + column2
					+ " = '" + columnValue2 + "'"+ "order by "+orderingColumn+" "+orderType+"");
			List<Object> list=query.list();
			// return query.list();
			return list;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public void deleteAnObjectById(String objectName, Integer id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Object object = getAnObjectByAnyUniqueColumn(objectName, "id", id
					+ "");
			session.delete(object);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByThreeColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and " + column2
					+ " = '" + columnValue2 + "' and " + column3 + " = '"
					+ columnValue3 + "'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getObjectListByThreeColumnWithFirstColumnAsList(String objectName, String column1,
			List<String> columnValue1, String column2, String columnValue2,
			String column3, String columnValue3) {
		try {
			
			String q = "";
			for (String s : columnValue1) {
				q = q + "'" + s + "', ";
			}
			q = q.trim();
			q = q.substring(0, q.length() - 1);

			Session session = sessionFactory.getCurrentSession();
			
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " in (" + q + ") and " + column2
					+ " = '" + columnValue2 + "' and " + column3 + " = '"
					+ columnValue3 + "'");
			
			return query.list();
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByTwoColumnWithOneNullValue(
			String objectName, String column1, String columnValue1,
			String nullColumnName) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and "
					+ nullColumnName + " is null");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByThreeColumnWithOneNullValue(
			String objectName, String column1, String columnValue1,
			String column2, String columnValue2, String nullColumnName) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and " + column2
					+ " = '" + columnValue2 + "' and " + nullColumnName
					+ " is null");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByThreeColumnWithOneNullOrValue(
			String objectName, String column1, String columnValue1,
			String column2, String columnValue2, String nullColumnName, String columnValue) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and " + column2
					+ " = '" + columnValue2 + "' and (" + nullColumnName 					
					+ " is null or "+ nullColumnName+" = "+columnValue+" )");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByTwoColumnWithOneNullAndOneNotNull(
			String objectName, String columnNameNull, String columnNameNotNull) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ columnNameNull + "  is null and " + columnNameNotNull
					+ " is not null");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getDistinctValueListByOneColumnNameAndValue(
			String objectName, String distinctColumnName, String column1,
			String columnValue1) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select distinct ( "
					+ distinctColumnName + " ) from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' ");
			List<Object> objList = query.list();
			return objList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getDistinctValueListByColumn(String objectName, String distinctColumnName) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select distinct ( "
					+ distinctColumnName + " ) from " + objectName + "");
			List<Object> objList = query.list();
			return objList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getDistinctValueListByColumnNameAndNullValue(
			String objectName, String distinctColumnName, String column1,
			String columnValue1, String nullColumnName) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select distinct ( "
					+ distinctColumnName + " ) from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and "
					+ nullColumnName + " is null");
			List<Object> objList = query.list();
			return objList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> getObjectListByFourColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and " + column2
					+ " = '" + columnValue2 + "' and " + column3 + " = '"
					+ columnValue3 + "' and " + column4 + " like '%"
					+ columnValue4 + "%'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object> getObjectListByFourColumnWithoutLike(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and " + column2
					+ " = '" + columnValue2 + "' and " + column3 + " = '"
					+ columnValue3 + "' and " + column4 + "= '"
					+ columnValue4 + "'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Object> getObjectListByFiveColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4, String column5,
			String columnValue5) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ column1 + " = '" + columnValue1 + "' and " + column2
					+ " = '" + columnValue2 + "' and " + column3 + " = '"
					+ columnValue3 + "' and " + column4 + " ='" + columnValue4
					+ "' and " + column5 + " ='" + columnValue5 + "'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public void deleteAnObjectListByAnyColumn(String objectName,
			String columnName, String columnValue) {
		try {
			Session session = sessionFactory.getCurrentSession();
			List<Object> objectList = getObjectListByAnyColumn(objectName,
					columnName, columnValue);
			for (Object ob : objectList) {
				session.delete(ob);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByDateRange(String objectName,
			String dateColumnName, String fromDate, String toDate,
			String column1, String columnValue1) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ dateColumnName + " BETWEEN  TO_DATE('" + fromDate
					+ "', 'yyyy/MM/dd') and TO_DATE('" + toDate
					+ "', 'yyyy/MM/dd') and " + column1 + " like '%"
					+ columnValue1 + "%'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	/**Checking a Column by two date 
	 * and checking two coulmns using like query
	 * @return List of object
	 */

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByDateRangeAndTwoColumn(String objectName,
			String dateColumnName, String fromDate, String toDate,
			String column1, String columnValue1, String column2,
			String columnValue2) {
		/**Checking a Column by two date 
		 * and checking two coulmns using like query
		 * @return List of object
		 * */
		
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			
			/*Query query = session.createQuery("from " + objectName + " where "
					+ dateColumnName + " BETWEEN  TO_DATE('" + fromDate
					+ "', 'yyyy/MM/dd') and TO_DATE('" + toDate
					+ "', 'yyyy/MM/dd') and " + column1 + " like '%"
					+ columnValue1 + "%'and " + column2 + " like '%"
					+ columnValue2 + "%'");*/
			
			Query query = session.createQuery("from " + objectName + " where "
					+ dateColumnName + " >= '" + fromDate
					+ dateColumnName + " <= '" + toDate
					+ "' and " + column1 + " like '%"
					+ columnValue1 + "%'and " + column2 + " like '%"
					+ columnValue2 + "%'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByDateRangeAndThreeColumn(
			String objectName, String dateColumnName, String fromDate,
			String toDate, String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ dateColumnName + " BETWEEN  TO_DATE('" + fromDate
					+ "', 'yyyy/MM/dd') and TO_DATE('" + toDate
					+ "', 'yyyy/MM/dd') and " + column1 + " like '%"
					+ columnValue1 + "%'and " + column2 + " like '%"
					+ columnValue2 + "%'and " + column3 + " like '%"
					+ columnValue3 + "%'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getDistinctValueListByColumnName(String objectName,
			String distinctColumnName) {
		// TODO Auto-generated method stub
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select distinct ( "
					+ distinctColumnName + " ) from " + objectName +" order by "+ distinctColumnName + " ASC");
			
			List<Object> objList = query.list();
			return objList;

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@Override
	public void deleteAnObjectByTwoColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2) {
		try {
			Session session = sessionFactory.getCurrentSession();
			List<Object> object = getObjectListByTwoColumn(objectName, column1, columnValue1, column2, columnValue2);
			session.delete(object.get(0));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@Override
	public void deleteAnObjectByThreeColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2, String column3, String columnValue3) {
		try {
			Session session = sessionFactory.getCurrentSession();
			List<Object> object = getObjectListByThreeColumn(objectName, column1, columnValue1, column2, columnValue2, column3, columnValue3);
			session.delete(object.get(0));

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@Override
	public void deleteObjectListByThreeColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2, String column3, String columnValue3) {
		try {
			Session session = sessionFactory.getCurrentSession();
			List<Object> objectList = getObjectListByThreeColumn(objectName, column1, columnValue1, column2, columnValue2, column3, columnValue3);
			for (Object ob : objectList) {
				session.delete(ob);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	
	
	
	@Override
	public Long countTotalRowByOneColumn(String objectName, String columnName, String columnValue) {
		 
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("select count(*) from " + objectName + " where "+ columnName + " = '" + columnValue + "'");
			Long count = (Long) query.uniqueResult();			
				return count;


		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}		 
		
	}
	
	@Override
	public Long countTotalRowByTwoColumnAnd(String objectName, String column1Name, String column1Value, String column2Name, String column2Value) {
		 
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("select count(*) from " + objectName + " where "+ column1Name + " = '" + column1Value + "' and "+ column2Name + " = '" + column2Value + "'");
			Long count = (Long) query.uniqueResult();			
				return count;


		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}		 
		
	}
	
	@Override
	public Long countTotalRowByThreeColumnsAnd(String objectName, String column1Name, String column1Value, String column2Name, String column2Value, String column3Name, String column3Value) {
		 
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("select count(*) from " + objectName + " where "+ column1Name + " = '" + column1Value + "' and "+ column2Name + " = '" + column2Value + "' and "+ column3Name + " = '" + column3Value + "'");
			Long count = (Long) query.uniqueResult();			
				return count;


		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}		 
		
	}
	
	@Override
	public Long countTotalRowByFourColumnsAnd(String objectName, String column1Name, String column1Value, String column2Name, String column2Value, String column3Name, String column3Value, String column4Name, String column4Value) {
		 
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("select count(*) from " + objectName + " where "+ column1Name + " = '" + column1Value + "' and "+ column2Name + " = '" + column2Value + "' and "+ column3Name + " = '" + column3Value + "'  and "+ column4Name + " = '" + column4Value + "' ");
			Long count = (Long) query.uniqueResult();			
				return count;


		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}		 
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByOneColumnWithOneFixedColumnAndLike( String objectName, String columnName, String columnValue ) {
		
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery( "from "+ objectName +" where "+ columnName +" = '"+columnValue+"' or "+columnName+" like '%"+columnValue+"%'");
			List<Object> objList = query.list();
			return objList;
		} catch(Exception ex) {
			logger.error( ex.getMessage() );
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByAnyColumnUsingOr(String objectName,
			String columnName, String columnValue1, String columnValue2) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where "
					+ columnName + " = '" + columnValue1 + "' or "+ columnName + " = '" + columnValue2 + "'");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	//TODO: Should improve the query or move this method in other DAO
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByTwoColumnWithOneFixedColumnAndLikeAndCurrentStatus( String objectName, String columnName1, String columnValue1, String columnName2, List<String> columnValue2 ) {
		
		try {

			String q = "";
			for (String s : columnValue2) {
				q = q + "'" + s + "', ";
			}
			q = q.trim();
			q = q.substring(0, q.length() - 1);

			Session session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from " + objectName + " where "
					+ columnName2 + " not in (" + q + ") and (" + columnName1 + " = '"
					+ columnValue1 + "' or "+ columnName1 + " like '%" + columnValue1 + "%') and (currentStateCode between 100 and 900)");
			
			return query.list();
			
		} catch( Exception e ) {
			logger.error( e.getMessage() );
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByTwoColumnWithOneNot( String objectName, String columnName1, String columnValue1, String  columnName2, List<String> columnValue2) {
		
		try {
			
			String q = "";
			for (String s : columnValue2) {
				q = q + "'" + s + "', ";
			}
			q = q.trim();
			q = q.substring(0, q.length() - 1);
			
			Session session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from " + objectName + " where " + columnName1 + " = '" + columnValue1 + "' AND " + columnName2 + " not in (" + q + ")");
			return query.list();
		} catch( Exception e ) {
			logger.error( e.getMessage() );
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByDateRangeAndOneColumn( String objectName, String dateColumn, String fromDate, String toDate, String columnName1, String columnValue1 ) {
		try {
			Session session = sessionFactory.getCurrentSession();
			
			//Query query = session.createQuery("from " + objectName + " where " + dateColumn + " between TO_DATE('" + fromDate + "', 'dd-MM-yyyy') and TO_DATE('" + toDate + "', 'dd-MM-yyyy' ) and " + columnName1 + " = '" + columnValue1 + "'");
			Query query = session.createQuery("from " + objectName + " where " + dateColumn + " >= '" + fromDate + "' and " + dateColumn + " <= '" + toDate + "' and " + columnName1 + " = '" + columnValue1 + "'");
			return query.list();
		} catch( Exception e ) {
			logger.error( e.getMessage() );
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByDateRangeAndTwoColumnWithFixedValue( String objectName, String dateColumn, String fromDate, String toDate, String columnName1, String columnValue1, String columnName2, String columnValue2 ) {
		try {
			
			Session session = sessionFactory.getCurrentSession();
			//Query query = session.createQuery("from " + objectName + " where " + dateColumn + " between TO_DATE('" + fromDate + "', 'dd-MM-yyyy') and TO_DATE('" + toDate + "', 'dd-MM-yyyy' ) and " + columnName1 + " = '" + columnValue1 + "' and " + columnName2 + " = '" + columnValue2 + "'");
			Query query = session.createQuery("from " + objectName + " where " + dateColumn + " >= '" + fromDate + "' and " + dateColumn + " <= '" + toDate + "' and " + columnName1 + " = '" + columnValue1 + "' and " + columnName2 + " = '" + columnValue2 + "'");
			return query.list();
			
		} catch( Exception e ) {
			logger.error( e.getMessage() );
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByAnyColumnValueNotInListAndOrderByColumn( String objectName, String columnName, List<String> columnValues, String orderColumnName, String orderType ) {
		try {
			String q = "";
			for (String s : columnValues) {
				q = q + "'" + s + "', ";
			}
			q = q.trim();
			q = q.substring(0, q.length() - 1);

			Session session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from " + objectName + " where "
					+ columnName + " not in (" + q + ") order by " + orderColumnName + " " + orderType );

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getCurrentUserRoles(){
		List<String> roles = new ArrayList<String>();
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		Iterator<SimpleGrantedAuthority> iterator = authorities.iterator();
		while (iterator.hasNext()) {
			String each = iterator.next().getAuthority();
			roles.add(each);
		}
		return roles;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Boolean userHasRole(String roleName){
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		Iterator<SimpleGrantedAuthority> iterator = authorities.iterator();
		while (iterator.hasNext()) {
			if(roleName.equals(iterator.next().getAuthority())){
				return true;
			}
			
			//SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(roleName);
		}
		return false;
	}
	
	@Override
	public Boolean hasRoleLike( String roleName ) {
		List<String> roles = this.getCurrentUserRoles();
		int counter = 0;
		
		for (String role : roles) {
			if( role.contains( roleName ) ) {
				counter++;
				break;
			}
		}
		
		if( counter > 0 ) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByTwoColumnInAndClauseAndOneColumnInOrClause( String objectName, String column1, String value1, String column2, String value2, String column3, String value3 ) {
		try {

			Session session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from " + objectName + " where (" +
					column1 +" = '" + value1 + "' AND " + column2 + "='" + value2 + "' ) OR (" + column3 + "='" + value3 + "') ORDER BY id DESC");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByTwoColumnOneFixedAndOneLikeOrderByAnyColumn(String objectName, String column1, String value1, String column2, String value2, String orderColumn, String orderValue) {
		try {

			Session session = sessionFactory.getCurrentSession();

			Query query = session.createQuery("from " + objectName + " where (" +
					column1 +" = '" + value1 + "' and " + column2 + " like '" + value2 + "%' ) order by "+ orderColumn +" "+ orderValue +"");

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByThreeColumnTwoColumnOrClauseAndOneColumnAndClauseOrderByOneColumn(String objectName, String column1, String value1, String column2, String value2, String column3, String value3, String orderColumn, String orderType) {
		try {
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where (" + column1 + " = '" + value1 + "' or " + column2 + " = '" + value2 + "') and " + column3 + " = '" + value3 + "' order by " + orderColumn + " " + orderType);
			
			return query.list();
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getObjectListByThreeColumnOrderByColumn(String objectName, String column1, String value1, String column2, String value2, String column3, String value3, String orderColumn, String orderType) {
		try {

			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from " + objectName + " where " + column1 + " = '" + value1 + "' AND " + column2 + " = '" + value2 + "' and " + column3 + " = '" + value3 + "' order by " + orderColumn + " " + orderType);

			return query.list();

		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Object getMaxValueByObjectAndTwoColumn(String objectName, String columnName, String column1, String value1) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("select max(" + columnName
				+ ") from " + objectName + " where " + column1 + " = " + value1);

		int max = 0;

		for (Iterator it = query.iterate(); it.hasNext();) {
			Object obj = it.next();
			if (obj != null) {
				max = (Integer) obj;

			}

			System.out.println("MAX ID : " + max);

		}

		return max;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Integer getMaxValueByObject(String objectName, String columnName) {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery("select max(" + columnName
				+ ") from " + objectName + "");

		Integer max = 0;
		for (Iterator it = query.iterate(); it.hasNext();) {
			Object obj = it.next();
			if (obj != null) {
				max = (Integer) obj;

			}
			System.out.println("MAX ID : " + max);

		}

		return max;
	}
	
	
}
