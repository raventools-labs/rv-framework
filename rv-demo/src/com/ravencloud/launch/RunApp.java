package com.ravencloud.launch;

import com.ravencloud.core.launch.LaunchTomcat;
import com.ravencloud.util.general.App;

public class RunApp {
	
	public static void main(String[] args) {
		LaunchTomcat.INSTANCE.launch(null);
	}
}
