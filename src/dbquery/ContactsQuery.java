package dbquery;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contacts;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactsQuery {

    public static ObservableList<Contacts> getContacts() {
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
        try {

            String sql = "SELECT * FROM client_schedule.contacts";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contact_id = rs.getInt("Contact_ID");
                String contact_Name = rs.getString("Contact_Name");
                String email = rs.getString("Email");

                Contacts c = new Contacts(contact_id, contact_Name, email);
                contactsList.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contactsList;

}
}
