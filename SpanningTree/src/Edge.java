import java.util.Random;

public class Edge {
	Random rand = new Random();
	int weight; 
	Point source2,target2;
	boolean visited; 
	
	public Edge(Point source2,Point target2,int weight){
		this.source2 = source2;
		this.target2 = target2;
		this.weight = weight;
	}
	
	public boolean visitedEdge(){
		return visited;
	}
	
	public int getWeight(){
		return weight;
	}
	
	public String toString(){
		return "source point: "+ source2.title +" to target: "
				+target2.title+" distance: "+weight;
	}
}