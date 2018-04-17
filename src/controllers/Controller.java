package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import views.Network;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;

public class Controller implements Initializable {

    @FXML
    private AnchorPane mainScene;
    @FXML
    private TextField _firstName;
    public void searchNetwork(){
        System.out.println(_firstName.getText());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void navToNetwork(ActionEvent actionEvent) throws IOException {
        Stage stage = Main.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/network.fxml"));
        stage.setScene(new Scene(root, 300, 275));


    }
}
