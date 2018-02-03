package com.restbase.model;

public class ConstraintValidators {

	private ConstraintValidators() {		
	}
	
	public static <T> void checkIfParameterIsNull(T object, String parameterName){
		String messageTemplate = "Parameter %s cannot be null";
		String message = String.format(messageTemplate, parameterName);
		checkNull(object, message);				
	}

	public static <T>  void checkNull(T object, String message) {
		if(object == null){
			throw new IllegalArgumentException(message);			
		}
	}
}
