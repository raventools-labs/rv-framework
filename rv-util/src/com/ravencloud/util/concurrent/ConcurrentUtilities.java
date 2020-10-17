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

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;

import com.ravencloud.util.db.UUIDGenerator;
import com.ravencloud.util.general.Condition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum ConcurrentUtilities {
	
	INSTANCE;
	
	public static final String KEY_MONITOR = "monitor";
	
	public JobKey addJob(JobDetail job,String group) {

		JobKey jobKey = null;
		
		try {
			
			ManagerBackgroundJob.getScheduler().scheduleJob(job, buildTrigger(group));
			
			jobKey =  job.getKey();
			
		} catch (SchedulerException ex) {
		
			log.error(ExceptionUtils.getStackTrace(ex));
		}
	    
		return jobKey;
	}
	
	public <J extends Job> JobKey addJob(Class<J> classJob,String group) {

		JobDetail job = buildJob(classJob,group);
		
		return addJob(job, group);
	}
	
	public JobKey addJob(JobDetail job,String group,String cronSchedule) {
		
		JobKey jobKey = null;
		
		try {
			
			ManagerBackgroundJob.getScheduler().scheduleJob(job, buildTrigger(group, cronSchedule));
			
			jobKey =  job.getKey();
			
		} catch (SchedulerException ex) {
			
			log.error(ExceptionUtils.getStackTrace(ex));
		}
	   	
		return jobKey;
	}
	
	public <J extends Job> JobKey addJob(Class<J> classJob,String group,String cronSchedule) {
		
		JobDetail job = buildJob(classJob,group);
			
		return addJob(job, group, cronSchedule);
	}
	
	public JobDetail buildJob(Class<? extends Job> classJob,String group) {
		
		return buildJob(classJob, group, null);
	}
	
	public <M extends QueueJobMonitor<?>> JobDetail buildJob(Class<? extends Job> classJob,String group,M monitor) {
		
		String uuid = UUIDGenerator.generate(classJob);
		
		JobBuilder jobBuilder = newJob(classJob).withIdentity(uuid, group);
		
		if(!Condition.empty(monitor)) {
			
			JobDataMap dataMap = new JobDataMap();
			
			dataMap.put(KEY_MONITOR, monitor);
			
			jobBuilder.usingJobData(dataMap);
		}
		
		return jobBuilder.build();
	}
	
	private Trigger buildTrigger(String group) {
		
		String uuid = UUIDGenerator.generate(Trigger.class);
	    
		return newTrigger().withIdentity(uuid, group).startNow().build();
	}
	
	private Trigger buildTrigger(String group,String cronSchedule) {
		
		String uuid = UUIDGenerator.generate(Trigger.class);
	    
		return newTrigger()
			.withIdentity(uuid, group)
			.withSchedule(cronSchedule(cronSchedule))
			.build();
	}
	
	public int getActiveJobs(Scheduler scheduler,String group) {
		
		try {
			
			return scheduler.getJobKeys(GroupMatcher.groupEquals(group)).size();
			
		} catch (SchedulerException ex) {
			
			log.warn("can't return active job",ex);
			
			return 0;
		}
	}
}
