/***************
 * this class places the roots in the ground to grow 
 * Tree takes in the root that this class makes
 * stores trees in a tree arraylist that may combine togeather 
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
public class JsoupReading {

	
	Scanner scan;
	ArrayList<Tree> checkList = new ArrayList<Tree>();
	public void readInRoots() throws IOException{
		Document doc,temp;
		String rootUrl = "";
		String title;
		File myFile = new File("Tree.txt");
		scan = new Scanner(myFile); 
		Node n,link;
		Tree aTree;
		Elements ele;
		
		while(scan.hasNext()){
			//scanner reads in a new rootUrl
			rootUrl = scan.nextLine();
			doc = Jsoup.connect(rootUrl).get();
			title = doc.title();
			n = new Node(title,rootUrl);
			
			//get all the links
			ele = doc.select("a[href]");
			for(Element e:ele){
				temp = Jsoup.connect(e.attr("href")).get();
				link = new Node(temp.title(),e.attr("href"));
				n.paths.add(link);
			}
			aTree= new Tree(n);
			aTree.makeTree();
			checkList.add(aTree);
			
		}//end while	
	}
}
