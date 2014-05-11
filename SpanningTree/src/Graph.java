import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Graph{
	Map<Point,Map<Point,Integer>> graph;
	
	public Graph(){
		graph = new HashMap<Point,Map<Point,Integer>>();
	}
	
	
	public void addEdges(Point Source,Point Target){
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
		graph.get(Source).put(Target, 1); 
		graph.get(Target).put(Source, 1);
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