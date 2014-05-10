import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class myPanel extends JPanel implements ActionListener{
private static final long serialVersionUID = 1L;
protected JTextField sourceField, targetField;
protected JTextArea list;
protected JLabel myLabel;
protected JButton startButton;
protected PaintPanel paintPanel;
protected JPanel listPanel;
protected makeGraph make = new makeGraph();

	public myPanel() throws IOException{
		super(new GridBagLayout());
		make.makeMap();
		 paintPanel = new PaintPanel(make);
		 listPanel = listPanel(make.myPoints);
		 sourceField = new JTextField("source",20);
		 targetField = new JTextField("target",20);
		 startButton = new JButton("go");
		
		 
		JLabel label = new JLabel("graph");
		JPanel entrancePanel = new JPanel();
		entrancePanel.add(sourceField);
		entrancePanel.add(targetField);
		entrancePanel.add(startButton);
	
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx =0;
		c.gridy = 0;
		c.weightx = 0.0;
		c.weighty = 0.0;
		add(label,c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0; 
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 8.0;
		c.anchor = GridBagConstraints.CENTER;
		
		paintPanel.setPreferredSize(new Dimension(0,121000));
		JScrollPane scroll = new JScrollPane(paintPanel);
		scroll.setViewportView(paintPanel);
		add(scroll,c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0; 
		c.gridy = 2;
		c.weightx = 0.0;
		c.weighty = 0.0;
		add(entrancePanel,c);
		
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.EAST;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 0.2;
		c.weighty = 0.0;
		add(listPanel,c);
		
		sourceField.addActionListener(this);
		startButton.addActionListener(this);
		targetField.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String s,t;
		
			if(e.getSource() == startButton){
				
				s = sourceField.getText();
				t = targetField.getText();
					sourceField.setText("source");
					targetField.setText("target");
					make.findPath(s, t);		
			}
			paintPanel.repaint();
			paintPanel.revalidate();
	}
	
	
	private JPanel listPanel(ArrayList<Point> titles){
		
		//give this a title from the Gui
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JTextArea area = new JTextArea();
		area.setEditable(false);
		JScrollPane scroll = new JScrollPane(area);
		JLabel label = new JLabel("list");
		
		for(Point p: titles){
			area.append(p.getTitle() +"\n");
		}
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.0;
		c.weighty = 0.0;
		panel.add(label,c);	
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0; 
		c.gridy = 1; 
		c.weightx = 8.0;
		c.weighty = 1.0;
		panel.add(scroll,c);
		
		return panel;
	}
	
	public class PaintPanel extends JPanel {
		//source offset
		int sourceXoff = 20; 
		int sourceYoff = 20;
		
		//target offset
		int targetXoff = 820;
		int targetYoff = 20;
		int multiplyer = 2;
		ArrayList<Edge> path;
			
		private static final long serialVersionUID = 1L;
		public PaintPanel(makeGraph make){
			setBorder(BorderFactory.createLineBorder(Color.black));
			setBackground(Color.WHITE);
			path = make.path;	
		}
		
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			int count = 0;
			System.out.println("here in paint"+"path size: "+path.size());
			for(Edge e:path){
				g.drawString("source: "+e.getTarget().title, sourceXoff, sourceYoff*(count*multiplyer));
				g.drawString("target: "+e.getSource().title, targetXoff, targetYoff*(count*multiplyer));
				count++;
			}
		}
	}// end paint panel class	
	
	private static void createAndShowGUI() throws IOException{
		myPanel panel = new myPanel();
		panel.setOpaque(true);
		SwingUtilities.isEventDispatchThread();
		JFrame frame = new JFrame("tree Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);
	}
	
	public static void main(String [] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				try {
					createAndShowGUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	
}// end myPanelClass