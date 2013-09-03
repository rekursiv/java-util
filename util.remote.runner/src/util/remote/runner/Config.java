package util.remote.runner;

import util.config.ConfigBase;

public class Config extends ConfigBase {

	public String antbin = "C:/projects/eclipse/plugins/org.apache.ant_1.8.4.v201303080030/bin";
	public String deploydir = "C:/projects/eclipse_workspace/_deploy/test";
	public String antscript = "makejar.xml";
	public String jarname = "test.jar";
	public String remoteip = "192.168.1.42";
	public String remoteun = "pi";
	public String remotepw = "raspberry";
	
	public boolean isGuiApp = false;
	
	
}
