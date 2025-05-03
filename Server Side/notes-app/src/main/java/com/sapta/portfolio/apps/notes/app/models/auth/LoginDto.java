package com.sapta.portfolio.apps.notes.app.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {
	
	private String Username;
	private String Password;
	
}


