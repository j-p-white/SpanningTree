import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

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
	HashMap<String,Point> treeMap= new HashMap<String,Point>();
	File myFile = new File("Tree.txt");
	
	public void makePoint() throws IOException {
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
	}//end method	
	
	private void populatePoint(Point parent) throws IOException{
		Scanner scan;
		Edge ed;
		Elements htmlLinks;
		Document doc;
		String rootUrl;
		scan = new Scanner(parent.getUrl());
		rootUrl = scan.nextLine();
		scan.close();
		doc = Jsoup.connect(rootUrl).timeout(0).get();
		Point p;
		htmlLinks = doc.select("a[href]");
		
		for(Element e:htmlLinks){
			if(e.attr("href").contains("/wiki/") && !e.attr("href").contains("svg")&& !e.attr("href").contains(".jpg")
			&& !e.attr("href").contains("//")&& !e.attr("href").contains("#")&& !e.attr("href").contains("Amazon") && e.attr("href").length()>0){
				
				p = new Point(parseTitle("http://en.wikipedia.org"+e.attr("href")),"http://en.wikipedia.org"+e.attr("href"));
					myPoints.add(p);
					parent.myList.add(p);
					//g.addEdges(parent, p);
					ed = new Edge(parent,p,1);
					edgeList.add(ed);
				}
			}
		treeMap.put(parent.title, parent);	
	}
	
	private String parseTitle(String title){
		String newTitle;
		String [] splitTitle;
		splitTitle = title.split("[\\/]+");
		newTitle = splitTitle[splitTitle.length-1];
		return newTitle;
	}
}