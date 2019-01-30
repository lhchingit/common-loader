package com.lhchin.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 
 * @author LHCHIN
 *
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AllowableModeValidator.class)
public @interface AllowableMode {
	String message() default "{com.lhchin.allowable.mode.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
