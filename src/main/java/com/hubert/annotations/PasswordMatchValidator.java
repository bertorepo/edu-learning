package com.hubert.annotations;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, String> {

	List<String> weakPassword;

	@Override
	public void initialize(PasswordMatch constraintAnnotation) {
		weakPassword = Arrays.asList("1234", "admin", "password");
	}

	@Override
	public boolean isValid(String passwordField, ConstraintValidatorContext context) {
		return passwordField != null && (!weakPassword.contains(passwordField));
	}

}
