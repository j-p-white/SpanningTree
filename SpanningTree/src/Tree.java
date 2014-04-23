import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element; 
import org.jsoup.select.Elements;

public class Tree {
	
	Node root;
	int count; 
	ArrayList<Node> checkList = new ArrayList<Node>();
	
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
		while(count <=100){
		count =count + makeTree(root);
		}
	}//end public makeTree
	
	private int makeTree(Node n) throws IOException{
		int count  =0;
		for(Node link:n.paths){
			if(link.isleaf()){
				count = makeLeaf(link);
			}//end if
			else{
				 count =count + makeTree(link);
			}// end else
		}//end for
		return count;
	}// end recursive check
	
	private int makeLeaf(Node link) throws IOException{
		Document doc,temp;
		int count = 0;
		Elements e;
		Node myNode;
		//connect ot the page 
		doc = Jsoup.connect(link.url).get();
		
		//get all the links
		e = doc.select("a[href]");
		
		for(Element ele: e){
			temp = Jsoup.connect(ele.attr("href")).get();
			
			//need to check if they are wiki pages or not
			
			myNode = new Node(temp.title(),ele.attr("href"));
			if(checkList.contains(myNode)){
				link.paths.add(checkList.get(checkList.indexOf(myNode)));
			}
			else{
				checkList.add(myNode); 
				link.paths.add(myNode);
				count++;
			}
		}//end for
		return count;
	}
	
}