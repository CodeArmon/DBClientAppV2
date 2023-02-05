package dbquery;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;

public abstract class AppointmentQuery {
    Appointment ap = null;

    public static ObservableList<Appointment> getAllAppointments()  {
        ObservableList<Appointment> aList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, customers.Customer_ID, users.User_ID FROM Client_schedule.appointments, Client_schedule.users, Client_schedule.customers, Client_schedule.contacts WHERE appointments.customer_id=customers.customer_id AND appointments.user_id=users.user_id AND appointments.contact_id=contacts.contact_id";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointment_ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact_Name = rs.getString("Contact_name");
                String type = rs.getString("Type");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                int customer_ID = rs.getInt("Customer_ID");
                int user_ID = rs.getInt("User_ID");
                Appointment a = new Appointment(appointment_ID, title, description, location, contact_Name, type, startDate, endDate, customer_ID, user_ID);
                aList.add(a);

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return aList;
    }
    public static ObservableList<AppointmentID> getAAppointmentsIDs()  {
        ObservableList<AppointmentID> appointmentIDList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Appointment_ID FROM client_schedule.appointments;";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointment_ID = rs.getInt("Appointment_ID");
                AppointmentID a = new AppointmentID(appointment_ID);
                appointmentIDList.add(a);

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return appointmentIDList;
    }
    public static ObservableList<Appointment> fetchUpcomingAppointments(int User_ID) throws SQLException {
        ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Appointment_ID, Start FROM Client_schedule.appointments, Client_schedule.users WHERE client_schedule.users.User_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, User_ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointment_ID = rs.getInt("Appointment_ID");
                Timestamp startDate = rs.getTimestamp("Start");
               //int user_id = rs.getInt("User_ID");
                Appointment ap = new Appointment(appointment_ID, startDate, User_ID);
                upcomingAppointments.add(ap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return upcomingAppointments;
    }


    public static ObservableList<Appointment> appointmentsOfCustomer(int customer_ID) throws SQLException {
        //function finds all appointments of customer id and searches if time is already booked.
        ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, customers.Customer_ID, users.User_ID FROM Client_schedule.appointments, Client_schedule.customers, Client_schedule.contacts, Client_schedule.users WHERE appointments.customer_id=customers.customer_id and customers.customer_id = ? ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, customer_ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointment_ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact_Name = rs.getString("Contact_name");
                String type = rs.getString("Type");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                customer_ID = rs.getInt("Customer_ID");
                int user_ID = rs.getInt("User_ID");
                Appointment a = new Appointment(appointment_ID, title, description, location, contact_Name, type, startDate, endDate, customer_ID, user_ID);
                customerAppointments.add(a);

            }
        }
        catch(SQLException e){
                e.printStackTrace();

            }

        return customerAppointments;
    }
