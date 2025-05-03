package com.sapta.portfolio.apps.notes.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sapta.portfolio.apps.notes.app.dao.UserDao;
import com.sapta.portfolio.apps.notes.app.models.cnct.CnctDto;
import com.sapta.portfolio.apps.notes.app.models.common.OperationResultDto;
import com.sapta.portfolio.apps.notes.app.models.common.UserDetailsDto;
import com.sapta.portfolio.apps.notes.app.models.common.db.UsersDBDto;

@Service
public class AppUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		OperationResultDto<UsersDBDto> result = new OperationResultDto<UsersDBDto>();
		
		result = this.userDao.getUserDataFromEncryptedUsername(username, false);
		
		if(result.isSuccess()) 					
			return new UserDetailsDto(result.getData().getUsername(),result.getData().getPassword());
		
		return null;			
	}	
	
}
