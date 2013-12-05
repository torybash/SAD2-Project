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
	String username = "admin";
	String password = "admin";
	//String sqlStatement = "Select a1.actor_id, a2.actor_id, m.year, m.id from roles a1 inner join roles a2 on a1.movie_id=a2.movie_id and a1.actor_id < a2.actor_id inner join movies m on a1.movie_id=m.id where not a1.actor_id=a2.actor_id and m.year<=2004 and m.year>=1998 Order by a1.actor_id;";

	
	String sqlStatement = "Select a1.actor_id, a2.actor_id from roles a1 " +
							"inner join roles a2 on a1.movie_id=a2.movie_id and a1.actor_id<a2.actor_id " +
							"inner join movies m on a1.movie_id=m.id " +
							"where m.year<=1910 and m.year>=1904 and not (a1.actor_id=a2.actor_id) "; 
	
	java.sql.PreparedStatement ps = null;
	java.sql.ResultSet rs = null;
	java.sql.Connection cn = null;
//815588 - 841203: 1

	@Override
	public void setStream(int from, int to) {
		// TODO Auto-generated method stub
		sqlStatement = "Select a1.actor_id, a2.actor_id, a2.movie_id from roles a1 " +
				"inner join roles a2 on a1.movie_id=a2.movie_id and a1.actor_id<a2.actor_id " +
				"inner join movies m on a1.movie_id=m.id " +
				"inner join actors a on a1.actor_id=a.id " +
				"inner join actors a3 on a2.actor_id=a3.id " +
				"where m.year>="+from+" and m.year<="+to+" and not (a1.actor_id=a2.actor_id) " +
				"Order by a1.actor_id, a2.actor_id";
		setStream();
	}

	public void setStream()
	{
		try {
			
				cn = MysqlConnectionProvider.getNewConnection(host, table, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		try{
		    java.sql.Statement stmt = cn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY, java.sql.ResultSet.CONCUR_READ_ONLY);
		    stmt.setFetchSize(Integer.MIN_VALUE);
		    
		    rs =stmt.executeQuery(sqlStatement); //ps.executeQuery();
		    rs.next(); //flytter til f¿rste result
		} catch (Exception s){
			System.out.println(s);
		} 
		    
	}
	
	
	public Pair getNextPair() {		
		try{
			if (rs.next()){
				int a1 = rs.getInt(1);
		        int a2 = rs.getInt(2);
		        
			    return new Pair(a1,a2);
			}
			CloseOff();
			return null;
	   
		} catch (Exception e){
			e.printStackTrace();
		}
		CloseOff();
		return null;
	}
	
	public void CloseOff(){
		try {
			cn.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

}
