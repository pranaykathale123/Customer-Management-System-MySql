package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import DBConnection.DbConnection;
import model.User;

public class UserDao {
	Connection conn = DbConnection.getConnection();
    public UserDao() {
    	
    }
    public boolean login(String email, String password) throws Exception
    {
        String sql = "select password from user where email=?";
        PreparedStatement statement;

        try{
            statement = conn.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                if(password.equals(rs.getString(1)))
                    return true;
            }
            throw new Exception("Invalid Credentials");
        }
        catch(SQLException e){
            throw new Exception(e.getMessage());
        }
    }
    public boolean addUser(User user) throws Exception
    {
        String sql = "insert into user values(?,?,?)";
        System.out.println(sql);
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,user.getUsername());
            statement.setString(2,user.getEmail());
            statement.setString(3,user.getPassword());
            statement.execute();
        }
        catch(SQLException e){
            throw new Exception(e.getMessage());
        }
        return true;
    }
    public List<User> getAllUsers() throws Exception
    {
        String sql = "select * from user";
        List<User> user = new ArrayList<>();
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                User u1 = new User();
                u1.setUsername(rs.getString(1));
                u1.setEmail(rs.getString(2));
                u1.setPassword(rs.getString(3));
                user.add(u1);
            }
        }
        catch(SQLException e){
            throw new Exception(e.getMessage());
        }
        return user;
    }
    public boolean updateUser(User user) throws Exception
    {
        String sql = "UPDATE user SET username=?, password=? WHERE email=?";

        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getUsername());
            statement.setString(2,user.getPassword());
            statement.execute();
        }
        catch(SQLException e){
            System.out.println("ERROR");
            throw new Exception(e.getMessage());
        }
        return true;
    }
    public boolean deleteUser(String email) throws Exception
    {
        String sql = "delete from user where email=?";

        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,email);
            statement.execute();
        }
        catch(SQLException e){
            throw new Exception(e.getMessage());
        }
        return true;
    }
    public User getUserById(String email) throws Exception
    {
        String sql = "select * from user where email=?";
        User user = null;
        try{
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,email);
            ResultSet rs = statement.executeQuery();

            if(rs.next()){
                user = new User();
                user.setEmail(rs.getString(1));
                user.setUsername(rs.getString(2));
            }
            else
                throw new Exception("No customer with "+email+" found");
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
        return user;
    }
}
