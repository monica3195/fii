package m3main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Ovidiu
 */
public class M3 extends JFrame implements ActionListener{
	JPanel container = (JPanel) getContentPane();
	JButton start = new JButton("Start");
	JLabel status = new JLabel("Apasa start pentru a incepe...");
	JProgressBar progress = new JProgressBar(0,200);
	public M3(){
		JPanel panouPrincipal = new JPanel();
		setTitle("Concept Identifier By Ovidiu V. and M3 Team");
		container.add(panouPrincipal);
		panouPrincipal.setLayout(new BoxLayout(panouPrincipal, BoxLayout.Y_AXIS));
		JPanel startPanel = new JPanel();
		start.addActionListener(this);
		startPanel.add(start);
		startPanel.setMaximumSize(new Dimension(150,60));
		startPanel.setBorder(BorderFactory.createTitledBorder(null,"Start processing",TitledBorder.CENTER,TitledBorder.TOP));
		panouPrincipal.add(startPanel);
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(null,"Status",TitledBorder.CENTER,TitledBorder.TOP),BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
		statusPanel.setMaximumSize(new Dimension(300,50));
		statusPanel.add(status);
		panouPrincipal.add(statusPanel);
		JPanel progressBar = new JPanel();
		progressBar.setBorder(BorderFactory.createTitledBorder(null,"Progress",TitledBorder.CENTER,TitledBorder.TOP));
		progressBar.setMaximumSize(new Dimension(300,100));
		progress.setPreferredSize(new Dimension(250,50));
		progressBar.add(progress);
		panouPrincipal.add(progressBar);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setPreferredSize(new Dimension(440,270));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocation(dim.width/2-getSize().width/2, dim.height/2-getSize().height/2);
		setVisible(true);
	}
	public static void main(String[] args) {
		M3 m3 = new M3();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Thread runner = new Thread(new backgroundThread(progress,start,status));
		runner.start();
	}
}
