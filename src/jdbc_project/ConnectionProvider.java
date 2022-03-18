// Server Connection // Encripted Data - Hidden
package jdbc_project;
import java.sql.*;

public class ConnectionProvider {
    
    private static Connection con;

    public static Connection getConnection() {
        try {
            if (con == null) {
                 // Initializing Drivers
                Class.forName("oracle.jdbc.driver.OracleDriver");
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
                
                // Connecting with database
                final String oracleUrl = "jdbc:oracle:thin:@localhost:1521:XE";
                final String user = "system";
                final String pass = "sys";
                con = DriverManager.getConnection(oracleUrl, user, pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}