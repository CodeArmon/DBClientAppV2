package dbquery;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountriesQuery {

    public static ObservableList<Country> getCountryList(){

        ObservableList<Country> countryList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT COUNTRY_ID, COUNTRY FROM COUNTRIES ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int country_ID = rs.getInt("Country_ID");
                String country = rs.getString("Country");

                Country c = new Country(country_ID, country);
                countryList.add(c);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return countryList;
    }


}
