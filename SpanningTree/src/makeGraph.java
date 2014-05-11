import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class makeGraph {
	
	public static Comparator<Edge> compareEdge = new Comparator<Edge>(){	
		public int compare(Edge e1,Edge e2){
			return e1.getWeight() - e2.getWeight();
		}
	};
	
	Graph g= new Graph();
	ArrayList<Edge> edgeList = new ArrayList<Edge>();
	ArrayList<Edge> path = new ArrayList<Edge>();
	ArrayList<Point> myPoints = new ArrayList<Point>();
	ArrayList<Edge> goodEdges = new ArrayList<Edge>();
	File myFile = new File("Tree.txt");
	
	private void makePoint() throws IOException {
		Point parent;
		Point poi;
		int count = 0;
		int rotationCount = 0;
			parent = new Point("Pok%C3%A9mon","http://en.wikipedia.org/wiki/Pok%C3%A9mon");
			populatePoint(parent);
			while(count <1000){		
				for(int i =0; i < parent.myList.size();i++){
					System.out.println("im in link = "+i+" of "+parent.title);
					System.out.println("i currently have: "+count+" nodes");
					count++;
					poi = parent.myList.get(i);
					myPoints.add(poi);
					System.out.println("poi is :"+poi.title);
					populatePoint(poi);
				}
				parent = parent.myList.get(rotationCount);
				rotationCount++;
			}
			writeEdgeList(myFile);
	}//end method	
	
	private void writeEdgeList(File myFile) throws IOException{
		FileWriter fw = new FileWriter(myFile,true);
		BufferedWriter bw = new BufferedWriter(fw);
			for(Edge e:edgeList){
				bw.write(e.source2.url+","+e.target2.url+"\n");
			}
			bw.flush();
			bw.close();
	}
	
	public void readEdgeList(File myFile) throws IOException{
		Scanner scan = new Scanner(myFile);
		Point s,t;
		while(scan.hasNext()){
			String edge = scan.next();
			String[] pice = edge.split(",");
			s = new Point(parseTitle(pice[0]),pice[0]);
			t = new Point(parseTitle(pice[1]),pice[1]);
			//e = new Edge(s,t,1);
			g.addEdges(s, t);
			if(!myPoints.contains(s)){
				myPoints.add(s);
			}
		}
		scan.close();
		System.out.println("myPoints size: "+myPoints.size());
	}
	
	private void populatePoint(Point parent) throws IOException{
		//ArrayList<Point> nPoints = new ArrayList<Point>();
		Scanner scan;
		Edge ed;
		Elements htmlLinks;
		Document doc;
		String rootUrl;
		scan = new Scanner(parent.getUrl());
		rootUrl = scan.nextLine();
		doc = Jsoup.connect(rootUrl).timeout(0).get();
		Point p;
		htmlLinks = doc.select("a[href]");
		
		for(Element e:htmlLinks){
			if(e.attr("href").contains("/wiki/") && !e.attr("href").contains("svg")&& !e.attr("href").contains(".jpg")
			&& !e.attr("href").contains("//")&& !e.attr("href").contains("#")&& !e.attr("href").contains("Amazon") && e.attr("href").length()>0){
				
				p = new Point(parseTitle("http://en.wikipedia.org"+e.attr("href")),"http://en.wikipedia.org"+e.attr("href"));
					myPoints.add(p);
					parent.myList.add(p);
					g.addEdges(parent, p);
					ed = new Edge(parent,p,1);
					edgeList.add(ed);
				}
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

	public void makeMap() throws IOException{
		makePoint();
		//write the map to the file
		
		
		Edge smallestEdge;
	
		Queue<Edge>openEdges = new PriorityQueue<Edge>(11,compareEdge);
		Set<Point> unusedPoints = new HashSet<Point>();
		unusedPoints.addAll(g.getMapPoints());
		
		Point sourceP = unusedPoints.iterator().next();
		unusedPoints.remove(sourceP);
		
			while(!unusedPoints.isEmpty()){
				for(Entry<Point,Integer> e:g.getEdges(sourceP).entrySet()){					
					if(unusedPoints.contains(e.getKey())){
						openEdges.add(new Edge(sourceP,e.getKey(),(Integer)e.getValue()));
					}
				}//end for
				System.out.println("unusedPoints: "+unusedPoints.size());
				//get the smallest edge
				smallestEdge = openEdges.poll(); 
	
				//while its not in the list, its been visited, get a new one
				while(!unusedPoints.contains(smallestEdge.getTarget())){
					smallestEdge = openEdges.poll();
				}
				
				goodEdges.add(smallestEdge);
	
				sourceP = smallestEdge.getTarget(); 
				unusedPoints.remove(sourceP);
			}//end big while
	}
	
	public void findPath(String source,String target){
		for(Edge e:goodEdges){
			if(e.source2.title.equals(source) && e.visited == false){
				e.visited = true;
				if(findPathRecursive(e,goodEdges,target)){
					System.out.println("path found!");
					break;
				}
			}
		}
	}
	
	private boolean findPathRecursive(Edge edge,ArrayList<Edge> map,String goal){
		System.out.println("im recursing: "+ edge.source2.title);
		boolean found = false;
			edge.visited = true;
			if(edge.target2.title.equals(goal)){
				path.add(edge);
				return true;
			}
			for(Edge e:map){
				if(e.source2.title.equals(edge.target2.title)&& !e.visited == false){
					found = findPathRecursive(e,map,goal);
					if(found == true){
						path.add(e);
						break;
					}
				}
			}
			return found;
	}
}