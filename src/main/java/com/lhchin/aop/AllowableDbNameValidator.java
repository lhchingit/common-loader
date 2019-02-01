package com.lhchin.aop;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.lhchin.config.DataSourceConfiguration;

/**
 * 
 * @author LHCHIN
 *
 */
public class AllowableDbNameValidator implements ConstraintValidator<AllowableDbName, String> {
	@Autowired
	DataSourceConfiguration dataSourceConfig;

	@Override
	public boolean isValid(String dbName, ConstraintValidatorContext context) {
		return (dataSourceConfig.getDataSourcesByDbName(dbName) != null);
	}

}
