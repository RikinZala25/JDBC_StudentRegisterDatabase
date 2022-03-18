// Optimised Data
package jdbc_project;
import java.sql.*;
import java.util.*;
public class JDBC_Project {
    
    static Scanner sc = new Scanner(System.in);
    
    // JDBC API classes
    static Connection c = ConnectionProvider.getConnection();
    static Statement stmt = null;
    static PreparedStatement pstmt = null;
    static ResultSet rs = null;
    
    // SQL Operations
    // 1. Data Insert Method
    public static void insertData(String t_name) 
    {
        try {
            System.out.println("Enter First Name: ");
            String fn = sc.next();
            System.out.println("Enter Last Name: ");
            String ln = sc.next();
            System.out.println("Enter Enrollment Number: ");
            int enroll = sc.nextInt();
            System.out.println("Enter Semester: ");
            int sem = sc.nextInt();

            String inp_new = "insert into " + t_name + " values (? , ? , ? , ?)";
            pstmt = c.prepareStatement(inp_new);
            pstmt.setInt(1, enroll);
            pstmt.setString(2, fn);
            pstmt.setString(3, ln);
            pstmt.setInt(4, sem);
            pstmt.executeUpdate();
            System.out.println("Insertion of data completed");
        } catch (SQLException e1) {
            System.err.println(e1.getMessage());
        }
    }
    
    // 2. Data Delete Method
    public static void deleteData(String t_name) 
    {
        try {
            System.out.println("Enter Enroll Id of student to delete its data: ");
            int del_id = sc.nextInt();

            String del = "delete from " + t_name + " where Enroll = " + del_id + "";
            pstmt = c.prepareStatement(del);
            pstmt.execute();
            System.out.println("Deletion of data completed");
        } catch (SQLException e2) {
            System.err.println(e2.getMessage());
        }
    }
    
    // 3. Data Modify Method
    public static void modifyData(String t_name)
    {
        try {
            System.out.println("Enter Enroll Id of student to modify its data: ");
            int mod_id = sc.nextInt();

            System.out.println("Enter new First Name: ");
            String fn = sc.next();
            System.out.println("Enter new Last Name: ");
            String ln = sc.next();
            System.out.println("Enter new Semester: ");
            int sem = sc.nextInt();
            
            String mod = "update " + t_name + " SET First_Name = '" + fn + "' , Last_Name = '" + ln + "' , Semester = " + sem + "  WHERE Enroll = " + mod_id + "";
            stmt = c.createStatement();
            stmt.executeUpdate(mod);

            System.out.println("Modification of data completed");
        } catch (SQLException e3) {
            System.err.println(e3.getMessage());
        }
    }
    
    // 4. Data Display Method
    public static void displayData(String t_name) 
    {
        try {
            String dis = "select * from " + t_name + "";
            stmt = c.createStatement();
            rs = stmt.executeQuery(dis);
            System.out.println("Output:");
            while (rs.next()) {
                System.out.println(rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getInt(1) + " | " + rs.getInt(4));
            }
        } catch (SQLException e4) {
            System.err.println(e4.getMessage());
        }
    }
    
    // 5. Table Drop Method 
    public static void dropTable(String t_name) 
    {
        try {
            System.out.println("Do you want to delete table? (Y/N) : ");
            String ans = sc.next();
            if ("Y".equals(ans)) {
                String query = "Drop table " + t_name + "";
                stmt = c.createStatement();
                stmt.execute(query);
                System.out.println("Table dropped sucessfully");
            }
        } catch (SQLException e5) {
            System.err.println(e5.getMessage());
        }
    }

    public static void main(String[] args) throws SQLException {
        try {
            if (c.isClosed()) {
                System.out.println("Connection failed");
            } else {
                System.out.println("Connection established at port 1521");
            }
            
            // Creation of Table
            System.out.println("Enter Table name: ");
            String t_name = sc.next();

            String create_table = "CREATE TABLE " + t_name + "( Enroll INT PRIMARY KEY, First_Name VARCHAR (20) NOT NULL, Last_Name VARCHAR (20) NOT NULL, Semester INT)";
            stmt = c.createStatement();
            stmt.execute(create_table);
            System.out.println("Table Created");

            // Insertion of Initial data
            System.out.println("Enter Number of entities to add: ");
            int num = sc.nextInt();

            for (int i = 0; i < num; i++) {
                System.out.println("Enter Details for Student " + (i + 1) + ": ");
                insertData(t_name);
            }

            while (true) {
                System.out.println("Enter 1 to add new data \n2 to delete data \n3 to modify data \n4 to display table(in terminal) \n5 to exit");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1: // Insertion of  data
                    {
                        System.out.println("Enter Details for new Student : ");
                        insertData(t_name);
                    }
                    break;

                    case 2: // deletion of data
                    {
                        deleteData(t_name);
                    }
                    break;

                    case 3: // modification of data
                    {
                        modifyData(t_name);
                    }
                    break;

                    case 4: // display data in terminal
                    {
                        displayData(t_name);
                    }
                    break;

                    case 5: // exit table (drop table - optional)
                    {
                        dropTable(t_name);
                        return;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Got SQL exception! ");
            System.err.println(e.getMessage());
        }
    }
}
