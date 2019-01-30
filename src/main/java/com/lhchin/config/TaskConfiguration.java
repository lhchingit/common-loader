package com.lhchin.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.lhchin.config.DataSourceConfiguration.Setting;

/**
 * 
 * @author LHCHIN
 *
 */
@Component
@ConfigurationProperties("task")
public class TaskConfiguration {
	private List<Setting> settings = new ArrayList<>();

	public List<Setting> getSettings() {
		return this.settings;
	}

	public static class Setting {
		private String taskId;
		private String targetTableName;

		public String getTaskId() {
			return taskId;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;
		}
		public String getTargetTableName() {
			return targetTableName;
		}
		public void setTargetTableName(String targetTableName) {
			this.targetTableName = targetTableName;
		}

		@Override
		public String toString() {
			return "Task [taskId=" + taskId + ", targetTableName=" + targetTableName + "]";
		}
	}
}
