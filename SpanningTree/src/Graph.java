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
	
	public void addEdges(Point source,Point target,int weight){
		//if graph does not have source 
		if(source == null){
			throw new NullPointerException("source is null");
		}
		if(target == null){
			throw new NullPointerException("target is null");
		}
		if(!graph.containsKey(source)){
			graph.put(source, new HashMap<Point,Integer>());
		}
		if(!graph.containsKey(target)){
			graph.put(target, new HashMap<Point,Integer>());
		}
		graph.get(source).put(target, weight); 
		graph.get(target).put(source, weight);
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