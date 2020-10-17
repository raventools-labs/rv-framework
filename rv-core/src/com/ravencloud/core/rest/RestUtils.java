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
	
	public void initOpenApiRest() {
		
		DynamicOpenAPIDefinition dynamic = new DynamicOpenAPIDefinition(
			OpenApi.class.getAnnotation(OpenAPIDefinition.class));
		
		List<DynamicServer> servers = new ArrayList<>();
		
		DynamicServer server = new DynamicServer();
		
		server.setUrl(App.INSTANCE.contextPath() + RestServlet.PATH_REST);
		
		servers.add(server);
		
		server = new DynamicServer();
		
		server.setUrl(App.INSTANCE.contextPath() + RestServlet.PATH_MODULE);
		
		servers.add(server);
		
		dynamic.setServers(ListUtil.INSTANCE.toArray(DynamicServer.class, servers));
		
		AnnotationUtil.INSTANCE.alterAnnotation(OpenApi.class, OpenAPIDefinition.class, dynamic);
	}
}
