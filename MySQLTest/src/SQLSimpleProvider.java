import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class SQLSimpleProvider implements Provider {
	String host = "127.0.0.1";
	String table = "test";
	String username = "admin";
	String password = "admin";
	//String sqlStatement = "Select a1.actor_id, a2.actor_id, m.year, m.id from roles a1 inner join roles a2 on a1.movie_id=a2.movie_id and a1.actor_id < a2.actor_id inner join movies m on a1.movie_id=m.id where not a1.actor_id=a2.actor_id and m.year<=2004 and m.year>=1998 Order by a1.actor_id;";

	int beginYear; 
	int endYear;
	int currentMovieID;
	
	String sqlStatement = "";
	java.sql.ResultSet moviesRS = null;
	java.sql.ResultSet actorsRS = null;
	java.sql.Connection cn = null;
	
	LinkedList<Pair> currentMovieActorPairs = new LinkedList<Pair>();

	@Override
	public void setStream(int from, int to) {
		beginYear = from;
		endYear = to;
		
		if (moviesRS == null){
			sqlStatement = "Select id, year from movies;";
			executeMovieRetrieval();
		}else{
			sqlStatement = "Select actor_id, movie_id from roles "
					+ "where movie_id="+currentMovieID;
							
					
//					"Select a1.actor_id, a2.actor_id, a2.movie_id from roles a1 " +
//					"inner join roles a2 on a1.movie_id=a2.movie_id and a1.actor_id<a2.actor_id " +
//					"inner join movies m on a1.movie_id=m.id " +
//					"inner join actors a on a1.actor_id=a.id " +
//					"inner join actors a3 on a2.actor_id=a3.id " +
//					"where m.year>="+from+" and m.year<="+to+" and not (a1.actor_id=a2.actor_id) " +
//					"Order by a1.actor_id, a2.actor_id";
			executeActorRetrieval();
		}
	}
	
	@Override
	public void executeMovieRetrieval(){
		try {
			cn = MysqlConnectionProvider.getNewConnection(host, table, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		try{
		    java.sql.Statement stmt = cn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);		    
		    moviesRS = stmt.executeQuery("Select id, year from movies;"); //ps.executeQuery();
		} catch (Exception s){
			System.out.println(s);
		} 
		
	}
	
	
	public void executeActorRetrieval(){
		try {
			if(cn==null)
			{cn = MysqlConnectionProvider.getNewConnection(host, table, username, password);}
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		try{
		    java.sql.Statement stmt = cn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
		    //stmt.setFetchSize(Integer.MIN_VALUE);
		    actorsRS =stmt.executeQuery(sqlStatement); //ps.executeQuery();
		} catch (Exception s){
			System.out.println(s);
		} 
		    
	}
	
	
	public Pair getNextPair() {		
		try{
			if (!currentMovieActorPairs.isEmpty()){
				Pair nextPair = currentMovieActorPairs.getFirst();
				currentMovieActorPairs.removeFirst();

				return nextPair;
			}else{
				while (true){
					if (moviesRS.next()){	
						int currentMovieYear = moviesRS.getInt(2);
						if (currentMovieYear >= beginYear && currentMovieYear <= endYear){
							currentMovieID = moviesRS.getInt(1);
							setStream(beginYear, endYear); //creates actorRS
							prepareActorPairs();
							return getNextPair(); //try again now that we loaded a movies actors
						}
					}else{ //No movies left! Return null to indicate no more pairs
						return null;
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public void restartMovieOutput(){
		try {
			moviesRS.first();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private void prepareActorPairs(){		
		ArrayList<Integer> actorList = new ArrayList<Integer>();
		
		try {			
			while (actorsRS.next()){	
				actorList.add(actorsRS.getInt(1));
			}
			
			//all actors have been added
			for (int i = 0; i < actorList.size(); i++) {
				for (int j = i + 1; j < actorList.size(); j++) {
					if(actorList.get(i) != actorList.get(j)){
					Pair pair = new Pair(actorList.get(i), actorList.get(j));
					currentMovieActorPairs.add(pair);}
				}
			}
			actorsRS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public Provider copy()
	{
		return new SQLSimpleProvider();
	}
	
	public void closeOff(){
		try {
			cn.close();
			moviesRS.close();
			actorsRS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

}
