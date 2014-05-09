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
		//testConnectibity();
	}// end main
	
	public static void testCollectEdges() throws IOException{
		makeGraph make = new makeGraph();
		ArrayList<Edge> madeEdges;
		make.makeEdges();
		
		madeEdges = make.edgeList;
		System.out.println("total edges: "+ madeEdges.size());
	}
	public static void testConnectibity() throws IOException{
		makeGraph make = new makeGraph();
		make.makePoint();
		
		for(Point p:make.myPoints){
			if(p.links.size()>0){
				System.out.println("");
				System.out.println("p's name: "+p.title);
				for(String s:p.links){
					System.out.println("p's links:"+s);
				}
				System.out.println("numbLInks: "+ p.links.size());
			}
		}
	}
}//end class
