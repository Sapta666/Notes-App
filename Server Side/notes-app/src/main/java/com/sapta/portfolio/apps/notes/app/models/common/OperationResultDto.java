package com.sapta.portfolio.apps.notes.app.models.common;

import lombok.Data;

@Data
public class OperationResultDto<T> {

	private boolean isSuccess;
	private String message;
	private T data;
	
}
