package util.logging;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;


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
		Handler ch = new ConsoleHandler();
		ch.setFormatter(new ConsoleFormatter());
		ch.setLevel(level);
		LogManager.getLogManager().getLogger("").addHandler(ch);
	}
	

}

