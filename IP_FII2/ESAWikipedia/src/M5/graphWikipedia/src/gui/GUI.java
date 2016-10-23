package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import esaWkipedia.CircleLayoutFrame;
import esaWkipedia.ParseText;

public class GUI {

	private JFrame frmEsawiki;
	private JTextField textField;
	private JTextField conceptField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmEsawiki.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmEsawiki = new JFrame();
		frmEsawiki.setTitle("ESAWikipedia");
		frmEsawiki.setBounds(100, 100, 550, 250);
		frmEsawiki.setResizable(false);
		frmEsawiki.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.out.println("UIManager Exception : " + e);
		}
		frmEsawiki.getContentPane().setLayout(null);

		JButton btnRoot = new JButton("Concept");
		JLabel textLabel = new JLabel("CONCEPT NAME     ");
		textLabel.setBounds(20, 20, 400, 50);
		textLabel.setFont(new java.awt.Font("Georgia", Font.BOLD, 12));
		frmEsawiki.getContentPane().add(textLabel);

		conceptField = new JTextField();
		conceptField.setHorizontalAlignment(SwingConstants.CENTER);
		conceptField.setForeground(Color.DARK_GRAY);
		conceptField.setColumns(10);
		conceptField.setFont(new Font("Arial", Font.PLAIN, 14));
		conceptField.setBounds(150, 30, 360, 30);
		frmEsawiki.getContentPane().add(conceptField);

		JLabel textLabel2 = new JLabel("NR. CONCEPTS ");
		textLabel2.setBounds(20, 90, 400, 50);
		textLabel2.setFont(new java.awt.Font("Georgia", Font.BOLD, 12));
		frmEsawiki.getContentPane().add(textLabel2);

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setForeground(Color.DARK_GRAY);
		textField.setColumns(10);
		textField.setFont(new Font("Arial", Font.PLAIN, 14));
		textField.setBounds(150, 100, 360, 30);
		frmEsawiki.getContentPane().add(textField);

		btnRoot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if ((conceptField.getText() == null || conceptField.getText()
						.equals(""))
						&& (textField.getText() == null || textField.getText()
								.equals(""))) {
					JOptionPane
							.showMessageDialog(null,
									"Please enter Concept and the number of concepts !!!");
				} else {
					if (textField.getText() == null
							|| textField.getText().equals("")) {
						JOptionPane.showMessageDialog(null,
								"Please enter the number of concepts !!!");
					} else {
						if (conceptField.getText() == null
								|| conceptField.getText().equals("")) {
							JOptionPane.showMessageDialog(null,
									"Please enter a CONCEPT !!!");
						} else {
							String textString = conceptField.getText();
							String textNumber = textField.getText();
							int aInt = Integer.parseInt(textNumber);

							ParseText p1 = new ParseText();
							boolean ok = p1.checkIfFileExists(textString);

							if (aInt == 0) {
								JOptionPane
										.showMessageDialog(null,
												"Please enter a number of concepts greater than 0 !");
							} else {
								if (ok == false) {
									JOptionPane.showMessageDialog(null,
											"File is missing");
								} else {
									CircleLayoutFrame frame = new CircleLayoutFrame();
									frame.function(textString, aInt);
								}
							}
						}
					}
				}
			}
		});

		btnRoot.setFont(new Font("Arial", Font.PLAIN, 14));
		btnRoot.setBounds(230, 160, 100, 32);
		frmEsawiki.getContentPane().add(btnRoot);

	}
}
