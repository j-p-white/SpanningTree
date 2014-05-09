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

//the tree is not connected completely, 
public class makeGraph {
	
	public static Comparator<Edge> compareEdge = new Comparator<Edge>(){	
		public int compare(Edge e1,Edge e2){
			return e1.getWeight() - e2.getWeight();
		}
	};
	
	ArrayList<Point> myPoints = new ArrayList<Point>();
	ArrayList<Edge> edgeList = new ArrayList<Edge>();
	ArrayList<Edge> path = new ArrayList<Edge>();
	public ArrayList<Point> makePoint() throws IOException{
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
				//inital setup if
				if(!checkList.contains(rootUrl)){
					parent.title = parseTitle(rootUrl);
					myPoints.add(parent);
					checkList.add(rootUrl);
				}
				//after inital set up
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
						if(parent.links.contains(parseTitle("http://en.wikipedia.org"+e.attr("href")))){
							parent.links.add(parseTitle("http://en.wikipedia.org"+e.attr("href")));
						}
						if(!myPoints.contains(p)){
							myPoints.add(p);
						}
						checkList.add("http://en.wikipedia.org"+e.attr("href"));
					}
				}
				scan.close();
				count++;
			}//end while
			bw.close();
		return myPoints;
	}//end method	
	
	public void makeEdges() throws IOException{
		ArrayList<Point> collectedPoints = makePoint();//very small like 7
		
		Point p;
		for(int i =0;i< collectedPoints.size();i++){
			System.out.println("the node im on: "+i);
		p = searchingEdge(edgeList,collectedPoints.get(i));
			if(!(p == null)){
				useLinks(p,collectedPoints);
			}
			else{
				useLinks(collectedPoints.get(i),collectedPoints);
			}
		}
		System.out.println("the edgeList after: "+edgeList.size());
		
		//System.out.println("Points: "+collectedPoints.size());
		
		//DoubleUpEdges(edgeList);
	}
	// this method is not collecting edges--madness!!!
	private Point searchingEdge(ArrayList<Edge> edgeList,Point p){
		System.out.println("edgeList size: "+edgeList.size());
		for(Edge e:edgeList){
			System.out.println("the e im useing: "+e.target2.title);
			
			if(e.target2.title.equals(p.title)){
				System.out.println("match found :"+p.title);
				return p;
			}
		}
		//System.out.println("match not found :"+p.title);
		return null;
	}
	
	private void useLinks(Point p,ArrayList<Point> collectedPoints){
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
	


	public void Start(String source,String destination) throws IOException{
		Point temp = null;
		Edge smallestEdge;
	
		Queue<Edge>openEdges = new PriorityQueue<Edge>(11,compareEdge);
		ArrayList<Edge> goodEdges = new ArrayList<Edge>();
		ArrayList<Edge> edges = new ArrayList<Edge>(edgeList);
		ArrayList<Point> unusedPoints = new ArrayList<Point>(myPoints);
		
		System.out.println("edges: "+edges.size());
		
		if(validate(unusedPoints,source,destination)){
		Point sourceP = unusedPoints.iterator().next();
		unusedPoints.remove(sourceP);
		
			while(!unusedPoints.isEmpty()){
				//get edges that contain temp as a source
				for(Edge e: edges){
					if(e.getSource() == sourceP){
							// if your souce is visited and tartget unvisited
							if(unusedPoints.contains(e.getTarget())){
								//add edge to the que
								openEdges.add(e);
							}
					}
				}//end for
				System.out.println("unusedPoints: "+unusedPoints.size());
				//get the smallest edge
				smallestEdge = openEdges.poll(); 
	
				//while its not in the list, its been visited, get a new one
				while(!unusedPoints.contains(smallestEdge.getTarget())){
					if(!openEdges.isEmpty()){
						smallestEdge = openEdges.poll();
					}
					else{
						sourceP = unusedPoints.iterator().next();
						unusedPoints.remove(sourceP);
					}
				}
				
				goodEdges.add(smallestEdge);
	
				temp = smallestEdge.getTarget(); 
				unusedPoints.remove(temp);
				System.out.println("goodEdges: "+goodEdges.size());
			}//end big while
		}
		else{
			System.out.println("bad titles");
		}
		
		findPath(goodEdges,source,destination);
	}

	private Boolean validate(ArrayList<Point> list,String source,String target){
		boolean b2 = false;
		boolean b1 = false;
		
		for(Point p:list){
			if(p.title.equals(source)){
				b1 = true;
			}
			if(p.title.equals(target)){
				b2 = true;
			}
		}
		return b1 & b2;
	}
	
	public void findPath(ArrayList<Edge> map,String source,String target){
		for(Edge e:map){
			if(e.source2.title.equals(source) && e.visited == false){
				e.visited = true;
				if(findPathRecursive(e,map,target)){
					System.out.println("path found!");
					break;
				}
			}
		}
	}
	
	public boolean findPathRecursive(Edge edge,ArrayList<Edge> map,String goal){
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