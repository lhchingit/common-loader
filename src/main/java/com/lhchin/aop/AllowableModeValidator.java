package com.lhchin.aop;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 
 * @author LHCHIN
 *
 */
public class AllowableModeValidator implements ConstraintValidator<AllowableMode, String> {

	@Override
	public boolean isValid(String modeName, ConstraintValidatorContext context) {
		return true;
	}

}
