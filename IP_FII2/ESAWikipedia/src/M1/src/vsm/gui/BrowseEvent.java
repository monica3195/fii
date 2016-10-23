package vsm.gui;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class BrowseEvent implements SelectionListener {

	Shell shell;
	Text text;

	/**
	 * @param shell
	 * @param text
	 */
	public BrowseEvent(Shell shell, Text text) {
		this.shell = shell;
		this.text = text;
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		DirectoryDialog dialog = new DirectoryDialog(shell);
		dialog.setText("Select directory");
		dialog.setFilterPath("C:/");
		String selected = dialog.open();
		
		if (selected != null) {
			text.setText(selected);
		}
	}

}
