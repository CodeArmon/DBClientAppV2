package model;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Appointment {
    private int appointment_ID;
    private String title;
    private String description;
    private String location;
    private String contact_Name;
    private String type;
    private Timestamp startDate;
    private Timestamp endDate;
    private int timeDifference;
    private int customer_ID;
    private int user_ID;


    public Appointment(int appointment_ID, String title, String description, String location, String contact_Name, String type, Timestamp startDate, Timestamp endDate, int customer_ID, int user_ID) {
        this.appointment_ID = appointment_ID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact_Name = contact_Name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customer_ID = customer_ID;
        this.user_ID = user_ID;

    }
    public Appointment(int appointment_ID, Timestamp startDate, int user_ID){
        this.appointment_ID = appointment_ID;
        this.startDate = startDate;
        this.user_ID = user_ID;
    }


    public Appointment(int appointment_id, String title, String description, String type, Timestamp startDate, Timestamp endDate, int customer_id) {
        this.appointment_ID = appointment_id;
        this.title = title;
        this.description = description;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customer_ID = customer_id;
    }

    public Appointment(Timestamp startDate, Timestamp endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Appointment(int timeDifference) {
        this.timeDifference = timeDifference;
    }

    public int getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(int timeDifference) {
        this.timeDifference = timeDifference;
    }

    public int getAppointment_ID() {
        return appointment_ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getContact_Name() {
        return contact_Name;
    }

    public String getType() {
        return type;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public int getCustomer_ID() {
        return customer_ID;
    }

    public int getCustomer_IDFromName(Customers customers){
        return customer_ID;
    }

    public int getUser_ID() {
        return user_ID;
    }

    @Override
    public String toString() {
            return appointment_ID + title + description +location + contact_Name + type +  startDate +
                endDate + customer_ID + user_ID ;


    }
}