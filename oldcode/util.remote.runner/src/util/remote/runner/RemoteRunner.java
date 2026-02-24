package util.remote.runner;



import java.io.File;
import java.io.IOException;

import util.config.ConfigManager;

public class RemoteRunner {

	private Config config;
	private ConfigManager<?> cfgMgr;
	private SshLink ssh;
	
	public void run() throws Exception {
		cfgMgr = new ConfigManager<Config>(Config.class, "remote-run-config.js");
		config = (Config)cfgMgr.load();
		makeJar();
		ssh = new SshLink(config);
		ssh.run();
	}
	
	public void makeJar() throws IOException {
		ProcessBuilder pb = new ProcessBuilder(config.antbin+"/ant.bat", "-f", config.antscript);
		pb.directory(new File(config.deploydir));
		pb.inheritIO();
		pb.start();		
	}
	
}
