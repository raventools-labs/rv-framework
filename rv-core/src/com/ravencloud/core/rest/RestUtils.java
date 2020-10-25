package com.ravencloud.core.rest;

import java.util.ArrayList;
import java.util.List;

import com.ravencloud.core.services.OpenApi;
import com.ravencloud.util.general.AnnotationUtil;
import com.ravencloud.util.general.App;
import com.ravencloud.util.general.ListUtil;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

public enum RestUtils {
	
	INSTANCE;
	
	private static final int POSITION_FIRST_SLASH = 1;
	
	public void initOpenApiRest() {
		
		DynamicOpenAPIDefinition dynamic = new DynamicOpenAPIDefinition(
			OpenApi.class.getAnnotation(OpenAPIDefinition.class));
		
		List<DynamicServer> servers = new ArrayList<>();
		
		DynamicServer server = new DynamicServer();
		
		String contextPath = App.INSTANCE.contextPath();
		
		server.setUrl(contextPath + RestServlet.PATH_REST.substring(POSITION_FIRST_SLASH));
		
		servers.add(server);
		
		server = new DynamicServer();
		
		server.setUrl(contextPath + RestServlet.PATH_MODULE.substring(POSITION_FIRST_SLASH));
		
		servers.add(server);
		
		dynamic.setServers(ListUtil.INSTANCE.toArray(DynamicServer.class, servers));
		
		AnnotationUtil.INSTANCE.alterAnnotation(OpenApi.class, OpenAPIDefinition.class, dynamic);
	}
}
