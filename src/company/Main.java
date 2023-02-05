package company;

import helper.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
   /* if(Locale.getDefault().getLanguage().equals("en"))
        stage.setTitle("Cliente Planification Système");
    else  */
    stage.setTitle("Customer Scheduling Log-in");
    stage.setScene(new Scene(root, 600, 400));
    stage.show();

    JDBC.openConnection();


   // JDBC.closeConnection();

}
    public static void main(String[] args) throws SQLException {
        launch(args);


    }


}