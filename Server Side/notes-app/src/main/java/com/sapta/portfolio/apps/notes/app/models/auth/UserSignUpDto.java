package com.sapta.portfolio.apps.notes.app.models.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSignUpDto {

	private String Username;
	private String Password;
	private String FirstName;
	private String LastName;
	
}
