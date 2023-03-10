package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	 public static Connection getConnection(){
	        Connection conn = null;
	        try{
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            System.out.println("Driver loaded");

	            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TaskDb","root","root123");
	            System.out.println("Connected");
	        }
	        catch(ClassNotFoundException e){
	            e.printStackTrace();
	        } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return conn;
	    }
}
