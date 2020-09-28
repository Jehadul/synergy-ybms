package com.nazdaq.ybms.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.gson.Gson;
import com.nazdaq.ybms.dao.CommonDao;

@Service("CommonService")
public class CommonServiceImpl implements CommonService {

	@Autowired
	private CommonDao commonDao;


	@Override
	public String getAuthRoleName() {
		// TODO Auto-generated method stub
		String roleName = "";
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			// auth.getAuthorities()
			Collection<? extends GrantedAuthority> auths = auth
					.getAuthorities();
			for (GrantedAuthority a : auths) {
				roleName = a.getAuthority();
				break;
			}
		}
		return roleName;
	}

	@Override
	public String getAuthUserName() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		return auth.getName();
	}
	
	@Override
	public String filterAmpersand(String stringData) {
		if (stringData.contains("&")) {
			stringData = stringData.replace("&", "'||'&'||'");
		}
		return stringData;
	}

	@Override
	public Object getMaxValueByObjectAndColumn(String objectName,
			String columnName) {
		// TODO Auto-generated method stub
		return commonDao.getMaxValueByObjectAndColumn(objectName, columnName);
	}

	@Override
	public void saveOrUpdateModelObjectToDB(Object object) {
		// TODO Auto-generated method stub
		commonDao.saveOrUpdateModelObjectToDB(object);
	}

	@Override
	public void deleteAnObjectById(String objectName, Integer id) {
		// TODO Auto-generated method stub
		commonDao.deleteAnObjectById(objectName, id);
	}

	@Override
	public Object getAnObjectByAnyUniqueColumn(String objectName,
			String columnName, String columnValue) {
		return commonDao.getAnObjectByAnyUniqueColumn(objectName, columnName,
				columnValue);
	}

	@Override
	public List<Object> getObjectListByAnyColumn(String objectName,
			String columnName, String columnValue) {
		return commonDao.getObjectListByAnyColumn(objectName, columnName,
				columnValue);
	}

	
	@Override
	public List<Object> getObjectListByAnyColumnValueList(String objectName,
			String columnName, List<String> columnValues) {
		return commonDao.getObjectListByAnyColumnValueList(objectName,
				columnName, columnValues);
	}

	@Override
	public List<Object> getObjectListByAnyColumnValueListAndOneColumn(
			String objectName, String columnName, List<String> columnValues,
			String columnName1, String columnValue1) {
		return commonDao
				.getObjectListByAnyColumnValueListAndOneColumn(objectName,
						columnName, columnValues, columnName1, columnValue1);
	}

	@Override
	public boolean isJSONValid(String JSON_STRING) {
		Gson gson = new Gson();
		try {
			gson.fromJson(JSON_STRING, Object.class);
			return true;
		} catch (com.google.gson.JsonSyntaxException ex) {
			return false;
		}
	}

	@Override
	public List<Object> getAllObjectList(String objectName) {
		// TODO Auto-generated method stub
		return commonDao.getAllObjectList(objectName);
	}
	
	@Override
	public List<Object> getObjectListOrderByAnyColumn(String objectName,
			String orderBy, String orderedFormat){
		return commonDao.getObjectListOrderByAnyColumn(objectName,orderBy, orderedFormat);
	}

	@Override
	public List<Object> getObjectListByTwoColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByTwoColumn(objectName, column1,
				columnValue1, column2, columnValue2);
	}


	@Override
	public List<Object> getObjectListByTwoColumnOrderByColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String orderingColumn, String orderType) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByTwoColumnOrderByColumn(objectName, column1,
				columnValue1, column2, columnValue2, orderingColumn, orderType);
	}

	@Override
	public List<Object> getObjectListByThreeColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByThreeColumn(objectName, column1,
				columnValue1, column2, columnValue2, column3, columnValue3);
	}
	
	@Override
	public List<Object> getObjectListByThreeColumnWithFirstColumnAsList(String objectName, String column1,
			List<String> columnValue1, String column2, String columnValue2,
			String column3, String columnValue3) {
		return commonDao.getObjectListByThreeColumnWithFirstColumnAsList(objectName, column1, columnValue1, column2, columnValue2, column3, columnValue3);
	}

	@Override
	public List<Object> getObjectListByTwoColumnWithOneNullValue(
			String objectName, String column1, String columnValue1,
			String nullColumnName) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByTwoColumnWithOneNullValue(objectName,
				column1, columnValue1, nullColumnName);
	}

	@Override
	public List<Object> getObjectListByFourColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4) {
		return commonDao.getObjectListByFourColumn(objectName, column1,
				columnValue1, column2, columnValue2, column3, columnValue3,
				column4, columnValue4);
	}

	@Override
	public List<Object> getObjectListByFiveColumn(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4, String column5,
			String columnValue5) {
		return commonDao.getObjectListByFiveColumn(objectName, column1,
				columnValue1, column2, columnValue2, column3, columnValue3,
				column4, columnValue4, column5, columnValue5);
	}

	@Override
	public void deleteAnObjectListByAnyColumn(String objectName,
			String columnName, String columnValue) {
		// TODO Auto-generated method stub
		commonDao.deleteAnObjectListByAnyColumn(objectName, columnName,
				columnValue);

	}

	@Override
	public List<Object> getObjectListByDateRange(String objectName,
			String dateColumnName, String fromDate, String toDate,
			String column1, String columnValue1) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByDateRange(objectName, dateColumnName,
				fromDate, toDate, column1, columnValue1);
	}

	@Override
	public List<Object> getObjectListByDateRangeAndTwoColumn(String objectName,
			String dateColumnName, String fromDate, String toDate,
			String column1, String columnValue1, String column2,
			String columnValue2) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByDateRangeAndTwoColumn(objectName,
				dateColumnName, fromDate, toDate, column1, columnValue1,
				column2, columnValue2);
	}

	@Override
	public List<Object> getObjectListByDateRangeAndThreeColumn(
			String objectName, String dateColumnName, String fromDate,
			String toDate, String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByDateRangeAndThreeColumn(objectName,
				dateColumnName, fromDate, toDate, column1, columnValue1,
				column2, columnValue2, column3, columnValue3);
	}

	@Override
	public List<Object> getDistinctValueListByColumnName(String objectName,
			String distinctColumnName) {
		// TODO Auto-generated method stub
		return commonDao.getDistinctValueListByColumnName(objectName,
				distinctColumnName);
	}

	@Override
	public List<Object> getObjectListByAnyColumnOrderByAnyColumn(
			String objectName, String columnName, String columnValue,
			String orderBy, String orderedFormat) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByAnyColumnOrderByAnyColumn(objectName,
				columnName, columnValue, orderBy, orderedFormat);
	}

	@Override
	public List<Object> getDistinctValueListByOneColumnNameAndValue(
			String objectName, String distinctColumnName, String column1,
			String columnValue1) {
		// TODO Auto-generated method stub
		return commonDao.getDistinctValueListByOneColumnNameAndValue(
				objectName, distinctColumnName, column1, columnValue1);
	}
	
	@Override
	public List<Object> getDistinctValueListByColumnNameAndNullValue(
			String objectName, String distinctColumnName, String column1,
			String columnValue1, String nullColumnName) {
		// TODO Auto-generated method stub
		return commonDao.getDistinctValueListByColumnNameAndNullValue(
				objectName, distinctColumnName, column1, columnValue1,
				nullColumnName);
	}

	@Override
	public List<Object> getObjectListByThreeColumnWithOneNullValue(
			String objectName, String column1, String columnValue1,
			String column2, String columnValue2, String nullColumnName) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByThreeColumnWithOneNullValue(objectName, column1, columnValue1, column2, columnValue2, nullColumnName);
	}
	
	@Override
	public List<Object> getObjectListByTwoColumnWithOneNullAndOneNotNull(
			String objectName, String columnNameNull, String columnNameNotNull) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByTwoColumnWithOneNullAndOneNotNull(objectName, columnNameNull, columnNameNotNull);
	}

	@Override
	public List<Object> getObjectListByAnyColumnValueNotInListAndOneColumn(
			String objectName, String columnName, List<String> columnValues,
			String columnName1, String columnValue1) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByAnyColumnValueNotInListAndOneColumn(objectName, columnName, columnValues, columnName1, columnValue1);
	}
	
	@Override
	public String saveFileToDrive(MultipartFile file, String rootPath, String folderName) {
		File serverFile = null;
		if (!file.isEmpty()) {
			String fileName = file.getOriginalFilename();

			String extension = "";

			int i = fileName.lastIndexOf('.');
			if (i > 0) {
				extension = fileName.substring(i + 1);
			}

			if (extension.equalsIgnoreCase("pdf")) {
				String uniqueFileName = java.util.UUID.randomUUID().toString();
				try {
					byte[] bytes = file.getBytes();

					File dir = new File(rootPath + File.separator
							+ folderName);
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					serverFile = new File(dir.getAbsolutePath()
							+ File.separator + uniqueFileName + "." + extension);
					BufferedOutputStream stream = new BufferedOutputStream(
							new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					// return serverFile.getAbsolutePath();
				} catch (Exception e) {
					return "You failed to upload " + uniqueFileName + " => "
							+ e.getMessage();
				}
				return serverFile.getAbsolutePath();
			}
		} else {
			return "";
		}
		return "";
	}
	
	@Override
	public void deleteAnObjectByTwoColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2) {
		commonDao.deleteAnObjectByTwoColumn(objectName, column1, columnValue1, column2, columnValue2);
	}
	
	@Override
	public void deleteAnObjectByThreeColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2, String column3, String columnValue3) {
		commonDao.deleteAnObjectByThreeColumn(objectName, column1, columnValue1, column2, columnValue2, column3, columnValue3);
	}
	
	@Override
	public void deleteObjectListByThreeColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2, String column3, String columnValue3) {
		commonDao.deleteObjectListByThreeColumn(objectName, column1, columnValue1, column2, columnValue2, column3, columnValue3);
	}

	@Override
	public List<Object> getObjectListByFourColumnWithoutLike(String objectName,
			String column1, String columnValue1, String column2,
			String columnValue2, String column3, String columnValue3,
			String column4, String columnValue4) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByFourColumnWithoutLike(objectName, column1,
				columnValue1, column2, columnValue2, column3, columnValue3,
				column4, columnValue4);
	}

	@Override
	public Long countTotalRowByOneColumn(String objectName,
			String columnName, String columnValue) {
		
		return commonDao.countTotalRowByOneColumn(objectName, columnName, columnValue);
	}

	@Override
	public Long countTotalRowByTwoColumnAnd(String objectName,
			String column1Name, String column1Value, String column2Name,
			String column2Value) {
		
		return commonDao.countTotalRowByTwoColumnAnd(objectName, column1Name, column1Value, column2Name, column2Value);
	}

	@Override
	public Long countTotalRowByThreeColumnsAnd(String objectName,
			String column1Name, String column1Value, String column2Name,
			String column2Value, String column3Name, String column3Value) {
		return commonDao.countTotalRowByThreeColumnsAnd(objectName, column1Name, column1Value, column2Name, column2Value, column3Name, column3Value);

	}

	@Override
	public Long countTotalRowByFourColumnsAnd(String objectName,
			String column1Name, String column1Value, String column2Name,
			String column2Value, String column3Name, String column3Value,
			String column4Name, String column4Value) {
		return commonDao.countTotalRowByFourColumnsAnd(objectName, column1Name, column1Value, column2Name, column2Value, column3Name, column3Value, column4Name, column4Value);
	}
	
	@Override
	public List<Object> getObjectListByOneColumnWithOneFixedColumnAndLike( String objectName, String columnName, String columnValue ) {
		return commonDao.getObjectListByOneColumnWithOneFixedColumnAndLike(objectName, columnName, columnValue);
	}
	
	@Override
	public List<Object> getObjectListByAnyColumnUsingOr(String objectName,
			String columnName, String columnValue1, String columnValue2){
		return commonDao.getObjectListByAnyColumnUsingOr(objectName, columnName, columnValue1, columnValue2);
		
	}
	
	@Override
	public List<Object> getObjectListByTwoColumnWithOneFixedColumnAndLikeAndCurrentStatus( String objectName, String columnName1, String columnValue1, String columnName2, List<String> columnValue2 ) {
		return commonDao.getObjectListByTwoColumnWithOneFixedColumnAndLikeAndCurrentStatus(objectName, columnName1, columnValue1, columnName2, columnValue2);
	}
	
	@Override
	public List<Object> getObjectListByTwoColumnWithOneNot( String objectName, String columnName1, String columnValue1, String  columnName2, List<String> columnValue2) {
		return commonDao.getObjectListByTwoColumnWithOneNot(objectName, columnName1, columnValue1, columnName2, columnValue2);
	}
	
	@Override
	public List<Object> getObjectListByDateRangeAndOneColumn( String objectName, String dateColumn, String fromDate, String toDate, String columnName1, String columnValue1 ) {
		return commonDao.getObjectListByDateRangeAndOneColumn(objectName, dateColumn, fromDate, toDate, columnName1, columnValue1);
	}

	@Override
	public List<Object> getObjectListByDateRangeAndTwoColumnWithFixedValue( String objectName, String dateColumn, String fromDate, String toDate, String columnName1, String columnValue1, String columnName2, String columnValue2 ) {
		return commonDao.getObjectListByDateRangeAndTwoColumnWithFixedValue(objectName, dateColumn, fromDate, toDate, columnName1, columnValue1, columnName2, columnValue2);
	}

	@Override
	public List<Object> getDistinctValueListByColumn(String objectName,
			String distinctColumnName) {
		// TODO Auto-generated method stub
		return commonDao.getDistinctValueListByColumn(objectName, distinctColumnName);
	}

	@Override
	public Integer saveWithReturnId(Object object) {
		return commonDao.saveWithReturnId(object);
	}
	
	@Override
	public List<Object> getObjectListByAnyColumnValueNotInListAndOrderByColumn(
			String objectName, String columnName, List<String> columnValues, String orderColumnName, String orderType) {
			return commonDao.getObjectListByAnyColumnValueNotInListAndOrderByColumn(objectName, columnName, columnValues, orderColumnName, orderType);
	}

	@Override
	public List<Object> getObjectListByThreeColumnWithOneNullOrValue(
			String objectName, String column1, String columnValue1,
			String column2, String columnValue2, String nullColumnName,
			String columnValue) {
		// TODO Auto-generated method stub
		return commonDao.getObjectListByThreeColumnWithOneNullOrValue(objectName, column1, columnValue1, column2, columnValue2, nullColumnName, columnValue);
	}

	@Override
	public List<String> getCurrentUserRoles() {
		// TODO Auto-generated method stub
		return commonDao.getCurrentUserRoles();
	}

	@Override
	public Boolean userHasRole(String roleName) {
		// TODO Auto-generated method stub
		return commonDao.userHasRole(roleName);
	}
	
	@Override
	public Boolean hasRoleLike( String roleName ) {
		return commonDao.hasRoleLike(roleName);
	}

	@Override
	public List<Object> getListBySqlQuery(String query) {
		// TODO Auto-generated method stub
		return commonDao.getListBySqlQuery(query);
	}
	
	@Override
	public List<Object> getObjectListByTwoColumnInAndClauseAndOneColumnInOrClause( String objectName, String column1, String value1, String column2, String value2, String column3, String value3 ) {
		return commonDao.getObjectListByTwoColumnInAndClauseAndOneColumnInOrClause(objectName, column1, value1, column2, value2, column3, value3);
	}
	
	@Override
	public List<Object> getObjectListByTwoColumnOneFixedAndOneLikeOrderByAnyColumn(String objectName, String column1, String value1, String column2, String value2, String orderColumn, String orderValue) {
		return commonDao.getObjectListByTwoColumnOneFixedAndOneLikeOrderByAnyColumn(objectName, column1, value1, column2, value2, orderColumn, orderValue);
	}
	
	@Override
	public List<Object> getObjectListByThreeColumnTwoColumnOrClauseAndOneColumnAndClauseOrderByOneColumn(String objectName, String column1, String value1, String column2, String value2, String column3, String value3, String orderColumn, String orderType) {
		return commonDao.getObjectListByThreeColumnTwoColumnOrClauseAndOneColumnAndClauseOrderByOneColumn(objectName, column1, value1, column2, value2, column3, value3, orderColumn, orderType);
	}
	
	@Override
	public List<Object> getObjectListByThreeColumnOrderByColumn(String objectName, String column1, String columnValue1, String column2, String columnValue2, String column3, String columnValue3, String orderColumn, String orderType) {
		return commonDao.getObjectListByThreeColumnOrderByColumn(objectName, column1, columnValue1, column2, columnValue2, column3, columnValue3, orderColumn, orderType);
	}
	
	@Override
	public Object getMaxValueByObjectAndTwoColumn(String objectName, String columnName, String column1, String value1) {
		return commonDao.getMaxValueByObjectAndTwoColumn(objectName, columnName, column1, value1);
	}	
		
}
