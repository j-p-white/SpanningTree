import java.io.IOException;

/***************
 * 
 * @author jamie
 *this class will test other calsses and methods
 */
public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		writeToFileTest();
	}// end main
	

	public static void writeToFileTest() throws IOException{
		makeGraph make = new makeGraph();
		int count = 0;
		for(Edge e :make.makeEdges(make.collectingPoints())){
			System.out.println(e.toString());
			count++;
		}
		System.out.println("total edges: "+count);
		
		System.out.println("finished makeGraph");
	}
	
}//end class
