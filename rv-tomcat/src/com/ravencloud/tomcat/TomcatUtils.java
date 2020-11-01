package com.ravencloud.tomcat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.catalina.connector.Connector;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.ravencloud.util.exception.ProgrammingException;
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
	
	private static final int DEFAULT_SHUTDOWN_PORT = 8005;
	
	private static final String DEFAULT_URI_ENCODING = "UTF-8";
	
	private static final String DEFAULT_ADDRESS = "0.0.0.0";
	
	private static final String DEFAULT_SECRET_REQUIRED = "false";
	
	private static final String XPATH_CONNECTOR = "//Connector";
	
	private static final String DEFAULT_SERVER_XML = "default-server.xml";
	
	private static final String PATH_SERVER_XML = "/conf/server.xml";
	
	private static final String PATH_CATALINA_PID = "/catalina_pid";

	public void createCatalinaPid() {

		try {
		
			String name = ManagementFactory.getRuntimeMXBean().getName();
			
			try (FileOutputStream out = new FileOutputStream(new File(App.INSTANCE.catalinaHome() + PATH_CATALINA_PID))) {
				
				out.write(name.substring(0, name.indexOf('@')).getBytes());
			}
			
		} catch (Exception ex) {
			
			log.warn("Don't create catalina_pid: " + ex.getMessage());
		}
	}
	
	public void removeCatalinaPid() {
		
		try {
			
			Files.delete(Paths.get(App.INSTANCE.catalinaHome() + PATH_CATALINA_PID));
		
		} catch (Exception ex) {
		
			log.warn("Don't remove catalina_pid: " + ex.getMessage());
		}
	}
	
	public Document getServerXml() {
		
		Document document = null;
		
		try {
			
			String path = App.INSTANCE.catalinaHome() + PATH_SERVER_XML;
			
			document = getDocument(new FileInputStream(Paths.get(path).toFile()));
			
		} catch (Exception ex) {
			
			try {
				
				document = getDocument(ClassUtils.INSTANCE.getResourceFromClasspath(DEFAULT_SERVER_XML));
				
				log.warn("Not found server.xml in catalina home, using the default");
				
			} catch (Exception ex2) {
				
				throw new ProgrammingException("Not load default properties", ex2);
			}
		}
		
		return document;
	}
	
	private Document getDocument(InputStream xml) throws ParserConfigurationException, SAXException, IOException {
		
		DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
		df.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		df.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		DocumentBuilder builder = df.newDocumentBuilder();
		return builder.parse(new InputSource(xml));
	}
	
	public List<Connector> getConnectors(Document document) throws XPathExpressionException {
		
		List<Connector> connectors = new ArrayList<>();
		
		XPath xpath = XPathFactory.newInstance().newXPath();
		
		NodeList nodes = (NodeList) xpath.evaluate(XPATH_CONNECTOR, document, XPathConstants.NODESET);
		
		for(int i = 0; i < nodes.getLength(); i++) {
			
			NamedNodeMap attributes = nodes.item(i).getAttributes();
			
			Connector connector = new Connector(getAttribute(attributes, "protocol"));
			
			connector.setPort(StringConverter.INSTANCE.converter(getAttribute(attributes, "port"), int.class));
			
			int redirectPort = StringConverter.INSTANCE.converter(
				getAttribute(attributes, "redirectPort"), int.class, DEFAULT_REDIRECT_PORT);
			
			connector.setRedirectPort(redirectPort);
			
			int maxPostSize = StringConverter.INSTANCE.converter(
				getAttribute(attributes, "maxPostSize"), int.class, DEFAULT_MAX_POST_SIZE);
			
			connector.setMaxPostSize(maxPostSize);
			
			String uriEncoding = Condition.evalNotEmpty(
				getAttribute(attributes, "URIEncoding"), DEFAULT_URI_ENCODING);
			
			connector.setURIEncoding(uriEncoding);
			
			String address = Condition.evalNotEmpty(
				getAttribute(attributes, "address"), DEFAULT_ADDRESS);
			
			connector.setProperty("address", address);
			
			String secretRequired = Condition.evalNotEmpty(
				getAttribute(attributes, "secretRequired"), DEFAULT_SECRET_REQUIRED);
			
			connector.setProperty("secretRequired", secretRequired);
			
			connectors.add(connector);
		}
		
		return connectors;
	}
	
	private String getAttribute(NamedNodeMap attributes, String name) {
		
		String value = null;
		
		Node node = attributes.getNamedItem(name);
		
		if(!Condition.empty(node)) {
			
			value = node.getTextContent();
		}
		
		return value;
	}
	
	public int getShutdownPort(Document serverXml) {
		
		int shutdownPort;
		
		try {
			
			Node nodePort = serverXml.getChildNodes().item(0).getAttributes().getNamedItem("port");
			
			shutdownPort = StringConverter.INSTANCE.converter(nodePort.getTextContent(), int.class,
				DEFAULT_SHUTDOWN_PORT);
		
		} catch (Exception e) {
			
			shutdownPort = DEFAULT_SHUTDOWN_PORT;
		}
		
		return shutdownPort;
	}
}
