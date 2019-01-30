package com.lhchin.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * @author LHCHIN
 *
 */
@Component
@ConfigurationProperties("datasource")
public class DataSourceConfiguration {
	private List<Setting> settings = new ArrayList<>();
	private Map<String, HikariDataSource> dataSources = new HashMap<>();

	public List<Setting> getSettings() {
		return this.settings;
	}

	public HikariDataSource getDataSourcesByDbName(String dbName) {
		if (this.dataSources.isEmpty()) {
			settings.forEach(dataSource -> {
				HikariDataSource hikaliDataSource = new HikariDataSource();
				hikaliDataSource.setJdbcUrl(dataSource.getUrl());
				hikaliDataSource.setDriverClassName(dataSource.getDriverClassName());
				hikaliDataSource.setUsername(dataSource.getUsername());
				hikaliDataSource.setPassword(dataSource.getPassword());

				this.dataSources.put(dataSource.getDbName(), hikaliDataSource);
			});
		}

		return this.dataSources.get(dbName);
	}

	public static class Setting {
		private String dbName;
		private String url;
		private String driverClassName;
		private String username;
		private String password;

		public String getDbName() {
			return dbName;
		}
		public void setDbName(String dbName) {
			this.dbName = dbName;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getDriverClassName() {
			return driverClassName;
		}
		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}

		@Override
		public String toString() {
			return "DataSource [dbName=" + dbName + ", url=" + url + ", driverClassName=" + driverClassName
					+ ", username=" + username + ", password=" + password + "]";
		}
	}
}
