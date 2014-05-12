import java.io.IOException;
import java.util.ArrayList;


/***************
 * 
 * @author jamie
 *this class will test other calsses and methods
 */
public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		testPrimsWorking();
	}// end main
	public static void testPrimsWorking() throws IOException{
		Point zero = new Point("pokemon");
		Point one =new Point("charmander"); 
		Point two = new Point("bulbasor");
		Point three = new Point("squrtal"); 
		Point four = new Point("weedl"); 
		Point five = new Point("charbok"); 
		Point six = new Point("weezing"); 
		Point seven = new Point("rattatat");
		
		ArrayList<Point> myPoints = new ArrayList<Point>();
		myPoints.add(zero);
		myPoints.add(one);
		myPoints.add(two);
		myPoints.add(three);
		myPoints.add(four);
		myPoints.add(five);
		myPoints.add(six);
		myPoints.add(seven);
		
		for(Point p:myPoints){
			for(Point p2:myPoints){
				p.myList.add(p2);
			}
		}
		
		ArrayList<Edge> map = new ArrayList<Edge>();
		Graph g = new Graph();
		g.addEdges(zero, one, 5);
		g.addEdges(zero, two, 8);
		g.addEdges(zero, four, 2);
		g.addEdges(one, three, 2);
		g.addEdges(one, two, 3);
		g.addEdges(two, six, 9);
		g.addEdges(two, five, 8);
		g.addEdges(two, four, 4);
		g.addEdges(five,seven, 2);
		
		g.addEdges(one, zero, 5);
		g.addEdges(two, zero, 8);
		g.addEdges(four, zero, 2);
		g.addEdges(three, one, 2);
		g.addEdges(two, one, 3);
		g.addEdges(six, two, 9);
		g.addEdges(five, two, 8);
		g.addEdges(four, two, 4);
		g.addEdges(seven,five, 2);
			
		map = Prims.makeTree(g);
		
		for(Edge e:map){
			System.out.println(e.toString());
			//System.out.println((char)0x2193);//down arrow
		}
	}
}//end class
