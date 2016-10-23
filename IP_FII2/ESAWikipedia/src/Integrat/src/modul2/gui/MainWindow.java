package modul2.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import mainApp.Main;

public class MainWindow {
	private JFrame frame;
	private JButton start;
	private JTextArea information;
	private JProgressBar pbar;
	private Thread t;
	protected Main main;
	final static int interval = 1000;

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { MainWindow window = new
	 * MainWindow(); window.frame.setVisible(true);
	 * window.frame.setResizable(false);
	 * window.frame.setLocationRelativeTo(null); } catch (Exception e) {
	 * e.printStackTrace(); } } }); }
	 */

	public static void init(Main win) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
					window.frame.setLocationRelativeTo(null);
					window.main = win;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow() {
		initialize();
	}

	protected void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setTitle("Concept Identification");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.out.println("UIManager Exception : " + e);
		}
		frame.getContentPane().setLayout(null);

		start = new JButton("Start");
		start.setBounds(50, 42, 334, 20);

		start.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().compareToIgnoreCase("Start") == 0) {
					start.setText("Cancel");
					t = new Thread(new BackgroundRun(pbar, information, start));
					t.start();
				} else if (e.getActionCommand().compareToIgnoreCase("Cancel") == 0) {
					start.setText("Start");
					t.stop();
				} else if (e.getActionCommand().compareToIgnoreCase("Close") == 0) {
					frame.dispose();
				}

			}
		});

		pbar = new JProgressBar(0, 100);
		pbar.setBounds(50, 140, 334, 20);
		information = new JTextArea();
		information.setBounds(50, 100, 334, 25);
		information.setFont(new Font("Verdana", Font.BOLD, 15));
		information.setEditable(false);

		frame.getContentPane().add(start);
		frame.getContentPane().add(pbar);
		frame.getContentPane().add(information);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				main.getDisplay().asyncExec(new Runnable() {
					public void run() {
						main.toggle();
					}
				});
				e.getWindow().dispose();
			}
		});

	}
}
