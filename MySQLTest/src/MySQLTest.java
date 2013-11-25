import java.io.*;
import java.sql.SQLException;


public class MySQLTest {

	public MySQLTest(){
	}
	 
    public void run(){
    	String host = "127.0.0.1";
    	String table = "test";
    	String username = "";
    	String password = "";
    	
    	String sqlStatement = "SELECT * FROM testtable;";

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
	        InputStream in = null;

		    while (rs.next()) {
		        in = rs.getBinaryStream(2);

		        byte[] buffer = new byte[8];
		        int n = 0;
		        while ((n = in.read(buffer)) != -1){
		            out.write(buffer, 0, n);
		            out.writeChars("\n");
		        }
		    }
		    
	        in.close();
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
