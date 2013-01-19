package util.config;


import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ConfigManager<T> {

	private String defaultFileName;
	private ObjectMapper mapper;
	private Stack<Exception>  errorStack;
	private Class<T> modelType;
	
	public ConfigManager(Class<T> modelType, String defaultFileName) {
		this.modelType = modelType;
		this.defaultFileName = defaultFileName;
		errorStack= new Stack<Exception>();
		mapper = new ObjectMapper();
	}
	
	public ConfigBase createConfig() {
		try {
			return (ConfigBase) modelType.newInstance();
		} catch (Exception e) {
			errorStack.push(e);
		} 
		return null;
	}
	
	public T load() {
		ConfigBase config = createConfig();
		
		if (!config.cfgUseDefaults) {
		
			if (Files.exists(buildPath(defaultFileName))) {
				try {
					config = (ConfigBase) loadFromFile(defaultFileName);
				} catch (Exception e) {
					errorStack.push(e);
				}
				if (config==null || config.cfgUseDefaults) {
					config = createConfig();
				}
			} else {
				config.cfgSaveToFile = defaultFileName;
			}
			
			if (config.cfgLoadFromFile!=null) {
				try {
					config = (ConfigBase) loadFromFile(config.cfgLoadFromFile);
				} catch (Exception e) {
					errorStack.push(e);
				}
			} 
			
			if (config.cfgSaveToFile!=null) {
				String fileName = config.cfgSaveToFile;
//				if (fileName.isEmpty()) {  // this would overwrite cfg file, but prob. better to delete manually if update needed
//					fileName = defaultFileName;
//				}
				config.cfgSaveToFile = null;
				try {
					saveToFile(fileName, config);
				} catch (Exception e) {
					errorStack.push(e);
				}
			}
			
		}
		
//		debug(config);
		
		return modelType.cast(config);
	}

	public void logErrorsIfAny() {
		Logger log = Logger.getGlobal();
		while (!errorStack.empty()) log.log(Level.SEVERE, "CONFIG", errorStack.pop());
	}
	
	
	private Path buildPath(String fileName) {
		String pathStr = System.getProperty("user.dir")+"/"+fileName;
//		System.out.println(pathStr);
		Path path = Paths.get(pathStr);
		return path;
	}
	
	private void saveToFile(String fileName, Object model) throws Exception {
//		System.out.println("save: "+fileName);
		String txt = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);
		List<String> lines = Arrays.asList(txt.replace("\r", "").split("\\n"));
		Files.write(buildPath(fileName), lines, StandardCharsets.UTF_8);
	}

	private T loadFromFile(String fileName) throws Exception {
//		System.out.println("load: "+fileName);
		StringBuilder sb = new StringBuilder();
		for (String line : Files.readAllLines(buildPath(fileName), StandardCharsets.UTF_8)) {
			if (sb.length()>0) sb.append("\n");
			sb.append(line);
		}
		return mapper.readValue(sb.toString(), modelType);
	}

	
	
	@SuppressWarnings("unused")
	private void debug(Object obj) {
		if (obj==null) {
			System.out.println("!!!   NULL");
			return;
		}
		System.out.println("--------------");
		try {
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("^^^^^^^^^^^^^^");
	}
	
}
