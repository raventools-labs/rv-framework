package com.ravencloud.core.launch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.ravencloud.util.general.App;
import com.ravencloud.util.general.Condition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum LaunchTomcat {
	
	INSTANCE;
	
	private static final int EXIT_OK = 0;
	
	private static final int EXIT_ERROR = 1;
	
	public int launch(String javaOpts) {
		
		int status = EXIT_OK;
		
		try {
			
			terminateTomcatIfRun();
			
			runTomcat(javaOpts);
			
		} catch (Exception ex) {
			
			log.error("Fail run tomcat", ex);
			
			status = EXIT_ERROR;
			
		}
		
		return status;
	}
	
	private static final String RUN_COMMAND = "java -jar %s -Dcatalina.home=%s -Dwtp.deploy=%s target/tomcat/tomcat.jar";
	
	private static final String DEFAULT_JAVA_OPTS = "-Duser.language=es -Duser.region=ES";
	
	private void runTomcat(String javaOpts) throws IOException, InterruptedException {
		
		String pathWebapps = (System.getProperty("user.dir") + "/target/webapps");
		
		if(Condition.empty(javaOpts)) javaOpts = DEFAULT_JAVA_OPTS;
		
		String command = String.format(RUN_COMMAND, javaOpts, App.INSTANCE.catalinaHome(), pathWebapps);
		
		final Process proc = Runtime.getRuntime().exec(command);
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
			
			String line = null;
			
			while ((line = br.readLine()) != null) System.out.println(line);
			
			proc.waitFor();
		}
	}
	
	private static final String LOCALHOST = "127.0.0.1";
	
	private static final String COMMAND_SHUTDOWN = "SHUTDOWN";
	
	private static final int SHUTDOWN_PORT = 8005;
	
	private void terminateTomcatIfRun() {
		
		try (Socket socket = new Socket(LOCALHOST,
			SHUTDOWN_PORT);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
			
			out.println(COMMAND_SHUTDOWN);
			
		} catch (IOException ex) {
			
			log.debug("Problem to stop Tomcat - " + ex.getMessage());
		}
	}
}
