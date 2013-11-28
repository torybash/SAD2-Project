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
	String sqlStatement = "Select distinct a1.actor_id, a2.actor_id, m.year, m.id from roles a1 inner join roles a2 on a1.movie_id=a2.movie_id inner join movies m on a1.movie_id=m.id where not a1.actor_id=a2.actor_id and m.year<=1904 and m.year>=1904 Order by a1.actor_id;";

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
		    ps = cn.prepareStatement(sqlStatement);
		    rs = ps.executeQuery();
		} catch (Exception s){
			System.out.println(s);
		} 
		    
	}
	
	@Override
	public Pair NextItem() {		
				
		try{		    		   	   	        	        
	        int a1 = 0;
	        int a2 = 0;
	        

		    while (rs.next()) {
		        a1 = rs.getInt(1);
		        a2 = rs.getInt(2);
		        
		    }
		    return new Pair(a1,a2);
	        
		
		} catch (Exception s){
			System.out.println(s);
		}
		return null;
    	
    	

	}

	@Override
	public boolean Next() {
		// TODO Auto-generated method stub
		try{
			if(rs.isLast() || rs.isAfterLast())
			{
				rs.close();
	            ps.close();
	            return false;
			}
			return true;
		}
		catch(Exception e)
		{							
			e.printStackTrace();
		}
		
		return false;
	}

}
