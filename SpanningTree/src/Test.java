import java.io.IOException;
import java.util.Map.Entry;

/***************
 * 
 * @author jamie
 *this class will test other calsses and methods
 */
public class Test {

	public static void main(String[] args) throws IOException {
		buildGraphTest(); //test reviled lots of copies// need to fix this
	}// end main
	
	public static void buildGraphTest() throws IOException{
		JsoupReading jsoupR = new JsoupReading();
		jsoupR.makeTree();
		int count = 0;
			for(Entry<Node,Integer> e: jsoupR.g.getEdges(jsoupR.root).entrySet()){
				System.out.println(" destination: "+e.getKey().Title);
				System.out.println(count);
				count++;
			}
		
	}
}//end class
