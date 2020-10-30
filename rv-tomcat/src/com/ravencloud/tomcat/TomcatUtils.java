package com.ravencloud.tomcat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.ravencloud.util.general.App;
import com.ravencloud.util.general.ClassUtils;
import com.ravencloud.util.general.Condition;
import com.ravencloud.util.stringconverter.StringConverter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum TomcatUtils {
	
	INSTANCE;
	
	private static final int DEFAULT_MAX_POST_SIZE = -1;
	
	private static final int DEFAULT_REDIRECT_PORT = 8443;
	
	private static final String DEFAULT_URI_ENCODING = "UTF-8";
	
	private static final String DEFAULT_ADDRESS = "0.0.0.0";
	
	private static final String DEFAULT_SECRET_REQUIRED = "false";
	
	private static final String XPATH_CONNECTOR = "//Connector";
	
	private static final String DEFAULT_SERVER_XML = "default-server.xml";
	
	private static final String PATH_SERVER_XML = "/conf/server.xml";
	
	private static final String PATH_CATALINA_PID = "/catalina_pid";
	
	public void setConnectors(Tomcat tomcat) throws Exception {
		
		Document document = null;
		
		try {
			
			String path = App.INSTANCE.catalinaHome() + PATH_SERVER_XML;
			
			document = getDocument(path);
			
		} catch (Exception ex) {
			
			document = getDocument(ClassUtils.INSTANCE.getPathClass(DEFAULT_SERVER_XML));
			
			log.warn("Not found server.xml in catalina home, using the default");
		}
		
		for(Connector connector : getConnectors(document)) {
			
			tomcat.getService().addConnector(connector);
		}
	}
	
	public void writeCatalinaPid() throws IOException {
		
		String name = ManagementFactory.getRuntimeMXBean().getName();
		
		try (FileOutputStream out = new FileOutputStream(new File(App.INSTANCE.catalinaHome() + PATH_CATALINA_PID))) {
			
			out.write(name.substring(0, name.indexOf('@')).getBytes());
		}
		
	}
	
	private Document getDocument(String xml) throws Exception {
		
		DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
		df.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		df.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		DocumentBuilder builder = df.newDocumentBuilder();
		return builder.parse(new InputSource(xml));
	}
	
	private List<Connector> getConnectors(Document document) throws XPathExpressionException {
		
		List<Connector> connectors = new ArrayList<>();
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		NodeList nodes = (NodeList) xpath.evaluate(XPATH_CONNECTOR, document, XPathConstants.NODESET);
		
		for(int i = 0; i < nodes.getLength(); i++) {
			
			NamedNodeMap attributes = nodes.item(i).getAttributes();
			
			Connector connector = new Connector(getValue(attributes, "protocol"));
			
			connector.setPort(StringConverter.INSTANCE.converter(getValue(attributes, "port"), int.class));
			
			int redirectPort = StringConverter.INSTANCE.converter(
				getValue(attributes, "redirectPort"), int.class, DEFAULT_REDIRECT_PORT);
			
			connector.setRedirectPort(redirectPort);
			
			int maxPostSize = StringConverter.INSTANCE.converter(
				getValue(attributes, "maxPostSize"), int.class, DEFAULT_MAX_POST_SIZE);
			
			connector.setMaxPostSize(maxPostSize);
			
			String uriEncoding = Condition.evalNotEmpty(
				getValue(attributes, "URIEncoding"), DEFAULT_URI_ENCODING);
			
			connector.setURIEncoding(uriEncoding);
			
			String address = Condition.evalNotEmpty(
				getValue(attributes, "address"), DEFAULT_ADDRESS);
			
			connector.setProperty("address", address);
			
			String secretRequired = Condition.evalNotEmpty(
				getValue(attributes, "secretRequired"), DEFAULT_SECRET_REQUIRED);
			
			connector.setProperty("secretRequired", secretRequired);
			
			connectors.add(connector);
		}
		
		return connectors;
	}
	
	private String getValue(NamedNodeMap attributes, String name) {
		
		String value = null;
		
		Node node = attributes.getNamedItem(name);
		
		if(!Condition.empty(node)) {
			
			value = node.getTextContent();
		}
		
		return value;
	}
}
