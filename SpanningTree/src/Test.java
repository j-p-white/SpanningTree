import java.io.IOException;
import java.util.ArrayList;

/***************
 * 
 * @author jamie
 *this class will test other calsses and methods
 */
public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		//testCollectPoints(); to make this work again make makepoints public
		//testCollectEdges();
		testFindShortestPath();
	}// end main
	/* to make this work again make makepoints public
	public static void testCollectPoints() throws IOException{
		makeGraph make = new makeGraph();
		ArrayList<Point> madePoints;
		madePoints =make.makePoint();
		
		for(int i =0; i <10;i++){
			System.out.println(madePoints.get(i).title);
		}
		System.out.println("totale points: "+madePoints.size());
	
	}
	*/
	/*
	public static void testCollectEdges() throws IOException{
		makeGraph make = new makeGraph();
		ArrayList<Edge> madeEdges;
		madeEdges =make.makeEdges();
		System.out.println("total edges: "+ madeEdges.size());
	}
	*/
	public static void testFindShortestPath() throws IOException{
		makeGraph make = new makeGraph();
		ArrayList<Edge> madeEdges;
		make.makeEdges();
		madeEdges = make.Start("Anime", "Manga");
		System.out.println("total edges: "+ madeEdges.size());
	}
}//end class
