import java.util.ArrayList;
import java.util.List;


public class Point {
	String title;
	ArrayList<String> links = new ArrayList<String>();

	public Point(String title){
		this.title = title;
	}
	public Point(){
		
	}
	public Point(String t,ArrayList<String> l){
		title = t;
		links = l;
	}
	
	public String getTitle(){
		return title;
	}
	
	public List<String> getLinks(){
		return links;
	}
}