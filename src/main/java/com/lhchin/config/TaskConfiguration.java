package com.lhchin.config;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

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

	public static class Setting {
		@NotEmpty
		private String taskId;

		@AllowableMode
		private String mode;

		@NotEmpty
		private String targetTableName;

		public String getTaskId() {
			return taskId;
		}
		public void setTaskId(String taskId) {
			this.taskId = taskId;
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

		@Override
		public String toString() {
			return "Task [taskId=" + taskId + ", mode=" + mode + ", targetTableName=" + targetTableName + "]";
		}
	}
}
