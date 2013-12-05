import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.sql.SQLException;


public class SQLSimpleProvider implements Provider {
	String host = "127.0.0.1";
	String table = "test";
	String username = "";
	String password = "";
	//String sqlStatement = "Select a1.actor_id, a2.actor_id, m.year, m.id from roles a1 inner join roles a2 on a1.movie_id=a2.movie_id and a1.actor_id < a2.actor_id inner join movies m on a1.movie_id=m.id where not a1.actor_id=a2.actor_id and m.year<=2004 and m.year>=1998 Order by a1.actor_id;";

	int beginYear; 
	int endYear;
	int currentMovieID;
	
	String sqlStatement = "";
	java.sql.ResultSet moviesRS = null;
	java.sql.ResultSet actorPairsRS = null;
	java.sql.Connection cn = null;

	@Override
	public void setStream(int from, int to) {
		beginYear = from;
		endYear = to;
		
		if (moviesRS == null){
			sqlStatement = "Select id, year from movies;";
			executeMovieRetrieval();
		}else{
			sqlStatement = "Select actor_id from roles "
					+ "where actor_id="+currentMovieID+";";
					
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
			cn = MysqlConnectionProvider.getNewConnection(host, table, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		try{
		    java.sql.Statement stmt = cn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
		    stmt.setFetchSize(Integer.MIN_VALUE);
		    
		    actorPairsRS =stmt.executeQuery(sqlStatement); //ps.executeQuery();
		    actorPairsRS.next(); //flytter til f¿rste result
		} catch (Exception s){
			System.out.println(s);
		} 
		    
	}
	
	
	public Pair getNextPair() {		
		try{
			if (actorPairsRS != null && actorPairsRS.next()){
				int a1 = actorPairsRS.getInt(1);
		        int a2 = actorPairsRS.getInt(2);
		        
			    return new Pair(a1,a2);
			}else{
				if (moviesRS.next()){	
					int currentMovieYear = moviesRS.getInt(2);
					if (currentMovieYear >= beginYear && currentMovieYear <= endYear){
						currentMovieID = moviesRS.getInt(1);
						setStream(beginYear, endYear);
					}
					return getNextPair();
				}else{
					closeOff();
					return null;
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		closeOff();
		return null;
	}
	
	public void closeOff(){
		try {
			cn.close();
			moviesRS.close();
			actorPairsRS.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

}
