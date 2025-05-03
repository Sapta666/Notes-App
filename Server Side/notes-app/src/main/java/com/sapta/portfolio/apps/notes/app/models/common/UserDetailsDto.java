package com.sapta.portfolio.apps.notes.app.models.common;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class UserDetailsDto implements UserDetails {
	
	private String Username = "";
	private String Password = "";

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {		
		return this.Password;
	}

	@Override
	public String getUsername() {		
		return this.Username;
	}

}
