package dbquery;


import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public abstract class UserQuery {

    public static ObservableList<Users> getUserList (){
        ObservableList<Users> userList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM CLIENT_SCHEDULE.USERS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int user_ID = rs.getInt("User_ID");
                String user_Name = rs.getString("User_Name");
                String password = rs.getString("Password");

                Users u = new Users(user_ID, user_Name, password);
                userList.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }
    /*
    Users findUserID(String User_Name) {
        for(Users users : getUserList()) {
            if(users.getUser_Name().equals(User_Name)) {findUserID(User_Name);
                return ;
            }
        }
        return null;
    } */


    public static boolean checkUser(String userName, String password) {
        try{
            String sql = "SELECT * FROM USERS WHERE User_Name = ? AND Password = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1,userName);
            ps.setString(2,password);

            ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    return true;
                }
                else {
                    return false;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            }
        return false;
        }



}

