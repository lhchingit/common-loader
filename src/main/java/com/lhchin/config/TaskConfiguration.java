package com.lhchin.config;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.lhchin.aop.AllowableDbName;
import com.lhchin.aop.AllowableMode;

/**
 * 
 * @author LHCHIN
 *
 */
@Component
@ConfigurationProperties("task")
@Validated
public class TaskConfiguration {
	private List<Setting> settings = new ArrayList<>();

	public List<Setting> getSettings() {
		return this.settings;
	}

	@Valid
	public static class Setting {
		@NotEmpty
		private String taskId;

		@AllowableDbName
		private String dbName;

		@AllowableMode
		private String mode;

		@NotEmpty
		private String targetTableName;

		private String sourceSqlStmt;

		private String deleteSqlStmt;

		private int batchSize;

		public String getTaskId() {
			return taskId;
		}

		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}

		public String getDbName() {
			return dbName;
		}

		public void setDbName(String dbName) {
			this.dbName = dbName;
		}

		public String getMode() {
			return mode;
		}

		public void setMode(String mode) {
			this.mode = mode;
		}

		public String getTargetTableName() {
			return targetTableName;
		}

		public void setTargetTableName(String targetTableName) {
			this.targetTableName = targetTableName;
		}

		public String getSourceSqlStmt() {
			return sourceSqlStmt;
		}

		public void setSourceSqlStmt(String sourceSqlStmt) {
			this.sourceSqlStmt = sourceSqlStmt;
		}

		public String getDeleteSqlStmt() {
			return deleteSqlStmt;
		}

		public void setDeleteSqlStmt(String deleteSqlStmt) {
			this.deleteSqlStmt = deleteSqlStmt;
		}

		public int getBatchSize() {
			return batchSize;
		}

		public void setBatchSize(int batchSize) {
			this.batchSize = batchSize;
		}

		@Override
		public String toString() {
			return "Setting [taskId=" + taskId + ", dbName=" + dbName + ", mode=" + mode + ", targetTableName="
					+ targetTableName + ", sourceSqlStmt=" + sourceSqlStmt + ", deleteSqlStmt=" + deleteSqlStmt
					+ ", batchSize=" + batchSize + "]";
		}
	}
}
