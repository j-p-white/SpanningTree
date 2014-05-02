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
		
	private Node readInRoots() throws IOException{
		Document doc;
		File myFile = new File("Tree.txt");
		Scanner scan = new Scanner(myFile);
		String rootUrl = "";
		String title;
		
			rootUrl = scan.nextLine();
			doc = Jsoup.connect(rootUrl).timeout(0).get();
			title = doc.title();
			root = new Node(title,rootUrl);
			checkList.add(root);
			scan.close();
			return root;
	}//end method		
			
		private void growTree(Node node) throws IOException{
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
						node.paths.add(temp);
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
	
	public void makeTree() throws IOException, InterruptedException{
		makeTree(readInRoots());
	}//end public makeTree
	
	private void makeTree(Node n) throws IOException, InterruptedException{
		int count = 0;
		while(checkList.size() <1000){
			System.out.println("inside while");
			if(n.isleaf()){
				if(n == root){
					System.out.println("im root");
				}
				growTree(n);
				System.out.println(checkList.size());
			}
			else if(!n.isleaf() && checkList.size()<1000){
					try {
						
						System.out.println(n.paths.get(count).Title);
						System.out.println(n.paths.get(count).url);
						System.out.println(count);
						growTree(n.paths.get(count));
						count++;
					}
					catch(NullPointerException e){
						// his name was ...bob
						System.out.println("killing Node");
						n.paths.remove(count);
						Thread.sleep(5000);
					}	
			}
		}//end while
	}// end recursive check
	
	private String correctUrl(Element e) throws IOException{
		String linkBase = "http://en.wikipedia.org";
		String http = "http:";
		String website;
		String Url = null;
			if(!e.attr("href").contains("#") && !(e.attr("href").compareTo("en.wikipedia.orghttp") == 0)){
				if(!e.attr("href").contains("en.")){
					Url = linkBase+e.attr("href");
				}
				else if(!e.attr("href").startsWith(http)){ 
						Url = http+e.attr("href");
						website = urlTrimming(Url)[1];
						if(!website.equals("wikipedia")){
							Url = null;
						}
				}
				else{
						Url =e.attr("href");
						website = urlTrimming(Url)[1];
						if(!website.equals("wikipedia")){
							Url = null;
						}
				}
			}//end entry if
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
}		

