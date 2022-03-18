// Full Data without Optimisation - Standalone
package jdbc_project;
import java.sql.*;
import java.util.*;

public class v2 {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        try {
            Scanner sc = new Scanner(System.in);
            
            // Initializing Drivers
            Class.forName("oracle.jdbc.driver.OracleDriver");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            
            // Connecting with database
            final String oracleUrl = "jdbc:oracle:thin:@localhost:1521:XE";
            final String user = "system";
            final String pass = "sys";

            // JDBC API classes
            Connection con = DriverManager.getConnection(oracleUrl, user, pass);
            Statement stmt = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            
            if(con.isClosed())
            {
                System.out.println("Connection failed");
            }
            else
            {
                System.out.println("Connection established at port 1521");
            }
            
            // Creation of Table
            System.out.println("Enter Table name: ");
            String t_name = sc.next();

            String create_table = "CREATE TABLE " + t_name + "( Enroll INT PRIMARY KEY, First_Name VARCHAR (20) NOT NULL, Last_Name VARCHAR (20) NOT NULL, Semester INT)";
            stmt = con.createStatement();
            stmt.execute(create_table);

            System.out.println("Table Created");
            
            // Insertion of data
            System.out.println("Enter Number of entities to add: ");
            int num = sc.nextInt();

            for (int i = 0; i < num; i++) {
                System.out.println("Enter Details for Student " + (i + 1) + ": ");
                System.out.println("Enter First Name: ");
                String fn = sc.next();
                System.out.println("Enter Last Name: ");
                String ln = sc.next();
                System.out.println("Enter Enrollment Number: ");
                int enroll = sc.nextInt();
                System.out.println("Enter Semester: ");
                int sem = sc.nextInt();
                
                String inp = "INSERT INTO " + t_name + " VALUES (? , ? , ? , ?)"; 
                pstmt = con.prepareStatement(inp);
                pstmt.setInt(1, enroll);
                pstmt.setString(2, fn);
                pstmt.setString(3, ln);
                pstmt.setInt(4, sem);    
                pstmt.executeUpdate(); 
            }

            System.out.println("Insertion of data completed, you can now proceed to SQLDeveloper Page.");

            while (true) {
                System.out.println("Enter 1 to add new data \n2 to delete data \n3 to modify data \n4 to display table(in terminal) \n5 to exit");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1: // Insert new data
                    {
                        System.out.println("Enter Details for new Student : ");   
                        System.out.println("Enter First Name: ");
                        String fn = sc.next();
                        System.out.println("Enter Last Name: ");
                        String ln = sc.next();
                        System.out.println("Enter Enrollment Number: ");
                        int enroll = sc.nextInt();
                        System.out.println("Enter Semester: ");
                        int sem = sc.nextInt();

                        String inp_new = "insert into " + t_name + " values (? , ? , ? , ?)"; 
                        pstmt = con.prepareStatement(inp_new);
                        pstmt.setInt(1, enroll);
                        pstmt.setString(2, fn);
                        pstmt.setString(3, ln);
                        pstmt.setInt(4, sem);    
                        pstmt.executeUpdate(); 
                        System.out.println("Insertion of data completed");
                    }
                    break;

                    case 2: // deletion of data
                    {
                        System.out.println("Enter Enroll Id of student to delete its data: ");
                        int del_id = sc.nextInt();

                        String del = "delete from " + t_name + " where Enroll = " + del_id + "";
                        pstmt = con.prepareStatement(del);
                        pstmt.execute();
                        System.out.println("Deletion of data completed");
                    }
                    break;

                    case 3: // modification of data
                    {
                        System.out.println("Enter Enroll Id of student to modify its data: ");
                        int mod_id = sc.nextInt();

                        System.out.println("Enter new First Name: ");
                        String fn = sc.next();
                        System.out.println("Enter new Last Name: ");
                        String ln = sc.next();
                        System.out.println("Enter new Semester: ");
                        int sem = sc.nextInt();
                         
                       String mod = "update " + t_name + " SET First_Name = '"+fn+"' , Last_Name = '"+ln+"' , Semester = "+sem+"  WHERE Enroll = "+mod_id+"";
                       stmt = con.createStatement();
                       stmt.executeUpdate(mod);

                        System.out.println("Modification of data completed");
                    }
                    break;
                    
                    case 4: // display data in terminal
                    {
                        String dis = "select * from " + t_name + "";
                        stmt = con.createStatement();
                        rs = stmt.executeQuery(dis);
                        System.out.println("Output:");
                        while (rs.next()) 
                        {
                            System.out.println( rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getInt(1) + " | " + rs.getInt(4));
                        }
                    }
                    break;
                    
                    case 5: // exit table (drop table - optional)
                    {
                        System.out.println("Do you want to delete table? (Y/N) : ");
                        String ans = sc.next();
                        if("Y".equals(ans))
                        {
                            String query = "Drop table " + t_name + "";
                            stmt = con.createStatement();
                            stmt.execute(query);
                            System.out.println("Table dropped sucessfully");
                        }
                        return;
                    }
                }
            }
        } 
        
        // Exceptions
        catch (SQLException e1) 
        {
            System.err.println("Got SQL exception! ");
            System.err.println(e1.getMessage());
        }
        catch (ClassNotFoundException e2)
        {
            System.err.println("Got Class exception! ");
            System.err.println(e2.getMessage());
        }
    }
}