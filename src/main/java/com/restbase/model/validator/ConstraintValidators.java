package com.restbase.model.validator;

import java.util.Optional;

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
	
	public static <T>  void checkPresent(Optional<T> object, String message) {
		object.orElseThrow(() -> new IllegalArgumentException(message));
	}
}