/*
        LocalDate parisDate = LocalDate.of(2022, 8, 31);
        LocalTime parisTime = LocalTime.of(05, 00);
        ZoneId parisZoneId = ZoneId.of("Europe/Paris");
        ZonedDateTime parisZDT = ZonedDateTime.of(parisDate,parisTime,parisZoneId);
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());  //gets default localtimezone

        LocalDate utcDate = LocalDate.of(2022,8,31);
        LocalTime utcTime = LocalTime.of(5,29);
        ZoneId utcZoneId = ZoneId.of("Greenwich");

        Instant parisToGMTInstant = parisZDT.toInstant();
        ZonedDateTime parisToLocalZDT = parisZDT.withZoneSameInstant(localZoneId);
        ZonedDateTime gmtToLocalZDT = parisToGMTInstant.atZone(localZoneId); */



    public static ObservableList<Appointment> getAppointmentsCurrentMonth()  {
        ObservableList<Appointment> aList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, customers.Customer_ID, users.User_ID FROM Client_schedule.appointments, Client_schedule.users, Client_schedule.customers, Client_schedule.contacts WHERE appointments.customer_id=customers.customer_id AND appointments.user_id=users.user_id AND appointments.contact_id=contacts.contact_id AND month(Start)=month(now()) AND year(Start)=year(now())";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointment_ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact_Name = rs.getString("Contact_name");
                String type = rs.getString("Type");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                int customer_ID = rs.getInt("Customer_ID");
                int user_ID = rs.getInt("User_ID");
                Appointment a = new Appointment(appointment_ID, title, description, location, contact_Name, type, startDate, endDate, customer_ID, user_ID);
                aList.add(a);

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return aList;
    }
    public static ObservableList<Appointment> getAppointmentsCurrentWeek()  {
        ObservableList<Appointment> aList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Location, Contact_Name, Type, Start, End, customers.Customer_ID, users.User_ID FROM Client_schedule.appointments, Client_schedule.users, Client_schedule.customers, Client_schedule.contacts WHERE appointments.customer_id=customers.customer_id AND appointments.user_id=users.user_id AND appointments.contact_id=contacts.contact_id AND week(Start)=week(now()) AND month(Start)=month(now()) AND year(Start)=year(now())";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointment_ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String contact_Name = rs.getString("Contact_name");
                String type = rs.getString("Type");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                int customer_ID = rs.getInt("Customer_ID");
                int user_ID = rs.getInt("User_ID");
                Appointment a = new Appointment(appointment_ID, title, description, location, contact_Name, type, startDate, endDate, customer_ID, user_ID);
                aList.add(a);

            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return aList;
    }
    public static void createAppointment(String title, String Description, String Location, String Type,
                                          Timestamp startDate, Timestamp endDate, int customer_ID, int user_ID, int contact_ID)  {
        try {
            String sql = "INSERT INTO APPOINTMENTS VALUES (NULL, ?, ?, ?, ?, ?, ?, NULL, NULL, NULL, NULL, ?, ?, ?)";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                //sets ? parameters to table columns in database
                ps.setString(1, title);
                ps.setString(2,Description);
                ps.setString(3, Location);
                ps.setString(4, Type);
                ps.setTimestamp(5, startDate);
                ps.setTimestamp(6, endDate);
                ps.setInt(7, customer_ID);
                ps.setInt(8, user_ID);
                ps.setInt(9, contact_ID);
                ps.executeUpdate();

            } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }



    public static void updateAppointment(int aid, String Title, String Description, String Location, int Contact,
                                         String Type, Timestamp startDate, Timestamp endDate, int cid, int uid){
        try{
            String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);

            ps.setString(1, Title);
            ps.setString(2, Description);
            ps.setString(3, Location);
            ps.setString(4, Type);
            ps.setTimestamp(5, startDate);
            ps.setTimestamp(6, endDate);
            ps.setInt(7, cid);
            ps.setInt(8, uid);
            ps.setInt(9, Contact);
            ps.setInt(10, aid);
            ps.executeUpdate();


            } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static void deleteAppointment(int aid) throws SQLException{
        try{
            String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, aid);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public static ObservableList<Appointment> getAppointmentsofContact(int contact_ID){
        ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Type, Start, End, customers.Customer_ID From client_schedule.appointments, Client_schedule.customers where contact_id = ? ";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contact_ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointment_ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String type = rs.getString("Type");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                int customer_ID = rs.getInt("Customer_ID");
                Appointment c = new Appointment(appointment_ID, title, description, type, startDate, endDate, customer_ID);
                contactAppointments.add(c);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return contactAppointments;
    }


    public static ObservableList<Appointment> customerAppointmentsReport()  {
        //function finds all customer appointments and returns in a list to display in tableview.
        ObservableList<Appointment> customerReportAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT Appointment_ID, Title, Description, Type, Start, End, customers.Customer_ID FROM Client_schedule.appointments, Client_schedule.customers WHERE appointments.customer_id=customers.customer_id" ;
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointment_ID = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String type = rs.getString("Type");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                int customer_ID = rs.getInt("Customer_ID");
                Appointment c = new Appointment(appointment_ID, title, description, type, startDate, endDate, customer_ID);
                customerReportAppointments.add(c);
            }
        }
        catch(SQLException e){

            e.printStackTrace();

        }

        return customerReportAppointments;
    }
    public static ObservableList<Appointment> getTime(int appointment_ID) {
        ObservableList<Appointment> timeList = FXCollections.observableArrayList();
        try {

            String sql = "SELECT TIMESTAMPDIFF(SECOND, Start, End) AS difference FROM client_schedule.appointments WHERE appointment_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, appointment_ID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int timeDifference = rs.getInt("difference");
                Appointment a = new Appointment(timeDifference);
                timeList.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return timeList;

    }


    public static void deleteCustomerWithAppointment(int cid){
        try{
            String sql = "DELETE * FROM client.schedules.appointments WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
