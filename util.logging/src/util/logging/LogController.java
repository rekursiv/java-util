package util.logging;

import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class LogController extends Handler {
	
	private LogView view;

	public LogController(LogView view) {
		this.view = view;
	}

	@Override
	public void publish(LogRecord record) {
        if (!isLoggable(record)) {
            return;
        }
		if (view!=null) {
			StringBuilder logMsg = new StringBuilder();
			logMsg.append(record.getSequenceNumber()+": ");
			logMsg.append(record.getMessage()+"  [");
			logMsg.append(record.getLevel()+"]  {");
			logMsg.append(record.getSourceClassName()+"#");
			logMsg.append(record.getSourceMethodName()+"}  ");
			if (record.getThrown()!=null) {
				logMsg.append(record.getThrown());
			}
			logMsg.append("\r\n");  //  TODO:  crossplatformatize  (line delimiter sys prop??)
			view.addLine(logMsg.toString());
		}
	}

	@Override
	public void flush() {
	}

	@Override
	public void close() throws SecurityException {
	}

}
