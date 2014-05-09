import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Graph {
	Random rand;
	Map<Point,Map<Point,Integer>> graph;
	
	public Graph(){
		graph = new HashMap<Point,Map<Point,Integer>>();
		rand = new Random();
	}
	
	
	public void addEdges(Point Source,Point Target){
		int distance = rand.nextInt(1000)+1;
		//if graph does not have source 
		if(Source == null){
			throw new NullPointerException("source is null");
		}
		if(Target == null){
			throw new NullPointerException("target is null");
		}
		if(!graph.containsKey(Source)){
			graph.put(Source, new HashMap<Point,Integer>());
		}
		if(!graph.containsKey(Target)){
			graph.put(Target, new HashMap<Point,Integer>());
		}
		graph.get(Source).put(Target, distance); 
		graph.get(Target).put(Source, distance);
		
	}
	public Set<Point> getMapPoints(){
		return Collections.unmodifiableSet(graph.keySet());
	}
	public Map<Point,Integer> getEdges(Point Source){
		return Collections.unmodifiableMap(graph.get(Source));
	}
	public Iterator<Point> iterator(){
		return graph.keySet().iterator();
	}	
}