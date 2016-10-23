package modul4.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import modul4.NewIndex;
import modul4.Observer;
import modul4.ReadXml;

public class GUI_Frame extends JFrame implements ActionListener, Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JButton parse = new JButton("Parse");
	JButton openDump = new JButton("Choose dump");
	JButton openIndex = new JButton("Choose index");
	JButton stop = new JButton("Stop Parsing");
	JLabel dumpFileName = new JLabel("No file choosen.");
	JLabel indexFileName = new JLabel("No file choosen.");
	JLabel progressLabel = new JLabel("Progress");
	JTextArea progress = new JTextArea(10, 25);
	JFileChooser dump = new JFileChooser();
	JFileChooser index = new JFileChooser();
	String dumpLocation = null, indexLocation = null;
	JScrollPane scroll;
	String filesLog = "Progress:\n";
	ReadXml reader;

	int numberOfFiles = 0;
	int currentFile = 0;

	public GUI_Frame() {
		scroll = new JScrollPane(progress,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		GridBagLayout layout = new GridBagLayout();
		openDump.addActionListener(this);
		openIndex.addActionListener(this);
		parse.addActionListener(this);
		stop.addActionListener(this);
		this.setLayout(layout);

		this.add(openDump);
		this.add(dumpFileName);
		this.add(openIndex);
		this.add(indexFileName);
		this.add(parse);
		this.add(stop);
		this.add(progressLabel);
		this.add(scroll);

		layout.setConstraints(openDump, setConstraints(0, 0, 1, 1));
		layout.setConstraints(dumpFileName, setConstraints(1, 0, 1, 1));
		layout.setConstraints(openIndex, setConstraints(0, 1, 1, 1));
		layout.setConstraints(indexFileName, setConstraints(1, 1, 1, 1));
		layout.setConstraints(parse, setConstraints(0, 2, 1, 1));
		layout.setConstraints(progressLabel, setConstraints(0, 3, 2, 1));
		layout.setConstraints(scroll, setConstraints(0, 4, 2, 1));
		layout.setConstraints(stop, setConstraints(1, 2, 1, 1));

		this.setResizable(false);
		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private GridBagConstraints setConstraints(int x, int y, int width,
			int height) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = width;
		constraints.gridheight = height;

		return constraints;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("Stop Parsing")) {
			filesLog += "\n\nLast saved file: " + (currentFile - 2) + "\n\n";
			if (reader != null) {
				reader.update(-1);
			}
			parse.setEnabled(true);
			openDump.setEnabled(true);
			openIndex.setEnabled(true);
		}

		if (e.getActionCommand().equals("Parse")) {
			if (dumpLocation != null && indexLocation != null) {
				parse.setEnabled(false);
				openDump.setEnabled(false);
				openIndex.setEnabled(false);
				new NewIndex(this, indexLocation).run();
				reader = new ReadXml(dumpLocation, indexLocation, this,
						currentFile - 2);
				reader.start();
			} else if (dumpLocation == null) {
				JOptionPane.showMessageDialog(new JPanel(),
						"Select dump location.");
			} else if (indexLocation == null) {
				JOptionPane.showMessageDialog(new JPanel(),
						"Select index location.");
			}
		}
		if (e.getActionCommand().equals("Choose dump")) {
			dump.setCurrentDirectory(new File("."));
			int returnVal = dump.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = dump.getSelectedFile();
				if (file.getName().indexOf(".xml") == file.getName().length() - 4) {
					dumpLocation = file.getAbsolutePath();
					// JOptionPane.showMessageDialog(new
					// JPanel(),"You have selected: " + file.getName() + ".");
					dumpFileName.setText(file.getName());
					this.validate();
				} else
					JOptionPane.showMessageDialog(new JPanel(),
							"Please select a XML file.");
			}
		}

		if (e.getSource() == openIndex) {

			dump.setCurrentDirectory(new File("."));
			int returnVal = dump.showOpenDialog(this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = dump.getSelectedFile();
				// This is where a real application would open the file.
				if (file.getName().indexOf(".txt") == file.getName().length() - 4) {
					indexLocation = file.getAbsolutePath();
					// JOptionPane.showMessageDialog(new
					// JPanel(),"You have selected: " + file.getName() + ".");
					indexFileName.setText(file.getName());
					this.validate();
				} else
					JOptionPane.showMessageDialog(new JPanel(),
							"Please select a TXT file.");
			}
		}
	}

	@Override
	public void update(int fileCount) {
		numberOfFiles = fileCount;
		System.out.println(fileCount);
		update(0, fileCount
				+ " files have been identified.\nParsing begins.\n\n");

	}

	@Override
	public void update(int fileCount, String filename) {
		currentFile = fileCount;
		double counter = fileCount;
		double percentage = (counter * 100) / numberOfFiles;
		System.out.println("---->>>" + percentage + "   : " + fileCount
				+ "   :  " + numberOfFiles);
		filesLog = filesLog + filename + "  \nCompleted: " + percentage + "%\n";
		progress.setText(filesLog);
		this.validate();
	}

	@Override
	public boolean verifyTermination() {
		return false;
	}

}
