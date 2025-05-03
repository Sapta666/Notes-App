package com.sapta.portfolio.apps.notes.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapta.portfolio.apps.notes.app.dao.UserDao;
import com.sapta.portfolio.apps.notes.app.models.auth.UserSignUpDto;
import com.sapta.portfolio.apps.notes.app.models.cnct.CnctDto;
import com.sapta.portfolio.apps.notes.app.models.common.AppSettingsDto;
import com.sapta.portfolio.apps.notes.app.models.common.OperationResultDto;
import com.sapta.portfolio.apps.notes.app.models.common.db.UsersDBDto;
import com.sapta.portfolio.apps.notes.app.utils.HelperUtils;
import com.sapta.portfolio.apps.notes.app.utils.JWTUtils;

@Service
public class UserService {
	
	@Autowired private UserDao userDao;
	@Autowired private JWTUtils jwtUtils;
	@Autowired private HelperUtils helperUtils;
	
	public OperationResultDto<AppSettingsDto> addUser(UserSignUpDto userSignUp) {
		OperationResultDto<AppSettingsDto> operationResult = new OperationResultDto<AppSettingsDto>();
		OperationResultDto<UsersDBDto> userDBResult = null;
		String cnct_PKey = "";
		
		operationResult.setSuccess(true);
		
		try {
			// ***************************************************
			// adding the user to the database			
			OperationResultDto<String> cnct_PKeyResult = new OperationResultDto<String>();			
			
			cnct_PKeyResult = this.userDao.addUser(userSignUp);		
			if(!cnct_PKeyResult.isSuccess())
				throw new Exception(cnct_PKeyResult.getMessage());			
			cnct_PKey = cnct_PKeyResult.getData();						
			
			
			// ***************************************************			
			// after adding the user the new user_Pkey is used
			// to get its info from the table
			userDBResult = this.userDao.getUserDataFromUserPKey(cnct_PKey);
			
			if(userDBResult.isSuccess() && userDBResult.getData().getPassword().equalsIgnoreCase(userSignUp.getPassword())) {			
				
				AppSettingsDto appSettings = helperUtils.createAppSettingsFromUserInfo(userDBResult.getData());
				
				appSettings.setToken(jwtUtils.getJWTWithUsername(userDBResult.getData().getUsername()));
				
				operationResult.setData(appSettings);
				operationResult.setSuccess(true);
			} else {
				operationResult.setSuccess(false);
				operationResult.setMessage(userDBResult.getMessage());
			}
		} catch(Exception e) {
			operationResult.setSuccess(false);
			operationResult.setMessage(e.getMessage());
			e.printStackTrace();
		}
					
		return operationResult;		
	}

}
