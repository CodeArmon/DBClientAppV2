package dbquery;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class CustomersQuery {


    public static void insertCustomer(String Customer_Name, String Address, String Postal_Code, String Phone, int Division_ID) throws SQLException {
       try {
           String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?)";
           PreparedStatement ps = JDBC.connection.prepareStatement(sql);
           ps.setString(1, Customer_Name);
           ps.setString(2, Address);
           ps.setString(3, Postal_Code);
           ps.setString(4, Phone);
           ps.setInt(5, Division_ID);
           ps.executeUpdate();
       }
       catch (SQLException e){
       e.printStackTrace();
       }
    }




   public static void updateCustomer(String Customer_Name, String Address, String Postal_Code, String Phone, int Division_ID, int Customer_ID ) throws SQLException {
       try {
           String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
           PreparedStatement ps = JDBC.connection.prepareStatement(sql);
           ps.setString(1, Customer_Name);
           ps.setString(2, Address);
           ps.setString(3, Postal_Code);
           ps.setString(4, Phone);
           ps.setInt(5, Division_ID);
           ps.setInt(6, Customer_ID);
           ps.executeUpdate();
       } catch (SQLException e) {
           e.printStackTrace();
       }

   }
    public static void deleteCustomer(int Customer_ID) throws SQLException {
        try {
            String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, Customer_ID);
            ps.executeUpdate();

        }
        catch(SQLException e){
        e.printStackTrace();
        }

    }


    public static ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> cList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Customer_ID, Customer_Name, Address, Postal_code, Phone, Division, Country FROM client_schedule.customers, client_schedule.first_level_divisions, client_schedule.countries WHERE customers.Division_ID = first_level_divisions.Division_ID AND first_level_divisions.Country_ID = countries.Country_ID";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int Customer_ID = rs.getInt("Customer_ID");
                String Customer_Name = rs.getString("Customer_Name");
                String Address = rs.getString("Address");
                String Postal_Code = rs.getString("Postal_Code");
                String Phone = rs.getString("Phone");
                String Division_ID = rs.getString("Division");
                String Country = rs.getString("Country");
                Customers c = new Customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone, Division_ID, Country);
                cList.add(c);
            }


        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return cList;
    }

        public static void selectAllCustomers() throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int Customer_ID = rs.getInt("Customer_ID");
            String Customer_Name = rs.getString("Customer_Name");
            String Address = rs.getString("Address");
            String Postal_Code = rs.getString("Postal_Code");
            String Phone = rs.getString("Phone");
            int Division_ID = rs.getInt("Division_ID");

        }

            }
    public static void selectACustomer(int Customer_ID) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, Customer_ID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int Customer_IDpk = rs.getInt("Customer_ID");
            String Customer_Name = rs.getString("Customer_Name");
            String Address = rs.getString("Address");
            String Postal_Code = rs.getString("Postal_Code");
            String Phone = rs.getString("Phone");
            int Division_ID = rs.getInt("Division_ID");


}
    }
}