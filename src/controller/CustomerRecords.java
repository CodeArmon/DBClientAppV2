package controller;

import dbquery.CountriesQuery;
import dbquery.CustomersQuery;
import dbquery.DivisionsQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Country;
import model.Customers;
import model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerRecords implements Initializable {

    public TableView<Customers> customerRecordsTable;
    public TableColumn appointmentIDCol;
    public TableColumn customerNameCol;
    public TableColumn addressCol;
    public TableColumn countryCol;
    public TableColumn stateCol;
    public TableColumn postalCodeCol;
    public TableColumn phoneCol;
    public ComboBox<Divisions> stateCombo;
    public ComboBox<Country> countryCombo;
    public TextField customerIDTextField;
    public TextField customerNameTextField;
    public TextField addressTextField;
    public TextField postalCodeTextField;
    public TextField phoneTextField;
    Customers cu = null;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        customerRecordsTable.setItems(CustomersQuery.getAllCustomers());
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("cid"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customer_Name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        stateCol.setCellValueFactory(new PropertyValueFactory<>("division"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postal_Code"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        stateCombo.setItems(DivisionsQuery.getAllDivisionsList());
        countryCombo.setItems(CountriesQuery.getCountryList());

    }


    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    public void sendUpdate(Customers customers) {
        cu = customers;
        customerIDTextField.setText(String.valueOf(customers.getCid()));
        customerNameTextField.setText(customers.getCustomer_Name());
        addressTextField.setText(customers.getAddress());
        postalCodeTextField.setText(customers.getPostal_Code());
        phoneTextField.setText(customers.getPhone());

        for (int i = 0; i < countryCombo.getItems().size(); i++) {
            Country c = countryCombo.getItems().get(i);
            if (c.getCountry().equals(customers.getCountry())) {
                countryCombo.setValue(c);
                break;
            }
        }

        for (int i = 0; i < stateCombo.getItems().size(); i++) {
            Divisions d = stateCombo.getItems().get(i);
            if (d.getDivision().equals(customers.getDivision())) {
                stateCombo.setValue(d);
                break;

            }
        }
    }


    public void onUpdateCustomer(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/CustomerRecords.fxml"));
        loader.load();

        CustomerRecords UPController = loader.getController();
        UPController.sendUpdate(customerRecordsTable.getSelectionModel().getSelectedItem());

        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    public void onSaveCustomer(ActionEvent actionEvent) throws SQLException, IOException {
        String cid = customerIDTextField.getText();
        String cname = customerNameTextField.getText();
        String addy = addressTextField.getText();
        String postal = postalCodeTextField.getText();
        String phone = phoneTextField.getText();
        Country c = countryCombo.getValue();
        Divisions d = stateCombo.getValue();

        if (cid == null || cid.length() == 0) {
            CustomersQuery.insertCustomer(cname, addy, postal, phone, d.getDivision_ID());
            customerRecordsTable.setItems(CustomersQuery.getAllCustomers());
        } else {
            int id = Integer.parseInt(cid);
            CustomersQuery.updateCustomer(cname, addy, postal, phone, d.getDivision_ID(), id);
            customerRecordsTable.setItems(CustomersQuery.getAllCustomers());
        }
        clearData();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/CustomerRecords.fxml"));
        loader.load();

        Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    private void clearData() {
        stateCombo.getSelectionModel().clearSelection();
        countryCombo.getSelectionModel().clearSelection();
        customerIDTextField.clear();
        customerNameTextField.clear();
        addressTextField.clear();
        postalCodeTextField.clear();
        phoneTextField.clear();
    }

    public void onSelectCountry(ActionEvent actionEvent) {

        if (countryCombo.getSelectionModel().getSelectedItem() == null) {
            return;
        } else if (countryCombo.getSelectionModel().getSelectedItem().getCountry_ID() == 1) {
            stateCombo.setItems(DivisionsQuery.getUnitedStatesList());
        } else if (countryCombo.getSelectionModel().getSelectedItem().getCountry_ID() == 2) {
            stateCombo.setItems(DivisionsQuery.getUnitedKingdomList());
        } else if (countryCombo.getSelectionModel().getSelectedItem().getCountry_ID() == 3) {
            stateCombo.setItems(DivisionsQuery.getCanadaList());
        }
    }

    public void onClearButton(ActionEvent actionEvent) {

        stateCombo.getSelectionModel().clearSelection();
        countryCombo.getSelectionModel().clearSelection();
        customerIDTextField.clear();
        customerNameTextField.clear();
        addressTextField.clear();
        postalCodeTextField.clear();
        phoneTextField.clear();
        //customerRecordsTable.getSelectionModel().clearSelection();
        countryCombo.setPromptText("Select Country");
        stateCombo.setPromptText("Select State/Province");

    }

    public void onDeleteCustomer(ActionEvent actionEvent) throws SQLException, IOException {
        Alert alertOnProductRemove = new Alert(Alert.AlertType.CONFIRMATION, "Clicking OK will delete Customer Record, do you wish to continue?");
        Optional<ButtonType> result = alertOnProductRemove.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (customerRecordsTable.getSelectionModel().isEmpty()) {
                Alert noSelection1 = new Alert(Alert.AlertType.ERROR, "You must first select a Customer to delete to continue.");

                Optional<ButtonType> result2 = noSelection1.showAndWait();
                if (result2.isPresent() && result2.get() == ButtonType.OK) {
                    return;
                }
            } else {
                CustomersQuery.deleteCustomer(customerRecordsTable.getSelectionModel().getSelectedItem().getCid());
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/CustomerRecords.fxml"));
                loader.load();


                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent scene = loader.getRoot();
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }
}
