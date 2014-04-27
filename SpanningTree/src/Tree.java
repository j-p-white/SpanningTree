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
			if(link.isleaf()){
				count = count + makeLeaf(link);
			}//end if
			else{
				 makeTree(link);
			}// end else
		}//end for
	}// end recursive check
	
	private int makeLeaf(Node link) throws IOException{
		Document doc,temp;
		int count = 0;
		Elements e;
		Node myNode;
		String webSite;
		//connect ot the page 
		doc = Jsoup.connect(link.url).get();
		
		//get all the links
		e = doc.select("a[href]");
		
		for(Element ele: e){
			
			//need to check if they are wiki pages or not
			
			try{
				if(!ele.attr("href").startsWith("#") || !(e.attr("href").compareTo("en.wikipedia.orghttp") == 0)){
					if(!e.attr("href").contains("en.")){
						
						temp = Jsoup.connect(linkBase+e.attr("href")).get(); 
						myNode = new Node(temp.title(),linkBase+e.attr("href"));
						link.paths.add(myNode);
					}
					else{
						if(!ele.attr("href").startsWith(http)){
							temp = Jsoup.connect(http+e.attr("href")).get(); 
							webSite = urlTrimming(http+e.attr("href"))[1];
							if(webSite == "wikipedia"){
								myNode = new Node(temp.title(),http+e.attr("href"));
								link.paths.add(myNode);
							}
						}
						else{
							temp = Jsoup.connect(e.attr("href")).get(); 
							webSite = urlTrimming(e.attr("href"))[1];
							if(webSite == "wikipedia"){
								myNode = new Node(temp.title(),e.attr("href"));
								link.paths.add(myNode);
							}
						}
							
					}
				}
				
			}catch(HttpStatusException err){
				System.out.println("dead link found");
			}catch(UnknownHostException err2){
				break;
			}
				System.out.println("number of links: "+ link.paths.size());
			}//end for
		return count;
	}
	private String[] urlTrimming(String url){
		return url.split("[.]+");
	}
}