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
package com.ravencloud.core.web;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ravencloud.core.bd.dao.OrganizationDao;
import com.ravencloud.core.bd.model.Organization;
import com.ravencloud.core.implemented.action.SignInAction;
import com.ravencloud.core.implemented.view.LoginView;
import com.ravencloud.core.iu.ManagerViews;
import com.ravencloud.core.iu.action.ManagerActions;
import com.ravencloud.core.labels.ManagerLabels;
import com.ravencloud.core.rest.RestUtils;
import com.ravencloud.util.db.DatabaseConstantsApp;
import com.ravencloud.util.db.ManagerChangeDB;
import com.ravencloud.util.general.App;
import com.ravencloud.util.general.InitMonitor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitJob implements Job {
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		try {

			log.info("Init application...");
			
			RestUtils.INSTANCE.initOpenApiRest();

			ManagerLabels.INSTANCE.setKeyIfNotFind(true);
			
			ManagerActions.INSTANCE.addActions(SignInAction.class.getPackage().getName());
			
			ManagerViews.INSTANCE.addViews(LoginView.class.getPackage().getName());
			
			initChangeDatabase();

		} finally {

			InitMonitor.INSTANCE.permitAll();
		}

		log.info("Finish init application");
	}
	
	private static final String PATH_MASTER_CHANGELOG = "master_databaseChangelog.xml";
	
	private static final String PATH_GENERAL_CHANGELOG = "general_databaseChangelog.xml";
	
	private static void initChangeDatabase() {
		
		ManagerChangeDB.INSTANCE.checkUpdate(PATH_MASTER_CHANGELOG, App.INSTANCE.masterDatabase());
		
		String suffix = App.INSTANCE.getProperty(String.class, DatabaseConstantsApp.GENERAL_DATABASE);
		
		if(App.INSTANCE.multiOrganization()) {
			
			for(Organization organization : OrganizationDao.INSTANCE.getAllActive()) {
				
				ManagerChangeDB.INSTANCE.checkUpdate(PATH_GENERAL_CHANGELOG, organization.getPreffixDatabase() + suffix);
			}
			
		} else {
			
			ManagerChangeDB.INSTANCE.checkUpdate(PATH_GENERAL_CHANGELOG, suffix);
		}
	}
}
