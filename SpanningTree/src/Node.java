import java.util.ArrayList;
import java.util.Random;

public class Node {
	Random rand = new Random();
	String Title; 
	String url; 
	int weight;
	//int id = 0;
	public Node(){
		weight = rand.nextInt(1000)+1;
	}
	public Node(String t,String u){
		Title = t;
		url = u;
		weight = rand.nextInt(1000)+1;
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