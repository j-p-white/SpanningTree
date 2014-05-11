import java.io.IOException;
import java.util.ArrayList;


/***************
 * 
 * @author jamie
 *this class will test other calsses and methods
 */
public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		//testGoodEdges();
		testPrimsWorking();
	}// end main
	public static void testGoodEdges() throws IOException{
		makeGraph make = new makeGraph();
		make.makeMap();
		for(Edge e: make.goodEdges){
			if(e.source2.title.equals("Anime")){
				System.out.println("found anime edge");
			}
		}
	}
	public static void testPrimsWorking(){
		Point zero = new Point("pokemon");
		Point one =new Point("charmander"); 
		Point two = new Point("bulbasor");
		Point three = new Point("squrtal"); 
		Point four = new Point("weedel"); 
		Point five = new Point("charbok"); 
		Point six = new Point("weezing"); 
		Point seven = new Point("rattatat");
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
			
		map = Prims.makeTree(g);
		
		for(Edge e:map){
			System.out.println(e.toString());
		}
	}
}//end class
