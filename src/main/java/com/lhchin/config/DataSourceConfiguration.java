package com.lhchin.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * @author LHCHIN
 *
 */
@Component
@ConfigurationProperties("datasource")
@Validated
public class DataSourceConfiguration {
	@NotEmpty
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

	@Valid
	public static class Setting {
		@NotEmpty
		private String dbName;

		@NotEmpty
		private String url;

		@NotEmpty
		private String driverClassName;

		@NotEmpty
		private String username;

		@NotEmpty
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
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((dbName == null) ? 0 : dbName.hashCode());
			result = prime * result + ((driverClassName == null) ? 0 : driverClassName.hashCode());
			result = prime * result + ((password == null) ? 0 : password.hashCode());
			result = prime * result + ((url == null) ? 0 : url.hashCode());
			result = prime * result + ((username == null) ? 0 : username.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Setting other = (Setting) obj;
			if (dbName == null) {
				if (other.dbName != null)
					return false;
			} else if (!dbName.equals(other.dbName))
				return false;
			if (driverClassName == null) {
				if (other.driverClassName != null)
					return false;
			} else if (!driverClassName.equals(other.driverClassName))
				return false;
			if (password == null) {
				if (other.password != null)
					return false;
			} else if (!password.equals(other.password))
				return false;
			if (url == null) {
				if (other.url != null)
					return false;
			} else if (!url.equals(other.url))
				return false;
			if (username == null) {
				if (other.username != null)
					return false;
			} else if (!username.equals(other.username))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "DataSource [dbName=" + dbName + ", url=" + url + ", driverClassName=" + driverClassName
					+ ", username=" + username + ", password=" + password + "]";
		}
	}
}
