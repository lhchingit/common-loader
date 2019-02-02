package com.lhchin.aop;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.lhchin.constant.Mode;

/**
 * 
 * @author LHCHIN
 *
 */
public class AllowableModeValidator implements ConstraintValidator<AllowableMode, String> {

	@Override
	public boolean isValid(String modeName, ConstraintValidatorContext context) {
		return Mode.getAllModesAliasNameAsSet().contains(modeName);
	}

}
