package com.sapta.portfolio.apps.notes.app.models.common;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class AppSettingsDto {

	private String Cnct_PKey = "";
	private String Token = "";
	private String Username = "";
	private String FirstName = "";
	private String LastName = "";
//	private 
	
}
