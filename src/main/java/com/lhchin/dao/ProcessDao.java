package com.lhchin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.lhchin.config.DataSourceConfiguration;
import com.lhchin.constant.Mode;
import com.lhchin.util.CommonLoaderUtil;

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
	NamedParameterJdbcTemplate jdbcTemplate;

	private static final Map<String, Object> EMPTY_PARAM_MAP = new HashMap<>(); 

	private void batchInsert(String targetTableName, List<Map<String, Object>> records, int batchSize) {
		batchUpdate(targetTableName, records, batchSize);
    }

    private void batchUpdate(String targetTableName, List<Map<String, Object>> records, int batchSize, String... primaryKeys) {
    	String sqlStmt = "";
    	if (primaryKeys.length == 0) {
    		sqlStmt = CommonLoaderUtil.generateInsertionSqlStmt(targetTableName, records.get(0));
    		logger.debug("The insertion SQL statement is: [{}]", sqlStmt);
    	} else {
    	    sqlStmt = CommonLoaderUtil.generateUpdateSqlStmt(targetTableName, records.get(0), primaryKeys);
    	    logger.debug("The update SQL statement is: [{}]", sqlStmt);
    	}

		long finalResult = 0;
		int[] updateCounts = new int[]{0};
		if (records.size() <= batchSize) {
			SqlParameterSource[] batchRecords = SqlParameterSourceUtils.createBatch(records);
			updateCounts = jdbcTemplate.batchUpdate(sqlStmt, batchRecords);
			finalResult = IntStream.of(updateCounts).sum();
		} else {
			int fromIndex = 0;
			int toIndex = batchSize;
			for (int batchIdx = 0; batchIdx <= records.size()/batchSize; batchIdx++) {
				SqlParameterSource[] batchRecords = SqlParameterSourceUtils.createBatch(records.subList(fromIndex, toIndex));
				updateCounts = jdbcTemplate.batchUpdate(sqlStmt, batchRecords);
				finalResult += IntStream.of(updateCounts).sum();

				fromIndex += batchSize;
				toIndex += batchSize;
				if (toIndex > records.size()) {
					toIndex = records.size();
				}
			}
		}
    }

    private void insertUpdate(String targetTableName, List<Map<String, Object>> records) {
    	
    }

    private void truncate(String tableName) {
    	int result = jdbcTemplate.update(String.format("TRUNCATE TABLE %s", tableName), EMPTY_PARAM_MAP);
    	logger.info("{}", result);
    }

    private void delete(String sqlStmt) {
    	int result = jdbcTemplate.update(sqlStmt, EMPTY_PARAM_MAP);
    	logger.info("{}", result);
    }

    @Transactional
    private void deleteInsert(String sqlStmt, String targetTableName, List<Map<String, Object>> records, int batchSize) {
    	delete(sqlStmt);
    	batchInsert(targetTableName, records, batchSize);
    }

    @Transactional
    private void deleteInsertUpdate(String sqlStmt, String targetTableName, List<Map<String, Object>> records) {
    	delete(sqlStmt);
    	insertUpdate(targetTableName, records);
    }

    public void dispatchTask(
    		String dbName,
    		String modeName,
    		String targetTableName,
    		String sourceSqlStmt,
    		int batchSize) {
    	List<Map<String, Object>> sourceRecords = null;
    	if (!"TRUNCATE-ONLY".equals(modeName) && !"DELETE-ONLY".equals(modeName)) {
    		JdbcTemplate srcJdbcTemplate = new JdbcTemplate(dataSourceConfig.getDataSourcesByDbName(dbName));
    		sourceRecords = srcJdbcTemplate.queryForList(sourceSqlStmt);
    	}

    	switch (Mode.getEnumValue(modeName)) {
    	case INSERT_ONLY:
    		batchInsert(targetTableName, sourceRecords, batchSize);
    		break;
    	case UPDATE_ONLY:
    		batchUpdate(targetTableName, sourceRecords, batchSize, "EMPNO");
    		break;
    	case TRUNCATE_ONLY:
    		truncate(targetTableName);
    		break;
    	case DELETE_ONLY:
    		delete(modeName.split("|")[1]);
    		break;
    	default:
    		
    	}
    }
}
