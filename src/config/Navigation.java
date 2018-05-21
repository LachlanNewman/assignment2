package config;

import controllers.RelationShipController;
import controllers.NewProfileController;
import controllers.ProfileController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.Profile;

import java.io.IOException;
import java.util.ArrayList;

public class Navigation {

    private final static int WIDTH = 900;
    private final static int HEIGHT = 800;

    private static Stage _primaryStage; // **Declare static Stage**


    public static void setStage(Stage primaryStage){
        _primaryStage = primaryStage;
    }

    public void navToMain() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        _primaryStage.setTitle("Hello World");
        showStage(root);
    }

    private void showStage(Parent root) {
        _primaryStage.setScene(new Scene(root,WIDTH,HEIGHT));
        _primaryStage.show();
    }

    public void navToNetwork() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/network.fxml"));
        showStage(root);
    }

    public void navToProfile(Profile profile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edit_profile.fxml"));
        Parent root = (Parent)loader.load();
        ProfileController profileController = loader.<ProfileController>getController();
        profileController.setProfile(profile);
        showStage(root);
    }

    public void navToAddNewProfile() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/new_profile.fxml"));
        Parent root = (Parent)loader.load();
        NewProfileController newProfileController = loader.<NewProfileController>getController();
        showStage(root);
    }

    public void navToRelationship(Profile profile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/relationship.fxml"));
        Parent root = (Parent)loader.load();
        RelationShipController relationShipController = loader.<RelationShipController>getController();
        relationShipController.initController(profile);
        showStage(root);

    }

    public interface Nav{
        Navigation navigation = new Navigation();
    }
}
