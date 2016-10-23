//package mainApp;
//
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowEvent;
//import java.awt.event.WindowListener;
//
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//
//import modul3.main.M3;
//import modul4.gui.GUI_Frame;
//
//public class MainWindow extends JFrame implements WindowListener,
//		ActionListener {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//
//	private JButton m1, m2, m3, m4, m5;
//	private GridLayout layout;
//	private JPanel menu;
//
//	public MainWindow() {
//		menu = new JPanel();
//		layout = new GridLayout(5, 1);
//		menu.setPreferredSize(new Dimension(300, 250));
//		m1 = new JButton("Concept Distances (m1)");
//		m1.setPreferredSize(new Dimension(10, 50));
//		m2 = new JButton("Concept Identification (m2)");
//		m3 = new JButton("Lematization and CI (m3)");
//		m4 = new JButton("Parse Dump (m4)");
//		m5 = new JButton("View Graph (m5)");
//		layout.setVgap(15);
//		layout.setHgap(155);
//		menu.add(m4);
//		menu.add(m2);
//		menu.add(m3);
//		menu.add(m1);
//		menu.add(m5);
//		menu.setLayout(layout);
//		this.add(menu);
//		this.setPreferredSize(new Dimension(400, 300));
//		menu.setLocation(100, 10);
//		this.setLayout(new FlowLayout());
//		this.pack();
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		this.setVisible(true);
//
//		m1.addActionListener(this);
//		m2.addActionListener(this);
//		m3.addActionListener(this);
//		m4.addActionListener(this);
//		m5.addActionListener(this);
//
//	}
//
//	@Override
//	public void windowActivated(WindowEvent arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void windowClosed(WindowEvent arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void windowClosing(WindowEvent arg0) {
//		m1.setEnabled(true);
//		m2.setEnabled(true);
//		m3.setEnabled(true);
//		m4.setEnabled(true);
//		m5.setEnabled(true);
//
//	}
//
//	@Override
//	public void windowDeactivated(WindowEvent arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void windowDeiconified(WindowEvent arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void windowIconified(WindowEvent arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void windowOpened(WindowEvent arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		JButton b = (JButton) e.getSource();
////		m1.setEnabled(false);
////		m2.setEnabled(false);
////		m3.setEnabled(false);
////		m4.setEnabled(false);
////		m5.setEnabled(false);
//		if (b == m1) {
//			try {
//				modul1.vsm.gui.MainWindow window = new modul1.vsm.gui.MainWindow();
//				window.open();
//			} catch (Exception err) {
//				err.printStackTrace();
//			}
//		} else if (b == m2) {
//			modul2.gui.MainWindow.init();
//		} else if (b == m3) {
//			M3 m3 = new M3();
//			m3.addWindowListener(this);
//		} else if (b == m4) {
//			GUI_Frame g = new GUI_Frame();
//			g.addWindowListener(this);
//		} else {
//			modul5.gui.GUI.modul5();
//		}
//
//	}
//
//}
