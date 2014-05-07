import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class paintPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public paintPanel(){
		setBorder(BorderFactory.createLineBorder(Color.black));
	}
	
	public Dimension getPreferedSize(){
		return new Dimension(450,400);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawString("Testing", 5, 20);
	}
}
