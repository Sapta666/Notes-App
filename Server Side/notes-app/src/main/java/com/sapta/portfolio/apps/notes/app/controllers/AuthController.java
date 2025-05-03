package com.sapta.portfolio.apps.notes.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sapta.portfolio.apps.notes.app.enums.SuccessFailedEnum;
import com.sapta.portfolio.apps.notes.app.models.auth.LoginDto;
import com.sapta.portfolio.apps.notes.app.models.auth.UserSignUpDto;
import com.sapta.portfolio.apps.notes.app.models.common.AppSettingsDto;
import com.sapta.portfolio.apps.notes.app.models.common.OperationResultDto;
import com.sapta.portfolio.apps.notes.app.models.common.ResponseDataDto;
import com.sapta.portfolio.apps.notes.app.services.AuthService;
import com.sapta.portfolio.apps.notes.app.services.UserService;

@RestController
@RequestMapping("auth")
//@CrossOrigin(origins = "*")
public class AuthController {
	
	@Autowired private AuthService authService;
	@Autowired private UserService userService;
	

	@PostMapping("/signup")
	public ResponseDataDto<AppSettingsDto> signup(@RequestBody UserSignUpDto userSignUp) {
		OperationResultDto<AppSettingsDto> result;
		ResponseDataDto<AppSettingsDto> response = new ResponseDataDto<AppSettingsDto>();			
		
		// *********************************
		//	Calling service for adding new user
		result = this.userService.addUser(userSignUp);		
		
		// *********************************
		// calculating response based on result
		if(result.isSuccess()) {
			response.setData(result.getData());
			response.setStatus(SuccessFailedEnum.SUCCESS.name());
		} else {
			response.setStatus(SuccessFailedEnum.FAILED.name());
			response.setMessage(result.getMessage());
		}
		
		return response;
	}
	
	@PostMapping("/login")
	public ResponseDataDto<AppSettingsDto> login(@RequestBody LoginDto login) {
		OperationResultDto<AppSettingsDto> result;
		ResponseDataDto<AppSettingsDto> response = new ResponseDataDto<AppSettingsDto>();			
		
		result = this.authService.authenticateUser(login);
		if(result.isSuccess()) {
			response.setData(result.getData());
			response.setStatus(SuccessFailedEnum.SUCCESS.name());
		} else {
			response.setStatus(SuccessFailedEnum.FAILED.name());
			response.setMessage(result.getMessage());
		}
		
		return response;
	}
	
}
