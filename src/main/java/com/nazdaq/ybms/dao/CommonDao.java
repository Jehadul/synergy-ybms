package com.nazdaq.ybms.dao;

import java.util.List;

//This data access interface is made to define common methods which will be reuse in all module.
public interface CommonDao {

	// This method will return maximum value of a column from given object
	public Object getMaxValueByObjectAndColumn(String objectName,
			String columnName);

	public void saveOrUpdateModelObjectToDB(Object object);

	Object getAnObjectByAnyUniqueColumn(String objectName, String columnName,
			String columnValue);

	List<Object> getObjectListByAnyColumn(String objectName, String columnName,
			String columnValue);

	List<Object> getObjectListByAnyColumnValueList(String objectName,
			String columnName, List<String> columnValues);

	public List<Object> getObjectListByAnyColumnValueListAndOneColumn(
			String objectName, String columnName, List<String> columnValues,
			String columnName1, String columnValue1);

	public List<Object> getAllObjectList(String objectName);

	public List<Object> getObjectListByTwoColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2);

	public void deleteAnObjectById(String objectName, Integer id);

	public List<Object> getObjectListByThreeColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3);
	
	public List<Object> getObjectListByThreeColumnWithFirstColumnAsList(String objectName, String column1,
			List<String> columnValue1, String column2, String columnValue2,
			String column3, String columnValue3);

	public List<Object> getObjectListByTwoColumnWithOneNullValue(
			String objectName, String column1, String columnValue1,
			String nullColumnName);

	public List<Object> getObjectListByFourColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4);

	public List<Object> getObjectListByFiveColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4, String column5,
			String columnValue5);

	public void deleteAnObjectListByAnyColumn(String objectName,
			String columnName, String columnValue);

	public List<Object> getObjectListByDateRange(String objectName,
			String dateColumnName, String fromDate, String toDate,
			String column1, String columnValue1);

	public List<Object> getObjectListByDateRangeAndTwoColumn(String objectName,
			String dateColumnName, String fromDate, String toDate,
			String column1, String columnValue1, String column2,
			String columnValue2);

	public List<Object> getObjectListByDateRangeAndThreeColumn(
			String objectName, String dateColumnName, String fromDate,
			String toDate, String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3);

	public List<Object> getDistinctValueListByColumnName(String objectName,
			String distinctColumnName);

	public List<Object> getObjectListByAnyColumnOrderByAnyColumn(
			String objectName, String columnName, String columnValue,
			String orderBy, String orderedFormat);

	public List<Object> getDistinctValueListByColumnNameAndNullValue(
			String objectName, String distinctColumnName, String column1,
			String columnValue1, String nullColumnName);

	public List<Object> getObjectListByThreeColumnWithOneNullValue(
			String objectName, String column1, String columnValue1,
			String column2, String columnValue2, String nullColumnName);

	public List<Object> getObjectListByTwoColumnWithOneNullAndOneNotNull(
			String objectName, String columnNameNull, String columnNameNotNull);

	public List<Object> getObjectListByAnyColumnValueNotInListAndOneColumn(
			String objectName, String columnName, List<String> columnValues,
			String columnName1, String columnValue1);

	public List<Object> getDistinctValueListByOneColumnNameAndValue(
			String objectName, String distinctColumnName, String column1,
			String columnValue1);

	public List<Object> getObjectListByFourColumnWithoutLike(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4);
	
	public void deleteAnObjectByTwoColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2);
	public void deleteAnObjectByThreeColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2, String column3, String columnValue3);
	public void deleteObjectListByThreeColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2, String column3, String columnValue3);

	List<Object> getObjectListByTwoColumnOrderByColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String orderingColumn, String orderType);

	Long countTotalRowByOneColumn(String objectName, String columnName,
			String columnValue);

	Long countTotalRowByTwoColumnAnd(String objectName, String column1Name,
			String column1Value, String column2Name, String column2Value);

	Long countTotalRowByThreeColumnsAnd(String objectName,
			String column1Name, String column1Value, String column2Name,
			String column2Value, String column3Name, String column3Value);

	Long countTotalRowByFourColumnsAnd(String objectName,
			String column1Name, String column1Value, String column2Name,
			String column2Value, String column3Name, String column3Value,
			String column4Name, String column4Value);
	
	public List<Object> getObjectListByOneColumnWithOneFixedColumnAndLike( String objectName, String columnName, String columnValue );

	List<Object> getObjectListByAnyColumnUsingOr(String objectName,
			String columnName, String columnValue1, String columnValue2);
	
	public List<Object> getObjectListByTwoColumnWithOneFixedColumnAndLikeAndCurrentStatus( String objectName, String columnName1, String columnValue1, String columnName2, List<String> columnValue2 );
	
	public List<Object> getObjectListByTwoColumnWithOneNot( String objectName, String columnName1, String columnValue1, String  columnName2, List<String> columnValue2);
	
	public List<Object> getObjectListByDateRangeAndOneColumn( String objectName, String dateColumn, String fromDate, String toDate, String columnName1, String columnValue1 );
	
	public List<Object> getObjectListByDateRangeAndTwoColumnWithFixedValue( String objectName, String dateColumn, String fromDate, String toDate, String columnName1, String columnValue1, String columnName2, String columnValue2 );

	List<Object> getDistinctValueListByColumn(String objectName,
			String distinctColumnName);

	List<Object> getObjectListOrderByAnyColumn(String objectName,
			String orderBy, String orderedFormat);

	Integer saveWithReturnId(Object object);
	
	public List<Object> getObjectListByAnyColumnValueNotInListAndOrderByColumn(
			String objectName, String columnName, List<String> columnValues, String orderColumnName, String orderType);

	List<Object> getObjectListByThreeColumnWithOneNullOrValue(
			String objectName, String column1, String columnValue1,
			String column2, String columnValue2, String nullColumnName,
			String columnValue);

	List<String> getCurrentUserRoles();

	Boolean userHasRole(String roleName);
	
	Boolean hasRoleLike( String roleName );

	List<Object> getListBySqlQuery(String query);
	
	List<Object> getObjectListByTwoColumnInAndClauseAndOneColumnInOrClause( String objectName, String column1, String value1, String column2, String value2, String column3, String value3 );
	
	List<Object> getObjectListByTwoColumnOneFixedAndOneLikeOrderByAnyColumn(String objectName, String column1, String value1, String column2, String value2, String orderColumn, String orderValue);
	
	List<Object> getObjectListByThreeColumnTwoColumnOrClauseAndOneColumnAndClauseOrderByOneColumn(String objectName, String column1, String value1, String column2, String value2, String column3, String value3, String orderColumn, String orderType);
	
	List<Object> getObjectListByThreeColumnOrderByColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2, String column3, String columnValue3, String orderColumn, String orderType);
	Object getMaxValueByObjectAndTwoColumn(String objectName, String columnName, String column1, String value1);
	public Integer getMaxValueByObject(String objectName, String columnName);
	
}
