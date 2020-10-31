package com.ravencloud.tomcat;

import java.util.List;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.ravencloud.util.general.App;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RavencloudTomcat extends Tomcat {
	
	private static final String SHUTDOWN = "SHUTDOWN";
	
	@Getter @Setter private int shutdownPort;
	
	public void setConnectors(List<Connector> connectors) {
		
		for(Connector connector : connectors) {
			
			getService().addConnector(connector);
		}
	}
	
	public void enableContext() {
		
		enableNaming();
		
		Context ctx = addWebapp("", App.INSTANCE.pathDeploy());
		
		((StandardJarScanner) ctx.getJarScanner()).setScanAllDirectories(true);
	}
	
	public void run() throws LifecycleException {
		
		start();
		
		TomcatUtils.INSTANCE.createCatalinaPid();
		
		getServer().setPort(shutdownPort);
		
		getServer().setShutdown(SHUTDOWN);
		
		getServer().await();
		
		TomcatUtils.INSTANCE.removeCatalinaPid();
	}

	public static void main(String[] args) throws Exception {
		
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		
		SLF4JBridgeHandler.install();
		
		log.info("catalinaHome: " + App.INSTANCE.catalinaHome());
		
		log.info("pathDeploy: " + App.INSTANCE.pathDeploy());
		
		RavencloudTomcat tomcat = TomcatBuilder.builder().build();
		
		tomcat.run();
		
		System.exit(0);
	}
}
