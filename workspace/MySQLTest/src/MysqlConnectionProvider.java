import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class MysqlConnectionProvider {
    private static MysqlConnectionProvider INST;
 
    private MysqlConnectionProvider() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }
 
    public static Connection getNewConnection(String host, String database, String user, String password)
            throws SQLException, ClassNotFoundException {
        if (INST == null) {
            INST = new MysqlConnectionProvider();
        }
        return INST.createConnection(host, database, user, password);
    }
 
    public Connection createConnection(String host, String database, String user, String password) throws SQLException {
        String url = new String("jdbc:mysql://" + host + "/" + database);
        return DriverManager.getConnection(url, user, password);
    }
 
}