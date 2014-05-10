import java.io.IOException;


/***************
 * 
 * @author jamie
 *this class will test other calsses and methods
 */
public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		//testGoodEdges();
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
}//end class
