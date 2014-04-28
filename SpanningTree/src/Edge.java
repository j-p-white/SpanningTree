import java.util.Random;

public class Edge {
	Random rand = new Random();
	int weight; 
	Node source,target;
	boolean visited; 
	
	public Edge(Node source,Node target){
		weight =rand.nextInt(1000)+1;
		this.source = source; 
		this.target = target;
	}//end pubilc constructor
	
	public boolean visitedEdge(){
		return visited;
	}
	
	public Node getSource(){
		return source;
	}
	
	public Node getTarget(){
		return target;
	}
	
	public boolean visitedNodes(Node source,Node target){
		source.visited = true;
		target.visited = true; 
		return(source.visited && target.visited);
	}
	
	public String toString(){
		return "source node: "+ source.Title +" to target: "
				+target.Title+" distance: "+weight;
	}
}