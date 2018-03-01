package util.fxtemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import util.config.ConfigBase;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplateConfig extends ConfigBase {

	// logging
	public boolean logToConsole=true;
	public boolean logToFile=false;
	

}
