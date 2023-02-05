package model;

import com.mysql.cj.jdbc.result.UpdatableResultSet;
import helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Users {
    private int user_ID;
    private String user_Name;
    private String password;

    public Users(int user_ID, String user_Name, String password) {
        this.user_ID = user_ID;
        this.user_Name = user_Name;
        this.password = password;
    }

    public Users(String user_Name, String password) {
        this.user_Name = user_Name;
        this.password = password;
    }
    public int getUserID(String User_Name) throws SQLException {
        int userID = -1;
        try {
            //write SQL statement
            String sql = "SELECT User_ID FROM CLIENT_SCHEDULE.USERS WHERE client_schedule.users.User_Name = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, User_Name);
            //create result set object
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                userID = rs.getInt("User_ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userID;
    }

    public int getUser_ID() {
        return user_ID;
    }
    public int getUser_IDFromName(String user_Name){
        return user_ID;
    }

    public String getUser_Name() {
        return user_Name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return user_Name ;
    }
}
