package com.ravencloud.tomcat;

import org.w3c.dom.Document;

import com.ravencloud.util.exception.BuildException;
import com.ravencloud.util.general.App;
import com.ravencloud.util.general.Builder;

public final class TomcatBuilder implements Builder<RavencloudTomcat> {
	
	private Document serverXml;
	
	private TomcatBuilder() {
		
		serverXml = TomcatUtils.INSTANCE.getServerXml();
	}
	
	public static TomcatBuilder builder() {
		
		return new TomcatBuilder();
	}
	
	@Override
	public RavencloudTomcat build() throws BuildException {
		
		try {
			
			RavencloudTomcat tomcat = new RavencloudTomcat();
			
			tomcat.setBaseDir(App.INSTANCE.catalinaHome());
			
			tomcat.setConnectors(TomcatUtils.INSTANCE.getConnectors(serverXml));
			
			tomcat.enableContext();
			
			tomcat.setShutdownPort(TomcatUtils.INSTANCE.getShutdownPort(serverXml));
			
			return tomcat;
			
		} catch (Exception ex) {
			
			throw new BuildException(ex);
		}
	}
}
