import java.util.ArrayList;


public class Node {
	String Title; 
	String url; 
	//int id = 0;
	public Node(){
		
	}
	public Node(String t,String u){
		Title = t;
		url = u;
		//id = numb;
	}
	
	ArrayList<Node> paths = new ArrayList<Node>();
	
	
	
	public boolean isleaf(){
		if(paths.size() == 0){
			return true;
		}
		else{
			return false;
		}
	}//end is leaf
	
}//end node class
