package util.logging;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class LogView extends Composite {
	private StyledText txtLog;

	public LogView(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		txtLog = new StyledText(this, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL );
		txtLog.setAlwaysShowScrollBars(false);

	}

	public void addLine(final String line) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				txtLog.append(line);
				txtLog.setTopIndex(txtLog.getLineCount()-1);
			}
		});
	}
	
	public StyledText getTextWidget() {
		return txtLog;
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
