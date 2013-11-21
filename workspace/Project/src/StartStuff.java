import java.util.*;
import java.awt.*;


public class StartStuff {
	public static void main(String[] arg) {
		System.out.println("Hey!");
	}
	
	 
    public void init(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("JDBC driver loaded");
        } catch (Exception e) {
            System.out.println(e.toString());
        } //Eclipse may auto-force some extra catch blocks here
     }
 
}
