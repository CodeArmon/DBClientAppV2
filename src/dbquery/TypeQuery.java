package dbquery;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Reports;
import model.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TypeQuery {
    public static ObservableList<Type> getType() {
        ObservableList<Type> typeList = FXCollections.observableArrayList();
        try {

            String sql = "SELECT Type FROM client_schedule.appointments";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String type = rs.getString("Type");
                Type r = new Type(type);
                typeList.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return typeList;

    }
    public static ObservableList<Reports> getTypeReports(String type){
        ObservableList<Reports> typeReports = FXCollections.observableArrayList();
        try{String sql = "select monthname(Start) AS Month, count(Type) as Count from client_schedule.appointments where Type = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String monthName = rs.getString("Month");
                int count = rs.getInt("Count");
                Reports r = new Reports(monthName, count);
                typeReports.add(r);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return typeReports;
    }

}

