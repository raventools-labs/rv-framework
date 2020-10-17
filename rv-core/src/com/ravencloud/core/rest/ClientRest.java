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
package com.ravencloud.core.rest;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Feature;

import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;

import com.ravencloud.util.exception.ProgrammingException;

public enum ClientRest {
	
	INSTANCE;
	
	public WebTarget getTarget(String uri, String path, String token) {
		
		try {
			
			final SSLContext context = SSLContext.getInstance("TLSv1.2");
			
			final TrustManager[] trustManagerArray = { new NullX509TrustManager() };
			
			context.init(null, trustManagerArray, null);
			
			Client client = ClientBuilder.newBuilder().hostnameVerifier(new NullHostnameVerifier()).sslContext(context).build();
			
			Feature feature = OAuth2ClientSupport.feature(token);
			
			client.register(feature);
			
			return client.target(uri).path(path);
		
		} catch (Exception ex) {
		
			throw new ProgrammingException("Bad definition ssl context");
		}
	}
	
	private static final class NullHostnameVerifier implements HostnameVerifier {
		
		@Override
		public boolean verify(String hostname, SSLSession session) {
			
			return true;
		}
	}
	
	private static final class NullX509TrustManager implements X509TrustManager {
		
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
		
		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
		
		@Override
		public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
	}
}
