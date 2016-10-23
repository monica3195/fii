package vsm.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;

public class MainWindow {

	protected Shell shell;
	private Text txtDipsampleinput;
	private Text txtDipoutput;
	private Button startButton;
	private Label progressLabel;
	private ProgressBar progressBar;
	private String message = "";
	private Display display;
	protected Thread thread;
	private Button cancelButton;
	private Text rateText;
	private Label rateLabel;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(426, 304);
		shell.setMinimumSize(426, 288);
		shell.setText("VSM Parser");
		shell.setLayout(new FormLayout());
		
		Label inputLabel = new Label(shell, SWT.NONE);
		FormData fd_inputLabel = new FormData();
		fd_inputLabel.top = new FormAttachment(0, 22);
		fd_inputLabel.left = new FormAttachment(0, 10);
		inputLabel.setLayoutData(fd_inputLabel);
		inputLabel.setText("Input path:");
		
		txtDipsampleinput = new Text(shell, SWT.BORDER);
		txtDipsampleinput.setText("D:\\ip\\sample-input");
		txtDipsampleinput.setEnabled(false);
		FormData fd_txtDipsampleinput = new FormData();
		fd_txtDipsampleinput.left = new FormAttachment(0, 10);
		fd_txtDipsampleinput.top = new FormAttachment(inputLabel, 6);
		txtDipsampleinput.setLayoutData(fd_txtDipsampleinput);
		
		Label outputLabel = new Label(shell, SWT.NONE);
		outputLabel.setText("Output path:");
		FormData fd_outputLabel = new FormData();
		fd_outputLabel.top = new FormAttachment(0, 70);
		fd_outputLabel.left = new FormAttachment(0, 10);
		outputLabel.setLayoutData(fd_outputLabel);
		
		txtDipoutput = new Text(shell, SWT.BORDER);
		txtDipoutput.setText("D:\\ip\\output");
		txtDipoutput.setEnabled(false);
		FormData fd_txtDipoutput = new FormData();
		fd_txtDipoutput.top = new FormAttachment(outputLabel, 6);
		fd_txtDipoutput.right = new FormAttachment(txtDipsampleinput, 0, SWT.RIGHT);
		fd_txtDipoutput.left = new FormAttachment(0, 10);
		txtDipoutput.setLayoutData(fd_txtDipoutput);
		
		Button inputButton = new Button(shell, SWT.NONE);
		fd_txtDipsampleinput.right = new FormAttachment(inputButton, -6);
		FormData fd_inputButton = new FormData();
		fd_inputButton.top = new FormAttachment(txtDipsampleinput, -2, SWT.TOP);
		inputButton.setLayoutData(fd_inputButton);
		inputButton.setText("Browse");
		inputButton.addSelectionListener(new BrowseEvent(shell, txtDipsampleinput));
		
		Button outputButton = new Button(shell, SWT.NONE);
		fd_inputButton.right = new FormAttachment(outputButton, 0, SWT.RIGHT);
		FormData fd_outputButton = new FormData();
		fd_outputButton.right = new FormAttachment(100, -10);
		fd_outputButton.top = new FormAttachment(txtDipoutput, -2, SWT.TOP);
		outputButton.setLayoutData(fd_outputButton);
		outputButton.setText("Browse");
		outputButton.addSelectionListener(new BrowseEvent(shell, txtDipoutput));
		
		startButton = new Button(shell, SWT.NONE);
		FormData fd_startButton = new FormData();
		fd_startButton.right = new FormAttachment(inputLabel, 0, SWT.RIGHT);
		fd_startButton.top = new FormAttachment(0, 230);
		fd_startButton.left = new FormAttachment(0, 10);
		startButton.setLayoutData(fd_startButton);
		startButton.setText("Start");
		
		MainWindow window = this;
		
