package controllers;

import config.Navigation;
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

public class Controller implements Navigation.Nav{

    public void navToNetwork(ActionEvent actionEvent) throws IOException {
        Navigation.Nav.navigation.navToNetwork();
    }
}
