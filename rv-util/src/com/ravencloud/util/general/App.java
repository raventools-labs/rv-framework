/**
 * Ravencloud, open source library.
 * Copyright (c) 2020 Alejandro Silva Sanahuja
 * mailto:asilva@ravencloud.es
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
package com.ravencloud.util.general;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.aeonbits.owner.ConfigFactory;
import org.quartz.Job;
import org.slf4j.LoggerFactory;

import com.ravencloud.util.session.ISessionManager;
import com.ravencloud.util.stringconverter.StringConverter;

public enum App implements AppConfig {

	INSTANCE;
	
	public static final String NAME_PROPERTIES = "app.properties";
	
	private AppConfig config;
	
	private Properties properties;
	
	App() {
		
		properties = System.getProperties();
		
		try (InputStream input = ClassUtils.INSTANCE.getResourceFromClasspath(NAME_PROPERTIES)) {

			properties.load(input);
			
			config = ConfigFactory.create(AppConfig.class,properties);

		} catch (Exception ex) {

			config = ConfigFactory.create(AppConfig.class);

			LoggerFactory.getLogger(App.class)
				.warn(MessageFormat.format("Problem to load {0} - cause: {1}", NAME_PROPERTIES, ex.getMessage()));
		}
	}
	
	public <V> V getProperty(Class<V> type, String key, V defaultValue) {

		return StringConverter.INSTANCE.converter(properties.getProperty(key), type, defaultValue);
	}
	
	public <V> V getProperty(Class<V> type, String key) {

		return StringConverter.INSTANCE.converter(properties.getProperty(key), type);
	}
	
	public String pathDeploy() {
		
		return getProperty(String.class,SystemProperty.PATH_DEPLOY.val());
	}
	
	public boolean debugMode() {

		return getProperty(Boolean.class,SystemProperty.DEBUG_MODE.val());
	}

	@Override
	public String version() {
		return config.version();
	}

	@Override
	public String groupId() {
		return config.groupId();
	}

	@Override
	public String artifactId() {
		return config.artifactId();
	}
	
	@Override
	public String appName() {
		return config.appName();
	}
	
	@Override
	public String defaultLocale() {
		return config.defaultLocale();
	}
	
	@Override
	public String masterDatabase() {
		return config.masterDatabase();
	}

	@Override
	public Class<? extends ISessionManager> classSessionManager() {
		return config.classSessionManager();
	}

	@Override
	public Class<? extends Job> classInitJob() {
		return config.classInitJob();
	}

	@Override
	public Class<? extends ICurrentUser> classManagerCurrentUser() {
		return config.classManagerCurrentUser();
	}

	@Override
	public boolean multiOrganization() {
		return config.multiOrganization();
	}
	
	@Override
	public String restPackages() {
		return config.restPackages();
	}
	
	@Override
	public String contextPath() {
		return config.contextPath();
	}
	
	@Override
	public String catalinaHome() {
		return config.catalinaHome();
	}
}
