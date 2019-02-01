package com.lhchin.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.lhchin.config.DataSourceConfiguration;

/**
 * 
 * @author LHCHIN
 *
 */
@Component
public class ProcessDao {
	private static final Logger logger = LoggerFactory.getLogger(ProcessDao.class);

	@Autowired
	DataSourceConfiguration dataSourceConfig;

	@Autowired
	JdbcTemplate jdbcTemplate;

    private void truncate(JdbcTemplate jdbcTemplate, String tableName) {
    	jdbcTemplate.execute(String.format("TRUNCATE TABLE %s", tableName));
    }
    
    public void dispatchTask(String dbName, String modeName, String targetTableName) {
    	JdbcTemplate srcJdbcTemplate = new JdbcTemplate(dataSourceConfig.getDataSourcesByDbName(dbName));

    	switch (modeName) {
    	case "TRUNCATE-ONLY":
    		truncate(srcJdbcTemplate, targetTableName);
    		break;
    	}
    }
}
