import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TreeGui {

	public static void main(String [] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				createAndShowGUI();
			}
		});
	}
	
	private static void createAndShowGUI(){
		myPanel panel = new myPanel();
		SwingUtilities.isEventDispatchThread();
		JFrame frame = new JFrame("tree Frame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);
	}
}
