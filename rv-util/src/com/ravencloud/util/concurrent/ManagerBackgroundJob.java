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
package com.ravencloud.util.concurrent;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.ravencloud.util.general.Condition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ManagerBackgroundJob {
	
	private static Scheduler scheduler;
	
	private ManagerBackgroundJob() {}
	
	public static synchronized Scheduler getScheduler() {
		
		if(Condition.empty(scheduler)) {
			
			SchedulerFactory sf = new StdSchedulerFactory();
	
			try {
				
				scheduler = sf.getScheduler();
				
			} catch (SchedulerException ex) {
				
				log.error("Fail create scheduler", ex);
			}
		}
		
		return scheduler;
	}
}
