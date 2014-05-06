import java.awt.Canvas;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class myPanel extends JPanel implements ActionListener{
protected JTextField sourceField, targetField;
protected JTextArea list;

	public myPanel(){
		super(new GridBagLayout());
		paintPanel paint = new paintPanel();
		
		sourceField = new JTextField(25);
		sourceField.addActionListener(this);
		
		targetField = new JTextField(25);
		targetField.addActionListener(this);
		
		list = new JTextArea(5,20);
		list.setEditable(false);
		JScrollPane scroll = new JScrollPane(list);
		
		//add gridbagcontrants
		GridBagConstraints c = new GridBagConstraints();
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 0;
		c.gridy = 0;
		add(sourceField,c);
		
		c.gridx = 2;
		c.gridy = 0;
		add(targetField,c);
		
		//c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(scroll,c);
		
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(paint,c);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		list.setCaretPosition(list.getDocument().getLength());
	}
}
