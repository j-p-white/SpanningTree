import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class Graph {
	Random rand;
	Map<Node,Map<Node,Integer>> graph;
	
	public Graph(){
		graph = new HashMap<Node,Map<Node,Integer>>();
		rand = new Random();
	}
	
	
	public void addEdges(Node Source,Node Target){
		int distance = rand.nextInt(1000)+1;
		//if graph does not have source 
		if(!graph.containsKey(Source)){
			graph.put(Source, new HashMap<Node,Integer>());
		}
		if(!graph.containsKey(Target)){
			graph.put(Target, new HashMap<Node,Integer>());
		}
		graph.get(Source).put(Target, distance); 
		graph.get(Target).put(Source, distance);
		
	}
	public Set<Node> getMapPoints(){
		return Collections.unmodifiableSet(graph.keySet());
	}
	public Map<Node,Integer> getEdges(Node Source){
		return Collections.unmodifiableMap(graph.get(Source));
	}
	public Iterator<Node> iterator(){
		return graph.keySet().iterator();
	}	
}