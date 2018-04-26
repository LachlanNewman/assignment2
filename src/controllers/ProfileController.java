package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import views.Profile;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//TODO view profile friends
//TODO view profile relationships

public class ProfileController {


    public TextField firstNameField;
    public TextField lastNameField;
    public TextField ageField;
    public TextField statusField;
    public Text genderField;
    public Text stateField;
    Profile profile;

    public void setProfile(Profile profile) {
        this.profile = profile;
        firstNameField.setText(profile.getFirstName());
        lastNameField.setText(profile.getLastName());
        ageField.setText(profile.getAge() + "");
        statusField.setText(profile.getStatus());
        genderField.setText(profile.getGender().getGender());
        stateField.setText(profile.getState().getState());
    }

    public void updateProfile() {
        //TODO update state
        String firstName = firstNameField.getText();
        String lastName  = lastNameField.getText();
        String status    = statusField.getText();

        int age = Integer.parseInt(ageField.getText());
        int id = Integer.parseInt(profile.getId());

        Main.updateConnection(id,firstName,lastName,age,status);
    }

    public void deleteProfile(ActionEvent actionEvent) {
    }

    public void goBack(ActionEvent actionEvent) {
        try {
            Stage stage = Main.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/network.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
