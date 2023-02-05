package dbquery;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Divisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionsQuery {

    public static ObservableList<Divisions> getAllDivisionsList() {
    ObservableList<Divisions> DivisionsList = FXCollections.observableArrayList();
    try{
        String sql = "SELECT Division_ID, Division FROM client_schedule.first_level_divisions, client_schedule.countries WHERE first_level_divisions.Country_ID = countries.Country_ID";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int division_ID = rs.getInt("Division_ID");
            String division = rs.getString("Division");

            Divisions d = new Divisions(division_ID, division);
            DivisionsList.add(d);
        }


    } catch (SQLException e) {
        e.printStackTrace();
    }

        return DivisionsList;
    }

    public static ObservableList<Divisions> getUnitedStatesList() {
        ObservableList<Divisions> unitedStatesList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Division_ID, Division FROM client_schedule.first_level_divisions WHERE Country_ID = 1";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int division_ID = rs.getInt("Division_ID");
                String division = rs.getString("Division");

                Divisions d = new Divisions(division_ID, division);
                unitedStatesList.add(d);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return unitedStatesList;
    }
    public static ObservableList<Divisions> getUnitedKingdomList() {
        ObservableList<Divisions> unitedKingdomList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Division_ID, Division FROM client_schedule.first_level_divisions WHERE Country_ID = 2";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int division_ID = rs.getInt("Division_ID");
                String division = rs.getString("Division");

                Divisions d = new Divisions(division_ID, division);
                unitedKingdomList.add(d);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return unitedKingdomList;
    }
    public static ObservableList<Divisions> getCanadaList() {
        ObservableList<Divisions> canadaList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Division_ID, Division FROM client_schedule.first_level_divisions WHERE Country_ID = 3";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                int division_ID = rs.getInt("Division_ID");
                String division = rs.getString("Division");

                Divisions d = new Divisions(division_ID, division);
                canadaList.add(d);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return canadaList;
    }



    }






