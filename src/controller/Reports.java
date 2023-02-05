package controller;

import dbquery.*;
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
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class Reports implements Initializable {


    public ComboBox reportsCombo;
    public TableColumn AppointmentIDColumn;
    public TableColumn TitleColumn;
    public TableColumn DescriptionColumn;
    public TableColumn TypeColumn;
    public TableColumn StartColumn;
    public TableColumn EndColumn;
    public TableColumn CustomerIDColumn;
    public Button backButton;
    public TableView<Appointment> customerScheduleTable;
    public Button customerScheduleButton;
    public ComboBox<Contacts> contactComboBox;
    public Button viewReportButton;
    public ComboBox typeComboBox;
    public TableView typeTableView;
    public TableColumn monthColumn;
    public TableColumn countColumn;
    public Button typeResultsButton;
    public TextField pricePerHourTextField;
    public TextField totalCostTextField;
    public Button calculateButton;
    public ComboBox<AppointmentID> appointmentComboBox;
    String comboResponse = null;
    int pricePerHour = 0;
    int id = 0;
    Timestamp totalTime = null;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        AppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("appointment_ID"));
        TitleColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        DescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        StartColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        EndColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        CustomerIDColumn.setCellValueFactory(new PropertyValueFactory<>("Customer_ID"));



        contactComboBox.setItems(ContactsQuery.getContacts());
        typeComboBox.setItems(TypeQuery.getType());
        appointmentComboBox.setItems(AppointmentQuery.getAAppointmentsIDs());


        monthColumn.setCellValueFactory(new PropertyValueFactory<>("monthName"));
        countColumn.setCellValueFactory(new PropertyValueFactory<>("Count"));




    }

    public void onToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }




    public void reportSubmit(ActionEvent event) {
        System.out.println("contact list" + AppointmentQuery.getAppointmentsofContact(1));
        if(contactComboBox.getSelectionModel().getSelectedItem()==null){
            return;
        }
        else if(contactComboBox.getSelectionModel().getSelectedItem().getContact_ID()== 1){
            customerScheduleTable.setItems(AppointmentQuery.getAppointmentsofContact(1));

        } else if (contactComboBox.getSelectionModel().getSelectedItem().getContact_ID() == 2) {
            customerScheduleTable.setItems(AppointmentQuery.getAppointmentsofContact(2));
        }
        else if(contactComboBox.getSelectionModel().getSelectedItem().getContact_ID()== 3){
            customerScheduleTable.setItems(AppointmentQuery.getAppointmentsofContact(3));

        }

        //customerScheduleTable.setItems(AppointmentQuery.getAppointmentsofContact())
    }

    public void populateTypeReport(ActionEvent event) {
        if (typeComboBox.getSelectionModel().getSelectedItem()== null){

        }
        else if(1<2){

        }
    }

    public void onTypeResults(ActionEvent event) {

        comboResponse = typeComboBox.getSelectionModel().getSelectedItem().toString();
        typeTableView.setItems(TypeQuery.getTypeReports(comboResponse));
        System.out.println(comboResponse);

    }

    public void onCalculate(ActionEvent event) throws NumberFormatException {
        for (AppointmentID appointmentID : AppointmentQuery.getAAppointmentsIDs()) {
            if (appointmentID.getAppointment_ID() == id) {
                System.out.println("appid= " + appointmentID.getAppointment_ID() + " id = " + id);
                continue; //this is the appointment being updated
            }

        pricePerHour = Integer.parseInt(pricePerHourTextField.getText());
        ObservableList<Appointment> ts = AppointmentQuery.getTime(id);
        System.out.println("Observable List" + ts.size());
        System.out.println("Price Per Hour " + pricePerHour);
        System.out.println("int " + ts.toString());

    }
}

    public void onSelectAppointmentID(ActionEvent event) {
        id = appointmentComboBox.getSelectionModel().getSelectedItem().getAppointment_ID();
        System.out.println("selected id" + id);
    }
    }
