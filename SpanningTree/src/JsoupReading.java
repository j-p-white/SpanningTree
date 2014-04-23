import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
public class JsoupReading {

	//Scanner scan = new Scanner(); // need to make a file to read in the roots 
	int count = 0;
	Document doc;
	String rootUrl,title;
	Elements hyperLinks;
	Node n;
	ArrayList<Node> checkList = new ArrayList<Node>();
	public void readInRoots() throws IOException{
		while(count%100 == 0){
			
			//scanner reads in a new rootUrl 
			doc = Jsoup.connect(rootUrl).get();
			title = doc.title();
			
		}//end while
		
	}
	
	
}
