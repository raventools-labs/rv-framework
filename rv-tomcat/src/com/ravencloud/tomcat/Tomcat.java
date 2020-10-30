package com.ravencloud.tomcat;

import org.apache.catalina.Context;
import org.apache.tomcat.util.scan.StandardJarScanner;

import com.ravencloud.util.general.App;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Tomcat {
	
	private static final String SHUTDOWN = "SHUTDOWN";
	
	public static void run() throws Exception {
		
		log.info("catalinaHome: " + App.INSTANCE.catalinaHome());
		
		log.info("pathDeploy: " + App.INSTANCE.pathDeploy());
		
		org.apache.catalina.startup.Tomcat tomcat = new org.apache.catalina.startup.Tomcat();

		tomcat.setBaseDir(App.INSTANCE.catalinaHome());
		
		TomcatUtils.INSTANCE.setConnectors(tomcat);
		
		tomcat.enableNaming();
		
		Context ctx = tomcat.addWebapp("", App.INSTANCE.pathDeploy());
		
		((StandardJarScanner) ctx.getJarScanner()).setScanAllDirectories(true);
		
		TomcatUtils.INSTANCE.writeCatalinaPid();
		
		tomcat.start();
		
		tomcat.getServer().setPort(8005);
		
		tomcat.getServer().setShutdown(SHUTDOWN);
		
		tomcat.getServer().await();
		
		System.exit(0);
	}
	
	public static void main(String[] args) throws Exception {
		
		Tomcat.run();
	}
}
