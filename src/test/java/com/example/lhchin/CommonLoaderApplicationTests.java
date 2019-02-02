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
import org.springframework.util.StopWatch;

import com.lhchin.CommonLoaderApplication;
import com.lhchin.config.DataSourceConfiguration;
import com.lhchin.dao.ProcessDao;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonLoaderApplication.class)
public class CommonLoaderApplicationTests {
	private static final Logger logger = LoggerFactory.getLogger(CommonLoaderApplicationTests.class);
	
	private static final String TABLE_NAME = "EMP";

	private static final String CREATE_EMP_TABLE = ""
			+ "CREATE TABLE EMP("
		    + "  EMPNO    NUMBER(4,0),"
		    + "  ENAME    VARCHAR2(10),"
		    + "  JOB      VARCHAR2(9),"
		    + "  MGR      NUMBER(4,0),"
		    + "  HIREDATE DATE,"
		    + "  SAL      NUMBER(7,2),"
		    + "  COMM     NUMBER(7,2),"
		    + "  DEPTNO   NUMBER(2,0))";

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
			srcdbcTemplate1.execute(CREATE_EMP_TABLE);
			srcdbcTemplate1.execute(""
					+ "insert into emp values(7839, 'KING', 'PRESIDENT', null, to_date('1981-11-17','yyyy-MM-dd'), 5000, null, 10);"
					+ "insert into emp values(7698, 'BLAKE', 'MANAGER', 7839, to_date('1981-5-1','yyyy-MM-dd'), 2850, null, 30);"
					+ "insert into emp values(7782, 'CLARK', 'MANAGER', 7839, to_date('1981-6-9','yyyy-MM-dd'), 2450, null, 10);"
					+ "insert into emp values(7566, 'JONES', 'MANAGER', 7839, to_date('1981-4-2','yyyy-MM-dd'), 2975, null, 20);"
					+ "insert into emp values(7788, 'SCOTT', 'ANALYST', 7566, to_date('1987-7-13','yyyy-MM-dd'), 3000, null, 20);"
					+ "insert into emp values(7902, 'FORD', 'ANALYST', 7566, to_date('1981-12-3','yyyy-MM-dd'), 3000, null, 20);"
					+ "insert into emp values(7369, 'SMITH', 'CLERK', 7902, to_date('1980-12-17','yyyy-MM-dd'), 800, null, 20);"
					+ "insert into emp values(7499, 'ALLEN', 'SALESMAN', 7698, to_date('1981-2-20','yyyy-MM-dd'), 1600, 300, 30);"
					+ "insert into emp values(7521, 'WARD', 'SALESMAN', 7698, to_date('1981-2-22','yyyy-MM-dd'), 1250, 500, 30);");

			targetJdbcTemplate.execute(CREATE_EMP_TABLE);
			isTestingDataInitialized = true;
		}
	}

	@Test
	public void unitTest1() {
		StopWatch stopWatch = new StopWatch("unitTest1");

		try {
			stopWatch.start("INSERT-ONLY");
			printSourceCount();
			printTargetCount();
			processDao.dispatchTask("db1", "INSERT-ONLY", TABLE_NAME, "SELECT * FROM EMP", 5);
			printTargetCount();
			stopWatch.stop();

			stopWatch.start("TRUNCATE-ONLY");
			printTargetCount();
			processDao.dispatchTask("", "TRUNCATE-ONLY", TABLE_NAME, "", 5);
			printTargetCount();
			stopWatch.stop();

			stopWatch.start("BATCH-INSERT");
			printSourceCount();
			printTargetCount();
			processDao.dispatchTask("db1", "INSERT-ONLY", TABLE_NAME, "SELECT * FROM EMP", 5);
			printTargetCount();
			stopWatch.stop();

			stopWatch.start("UPDATE-ONLY");
			printSourceCount();
			printTargetCount();
			processDao.dispatchTask("db1", "UPDATE-ONLY", TABLE_NAME, "SELECT * FROM EMP", 5);
			printTargetCount();
			stopWatch.stop();
		} finally {
			logger.info("{}", stopWatch.prettyPrint());
		}
	}

	private void printTotalCount(JdbcTemplate jdbcTemplate, String tableName) {
		logger.info("Total count of [{}] is [{}].",
				tableName,
				jdbcTemplate.queryForObject(String.format("SELECT COUNT(*) FROM %s", tableName), Integer.class));
	}

	private void printSourceCount() {
		printTotalCount(srcdbcTemplate1, TABLE_NAME);
	}

	private void printTargetCount() {
		printTotalCount(targetJdbcTemplate, TABLE_NAME);
	}
}
