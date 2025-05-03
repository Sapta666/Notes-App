package com.sapta.portfolio.apps.notes.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sapta.portfolio.apps.notes.app.dao.UserDao;
import com.sapta.portfolio.apps.notes.app.models.auth.LoginDto;
import com.sapta.portfolio.apps.notes.app.models.auth.UserSignUpDto;
import com.sapta.portfolio.apps.notes.app.models.cnct.CnctDto;
import com.sapta.portfolio.apps.notes.app.models.common.AppSettingsDto;
import com.sapta.portfolio.apps.notes.app.models.common.OperationResultDto;
import com.sapta.portfolio.apps.notes.app.models.common.db.UsersDBDto;
import com.sapta.portfolio.apps.notes.app.utils.HelperUtils;

@Service
public class AuthService {
	
	@Autowired private UserDao userDao;
	@Autowired private HelperUtils helperUtils;
	
	public OperationResultDto<AppSettingsDto> authenticateUser(LoginDto login) {
		OperationResultDto<AppSettingsDto> result = new OperationResultDto<AppSettingsDto>();
		OperationResultDto<UsersDBDto> cnctResult = new OperationResultDto<UsersDBDto>();
		AppSettingsDto appSettings = null;		
		
		
		// get user data from database		
		cnctResult = this.userDao.getUserDataFromEncryptedUsername(login.getUsername(),true);
		
		// checking whether the data fetching operation is successful or not		
		if(cnctResult.isSuccess()) {
			appSettings = this.helperUtils.createAppSettingsFromUserInfo(cnctResult.getData());
			
			result.setData(appSettings);
			result.setSuccess(true);
		} else {
			result.setMessage(cnctResult.getMessage());
			result.setSuccess(false);
		}
		
		return result;		
	}
	
}
