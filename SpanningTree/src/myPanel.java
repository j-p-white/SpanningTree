import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class myPanel extends JPanel implements ActionListener{

private static final long serialVersionUID = 1L;
protected JTextField sourceField, targetField;
protected JTextArea list;
protected JLabel myLabel;

	public myPanel(){
		super(new GridBagLayout());
		paintPanel paint = new paintPanel();
		JTextField source = new JTextField(20);
		JTextField target = new JTextField(20);
		JLabel label = new JLabel("graph");
		JLabel sourceL = new JLabel("source");
		JLabel targetL = new JLabel("target");
		JPanel entrancePanel = new JPanel();
		//entrencePanel.add
		entrancePanel.add(source);
		entrancePanel.add(target);
		entrancePanel.add(new JButton("go"));
	
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
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.CENTER;
		add(new JScrollPane(paint),c);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0; 
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.weightx = 0.0;
		c.weighty = 0.0;
		add(entrancePanel,c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.weightx = 3.0;
		c.weighty = 0.0;
		add(listPanel(),c);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private JPanel listPanel(){
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JTextArea area = new JTextArea();
		JScrollPane scroll = new JScrollPane(area);
		JLabel label = new JLabel("list");
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.0;
		c.weighty = 0.0;
		
		panel.add(label,c);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0; 
		c.gridy = 1; 
		c.weightx = 3.0;
		c.weighty = 1.0;
		panel.add(scroll,c);
		
		return panel;
	}
	
}
