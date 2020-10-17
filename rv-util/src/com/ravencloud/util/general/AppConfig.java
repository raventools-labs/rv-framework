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

import org.aeonbits.owner.Config;
import org.quartz.Job;

import com.ravencloud.util.session.ISessionManager;

public interface AppConfig extends Config {
	
	@Key("project.version")
	String version();
	
	@Key("project.groupId")
	String groupId();
	
	@Key("project.artifactId")
	String artifactId();
	
	@Key("project.appName")
	String appName();
	
	@Key("project.multiOrganization")
	@DefaultValue(value = "false")
	boolean multiOrganization();
	
	@Key("db.masterDatabase")
	@DefaultValue(value = "RVMASTER")
	String masterDatabase();
	
	@Key("class.managerSession")
	Class<? extends ISessionManager> classSessionManager();
	
	@Key("class.initJob")
	Class<? extends Job> classInitJob();
	
	@Key("class.managerCurrentUser")
	@DefaultValue(value = "com.ravencloud.util.general.NoneCurrentUser")
	Class<? extends ICurrentUser> classManagerCurrentUser();
	
	@Key("rest.packages")
	String restPackages();
	
	@Key("web.contextPath")
	@DefaultValue(value = "/")
	String contextPath();
}
