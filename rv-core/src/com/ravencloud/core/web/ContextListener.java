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

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import com.ravencloud.util.concurrent.ConcurrentUtilities;
import com.ravencloud.util.concurrent.ManagerBackgroundJob;
import com.ravencloud.util.general.App;
import com.ravencloud.util.general.Condition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class ContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {

		try {

			log.info("Start context");

			ManagerBackgroundJob.getScheduler().start();

			ConcurrentUtilities.INSTANCE.addJob(App.INSTANCE.classInitJob(), "init");

		} catch (SchedulerException ex) {

			log.error("Fail start manager background", ex);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {

		try {

			log.info("Context destroyed");

			Scheduler scheduler = ManagerBackgroundJob.getScheduler();

			if(!Condition.empty(scheduler)) {

				scheduler.shutdown(true);

				log.info("Stop manager background");
			}

		} catch (SchedulerException ex) {

			log.error("Fail stop manager background", ex);
		}
	}
}
