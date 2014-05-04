import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;


public class FindShortestPath{

	public static Comparator<Edge> compareEdge = new Comparator<Edge>(){
		public int compare(Edge e1,Edge e2){
			return e1.getWeight() - e2.getWeight();
		}
	};
	
	public List<Edge> Start(Graph graph,String source,String destination){
		Queue<Edge>openEdges = new PriorityQueue<Edge>(11,compareEdge);
		Node temp = new Node();
		Edge smallestEdge;
		List<Edge> goodEdges = new ArrayList<Edge>();
		Set<Node> unvisitedNodes = new HashSet<Node>();
		unvisitedNodes.addAll(graph.getMapPoints());
		unvisitedNodes.remove(source);
		
		 temp.Title = source;
		 while(temp.Title!=destination){
			 for(Entry<Node,Integer>e:graph.getEdges(temp).entrySet()){
				 // if your not visited add it to open edges
					if(unvisitedNodes.contains(e.getKey())){
						openEdges.add(new Edge(temp,e.getKey(),(Integer) e.getValue()));
					}
			 }//end for
	
			 //get the smallest edge
			 smallestEdge = openEdges.poll(); 
		
			 //while its not in the list, its been visited, get a new one
			 while(unvisitedNodes.contains(smallestEdge.getTarget())){
				 smallestEdge = openEdges.poll();
			 }
			 goodEdges.add(smallestEdge);
		
			 temp = smallestEdge.getTarget(); 
			 unvisitedNodes.remove(temp);
		 }//end big while
	return goodEdges;	
	}// return the list of paths i took to find the edge
}