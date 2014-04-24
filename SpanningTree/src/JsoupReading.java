/***************
 * this class places the roots in the ground to grow 
 * Tree takes in the root that this class makes
 * stores trees in a tree arraylist that may combine togeather 
 */

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
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
		String linkBase = "http://en.wikipedia.org";
		String http = "http:";
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
				//System.out.println("link: "+ e.attr("href"));
				//System.out.println(" ");
			try{
				if(!e.attr("href").startsWith("#") || !(e.attr("href").compareTo("en.wikipedia.orghttp") == 0)){
					if(!e.attr("href").contains("en.")){
						
						temp = Jsoup.connect(linkBase+e.attr("href")).get(); 
						link = new Node(temp.title(),linkBase+e.attr("href"));
						n.paths.add(link);
					}
					else{
						if(!e.attr("href").startsWith(http)){
							temp = Jsoup.connect(http+e.attr("href")).get(); 
							link = new Node(temp.title(),http+e.attr("href"));
							n.paths.add(link);
						}
						else{
							temp = Jsoup.connect(e.attr("href")).get(); 
							link = new Node(temp.title(),e.attr("href"));
							n.paths.add(link);
						}
							
					}
				}
				
			}catch(HttpStatusException err){
				System.out.println("dead link found");
			}catch(UnknownHostException err2){
				break;
			}
				System.out.println("number of links: "+ n.paths.size());
			}//end for
			
			aTree= new Tree(n);
			aTree.makeTree();
			checkList.add(aTree);
			if(aTree.checkList.size()>=1000){
				break;
			}
			
		}//end while	
	}
}
