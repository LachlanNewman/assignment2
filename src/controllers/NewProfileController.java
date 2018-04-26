package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class NewProfileController {


    public TextField firstNameField;
    public TextField lastNameField;
    public TextField ageField;
    public String gender = "UNKNOWN";
    public ComboBox stateField;
    public Button newProfileButton;
    public CheckBox femaleCheckbox;
    public CheckBox maleCheckBox;
    public TextField statusField;


    public void addNewProfile(ActionEvent actionEvent) {

        if( ageField.getText().matches("\\d") &&
            !firstNameField.getText().isEmpty()     &&
            !lastNameField.getText().isEmpty()      &&
            stateField.getValue() != null           )
        {

            String state     = stateField.getValue().toString();
            String firstName = firstNameField.getText();
            String lastName  = lastNameField.getText();
            String status    = statusField.getText();

            int age = Integer.parseInt(ageField.getText());

            Main.insertConnection(firstName, lastName, age, status, gender, state);
            goBack();
        }
        else{
            //TODO throw error when new profile is tried to  be created but fields are empty
        }

    }

    public void goBack(){
        try {
            Stage stage = Main.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/network.fxml"));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void configGenderCheckBoxes(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(maleCheckBox)){
            femaleCheckbox.setSelected(false);
            gender = "MALE";
        }

        if(actionEvent.getSource().equals(femaleCheckbox)){
            maleCheckBox.setSelected(false);
            gender = "FEMALE";
        }
    }
}
