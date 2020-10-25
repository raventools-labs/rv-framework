/**
 *  * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *  *
 *  * Copyright (c) 2020 Alejandro Silva Sanahuja <asilva@ravencloud.es>.
 *  *
 *  * This program and the accompanying materials are made available under the
 *  * terms of the Eclipse Public License 2.0 which is available at
 *  * https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 *  * which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *  *
 *  * SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */
package com.ravencloud.session.redis;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import com.ravencloud.util.exception.UncofiguratedException;
import com.ravencloud.util.general.App;
import com.ravencloud.util.general.Condition;
import com.ravencloud.util.general.Json;
import com.ravencloud.util.session.AbstractSessionManager;
import com.ravencloud.util.session.ISessionManager;

public final class ManagerSessionRedis extends AbstractSessionManager {

	private static final String KEY_RUTE_CONFIG = "redis.configFile";
	
	private static final String DEFAULT_VALUE = "/conf/redis.yalm";
	
	private static ISessionManager instance;

	private Config config;

	private ManagerSessionRedis() {

		super();
		
		String defaultPath = App.INSTANCE.catalinaHome() + DEFAULT_VALUE;
		
		String ruteConfig = App.INSTANCE.getProperty(String.class, KEY_RUTE_CONFIG, defaultPath);
		
		try {
			
			config = Config.fromYAML(new File(ruteConfig));
			
		} catch (IOException e) {
			
			throw new UncofiguratedException("Not find configuration file in the classpath(" + ruteConfig + ")");
		}
	}

	public static synchronized ISessionManager getInstance() {

		if (Condition.empty(instance)) instance = new ManagerSessionRedis();

		return instance;
	}

	@Override
	public void set(String key, String value) {

		RedissonClient client = Redisson.create(config);

		try {

			if(!Condition.empty(getSession())) {

				setValue(client, key, value);
			}

		} finally {

			if(!Condition.empty(client)) client.shutdown();
		}
	}
	
	private void setValue(RedissonClient client, String key, String value) {
		
		RMap<String, String> rMap = client.getMap(getSession());
		
		try {
			
			rMap.getLock(key).lock();
			
			if(!Condition.empty(value)) {
				
				value = rMap.put(key, value);
				
			} else {
				
				rMap.remove(key);
			}
			
		} finally {
			
			rMap.getLock(key).unlock();
		}
	}

	@Override
	public void set(String key, Object value) {

		String strValue = Json.getInstance().toJson(value);

		set(key, strValue);
	}

	@Override
	public String get(String key) {

		RedissonClient client = Redisson.create(config);
		
		String value = null;

		try {

			if(!Condition.empty(getSession())) {
				
				RMap<String, String> rMap = client.getMap(getSession());
	
				if(!Condition.empty(rMap)) {
				
					try {
						
						rMap.getLock(key).lock();
						
						value = rMap.get(key);
						
					} finally {
						
						rMap.getLock(key).unlock();
					}
				}
			}
			
		} finally {

			if(!Condition.empty(client)) client.shutdown();
		}
		
		return value;
	}

	@Override
	public <T> T get(String key, Class<T> clazz) {

		String json = get(key);

		T value = null;

		if (!Condition.empty(json)) {

			value = Json.getInstance().fromJson(json, clazz);
		}

		return value;
	}

	@Override
	public void sessionDestroyed(HttpSession session) {
		
		RedissonClient client = Redisson.create(config);

		try {

			RMap<String, String> rMap = client.getMap(session.getId());

			if(!Condition.empty(rMap)) {

				rMap.deleteAsync();
			}

		} finally {

			if (!Condition.empty(client)) client.shutdown();
		}
	}
}
