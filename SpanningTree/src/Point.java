import java.util.ArrayList;


public class Point {
	String title;
	String url;
	ArrayList<Point> myList = new ArrayList<Point>();

	public Point(String title){
		this.title = title;
	}
	
	public Point(){
		
	}
	
	public Point(String t,String u){
		title = t;
		url = u;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getUrl(){
		return url;
	}
	
	public boolean isLeaf(){
		if(myList.isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
}