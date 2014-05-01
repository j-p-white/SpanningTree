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

		
		Node root;
		ArrayList<Node> checkList = new ArrayList<Node>();
		Document tempDoc = null;
		Graph g;
	
	
	
	private Node readInRoots() throws IOException{
		Document doc;
		File myFile = new File("Tree.txt");
		Scanner scan = new Scanner(myFile);
		String rootUrl = "";
		String title;
		
		///while(scan.hasNext()){
			//scanner reads in a new rootUrl
			rootUrl = scan.nextLine();
			doc = Jsoup.connect(rootUrl).timeout(0).get();
			title = doc.title();
			root = new Node(title,rootUrl);
			checkList.add(root);
			scan.close();
			return root;
		//}//end while	
	}//end method		
			
		private void growTree(Node node) throws IOException{
			Graph g = new Graph(); 
			Elements ele;
			System.out.println("growing tree");
			Node myNode,temp = null;
			tempDoc = Jsoup.connect(node.url).timeout(0).get();
			 ele = tempDoc.select("a[href]");
			for(Element e:ele){
			try{	
				// your not in the list add to list
				if(!(correctUrl(e)== null)){
					tempDoc = Jsoup.connect(correctUrl(e)).timeout(0).get();
					myNode = new Node(tempDoc.title(),correctUrl(e));
					if(!scanList(myNode)){
						checkList.add(myNode);
						node.paths.add(myNode);
						g.addEdges(node, myNode);
						System.out.println("current Nodes in tree"+checkList.size());
						System.out.println("nodes title: "+myNode.Title);
						
					}
					// your in the list but not in my links
					else if( scanList(myNode) && !node.paths.contains(myNode)){
						for(int i=0;i< checkList.size();i++){
							if(checkList.get(i) == myNode){
								temp = checkList.get(i);
							}
						}
						g.addEdges(node, temp);
						node.paths.add(myNode);
					}
				}// end if
			}//end try
			catch(HttpStatusException err){
				tempDoc = null;
				System.out.println("dead link found");
				
			}catch(UnknownHostException err2){
				tempDoc = null;
				System.out.println("badHost");
			}
			}//end for	
		}// end method
			
	
	private String[] urlTrimming(String url){
		return url.split("[.]+");
	}
	
	public void makeTree() throws IOException{
		Node root = readInRoots();
		makeTree(root);
	}//end public makeTree
	
	private void makeTree(Node n) throws IOException{
		int count = 0;
		while(checkList.size() <1000){
			System.out.println("inside while");
			if(n.isleaf()){
				growTree(n);
				System.out.println(checkList.size());
			}
			else if(!n.isleaf() && checkList.size()<1000){
					growTree(n.paths.get(count));
					count++;
				
			}
		}//end while
	}// end recursive check
	
	private String correctUrl(Element e) throws IOException{
		tempDoc = new Document("");
		String linkBase = "http://en.wikipedia.org";
		String http = "http:";
		String website;
		String Url = null;
		//try{
			if(!e.attr("href").startsWith("#") || !(e.attr("href").compareTo("en.wikipedia.orghttp") == 0)){
				if(!e.attr("href").contains("en.")){
					//tempDoc = Jsoup.connect(linkBase+e.attr("href")).timeout(0).get();
					Url = linkBase+e.attr("href");
				}
				else if(!e.attr("href").startsWith(http)){
						//tempDoc = Jsoup.connect(http+e.attr("href")).timeout(0).get(); 
						Url = http+e.attr("href");
						website = urlTrimming(Url)[1];
						if(!website.equals("wikipedia")){
							Url = null;
						}
				}
				else{
						//tempDoc = Jsoup.connect(e.attr("href")).timeout(0).get(); 
						Url =e.attr("href");
						website = urlTrimming(Url)[1];
						if(!website.equals("wikipedia")){
							Url = null;
						}
				}
			}//end entry if
			/*
		}//end try
		catch(HttpStatusException err){
			tempDoc = null;
			System.out.println("dead link found");
			
		}catch(UnknownHostException err2){
			tempDoc = null;
			System.out.println("badHost");
		}
	*/
			return Url;
	}//end method
	
	private boolean scanList(Node node){
		for(Node c:checkList){
			if(c.Title.equals(node.Title)){
				return true;
			}
		}
		return false;
	}
	public Graph getGraph(){
		return g;
	}
}		

