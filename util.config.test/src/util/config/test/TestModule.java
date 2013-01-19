package util.config.test;

import util.config.ConfigManager;
import com.google.inject.AbstractModule;

public class TestModule  extends AbstractModule {
	private final ConfigManager<Config> cfgMgr = new ConfigManager<Config>(Config.class, "test-config.js");
	private Config config;
	
	@Override
	protected void configure() {
		loadConfig();
	}
	
	protected void loadConfig() {
		config = cfgMgr.load();
		bind(Config.class).toInstance(config);
	}
	
}