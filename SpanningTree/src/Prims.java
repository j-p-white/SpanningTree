import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.PriorityQueue;


public class Prims {
	
	public static Comparator<Edge> compareEdge = new Comparator<Edge>(){	
		public int compare(Edge e1,Edge e2){
			return e1.getWeight() - e2.getWeight();
		}
	};
	
	static File myFile = new File("Tree.txt");
	
	public static ArrayList<Edge> makeTree(Graph g) throws IOException{
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
		
		writePrims(goodEdges);
		
		return goodEdges;
	}//end method
	
	private static void writePrims(ArrayList<Edge> goodEdges) throws IOException{
		FileWriter fw = new FileWriter(myFile,true);
		BufferedWriter bw = new BufferedWriter(fw);
			for(Edge e:goodEdges){
				bw.write(e.source2.url+","+e.target2.url+"\n");
			}
			bw.flush();
			bw.close();
	}
	
	public void readPrims(File myFile) throws IOException{
		Scanner scan = new Scanner(myFile);
		Point s,t;
		while(scan.hasNext()){
			String edge = scan.next();
			String[] pice = edge.split(",");
			s = new Point(parseTitle(pice[0]),pice[0]);
			t = new Point(parseTitle(pice[1]),pice[1]);
			//e = new Edge(s,t,1);
			//g.addEdges(s, t);
		}
		scan.close();
	}
	
	private String parseTitle(String title){
		String newTitle;
		String [] splitTitle;
		splitTitle = title.split("[\\/]+");
		newTitle = splitTitle[splitTitle.length-1];
		return newTitle;
	}
}
