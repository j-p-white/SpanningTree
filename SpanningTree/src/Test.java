import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

/***************
 * 
 * @author jamie
 *this class will test other calsses and methods
 */
public class Test {

	public static void main(String[] args) throws IOException {
		//buildTreeTest();
		buildGraphTest(); //test reviled lots of copys// need to fix this

		
		
	}// end main

	
	public static void buildTreeTest() throws IOException{
		JsoupReading jsoupR = new JsoupReading();
		
		jsoupR.readInRoots();
		System.out.println("finished");
		
	}
	
	public static void buildGraphTest() throws IOException{
		JsoupReading jsoupR = new JsoupReading();
		Graph graph = new Graph();
		jsoupR.readInRoots();
		int count = 0;
		Map<Node,Integer> edges;
		
		
		for(Node n : jsoupR.aTree.checkList){
			for(Node l: n.paths){
				graph.addEdges(n, l);
			}//end second for
		}//end for
		
		for(Node n : jsoupR.aTree.checkList){
			edges =  graph.getEdges(n);
			for(Entry<Node,Integer> e: edges.entrySet()){
				System.out.println("source: "+n.Title+" destination: "+e.getKey().Title);
				System.out.println(count);
				count++;
			}
		}
	}
}//end class
