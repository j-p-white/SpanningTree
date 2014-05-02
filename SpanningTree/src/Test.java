import java.io.IOException;
import java.util.Map.Entry;

/***************
 * 
 * @author jamie
 *this class will test other calsses and methods
 */
public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		buildGraphTest(); //removed copys got to fix the graph!
	}// end main
	
	public static void buildGraphTest() throws IOException, InterruptedException{
		JsoupReading jsoupR = new JsoupReading();
		Graph g = new Graph();
		jsoupR.makeTree();
		int count = 0;
		for(Node n: jsoupR.root.paths){
			g.addEdges(jsoupR.root, n);
		}
			for(Entry<Node,Integer> e: g.getEdges(jsoupR.root).entrySet()){
				System.out.println(" destination: "+e.getKey().Title);
				System.out.println(count);
				count++;
			}
		
	}
}//end class
