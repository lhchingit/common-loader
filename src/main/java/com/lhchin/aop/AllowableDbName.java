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
@Constraint(validatedBy = AllowableDbNameValidator.class)
public @interface AllowableDbName {
	String message() default "{com.lhchin.allowable.dbName.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