		startButton.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!txtDipsampleinput.getText().isEmpty() && !txtDipoutput.getText().isEmpty()) {
					int rate = 0;
					
					if (rateText.isEnabled()) {
						rate = Integer.parseInt(rateText.getText());
					}
					
					thread = new Thread(new VsmThread(window, txtDipsampleinput.getText(), txtDipoutput.getText(), rate));
					thread.start();
				}
			}
		});
		
		progressBar = new ProgressBar(shell, SWT.NONE);
		progressBar.setEnabled(false);
		FormData fd_progressBar = new FormData();
		fd_progressBar.bottom = new FormAttachment(0, 210);
		fd_progressBar.right = new FormAttachment(100, -10);
		fd_progressBar.left = new FormAttachment(0, 10);
		progressBar.setLayoutData(fd_progressBar);
		
		progressLabel = new Label(shell, SWT.NONE);
		fd_progressBar.top = new FormAttachment(0, 176);
		progressLabel.setEnabled(false);
		FormData fd_lblNewLabel_1 = new FormData();
		fd_lblNewLabel_1.right = new FormAttachment(inputButton, 0, SWT.RIGHT);
		fd_lblNewLabel_1.top = new FormAttachment(0, 155);
		fd_lblNewLabel_1.left = new FormAttachment(0, 10);
		progressLabel.setLayoutData(fd_lblNewLabel_1);
		
		cancelButton = new Button(shell, SWT.NONE);
		cancelButton.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void mouseDown(MouseEvent e) {
				thread.stop();
				finish();
				progressLabel.setText("Aborted");
			}
		});
		
		FormData fd_cancelButton = new FormData();
		fd_cancelButton.top = new FormAttachment(progressBar, 20);
		fd_cancelButton.right = new FormAttachment(inputButton, 0, SWT.RIGHT);
		fd_cancelButton.left = new FormAttachment(100, -71);
		cancelButton.setLayoutData(fd_cancelButton);
		cancelButton.setText("Cancel");
		cancelButton.setVisible(false);
		
		Button normalizeCheckbox = new Button(shell, SWT.CHECK);
		normalizeCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				rateLabel.setEnabled(!rateLabel.getEnabled());
				rateText.setEnabled(!rateText.getEnabled());
			}
		});
		normalizeCheckbox.setSelection(true);
		FormData fd_normalizeCheckbox = new FormData();
		fd_normalizeCheckbox.top = new FormAttachment(0, 133);
		fd_normalizeCheckbox.left = new FormAttachment(0, 10);
		normalizeCheckbox.setLayoutData(fd_normalizeCheckbox);
		normalizeCheckbox.setText("Normalize");
		
		rateText = new Text(shell, SWT.BORDER);
		rateText.setText("10000");
		FormData fd_rateText = new FormData();
		fd_rateText.top = new FormAttachment(0, 131);
		fd_rateText.right = new FormAttachment(100, -10);
		rateText.setLayoutData(fd_rateText);
		
		rateLabel = new Label(shell, SWT.NONE);
		FormData fd_rateLabel = new FormData();
		fd_rateLabel.top = new FormAttachment(normalizeCheckbox, 1, SWT.TOP);
		fd_rateLabel.right = new FormAttachment(rateText, -6);
		rateLabel.setLayoutData(fd_rateLabel);
		rateLabel.setText("Normalization Rate :");

	}

	public void start(int numberOfDirs) {
		if (rateText.isEnabled()) {
			message = "Normalizing concepts: ";
		} else {
			message = "Calculating globals: ";
		}
		changeLabel(message);
		startButton.setEnabled(false);
		progressLabel.setEnabled(true);
		progressLabel.setText(message);
		progressBar.setEnabled(true);
		progressBar.setSelection(0);
		progressBar.setMaximum(numberOfDirs * 2);
		cancelButton.setVisible(true);
	}
	
	public void update(String dirName) {
		progressLabel.setText(message + dirName);
		progressBar.setSelection(progressBar.getSelection() + 1);
	}

	public void changeLabel(String message) {
		this.message = message;
	}
	
	public void finish() {
		startButton.setEnabled(true);
		progressLabel.setEnabled(false);
		progressLabel.setText("Finished");
		progressBar.setEnabled(false);
		cancelButton.setVisible(false);
	}

	public Display getDisplay() {
		return display;
	}
}
