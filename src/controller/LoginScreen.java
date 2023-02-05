package controller;

import dbquery.AppointmentQuery;
import dbquery.UserQuery;
import helper.JDBC;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Appointment;
import model.Users;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class LoginScreen implements Initializable {
    public Label frenchLabel;
    public Button loginExit;
    public Button loginSubmit;
    public Label appNameLabel;
    public Label loginUserLabel;
    public Label loginPasswordLabel;
    public Label zoneID;
    public TextField loginUserTextBox;
    public TextField loginPasswordTextBox;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    /*    //put in a try catch block wrap Resource to end of if in try and for catch
        ResourceBundle rb = ResourceBundle.getBundle("company/Nat", Locale.getDefault());
        if(Locale.getDefault().getLanguage().equals("fr"))
            System.out.println(rb.getString("Customer") +" " + rb.getString("Scheduling"));
        else if(Locale.getDefault().getLanguage().equals("en"))
            frenchLabel.setText(rb.getString("bonjour"));
            loginExit.setText(rb.getString("Exit"));
            loginSubmit.setText(rb.getString("Submit"));
            appNameLabel.setText(rb.getString("Customer"));
            loginUserLabel.setText(rb.getString("Username"));
            loginPasswordLabel.setText(rb.getString("Password"));

            System.out.println(rb.getString("bonjour"));
     */
       // ZoneId.getAvailableZoneIds().stream().forEach(System.out::println);
        // ZoneId.getAvailableZoneIds().stream().filter(c -> c.contains("Greenwich")).forEach(System.out::println);
    /*    LocalDate parisDate = LocalDate.of(2022, 8, 31);
        LocalTime parisTime = LocalTime.of(05, 00);
        ZoneId parisZoneId = ZoneId.of("Europe/Paris");
        ZonedDateTime parisZDT = ZonedDateTime.of(parisDate,parisTime,parisZoneId);
        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());  //gets default localtimezone

        LocalDate utcDate = LocalDate.of(2022,8,31);
        LocalTime utcTime = LocalTime.of(5,29);
        ZoneId utcZoneId = ZoneId.of("Greenwich");

        Instant parisToGMTInstant = parisZDT.toInstant();
        ZonedDateTime parisToLocalZDT = parisZDT.withZoneSameInstant(localZoneId);
        ZonedDateTime gmtToLocalZDT = parisToGMTInstant.atZone(localZoneId);  */
/*
        System.out.println("Local: " + ZonedDateTime.now());
        System.out.println("Paris: " + parisZDT);
        System.out.println("Paris -> GMT: " + parisToGMTInstant);
        System.out.println("GMT -> Local: " + gmtToLocalZDT);
        System.out.println("GMT to LocalDate: " + gmtToLocalZDT.toLocalDate());
        System.out.println("GMT to LocalTime: " + gmtToLocalZDT.toLocalTime());

        String date = String.valueOf(gmtToLocalZDT.toLocalDate());
        String time = String.valueOf(gmtToLocalZDT.toLocalTime());
        String dateTime = date + " " + time;
        System.out.println(dateTime);
        LocalDateTime atlantaLDT = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String text = formatter.format(atlantaLDT);
        LocalDateTime parsedDate = LocalDateTime.parse(text, formatter);
        System.out.print(parsedDate);

*/

    /*    loginSubmit.setOnKeyPressed( event -> {
            if( event.getCode() == KeyCode.ENTER) {
                System.out.println("Enter clicked");
                loginSubmit.fire();



            }
        });  */

    }


    public void onLoginSubmit(ActionEvent actionEvent) throws IOException, SQLException {
        String logUser = loginUserTextBox.getText();
        String logPass = loginPasswordTextBox.getText();
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());


        /*
         field.setOnKeyPressed( event -> {
  if( event.getCode() == KeyCode.ENTER ) {
    doSomething();
  }
} );
        * */

       if(UserQuery.checkUser(logUser, logPass))
            {
                //create variable, name file to write entry logs into
                String filename = "login_activity.txt";
                //create File Writer object to write successful login attempt to output file
                FileWriter fileWriter = new FileWriter(filename, true);
                fileWriter.append(("User login attempt successful for " + logUser + " at " + timestamp));
                fileWriter.append(System.lineSeparator());
                fileWriter.flush();
                fileWriter.close();
                //create and Open File
                int tempID = 0;
                Users user = new Users(logUser, logPass);
                tempID = user.getUserID(logUser);

              //  for (int i = 0; i < AppointmentQuery.getAllAppointments().size(); i++) {
                    for (Appointment appointment : AppointmentQuery.fetchUpcomingAppointments(tempID)) {
                        Timestamp upcomingAppointment = appointment.getStartDate();
                        LocalDateTime upcomingApp = upcomingAppointment.toLocalDateTime();
                        Integer appID = appointment.getAppointment_ID();
                        LocalDateTime now = LocalDateTime.now();
                        LocalDateTime then = now.plusSeconds(900);
                        if((upcomingApp.isAfter((now)) && (upcomingApp.isBefore((then))))) {
                           // System.out.println("User has an upcoming Appointment ID" + appID +"Scheduled at " + upcomingAppointment);
                            Alert upcomingAppoint = new Alert(Alert.AlertType.INFORMATION, "User has an upcoming Appointment # " + appID +" ,Scheduled at " + upcomingAppointment);
                            Optional<ButtonType> result = upcomingAppoint.showAndWait();
                        }
                        else{
                           Alert upcomingAppoint1 = new Alert(Alert.AlertType.INFORMATION, "User has no upcoming Appointments Scheduled");
                           Optional<ButtonType> result = upcomingAppoint1.showAndWait();
                           break;
                        }
                    }
               // }
                /*
                   for (int i = 0; i < AppointmentQuery.fetchUpcomingAppointments().size(); i++) {
            for (Appointment appointment : AppointmentQuery.fetchUpcomingAppointments()) {
                Timestamp appStart = appointment.getStartDate();
                Timestamp appEnd = appointment.getEndDate();
                LocalDateTime appStartLDT = appStart.toLocalDateTime();
                LocalDateTime appEndLDT = appEnd.toLocalDateTime();


            }
        }
                 */


                Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 600, 400);
                stage.setTitle("Main Menu");
                stage.setScene(scene);
                stage.show();
            }
       else {
           //create variable, name file to write entry logs into
           String filename = "login_activity.txt";
           //create File Writer object to write successful login attempt to output file
           FileWriter fileWriter = new FileWriter(filename, true);
           fileWriter.append(("User login attempt failed for " + logUser + " at " + timestamp));
           fileWriter.append(System.lineSeparator());
           fileWriter.flush();
           fileWriter.close();
           //create and Open File
            ResourceBundle rb = ResourceBundle.getBundle("company/Nat", Locale.getDefault());
            if (Locale.getDefault().getLanguage().equals("fr"))
                {
                Alert noSelectionFr = new Alert(Alert.AlertType.ERROR, "Nom d'utilisateur/Le mot de passe " +
                        "échec de la connexion");
                Optional<ButtonType> result = noSelectionFr.showAndWait();
            }
            else{
                Alert noSelection = new Alert(Alert.AlertType.ERROR, "Username/Password Login Fail");
                Optional<ButtonType> result = noSelection.showAndWait();
            }

        }

    }
}
