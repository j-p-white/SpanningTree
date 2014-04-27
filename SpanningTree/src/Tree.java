import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element; 
import org.jsoup.select.Elements;

public class Tree {
	
	Node root;
	int count; 
	ArrayList<Node> checkList = new ArrayList<Node>();
	String linkBase = "http://en.wikipedia.org";
	String http = "http:";
	public Tree(){
		count = 0;
	}
	public Tree(Node n){
		// this needs to add the root links to its list
		root = n;
		count = root.paths.size();
		for(Node m: root.paths){
			checkList.add(m);
		}
	}
	
	public void makeTree() throws IOException{
		 makeTree(root);
	}//end public makeTree
	
	private void makeTree(Node n) throws IOException{
		for(Node link:n.paths){
			if(link.isleaf() && !(count >=1000)){
				makeLeaf(link);
			}//end if
			else {
				break;
			}
		}//end for
	}// end recursive check
	
	private void makeLeaf(Node link) throws IOException{
		Document doc,temp;
		Elements e;
		Node myNode;
		String webSite;
		//connect ot the page 
		doc = Jsoup.connect(link.url).get();
		
		//get all the links
		e = doc.select("a[href]");
		
		for(Element ele: e){
			try{
				if(!ele.attr("href").startsWith("#") || !(e.attr("href").compareTo("en.wikipedia.orghttp") == 0)){
					if(!e.attr("href").contains("en.")){
						temp = Jsoup.connect(linkBase+e.attr("href")).get(); 
						myNode = new Node(temp.title(),linkBase+e.attr("href"));
						if(!checkForValue(myNode)){
							checkList.add(myNode);
							link.paths.add(myNode);
							count++;
						}
						else{
							link.paths.add(checkList.get(checkList.indexOf(myNode)));
							System.out.println("found a copy");
						}
					}
					else if(!ele.attr("href").startsWith(http)){
							temp = Jsoup.connect(http+e.attr("href")).get(); 
							webSite = urlTrimming(http+e.attr("href"))[1];
							if(webSite == "wikipedia"){
								myNode = new Node(temp.title(),http+e.attr("href"));
								if(!checkForValue(myNode)){
									checkList.add(myNode);
									link.paths.add(myNode);
									count++;
								}
								else{
									link.paths.add(checkList.get(checkList.indexOf(myNode)));
									System.out.println("found a copy");
								}
							}
						}
					else{
							temp = Jsoup.connect(e.attr("href")).get(); 
							webSite = urlTrimming(e.attr("href"))[1];
							if(webSite == "wikipedia"){
								myNode = new Node(temp.title(),e.attr("href"));
								if(!checkForValue(myNode)){
									checkList.add(myNode);
									link.paths.add(myNode);
									count++;
								}
								else{
									link.paths.add(checkList.get(checkList.indexOf(myNode)));
									System.out.println("found a copy");
								}
							}
						}
				}// end entry if
			}catch(HttpStatusException err){
				System.out.println("dead link found");
			}catch(UnknownHostException err2){
				break;
			}
				System.out.println("number of pages: "+count);
				if(count >=1000){
					break;
				}
			}//end for
	}
	private String[] urlTrimming(String url){
		return url.split("[.]+");
	}
	
	private boolean checkForValue(Node myNode){
		if(checkList.contains(myNode)){
			return true;
		}
		else{
			return false;
		}
	}//end checkForValue
}