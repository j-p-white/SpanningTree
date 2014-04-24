import java.io.IOException;

/***************
 * 
 * @author jamie
 *this class will test other calsses and methods
 */
public class Test {

	public static void main(String[] args) throws IOException {
		buildTreeTest();

		
		
	}// end main

	
	public static void buildTreeTest() throws IOException{
		JsoupReading jsoupR = new JsoupReading();
		
		jsoupR.readInRoots();
		System.out.println("finished");
		
	}
	
	
	
}//end class
