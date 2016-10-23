package mainApp;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import modul3.main.M3;
import modul4.gui.GUI_Frame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Main {

	protected Shell shlEsa;
	private Button btnNewButton;
	private Display display;

	/**
	 * @return the display
	 */
	public Display getDisplay() {
		return display;
	}

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Main window = new Main();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		display = Display.getDefault();
		createContents();
		shlEsa.open();
		shlEsa.layout();
		while (!shlEsa.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public void toggle() {
		Control[] controls = shlEsa.getChildren();
		
		for (Control control : controls) {
			control.setEnabled(!control.getEnabled());
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlEsa = new Shell(display, SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shlEsa.setSize(280, 250);
		shlEsa.setText("ESA");
		shlEsa.setLayout(new FormLayout());
		
		shlEsa.addListener(SWT.Close, new Listener() {
			@Override
			public void handleEvent(Event event) {
				display.dispose();
				System.exit(0);
			}
		});
		
		btnNewButton = new Button(shlEsa, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				GUI_Frame g = new GUI_Frame();
				g.addWindowListener(new WindowAdapter()
		        {
		            @Override
		            public void windowClosing(WindowEvent e)
		            {
		                display.asyncExec(new Runnable() {
							public void run() {
								toggle();
							}
						});
		                e.getWindow().dispose(); 
		            }
		        });
				toggle();
			}
		});
		
		FormData fd_btnNewButton = new FormData();
		fd_btnNewButton.top = new FormAttachment(0, 8);
		fd_btnNewButton.left = new FormAttachment(0, 10);
		fd_btnNewButton.height = 35;
		fd_btnNewButton.width = 250;
		btnNewButton.setLayoutData(fd_btnNewButton);
		btnNewButton.setText("M4 : Parse Dump");
		
		Button btnNewButton_1 = new Button(shlEsa, SWT.NONE);
		
		Main window = this;
		
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modul2.gui.MainWindow.init(window);
				toggle();
			}
		});
		FormData fd_btnNewButton_1 = new FormData();
		fd_btnNewButton_1.top = new FormAttachment(0, 49);
		fd_btnNewButton_1.left = new FormAttachment(0, 10);
		fd_btnNewButton_1.height = 35;
		fd_btnNewButton_1.width = 250;
		btnNewButton_1.setLayoutData(fd_btnNewButton_1);
		btnNewButton_1.setText("M2 : Concept Identification");
		
		Button btnNewButton_2 = new Button(shlEsa, SWT.NONE);
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				M3 m3 = new M3();
				m3.addWindowListener(new WindowAdapter()
		        {
		            @Override
		            public void windowClosing(WindowEvent e)
		            {
		                display.asyncExec(new Runnable() {
							public void run() {
								toggle();
							}
						});
		                e.getWindow().dispose(); 
		            }
		        });
				toggle();
			}
		});
		FormData fd_btnNewButton_2 = new FormData();
		fd_btnNewButton_2.top = new FormAttachment(0, 90);
		fd_btnNewButton_2.left = new FormAttachment(0, 10);
		fd_btnNewButton_2.width = 250;
		fd_btnNewButton_2.height = 35;
		btnNewButton_2.setLayoutData(fd_btnNewButton_2);
		btnNewButton_2.setText("M3 : Lematization and CI");
		
		Button btnNewButton_3 = new Button(shlEsa, SWT.NONE);
		btnNewButton_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modul1.vsm.gui.MainWindow window = new modul1.vsm.gui.MainWindow(display);
				toggle();
				
				window.getShell().addListener(SWT.Close, new Listener() {
					@Override
					public void handleEvent(Event event) {
						toggle();
						window.getShell().dispose();
					}
				});
				window.open();
			}
		});
		FormData fd_btnNewButton_3 = new FormData();
		fd_btnNewButton_3.top = new FormAttachment(0, 131);
		fd_btnNewButton_3.left = new FormAttachment(0, 10);
		fd_btnNewButton_3.height = 35;
		fd_btnNewButton_3.width = 250;
		btnNewButton_3.setLayoutData(fd_btnNewButton_3);
		btnNewButton_3.setText("M1 : Concept Distances");
		
		Button btnNewButton_4 = new Button(shlEsa, SWT.NONE);
		
		Main win = this;
		
		btnNewButton_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				modul5.gui.GUI.modul5(win);
				toggle();
			}
		});
		FormData fd_btnNewButton_4 = new FormData();
		fd_btnNewButton_4.top = new FormAttachment(0, 172);
		fd_btnNewButton_4.left = new FormAttachment(0, 10);
		fd_btnNewButton_4.height = 35;
		fd_btnNewButton_4.width = 250;
		btnNewButton_4.setLayoutData(fd_btnNewButton_4);
		btnNewButton_4.setText("M5 : Graphs");

	}
}
