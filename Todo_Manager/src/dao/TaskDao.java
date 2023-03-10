package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DBConnection.DbConnection;
import model.Task;
import model.User;

public class TaskDao {
	 Connection conn = DbConnection.getConnection();
	    public boolean addTask(Task task) throws Exception
	    {
	        String sql = "insert into task values(?,?,?,?,?)";
	        System.out.println(sql);
	        try{
	            PreparedStatement statement = conn.prepareStatement(sql);
	            statement.setInt(1,task.getTaskId());
	            statement.setString(2,task.getTaskTitle());
	            statement.setString(3,task.getTaskText());
	            statement.setString(4,task.getAssignedTo());
	            statement.setBoolean(5,task.isTaskCompleted());

	            statement.execute();
	        }
	        catch(SQLException e){
	            throw new Exception(e.getMessage());
	        }
	        return true;
	    }
	    public List<Task> getAllTasks() throws Exception
	    {
	        String sql = "select * from Task";
	        List<Task> task = new ArrayList<>();
	        try{
	            Statement statement = conn.createStatement();
	            ResultSet rs = statement.executeQuery(sql);
	            while(rs.next()){
	                Task t1 = new Task();
	                t1.setTaskId(rs.getInt(1));
	                t1.setTaskText(rs.getString(2));
	                t1.setTaskTitle(rs.getString(3));
	                t1.setAssignedTo(rs.getString(4));
	                t1.setTaskCompleted(rs.getBoolean(5));
	                task.add(t1);
	            }
	        }
	        catch(SQLException e){
	            throw new Exception(e.getMessage());
	        }
	        return task;
	    }
	    public boolean updateTask(Task task) throws Exception
	    {
	    	String sql = "UPDATE task SET title=?, description=?, assigned_to=?, completed=? WHERE taskId=?";
	        try {
	            PreparedStatement statement = conn.prepareStatement(sql);
	            statement.setString(1, task.getTaskTitle());
	            statement.setString(2, task.getTaskText());
	            statement.setString(3, task.getAssignedTo());
	            statement.setBoolean(4, task.isTaskCompleted());
	            statement.setInt(5, task.getTaskId());
	            statement.executeUpdate();
	            
	        } catch (SQLException e) {
	            System.out.println("ERROR");
	            throw new Exception(e.getMessage());
	        }
	        return true;
	    }
	    public boolean deleteTask(int taskId) throws Exception
	    {
	        String sql = "delete from Task where taskId=?";

	        try{
	            PreparedStatement statement = conn.prepareStatement(sql);
	            statement.setInt(1,taskId);
	            statement.execute();
	        }
	        catch(SQLException e){
	            throw new Exception(e.getMessage());
	        }
	        return true;
	    }
//	    public void searchTaskByemail(String email) throws Exception
//	    {
//	        String sql = "select * from user_task where user_email=?";
//	        Task task = null;
//	        try{
//	            PreparedStatement statement = conn.prepareStatement(sql);
//	            statement.setString(1,email);
//	            ResultSet rs = statement.executeQuery();
//
//	            if(rs.next()){
//	                task = new Task();
//	                task.setTaskId(rs.getInt(2));
//	                getTask(task.getTaskId());
//	            }
//	            else
//	                throw new Exception("No customer with "+email+" found");
//	        } catch (SQLException e) {
//	            throw new Exception(e.getMessage());
//	        }
//	        
//	       //return task;
//	    }
	    
	    public Task getTask(int taskId) throws Exception {
	    	String sql = "select * from task where taskId=?";
	    	  Task task = null;
		        try{
		            PreparedStatement statement = conn.prepareStatement(sql);
		            statement.setInt(1,taskId);
		            ResultSet rs = statement.executeQuery();

		            if(rs.next()){
		                task = new Task();
		                task.setTaskId(rs.getInt(1));
		                task.setTaskTitle(rs.getString(2));
		                task.setTaskText(rs.getString(3));
		                task.setAssignedTo(rs.getString(4));
		                task.setTaskCompleted(rs.getBoolean(5));
		            }
		            else
		                throw new Exception("No customer with "+taskId+" found");
		        } catch (SQLException e) {
		            throw new Exception(e.getMessage());
		        }
		        
		      return task;
	    	
	    }
	    public List<Task> searchTasks(String searchQuery, String email) throws Exception {
	        List<Task> searchResults = new ArrayList<>();
	        String sql = "SELECT * FROM task WHERE title LIKE ? and assigned_to = ?";
	        try {
	            PreparedStatement statement = conn.prepareStatement(sql);
	            statement.setString(1, "%" + searchQuery + "%");
	            statement.setString(2, email);
	            ResultSet rs = statement.executeQuery();
	            while (rs.next()) {
	                int taskId = rs.getInt("taskId");
	                String taskTitle = rs.getString("title");
	                String taskText = rs.getString("description");
	                boolean taskCompleted = rs.getBoolean("completed");
	                String assignedTo = rs.getString("assigned_to");
	                Task task = new Task(taskId, taskTitle, taskText, assignedTo, taskCompleted);
	                searchResults.add(task);
	            }
	        } catch (SQLException e) {
	            throw new Exception(e.getMessage());
	        }
	        return searchResults;
	    }
	    public List<Task> getTasksByStatus(boolean flag) {
	        List<Task> statusList = new ArrayList<>();
	        String sql = "SELECT * FROM task WHERE completed=?";
	        try {
	            PreparedStatement statement = conn.prepareStatement(sql);
	            statement.setBoolean(1, flag);
	            ResultSet rs = statement.executeQuery();
	            while(rs.next()){
	                Task t1 = new Task();
	                t1.setTaskId(rs.getInt(1));
	                t1.setTaskText(rs.getString(2));
	                t1.setTaskTitle(rs.getString(3));
	                t1.setAssignedTo(rs.getString(4));
	                t1.setTaskCompleted(rs.getBoolean(5));
	                statusList.add(t1);
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	        return statusList;
	    }
	    public List<Task> getIncompleteTasksByStatus(boolean flag) {
	    	List<Task> statusList = new ArrayList<>();
				String sql = "SELECT * FROM task WHERE completed=?";
				try{
		            PreparedStatement statement = conn.prepareStatement(sql);
		            statement.setBoolean(1, flag);
		            //statement.setString(3, email);
		            ResultSet rs = statement.executeQuery();
		            while(rs.next()){
		                Task t1 = new Task();
		                t1.setTaskId(rs.getInt(1));
		                t1.setTaskText(rs.getString(2));
		                t1.setTaskTitle(rs.getString(3));
		                t1.setAssignedTo(rs.getString(4));
		                t1.setTaskCompleted(rs.getBoolean(5));
		                statusList.add(t1);
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
			return statusList;
		}

}
