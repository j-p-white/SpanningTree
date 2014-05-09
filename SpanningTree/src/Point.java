import java.util.ArrayList;


public class Point {
	String title;
	String url;
	Point link;
	ArrayList<Point> myList;

	public Point(String title){
		this.title = title;
	}
	public Point(){
		
	}
	public void setupArray(String title,String url){
		myList.add(new Point(title,url));
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
}