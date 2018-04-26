package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import views.Network;

import javafx.scene.control.TextField;
import views.Profile;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class NetworkController implements Initializable {

    public AnchorPane mainScene;
    private Network network = new Network(Main.getConnection());

    @FXML
    private VBox searchResults;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pushProfiles(network.getNetwork());
    }


    public void searchNetwork(ActionEvent actionEvent) {
        searchResults.getChildren().clear();

        String firstName = this.firstNameField.getText();
        String lastName  = this.lastNameField.getText();

        Map<String,Profile> profiles = this.network.searchNetwork(firstName,lastName);
        pushProfiles(profiles);
    }

    private void pushProfiles(Map<String, Profile> profiles) {
        for(Profile profile: profiles.values()){

            searchResults.getChildren().add(new Text("First Name: " + profile.getFirstName()));
            searchResults.getChildren().add(new Text("Last Name: "  + profile.getLastName() ));
            searchResults.getChildren().add(new Text("Age: "        + profile.getAge()      ));

            Button veiwProfile = new Button("View Profile");
            veiwProfile.setOnAction(event -> {
                try {
                    navToProfile(profile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            searchResults.getChildren().add(veiwProfile);
        }
    }

    private void navToProfile(Profile profile) throws IOException {

        Stage stage = Main.getPrimaryStage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edit_profile.fxml"));
        Parent root = (Parent)loader.load();
        ProfileController profileController = loader.<ProfileController>getController();
        profileController.setProfile(profile);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void navToAddNewProfile(ActionEvent actionEvent) throws IOException {
        Stage stage = Main.getPrimaryStage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/new_profile.fxml"));
        Parent root = (Parent)loader.load();
        NewProfileController newProfileController = loader.<NewProfileController>getController();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
