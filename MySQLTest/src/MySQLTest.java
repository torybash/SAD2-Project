import java.io.*;
import java.sql.SQLException;


public class MySQLTest {

	public MySQLTest(){
	}
	 
    public void run(){
    	String host = "127.0.0.1";
    	String table = "test";
    	String username = "admin";
    	String password = "admin";
    	
    	String sqlStatement = "Select distinct a1.actor_id, a2.actor_id, m.name from roles a1 inner join roles a2 on a1.movie_id=a2.movie_id inner join movies m on a1.movie_id=m.id where not a1.actor_id=a2.actor_id and m.year<=1904 and m.year>=1904 Order by a1.actor_id;";

    	java.sql.PreparedStatement ps = null;
		java.sql.ResultSet rs = null;
		java.sql.Connection cn = null;
		try {
			cn = MysqlConnectionProvider.getNewConnection(host, table, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		try{
		    ps = cn.prepareStatement(sqlStatement);
		    rs = ps.executeQuery();
		    
	        File file = new File("out.txt");
	        file.createNewFile();
		    
	        DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
	        
	        InputStream a1 = null;
	        InputStream a2 = null;
	        InputStream y = null;

		    while (rs.next()) {
		        a1 = rs.getBinaryStream(1);
		        a2 = rs.getBinaryStream(2);
		        y = rs.getBinaryStream(3);

		        byte[] buffer = new byte[8];
		        byte[] buffer2 = new byte[2048];
		        int n = 0;
		        while ((n = a1.read(buffer)) != -1){
		        	
		        	
		            out.write(buffer, 0, n);
		            out.writeChars(" ");
		            n = a2.read(buffer);
		            out.write(buffer, 0, n);
		            out.writeChars(" ");
		            n = y.read(buffer2);
		            out.write(buffer2, 0, n);		            
		            out.writeChars("\n");
		        }
		    }
		    
	        a1.close();
	        a2.close();
	        y.close();	        
	        out.close();
		
		} catch (Exception s){
			System.out.println(s);
		} finally{
		    if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) {
		        }
		    }
		    if (ps != null) {
		        try {
		            ps.close();
		        } catch (SQLException e) {
		        }
		    }
		}
    	
    	
     }
	
}
