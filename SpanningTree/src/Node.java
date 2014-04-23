import java.util.ArrayList;


public class Node {
	String Title; 
	String url; 
	int id = 0;
	public Node(){
		
	}
	public Node(String t,String u, int numb){
		Title = t;
		url = u;
		id = numb;
	}
	
	ArrayList<Node> paths = new ArrayList<Node>();
	
}//end node class
