package controller;

import dbquery.AppointmentQuery;
import dbquery.ContactsQuery;
import dbquery.CustomersQuery;
import dbquery.UserQuery;
import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class Appointments implements Initializable {
    public TableView<Appointment> appointmentsTable;
    public TableColumn appointmentIDCol;
    public TableColumn titleCol;
    public TableColumn descriptionCol;
    public TableColumn locationCol;
    public TableColumn contactCol;
    public TableColumn typeCol;
    public TableColumn startCol;
    public TableColumn endCol;
    public TableColumn customerIDCol;
    public TableColumn userIDCol;
    public TextField appointmentIDTextField;
    public TextField titleTextField;
    public TextField descriptionTextField;
    public TextField locationTextField;
    public ComboBox<Contacts> contactComboBox;
    public TextField typeTextField;
    public DatePicker appointmentDatePicker;
    public ComboBox<Customers> customerIDComboBox;
    public ComboBox<Users> userIDComboBox;
    Appointment ap = null;

    public ObservableList<Integer> hours = FXCollections.observableArrayList();
    public ObservableList<Integer> minutes = FXCollections.observableArrayList();
    public ComboBox<Integer> startHour;
    public ComboBox<Integer> endHour;
    public ComboBox<Integer> startMinutes;
    public ComboBox<Integer> endMinutes;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentsTable.setItems(AppointmentQuery.getAllAppointments());
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact_Name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("user_ID"));

        contactComboBox.setItems(ContactsQuery.getContacts());
        contactComboBox.setPromptText("Select a Contact");
        customerIDComboBox.setItems(CustomersQuery.getAllCustomers());
        customerIDComboBox.setPromptText("Select a Customer");
        userIDComboBox.setItems(UserQuery.getUserList());
        userIDComboBox.setPromptText("Select a User");

        appointmentDatePicker.setValue(null);

        for (int i = 0; i <= 23; i++) {
            hours.add(i);
        }
        for (int i = 0; i < 60; i += 15) {
            minutes.add(i);
        }
        startHour.setItems(hours);
        startMinutes.setItems(minutes);
        endHour.setItems(hours);
        endMinutes.setItems(minutes);

    }


    public void sendAppointment(Appointment appointment) throws NullPointerException, SQLException {
        ap = appointment;
        appointmentIDTextField.setText(String.valueOf(appointment.getAppointment_ID()));
        titleTextField.setText(String.valueOf(appointment.getTitle()));
        descriptionTextField.setText(String.valueOf(appointment.getDescription()));
        locationTextField.setText(String.valueOf(appointment.getLocation()));

        typeTextField.setText(String.valueOf(appointment.getType()));

        //Business Hours in LocalDateTime to Check against appointment time
        LocalTime estOpen = LocalTime.of(8, 0);
        LocalTime estClose = LocalTime.of(22, 0);
        LocalDate estDate = appointment.getStartDate().toLocalDateTime().toLocalDate();
        LocalDateTime estLDTOpen = LocalDateTime.of(LocalDate.now(), estOpen);
        LocalDateTime estLDTClose = LocalDateTime.of(LocalDate.now(), estClose);
        ZoneId estZoneId = ZoneId.of("America/New_York");
        ZonedDateTime estZDTOpen = ZonedDateTime.of(estDate, estOpen, estZoneId);
        ZonedDateTime estZDTClose = ZonedDateTime.of(estDate, estClose, estZoneId);
        LocalDateTime estLDTOpenCheck = estZDTOpen.toLocalDateTime();
        LocalDateTime estLDTCloseCheck = estZDTClose.toLocalDateTime();
        System.out.println(estLDTOpenCheck);
        System.out.println(estLDTCloseCheck);

        //AppointmentQuery.checkForOverlapAppointment(appointment.getAppointment_ID(), appointment.getCustomer_ID(), appointment.getStartDate().toLocalDateTime());


        //Variables that set up Date picker and Time combo boxes
        Integer startM = appointment.getStartDate().toLocalDateTime().toLocalTime().getMinute();
        Integer startHr = appointment.getStartDate().toLocalDateTime().toLocalTime().getHour();
        Integer endM = appointment.getEndDate().toLocalDateTime().toLocalTime().getMinute();
        Integer endHr = appointment.getEndDate().toLocalDateTime().toLocalTime().getHour();

        LocalTime locStartTime = appointment.getStartDate().toLocalDateTime().toLocalTime();
        LocalDate locStartDate = appointment.getStartDate().toLocalDateTime().toLocalDate();
        LocalTime locEndTime = appointment.getEndDate().toLocalDateTime().toLocalTime();
        LocalDate locEndDate = appointment.getEndDate().toLocalDateTime().toLocalDate();
        LocalDateTime startTime = appointment.getStartDate().toLocalDateTime();
        LocalDateTime endTime = appointment.getEndDate().toLocalDateTime();

        ZoneId getLocalZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZoneId utcZoneId = ZoneId.of("Greenwich");

        ZonedDateTime startDateTimeToUTCZone = ZonedDateTime.of(locStartDate, locStartTime, utcZoneId);
        ZonedDateTime startTimeToUTCTime = startDateTimeToUTCZone.withZoneSameInstant(getLocalZoneId);
        ZonedDateTime endDateTimeToUTCZone = ZonedDateTime.of(locEndDate, locEndTime, utcZoneId);
        ZonedDateTime endTimeToUTCTime = endDateTimeToUTCZone.withZoneSameInstant(getLocalZoneId);

        LocalDateTime localDateTimeFromUTCStartTime = startTimeToUTCTime.toLocalDateTime();
        LocalDateTime localDateTimeFromUTCEndTime = endTimeToUTCTime.toLocalDateTime();

        LocalDate startLocDateFromUTCTime = localDateTimeFromUTCStartTime.toLocalDate();
        LocalTime startLocTimeFromUTCTime = localDateTimeFromUTCStartTime.toLocalTime();
        LocalDate endLocDateFromUTCTime = localDateTimeFromUTCEndTime.toLocalDate();
        LocalTime endLocTimeFromUTCTime = localDateTimeFromUTCEndTime.toLocalTime();
        Integer startZoneHr = startLocTimeFromUTCTime.getHour();
        Integer startZoneM = startLocTimeFromUTCTime.getMinute();
        Integer endZoneHr = endLocTimeFromUTCTime.getHour();
        Integer endZoneM = endLocTimeFromUTCTime.getMinute();

        //Sets the Date picker and Hour/Minute combo boxes from selection
        appointmentDatePicker.setValue(locEndDate);
        startHour.setValue(startZoneHr);
        startMinutes.setValue(startZoneM);
        endHour.setValue(endZoneHr);
        endMinutes.setValue(endZoneM);


        for (int i = 0; i < contactComboBox.getItems().size(); i++) {
            Contacts c = contactComboBox.getItems().get(i);
            if (c.getContact_Name().equals(appointment.getContact_Name())) {
                contactComboBox.setValue(c);
                break;
            }
        }
        //// customer/user function to change id to name to work below.
        for (int j = 0; j < customerIDComboBox.getItems().size(); j++) {
            Customers cu = customerIDComboBox.getItems().get(j);
            if (cu.getCid() == appointment.getCustomer_ID()) {
                customerIDComboBox.setValue(cu);
                break;
            }
        }

        for (int k = 0; k < userIDComboBox.getItems().size(); k++) {
            Users u = userIDComboBox.getItems().get(k);
            if (u.getUser_ID() == appointment.getUser_ID()) {
                userIDComboBox.setValue(u);
                break;
            }
        }


    }

    public void onUpdateAppointment(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/Appointments.fxml"));
        loader.load();

        Appointments UPController = loader.getController();
        UPController.sendAppointment(appointmentsTable.getSelectionModel().getSelectedItem());


        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();


    }

    public void onBackToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void onSaveAppointment(ActionEvent actionEvent) throws IOException, SQLException {
        String aid = appointmentIDTextField.getText();
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        Contacts contacts = contactComboBox.getValue();
        String type = typeTextField.getText();
        Customers customers = customerIDComboBox.getValue();
        Users users = userIDComboBox.getValue();

        //Grabs local ZoneId and establishes ZoneId for UTC and EST Time Zones
        ZoneId getLocalZoneId = ZoneId.of(TimeZone.getDefault().getID());
        ZoneId utcZoneId = ZoneId.of("Greenwich");
        ZoneId estZoneId = ZoneId.of("America/New_York");


        Integer startHr = startHour.getValue();
        Integer startMin = startMinutes.getValue();
        Integer endH = endHour.getValue();
        Integer endM = endMinutes.getValue();
        LocalDate localStartDate = appointmentDatePicker.getValue();

        LocalTime start = LocalTime.of(startHr, startMin);
        LocalTime end = LocalTime.of(endH, endM);
        ZonedDateTime zonedStartDateTime = ZonedDateTime.of(localStartDate, start, getLocalZoneId);
        ZonedDateTime zonedEndDateTime = ZonedDateTime.of(localStartDate, end, getLocalZoneId);
        ZonedDateTime ldtToUTC = zonedStartDateTime.withZoneSameInstant(utcZoneId);
        ZonedDateTime ldtToESTStart = zonedStartDateTime.withZoneSameInstant(estZoneId);
        ZonedDateTime ldtToESTEnd = zonedEndDateTime.withZoneSameInstant(estZoneId);
        LocalDateTime ldtStartUTC = ldtToUTC.toLocalDateTime();
        LocalDateTime ldtStartETC = ldtToESTStart.toLocalDateTime();
        LocalDateTime ldtEndETC = ldtToESTEnd.toLocalDateTime();

        LocalTime openTime = LocalTime.of(8,0);
        LocalTime closedTime = LocalTime.of(22,0);
        LocalDateTime ldtStart = LocalDateTime.of(appointmentDatePicker.getValue(), start);
        LocalDateTime ldtEnd = LocalDateTime.of(appointmentDatePicker.getValue(), end);
        LocalDateTime estOpen = LocalDateTime.of(appointmentDatePicker.getValue(), openTime);
        LocalDateTime estClosed = LocalDateTime.of(appointmentDatePicker.getValue(), closedTime);
        Timestamp startDate = Timestamp.valueOf(ldtStart);
        Timestamp endDate = Timestamp.valueOf(ldtEnd);

        LocalDate tpLocalDateStart = ldtStart.toLocalDate();
        LocalTime tpLocalTimeStart = ldtStart.toLocalTime();
        LocalDate tpLocalDateEnd = ldtEnd.toLocalDate();
        LocalTime tpLocalTimeEnd = ldtEnd.toLocalTime();
        ZonedDateTime appZoneDateTimeStart = ZonedDateTime.of(tpLocalDateStart, tpLocalTimeStart, getLocalZoneId);
        ZonedDateTime appZoneDateToUTCStart = appZoneDateTimeStart.withZoneSameInstant(utcZoneId);
        ZonedDateTime appZonedDateTimeEnd = ZonedDateTime.of(tpLocalDateEnd, tpLocalTimeEnd, getLocalZoneId);
        ZonedDateTime appZoneDateToUTCEnd = appZonedDateTimeEnd.withZoneSameInstant(utcZoneId);
        LocalDateTime zoneDateToUTCStart = appZoneDateToUTCStart.toLocalDateTime();
        LocalDateTime zoneDateToUTCEnd = appZoneDateToUTCEnd.toLocalDateTime();
        Timestamp startDateUTC = Timestamp.valueOf(zoneDateToUTCStart);
        Timestamp endDateUTC = Timestamp.valueOf(zoneDateToUTCEnd);

        if (ldtStartETC.isBefore(estOpen) || (ldtEndETC.isAfter(estClosed))) {
            Alert alertIsOpenEST = new Alert(Alert.AlertType.ERROR, "Please select appointment times between 8:00 a.m and 10:00 p.m. EST.");
            Optional<ButtonType> result = alertIsOpenEST.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                return;
            }
        }
        int id = 0;
        try {
            id = Integer.parseInt(aid);
        } catch (NumberFormatException | NullPointerException e) {
            //nothing to do here
        }

        for (Appointment appointment : AppointmentQuery.appointmentsOfCustomer(customers.getCid())) {
            if (appointment.getAppointment_ID() == id) {
                System.out.println("appid= " + appointment.getAppointment_ID() + " id = " + id);
                continue; //this is the appointment being updated
            }

            Timestamp savedStart = appointment.getStartDate();
            Timestamp savedEnd = appointment.getEndDate();
            System.out.println("saved Start Time= " + savedStart + "and this saved End Time = " + savedEnd);
            LocalDateTime savedStartLDT = savedStart.toLocalDateTime();
            LocalDateTime savedEndLDT = savedEnd.toLocalDateTime();
            System.out.println("saved Start LDT Time = " + savedStartLDT + "and this saved End LDT Time = " + savedEndLDT);
            if ((savedStartLDT.isAfter(ldtStart) || savedStartLDT.isEqual(ldtStart)) && (savedStartLDT.isBefore(ldtEnd))) {
            } else if ((savedEndLDT.isAfter(ldtStart)) && savedEndLDT.isBefore(ldtEnd) || savedEndLDT.isEqual(ldtEnd)) {
            } else if (savedStartLDT.isBefore(ldtStart) || savedStartLDT.isEqual(ldtStart) && (savedEndLDT.isAfter(ldtEnd) || savedEndLDT.isEqual(ldtEnd))) {
            } else if (!aid.equals(appointment.getAppointment_ID())) {
                Alert alertOnOverlap = new Alert(Alert.AlertType.ERROR, "Customer has an appointment already booked during this time please select an available time slot");
                Optional<ButtonType> result = alertOnOverlap.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    return;
                }

            }
            // }
            //   }
            //  }

            // }
            else if (aid.equals(appointment.getAppointment_ID())) {
                id = Integer.parseInt(aid);
            }
            AppointmentQuery.updateAppointment(id, title, description, location, contacts.getContact_ID(), type, startDateUTC, endDateUTC, customers.getCid(), users.getUser_ID());
            appointmentsTable.setItems(AppointmentQuery.getAllAppointments());

        }



            if (aid == null || aid.length() == 0) {
                AppointmentQuery.createAppointment(title, description, location, type, startDateUTC, endDateUTC, customers.getCid(), users.getUser_ID(), contacts.getContact_ID());
                appointmentsTable.setItems(AppointmentQuery.getAllAppointments());
            } else {
                // int id = Integer.parseInt(aid);
                AppointmentQuery.updateAppointment(id, title, description, location, contacts.getContact_ID(), type, startDateUTC, endDateUTC, customers.getCid(), users.getUser_ID());
                appointmentsTable.setItems(AppointmentQuery.getAllAppointments());
            }

            clearData();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Appointments.fxml"));
            loader.load();

            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }
   // }
    public void onDelete(ActionEvent actionEvent) throws IOException, SQLException {
        Alert alertOnProductRemove = new Alert(Alert.AlertType.CONFIRMATION, "Clicking OK will delete Appointment, do you wish to continue?");
        Optional<ButtonType> result = alertOnProductRemove.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (appointmentsTable.getSelectionModel().isEmpty()) {
                Alert noSelection1 = new Alert(Alert.AlertType.ERROR, "You must first select a Appointment to delete to continue.");

                Optional<ButtonType> result2 = noSelection1.showAndWait();
                if (result2.isPresent() && result2.get() == ButtonType.OK) {

                    return;
                }
            } else {
                Appointment selectedAppointment = appointmentsTable.getSelectionModel().getSelectedItem();
                if (selectedAppointment != null)
                    AppointmentQuery.deleteAppointment(selectedAppointment.getAppointment_ID());
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/Appointments.fxml"));
                loader.load();


                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }


    public void onClear(ActionEvent actionEvent) {
        appointmentIDTextField.clear();
        titleTextField.clear();
        descriptionTextField.clear();
        locationTextField.clear();
        contactComboBox.getSelectionModel().clearSelection();
        typeTextField.clear();
        appointmentDatePicker.setConverter(null);
        customerIDComboBox.getSelectionModel().clearSelection();
        userIDComboBox.getSelectionModel().clearSelection();
        startHour.getSelectionModel().clearSelection();
        endHour.getSelectionModel().clearSelection();
        startMinutes.getSelectionModel().clearSelection();
        endMinutes.getSelectionModel().clearSelection();

    }

    private void clearData() {
        appointmentIDTextField.clear();
        titleTextField.clear();
        descriptionTextField.clear();
        locationTextField.clear();
        contactComboBox.getSelectionModel().clearSelection();
        typeTextField.clear();
        appointmentDatePicker.setConverter(null);
        customerIDComboBox.getSelectionModel().clearSelection();
        userIDComboBox.getSelectionModel().clearSelection();
        startHour.getSelectionModel().clearSelection();
        endHour.getSelectionModel().clearSelection();
        startMinutes.getSelectionModel().clearSelection();
        endMinutes.getSelectionModel().clearSelection();

    }

    public void onAppointmentsWeekToggle(ActionEvent actionEvent) {
        appointmentsTable.setItems(AppointmentQuery.getAppointmentsCurrentWeek());
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact_Name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("user_ID"));

    }

    public void onAppointmentsMonthToggle(ActionEvent actionEvent) {
        appointmentsTable.setItems(AppointmentQuery.getAppointmentsCurrentMonth());
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact_Name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("user_ID"));

    }


    public void onAppointmentsAllToggle(ActionEvent actionEvent) {
        appointmentsTable.setItems(AppointmentQuery.getAllAppointments());
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact_Name"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customer_ID"));
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("user_ID"));

    }

}
