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
	public boolean isValid(String mode, ConstraintValidatorContext context) {
		if ("INSERT-UPDATE".equalsIgnoreCase(mode)) {
			return true;
		} else {
		    return false;
		}
	}

}
