package com.example.lhchin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.lhchin.CommonLoaderApplication;
import com.lhchin.config.DataSourceConfiguration;
import com.lhchin.dao.ProcessDao;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonLoaderApplication.class)
public class CommonLoaderApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(CommonLoaderApplicationTests.class);

	private static boolean isTestingDataInitialized = false;

	@Autowired
	DataSourceConfiguration dataSourceConfig;

	@Autowired
	ProcessDao processDao;

	private JdbcTemplate srcdbcTemplate1;

	@Autowired
	JdbcTemplate targetJdbcTemplate;

	@Before
	public void initializeTestingData() {
		if (!isTestingDataInitialized) {
			srcdbcTemplate1 = new JdbcTemplate(dataSourceConfig.getDataSourcesByDbName("db1"));
			srcdbcTemplate1.execute("CREATE TABLE PERSON_SOURCE (FIRST_NAME VARCHAR2(16), LAST_NAME VARCHAR2(16), ADDRESS VARCHAR2(128))");
			srcdbcTemplate1.execute(""
					+ "INSERT INTO PERSON_SOURCE (FIRST_NAME, LAST_NAME, ADDRESS) VALUES ('Dana', 'Whitley', '464 Gorsuch Drive');"
					+ "INSERT INTO PERSON_SOURCE (FIRST_NAME, LAST_NAME, ADDRESS) VALUES ('Robin', 'Cash', '64 Zella Park');");
			
			targetJdbcTemplate.execute("CREATE TABLE PERSON_TARGET (FIRST_NAME VARCHAR2(16), LAST_NAME VARCHAR2(16), ADDRESS VARCHAR2(128))");
			isTestingDataInitialized = true;
		}
	}

	@Test
	public void truncateTest() {
		String tableName = "PERSON_SOURCE";
		printTotalCount(srcdbcTemplate1, tableName);
		processDao.dispatchTask("db1", "TRUNCATE-ONLY", tableName);
		printTotalCount(srcdbcTemplate1, tableName);
	}

	private void printTotalCount(JdbcTemplate jdbcTemplate, String tableName) {
		logger.info("Total count of [{}] is [{}].",
				tableName,
				jdbcTemplate.queryForObject(String.format("SELECT COUNT(*) FROM %s", tableName), Integer.class));
	}
}

