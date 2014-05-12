import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

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
protected JPanel listPanel,pathpanel;
protected CollectPoints points = new CollectPoints();

	public myPanel() throws IOException, ClassNotFoundException{
		super(new GridBagLayout());
		if(!Prims.myFile.exists()){
			//make the points
			points.makePoint();
			//after the graph is made run prims
			Prims.makeTree(points.g);	
		}
		else{
			Prims.readPrims();
		}
		
		 pathpanel = new JPanel();
		 pathpanel.setPreferredSize(new Dimension(500,250));
		 listPanel = listPanel(Prims.treeMap.keySet());
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
		add(pathpanel,c);

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
				pathpanel.removeAll();
				s = sourceField.getText();
				t = targetField.getText();
					sourceField.setBackground(Color.red);
					targetField.setBackground(Color.red);
					pathpanel.add(pathPanel(bfs(Prims.treeMap,s,t)));
					pathpanel.revalidate();
					pathpanel.repaint();
			}
			
			sourceField.setText("source");
			targetField.setText("target");
			sourceField.setBackground(Color.WHITE);
			targetField.setBackground(Color.WHITE);
	}
	
	private JPanel listPanel(Set<String> set){
		
		//give this a title from the Gui
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JTextArea area = new JTextArea();
		area.setEditable(false);
		JScrollPane scroll = new JScrollPane(area);
		JLabel label = new JLabel("list");
		scroll.setPreferredSize(new Dimension(200,250));
		for(String p: set){
			area.append(p +"\n");
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
	
	private JScrollPane pathPanel(ArrayList<Point> arrayList){
		JTextArea area = new JTextArea();
		area.setEditable(false);
		JScrollPane scroll = new JScrollPane(area); 
		scroll.setPreferredSize(new Dimension(500,250));
		
		for(Point e: arrayList){
			area.append(e.title + "\n"+(char)0x2193+"\n");
		}
		return scroll;
	}
	
	private ArrayList<Point> bfs(HashMap<String,Point> treeMap,String source,String target){
		ArrayList<Point> pendingList = new ArrayList<Point>();
		ArrayList<String> usedList = new ArrayList<String>();
		Queue<Point> queue = new LinkedList<Point>();
		
		queue.add(treeMap.get(source));
		pendingList.add(treeMap.get(source));
		usedList.add(source);
		
		while(!queue.isEmpty()){
			Point point = queue.remove();
			for(Point p:point.myList ){
				if(!usedList.contains(p.title)){
					if(p.isLeaf() && !p.title.equals(target)){
						if(point.myList.size() == 1){
							pendingList.remove(point);
						}
						queue.add(p);
						pendingList.add(p);
						usedList.add(p.title);
					}
					else if(p.title.equals(target)){
						System.out.println("found target");
						pendingList.add(p);
						break;
					}
					else{
						pendingList.add(p);
						queue.add(p);
					}
				}
			}//end for
		}// end while
		if(!validate(pendingList,target)){
			Point failed = new Point("not found");
			pendingList.clear();
			pendingList.add(failed);
		}
		System.out.println("finished");
		System.out.println("path size is: "+pendingList.size());
		return pendingList;
	}//end method
	
	private boolean validate(ArrayList<Point> list,String target){
		for(Point p:list){
			if(p.title.equals(target)){
				return true;
			}
		}
		return false;
	}
	
	
	private static void createAndShowGUI() throws ClassNotFoundException, IOException {
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
				} catch (IOException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
}// end myPanelClass