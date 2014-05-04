import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class makeGraph {
	ArrayList<String> checkList = new ArrayList<String>();
	
	private Point makePoint(String Url) throws IOException{
		Document doc;
		File myFile = new File("Tree.txt");
		Scanner scan = new Scanner(Url);
		String rootUrl = "";
		String title;
		Elements htmlLinks;
		Point p = new Point();
		FileWriter fw = new FileWriter(myFile,true);
		BufferedWriter bw = new BufferedWriter(fw);
			if(scan.hasNext()){
				rootUrl = scan.nextLine();
				doc = Jsoup.connect(rootUrl).timeout(0).get();
				title = doc.title();
				p.title = title;
			
				htmlLinks = doc.select("a[href]");
			
				for(Element e:htmlLinks){
					if(e.attr("href").contains("/wiki/") && !e.attr("href").contains("svg")&& !e.attr("href").contains(".jpg")
					&& !e.attr("href").contains("//")&& !e.attr("href").contains("#")&& !e.attr("href").contains("Amazon")
					&&! checkList.contains("http://en.wikipedia.org"+e.attr("href"))){
						bw.write("http://en.wikipedia.org"+e.attr("href")+"\n");
						p.links.add("http://en.wikipedia.org"+e.attr("href"));
						checkList.add("http://en.wikipedia.org"+e.attr("href"));
					}
				}
				bw.close();
				scan.close();
			}
		return p;
	}//end method	
	
	public ArrayList<Point> collectingPoints() throws IOException{
		int count = 0;
		ArrayList<Point> myCollectedPoints = new ArrayList<Point>();
		Point temp = makePoint("http://en.wikipedia.org/wiki/Pok%C3%A9mon");
		myCollectedPoints.add(temp);
		while(checkList.size() <1000){
			myCollectedPoints.add(makePoint(temp.links.get(count)));
			count++;
		}
		return myCollectedPoints;
	}
	
	public ArrayList<Edge> makeEdges(ArrayList<Point> myPoints){
		//could make this more efficeint 
		ArrayList<Edge> edgeList = new ArrayList<Edge>();
		Point p;
		for(int i =0;i< myPoints.size();i++){
		p = searchingPoint(edgeList,myPoints.get(i));
			if(!(p == null)){
				useLinks(p,edgeList);
			}
			else{
				useLinks(myPoints.get(i),edgeList);
			}
		}
		DoubleUpEdges(edgeList);
		return edgeList;
	}
	
	private Point searchingPoint(ArrayList<Edge> edgeList,Point p){
		for(Edge e:edgeList){
			if(e.source2.title.equals(p.title)){
				return p;
			}
		}
		return null;
	}
	
	private void useLinks(Point p,ArrayList<Edge> edgeList){
		Random rand = new Random();
		for(String s:p.links){
			Point aPoint = new Point(s);
			int myInt = rand.nextInt(1000)+1;
			Edge e1 = new Edge(p,aPoint,myInt);
			edgeList.add(e1);
		}
	}
	
	private void DoubleUpEdges(ArrayList<Edge> edgeList){
		Point t1,t2 = new Point();
		int weight = 0;
		ArrayList<Edge> reverseEdges= new ArrayList<Edge>();
		for(Edge e:edgeList){
			t1 = e.source2; 
			t2 = e.target2; 
			weight = e.weight;
			Edge reverseCopy = new Edge(t2,t1,weight);
			reverseEdges.add(reverseCopy);
		}
		edgeList.addAll(reverseEdges);
	}
}
