package controllers;

import config.Database;
import config.Exceptions;
import config.Navigation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import views.Child;
import views.Network;
import views.Profile;
import views.YoungChild;
import views.enums.Gender;
import views.enums.Relationship;
import views.enums.State;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

//TODO view profile friends
//TODO view profile relationships

public class ProfileController implements Navigation.Nav {


    @FXML
    private ImageView profileImg;
    @FXML
    private Text nameField;
    @FXML
    private TextField ageField,statusField,genderField;
    @FXML
    private ComboBox stateField;

    @FXML
    private Button navToFamily;

    private Profile profile;

    public void setProfile(Profile profile) {
        this.profile = profile;
        nameField.setText(profile.getName());
        ageField.setText(profile.getAge() + "");
        statusField.setText(profile.getStatus());
        genderField.setText(profile.getGender().name());
        stateField.setItems(FXCollections.observableArrayList(State.values()));
        stateField.getSelectionModel().select(profile.getState().name());
        try {
            //check to see if the url in the database can be found
            profileImg.setImage(new Image(new FileInputStream(profile.getPhotoUrl())));
        } catch (FileNotFoundException e) {
            //set error
            e.printStackTrace();
        }
        catch (NullPointerException e){

        }
    }

    public void updateProfile() {
        try {
            this.profile.setStatus(statusField.getText());
            this.profile.setGender(Gender.valueOf(genderField.getText()));
            this.profile.setAge(Integer.parseInt(ageField.getText()));
            this.profile.setState(State.valueOf(stateField.getValue().toString()));
            Network.updateProfile(this.profile);
            Database.updateProfilesTable(this.profile);
            goBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteProfile() {
        //display warning
        //remove profile from network
        Network.deleteProfile(profile);
        Network.getNetwork().forEach((String s, Profile p) -> {
            for(Relationship r: Relationship.values()){
                try {
                    Network.getNetwork().get(p.getName()).removeRelationship(profile,r);
                } catch (Exceptions.NoParentException e) {
                    e.printStackTrace();
                }
            }
        });
        //remove profile from database
        Database.deleteProfile(this.profile);
        //go back to main menu
        try {
            goBack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goBack() throws IOException {
        navigation.navToNetwork();
    }


    public void navToFamily() throws IOException {
        navigation.navToRelationship(profile);
    }
}
