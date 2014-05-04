import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class makeGraph {
	ArrayList<Point> myPoints = new ArrayList<Point>();
	ArrayList<Edge> edgeList = new ArrayList<Edge>();
	private ArrayList<Point> makePoint() throws IOException{
		Document doc;
		File myFile = new File("Tree.txt");
		Scanner scan;
		String rootUrl = "";
		String word;
		Elements htmlLinks;
		ArrayList<String> checkList = new ArrayList<String>();
		Point p,parent = new Point();
		FileWriter fw = new FileWriter(myFile,true);
		BufferedWriter bw = new BufferedWriter(fw);
		int count =0;
		checkList.add("http://en.wikipedia.org/wiki/Pok%C3%A9mon");
		myPoints.add(new Point("Pok%C3%A9mon"));
			while(checkList.size()<1000){
				scan = new Scanner(checkList.get(count));
				rootUrl = scan.nextLine();
				doc = Jsoup.connect(rootUrl).timeout(0).get();
				if(!checkList.contains(rootUrl)){
					parent.title = parseTitle(rootUrl);
					myPoints.add(parent);
					checkList.add(rootUrl);
				}
				else{
					word =parseTitle(rootUrl);
					for(Point po:myPoints){
						if(po.title.equals(word)){
							parent = po;
						}
					}
				}
				htmlLinks = doc.select("a[href]");
				for(Element e:htmlLinks){
					if(e.attr("href").contains("/wiki/") && !e.attr("href").contains("svg")&& !e.attr("href").contains(".jpg")
					&& !e.attr("href").contains("//")&& !e.attr("href").contains("#")&& !e.attr("href").contains("Amazon")
					&&! checkList.contains("http://en.wikipedia.org"+e.attr("href"))){
						
						bw.write("http://en.wikipedia.org"+e.attr("href")+"\n");
						p = new Point(parseTitle("http://en.wikipedia.org"+e.attr("href")));
						parent.links.add(parseTitle("http://en.wikipedia.org"+e.attr("href")));
						myPoints.add(p);
						checkList.add("http://en.wikipedia.org"+e.attr("href"));
					}
				}
				scan.close();
				count++;
			}//end while
			bw.close();
		return myPoints;
	}//end method	
	
	private void makeEdges() throws IOException{
		ArrayList<Point> collectedPoints = makePoint();
		
		Point p;
		for(int i =0;i< collectedPoints.size();i++){
		p = searchingEdge(edgeList,collectedPoints.get(i));
			if(!(p == null)){
				useLinks(p,edgeList,collectedPoints);
			}
			else{
				useLinks(collectedPoints.get(i),edgeList,collectedPoints);
			}
		}
		System.out.println("made edges: "+edgeList.size());
		DoubleUpEdges(edgeList);
		//return edgeList;
	}
	private Point searchingEdge(ArrayList<Edge> edgeList,Point p){
		for(Edge e:edgeList){
			//if a edge exsist with p as a source
			if(e.source2.title.equals(p.title)){
				// send back that point as source
				return p;
			}
		}
		return null;
	}
	
	private void useLinks(Point p,ArrayList<Edge> edgeList,ArrayList<Point> collectedPoints){
		Random rand = new Random();
		Point temp = null;
			if(p.links.size()>0){
				for(String s :p.links){
					for(Point po:collectedPoints){
						if(po.title.equals(s)){
							temp = po;
							break;
						}
					}
					int myInt = rand.nextInt(1000)+1;
					Edge e1 = new Edge(p,temp,myInt);
					edgeList.add(e1);
				}
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
	
	private String parseTitle(String title){
		String newTitle;
		String [] splitTitle;
		splitTitle = title.split("[\\/]+");
		newTitle = splitTitle[splitTitle.length-1];
		return newTitle;
	}
	
	public static Comparator<Edge> compareEdge = new Comparator<Edge>(){	
		public int compare(Edge e1,Edge e2){
			return e1.getWeight() - e2.getWeight();
		}
	};

	public ArrayList<Edge> Start(String source,String destination) throws IOException{
		makeEdges();
		Point temp = null,temp2 = null;
		Edge smallestEdge;
	
		Queue<Edge>openEdges = new PriorityQueue<Edge>(11,compareEdge);
		ArrayList<Edge> goodEdges = new ArrayList<Edge>();
		ArrayList<Edge> edges = new ArrayList<Edge>(edgeList);
		ArrayList<Point> unusedPoints = new ArrayList<Point>(myPoints);
		ArrayList<Point> sourceAndTarget = new ArrayList<Point>();
	
		sourceAndTarget = validate(unusedPoints,source,destination);
		if(sourceAndTarget.size() == 2){
			for(Point p:sourceAndTarget){
				if(p.title.equals(source)){
					temp = p;
				}
				if(p.title.equals(destination)){
					temp2 = p;
				}
			}
			unusedPoints.remove(temp);
			while(temp!=temp2){
				//get edges that contain temp as a source
			
		
				for(Edge e: edges){
					if(e.getSource() == temp){	
							// if your not visited add it to open edges
							if(unusedPoints.contains(e.getTarget())){
								openEdges.add(e);
							}
					}
				}//end for

				//get the smallest edge
				smallestEdge = openEdges.poll(); 
	
				//while its not in the list, its been visited, get a new one
				while(!unusedPoints.contains(smallestEdge.getTarget())){
					smallestEdge = openEdges.poll();
				}
				goodEdges.add(smallestEdge);
	
				temp = smallestEdge.getTarget(); 
				unusedPoints.remove(temp);
			}//end big while
		}
		else{
			System.out.println("bad titles");
		}
		return goodEdges;	
	}// return the list of paths i took to find the edge

	private ArrayList<Point> validate(ArrayList<Point> list,String source,String target){
		ArrayList<Point> usersPoints = new ArrayList<Point>();

		for(Point p:list){
			if(p.title.equals(source)||p.title.equals(target)){
				usersPoints.add(p);
			}
		}
		return usersPoints;
	}
}
