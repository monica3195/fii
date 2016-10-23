package esaWkipedia;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class CircleLayoutFrame extends JFrame {

	private ArrayList<Line> lineList = new ArrayList<Line>();
	public CircleLayoutFrame()

	{
		//setTitle("DrawGraph");

	}

	@SuppressWarnings({ "rawtypes" })
	public void function(String nameConcept, int nr) {

		ParseText p = new ParseText(nameConcept);
		// String nameFile = "index-maping.txt";

		// p.print(p.sort(), 10);
		p.sort();
		//
		// p.setHashConcepte(p.parseTextForName(nameFile));
		int i = 0;
		Iterator<Entry<String, Double>> it = p.getHashFrecventa().entrySet()
				.iterator();
		
		//check if there are concepts for that concept
		if (!it.hasNext()) {
			JOptionPane.showMessageDialog(null, "We don't find any concepts for "+nameConcept+"\n Please choose another concept");
		}
		else {//if there are concepts draw graph
			JButton jb1;
			jb1 = new JButton(p.printRootName(p.getRoot()));
			setTitle("Draw " + p.printRootName(p.getRoot()));
			jb1.setFont(new Font("Georgia", Font.PLAIN, 17));
			//jb1.setBorder(new RoundBorder(3));
			jb1.setBackground(Color.WHITE);
			//jb1.setFocusPainted(false);
			jb1.setContentAreaFilled(false);
			jb1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null,
							"Please choose other concept !");
				}
			});
			add(jb1);

			while (it.hasNext() && i < nr) {
				setLayout(new Circle());
				Map.Entry pair = (HashMap.Entry) it.next();
				String aux = p.compare((String) pair.getKey());
				if (aux != null) {
					JButton jb;
					jb = new JButton(aux);
					jb.setBackground(Color.WHITE);
				//	jb.setFocusPainted(false);
					jb.setContentAreaFilled(false);
					jb.setFont(new Font("Palatino Linotype", Font.PLAIN, 16));
				//jb.setBorder(new RoundBorder(3));

					jb.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							ParseText p1 = new ParseText();
							boolean ok = p1.checkIfFileExists(aux);
							if (ok == false) {
								JOptionPane.showMessageDialog(null,
										"File is missing");
							} else {
								dispose();
								CircleLayoutFrame a = new CircleLayoutFrame();
								a.function(aux, nr);
							}
						}
					});

					add(jb);
					// pack();
					i++;

				}
			}

			setSize(950, 730);
			setResizable(false);
			setVisible(true);
			setForeground(Color.DARK_GRAY);
			this.revalidate();
		}

	}
	

	// draw lines between root and nodes
	public void add(ArrayList<Line> lineList) {
		this.lineList = lineList;
	}

	public void paint(Graphics g) {
		for (Line l : lineList) {
			g.drawLine(l.getStartX(),l.getStartY(),l.getEndX(), l.getEndY());
		}
	}

	/*
	 * public void paint(Graphics g) { Graphics2D g2 = (Graphics2D) g; Line2D
	 * lin = new Line2D.Float(400, 350, 250, 380); g2.draw(lin); }
	 */

	/**
	 * A layout manager that lays out components along a circle.
	 */
	class Circle implements LayoutManager {
		public void addLayoutComponent(String name, Component comp) {
		}

		public void removeLayoutComponent(Component comp) {

		}

		public void setSizes(Container parent) {
			if (sizesSet)
				return;
			int n = parent.getComponentCount();

			preferredWidth = 0;
			preferredHeight = 0;
			minWidth = 0;
			minHeight = 0;
			maxComponentWidth = 0;
			maxComponentHeight = 0;

			// compute the maximum component widths and heights
			// and set the preferred size to the sum of the component sizes.
			for (int i = 0; i < n; i++) {
				Component c = parent.getComponent(i);
				if (c.isVisible()) {
					Dimension d = c.getPreferredSize();
					maxComponentWidth = Math.max(maxComponentWidth, d.width);
					maxComponentHeight = Math.max(maxComponentHeight, d.height);
					preferredWidth += d.width;
					preferredHeight += d.height;
				}
			}
			minWidth = preferredWidth / 2;
			minHeight = preferredHeight / 2;
			sizesSet = true;
		}

		public Dimension preferredLayoutSize(Container parent) {
			setSizes(parent);
			Insets insets = parent.getInsets();
			int width = preferredWidth + insets.left + insets.right;
			int height = preferredHeight + insets.top + insets.bottom;
			return new Dimension(width, height);
		}

		public Dimension minimumLayoutSize(Container parent) {
			setSizes(parent);
			Insets insets = parent.getInsets();
			int width = minWidth + insets.left + insets.right;
			int height = minHeight + insets.top + insets.bottom;
			return new Dimension(width, height);
		}

		public void layoutContainer(Container parent) {
			setSizes(parent);

			// compute center of the circle

			Insets insets = parent.getInsets();
			int containerWidth = parent.getSize().width - insets.left
					- insets.right;
			int containerHeight = parent.getSize().height - insets.top
					- insets.bottom;

			int xcenter = insets.left + containerWidth / 2;
			int ycenter = insets.top + containerHeight / 2;

			// compute radius of the circle

			int xradius = (containerWidth - maxComponentWidth) / 2;
			int yradius = (containerHeight - maxComponentHeight) / 2;
			int radius = Math.min(xradius, yradius);

			// lay out components along the circle

			int n = parent.getComponentCount();
			double angle = 2 * Math.PI * 0 / n;
			int x = xcenter + (int) (Math.cos(angle) * radius);
			int y = ycenter + (int) (Math.sin(angle) * radius);
			Component c = parent.getComponent(0);
			Dimension d = c.getPreferredSize();

			c.setBounds(410, 310, d.width, d.height);

			for (int i = 1; i < n; i++) {
				c = parent.getComponent(i);
				if (c.isVisible()) {
					angle = 2 * Math.PI * i / n;

					// center point of component
					x = xcenter + (int) (Math.cos(angle) * radius);
					y = ycenter + (int) (Math.sin(angle) * radius);

					// move component so that its center is (x, y)
					// and its size is its preferred size
					d = c.getPreferredSize();
					c.setBounds(x - d.width / 2, y - d.height / 2, d.width,
							d.height);
					lineList.add(new Line(456, 362, (x - d.width / 2) + 25,
							(y - d.height / 2) + 30));
					add(lineList);
					
				}
			}
		}

		private int minWidth = 0;
		private int minHeight = 0;
		private int preferredWidth = 0;
		private int preferredHeight = 0;
		private boolean sizesSet = false;
		private int maxComponentWidth = 0;
		private int maxComponentHeight = 0;
	}
}
