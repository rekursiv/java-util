package util.logging;


import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.SimpleFormatter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;



public class LogSetup {
	
	public LogSetup() {
//		System.out.println("LogSetup");
	}
	
	public static void initView(LogView logView, Level level) {
		LogController logCon = new LogController(logView);
		logCon.setLevel(level);
		LogManager.getLogManager().getLogger("").addHandler(logCon);
	}
	
	public static void initConsole(Level level) {
		System.getProperties().setProperty("java.util.logging.SimpleFormatter.format", "%5$s  [%4$s] {%2$s} %6$s%n");   //  FIXME:  write custom formatter
		Handler ch = new ConsoleHandler();
		ch.setFormatter(new SimpleFormatter());
		ch.setLevel(level);
		LogManager.getLogManager().getLogger("").addHandler(ch);
	}
	
	public void test() {
		// -Djava.util.logging.config.file=logging.properties
//		Properties p = System.getProperties();
//		p.setProperty("java.util.logging.config.file", "logging.properties");		
	}

}
