import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CollectPoints {
	
	public static Comparator<Edge> compareEdge = new Comparator<Edge>(){	
		public int compare(Edge e1,Edge e2){
			return e1.getWeight() - e2.getWeight();
		}
	};
	protected Graph g = new Graph();
	public void makePoint() throws IOException {
		Point parent;
		Point poi;
		int count = 0;
		int rotationCount = 0;
			parent = new Point("Pok%C3%A9mon","http://en.wikipedia.org/wiki/Pok%C3%A9mon");
			populatePoint(parent);
			while(count <1000){		
				for(int i =0; i < parent.myList.size();i++){
					count++;
					poi = parent.myList.get(i);
					populatePoint(poi);
				}
				parent = parent.myList.get(rotationCount);
				rotationCount++;
			}
			for(int i =0; i < g.getMapPoints().size();i++){
				Point p = g.getMapPoints().iterator().next();
				for(Point link:p.myList){
					if(link.myList.isEmpty()){
						populatePoint(link);
					}
				}
			}
	//	for(Point p :g.getMapPoints()){
			
	//	}
	}//end method	
	
	private void populatePoint(Point parent) throws IOException{
		Scanner scan;
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
					parent.myList.add(p);
					g.addEdges(parent, p, 1);
				}
			}
	}
	
	private String parseTitle(String title){
		String newTitle;
		String [] splitTitle;
		splitTitle = title.split("[\\/]+");
		newTitle = splitTitle[splitTitle.length-1];
		return newTitle;
	}
}