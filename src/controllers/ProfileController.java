package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import views.Network;
import views.Profile;

import java.io.IOException;
import java.sql.Connection;

public class ProfileController {


    public TextField firstName;
    public Text profileType;
    public TextField age;
    public TextField lastName;
    public TextField status;
    public Text gender;
    public Text state;
    public Button profileAction;
    Profile profile;

    public void setProfile(Profile profile) {
        this.profile = profile;
        firstName.setText(profile.getFirstName());
        lastName.setText(profile.getLastName());
        age.setText(profile.getAge() + "");
        status.setText(profile.getStatus());
        gender.setText(profile.getGender().getGender());
        state.setText(profile.getState().getState());
    }

    public void updateProfile() {
        this.profile.setFirstName(firstName.getText());
        this.profile.setLastName(lastName.getText());
        this.profile.setAge(Integer.parseInt(age.getText()));
        this.profile.setStatus(status.getText());
        String update = "UPDATE PROFILES "
        + "SET "
        + "firstName = '" + profile.getFirstName() + "'"
        + " WHERE id = " + profile.getId() + ";";

        Main.updateConnection(update);
    }

    public void deleteProfile(ActionEvent actionEvent) {
    }

    public void goBack(ActionEvent actionEvent) {
        Stage stage = Main.getPrimaryStage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/network.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root, 300, 275));
    }

    public void updateProfileButton(){
        profileAction = new Button("Update Profile");
        profileAction.setOnAction(event -> {
            updateProfile();
        });
    }

    public void addProfileButton() {
        profileAction = new Button("Add Profile");
        profileAction.setOnAction(event -> {
            addProfile();
        });
    }

    private void addProfile() {
    }

}
