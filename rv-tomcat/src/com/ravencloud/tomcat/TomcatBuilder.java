package com.ravencloud.tomcat;

import org.w3c.dom.Document;

import com.ravencloud.util.exception.BuildException;
import com.ravencloud.util.general.App;
import com.ravencloud.util.general.Builder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TomcatBuilder implements Builder<Tomcat> {
	
	private Document serverXml;
	
	private TomcatBuilder() {
		
		serverXml = TomcatUtils.INSTANCE.getServerXml();
	}
	
	public static TomcatBuilder builder() {
		
		return new TomcatBuilder();
	}
	
	@Override
	public Tomcat build() throws BuildException {
		
		try {
			
			log.info("catalinaHome: " + App.INSTANCE.catalinaHome());
			
			log.info("pathDeploy: " + App.INSTANCE.pathDeploy());
			
			Tomcat tomcat = new Tomcat();
			
			tomcat.setBaseDir(App.INSTANCE.catalinaHome());
			
			tomcat.setConnectors(TomcatUtils.INSTANCE.getConnectors(serverXml));
			
			tomcat.enableLog();
			
			tomcat.enableContext();
			
			tomcat.setShutdownPort(TomcatUtils.INSTANCE.getShutdownPort(serverXml));
			
			return tomcat;
			
		} catch (Exception ex) {
			
			throw new BuildException(ex);
		}
	}
}
