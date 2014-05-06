import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;


public class paintPanel extends JPanel {

	public Dimension getPreferedSize(){
		return new Dimension(250,400);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawString("Testing", 20, 10);
	}
}
