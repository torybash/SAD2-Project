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
	String sqlStatement = "Select distinct a1.actor_id, a2.actor_id, m.year, m.id from roles a1 inner join roles a2 on a1.movie_id=a2.movie_id and a1.actor_id < a2.actor_id inner join movies m on a1.movie_id=m.id where not a1.actor_id=a2.actor_id and m.year<=1985 and m.year>=1980 Order by a1.actor_id;";

	
	//String sqlStatement = "Select a1.actor_id, a2.actor_id, a2.movie_id from roles a1 inner join roles a2 on a1.movie_id=a2.movie_id inner join movies m on a1.movie_id=m.id inner join actors a on a1.actor_id=a.id inner join actors a3 on a2.actor_id=a3.id where (a1.actor_id =151786 or a2.actor_id=151786 ) and not (a1.actor_id=a2.actor_id)";
	
	java.sql.PreparedStatement ps = null;
	java.sql.ResultSet rs = null;
	java.sql.Connection cn = null;

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
	
	@Override
	public Pair getCurrentItemAsPair() {		
				
		try{		    		   	   	        	        
	        int a1 = rs.getInt(1);
	        int a2 = rs.getInt(2);
	        

//		    while (rs.next()) {
//		        a1 = rs.getInt(1);
//		        a2 = rs.getInt(2);
//		        
//		    }
		    return new Pair(a1,a2);
	        
		
		} catch (Exception s){
			System.out.println(s);
		}
		return null;
    	
    	

	}
	
	public boolean moveToNextRow(){ //moves to next pair in the sql, and returns false if there are no more pairs.
		
		try {
			return rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		//	return false;
			//e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isCurrentRowTheLast() {
		int testInt = 0;
		// TODO Auto-generated method stub
		try{
			if(false)
			{
			
				rs.close();
	            ps.close();
	            return true;
			}
			testInt++;
			return false;
		}
		catch(Exception e)
		{							
			e.printStackTrace();
		}
		
		return true;
	}

}
