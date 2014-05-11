import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.PriorityQueue;


public class Prims {
	public static Comparator<Edge> compareEdge = new Comparator<Edge>(){	
		public int compare(Edge e1,Edge e2){
			return e1.getWeight() - e2.getWeight();
		}
	};
	
	public static ArrayList<Edge> makeTree(Graph g){
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>(11,compareEdge);
		ArrayList<Edge> goodEdges = new ArrayList<Edge>();
		HashSet<Point> unusedPoints = new HashSet<Point>();
		unusedPoints.addAll(g.getMapPoints());
		
		Point sourcePoint = unusedPoints.iterator().next();
		unusedPoints.remove(sourcePoint);
		
		while(!unusedPoints.isEmpty()){
			
			for(Entry<Point, Integer> e:g.getEdges(sourcePoint).entrySet()){
				
				if(unusedPoints.contains(e.getKey())){
					pq.add(new Edge(sourcePoint,e.getKey(),(Integer)e.getValue()));
				}
			}//end for
			
			Edge smallEdge = pq.poll();
			
			while(!unusedPoints.contains(smallEdge.getTarget())){
				smallEdge = pq.poll();
			}
			
			goodEdges.add(smallEdge);
			
			sourcePoint = smallEdge.getTarget();
			unusedPoints.remove(sourcePoint);
		}//end while
		return goodEdges;
	}//end method
}
