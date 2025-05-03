package com.sapta.portfolio.apps.notes.app.models.common;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class ResponseDataDto<T> extends BasicResponseDto {

	private T Data;
	
}
