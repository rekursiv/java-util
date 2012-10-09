package util.logging;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

public class LogView extends Composite {
	private Text txtLog;  //  TODO:  use StyledText and append() ??

	public LogView(Composite parent, int style) {
		super(parent, style);
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		txtLog = new Text(this, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);

	}

	public void addLine(final String line) {
		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				txtLog.setText(txtLog.getText()+line);
				int numLines = txtLog.getLineCount();
				txtLog.setTopIndex(numLines-1);
			}
		});
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
