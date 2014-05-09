import java.io.IOException;
import java.util.ArrayList;

/***************
 * 
 * @author jamie
 *this class will test other calsses and methods
 */
public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		testCollectEdges();
	}// end main
	
	public static void testCollectEdges() throws IOException{
		makeGraph make = new makeGraph();
		ArrayList<Edge> madeEdges;
		make.makeEdges();
		
		madeEdges = make.edgeList;
		System.out.println("total edges: "+ madeEdges.size());
	}
}//end class
