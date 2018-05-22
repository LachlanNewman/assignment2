package controllers;

import config.Database;
import config.Exceptions;
import config.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import views.Adult;
import views.Profile;
import views.enums.Gender;
import views.Network;
import views.enums.Relationship;
import views.enums.State;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NewProfileController implements Initializable, Navigation.Nav {

    private boolean isChild = false;
    private String photoUrl;

    @FXML
    private Text uploadError;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField ageField;
    @FXML
    private Gender gender;
    @FXML
    private ComboBox stateField;
    @FXML
    private Button newProfileButton;
    @FXML
    private CheckBox femaleCheckbox,maleCheckBox;
    @FXML
    private TextField statusField;
    @FXML
    private ComboBox parentFieldA;
    @FXML
    private ComboBox parentFieldB;
    @FXML  private ImageView profileImg;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //set the gender to be unknown
        gender = Gender.valueOf("U");
        photoUrl = "";
        //
        //set the parents field
        Network.getNetwork().forEach((s, profile) -> {
            //check to see if the profile is an adult
            if (profile instanceof Adult) {
                //add profile to the parent fields
                parentFieldA.getItems().add(s);
                parentFieldB.getItems().add(s);
            }
        });
    }

    public void addNewProfile(ActionEvent actionEvent) throws IOException, Exceptions.NoParentException, Exceptions.NoAvailableException {

        String name = checkNameField();
        Integer age = checkAgeField();

        //check to make sure namefield or agefield is null
        if (!name.equals(null) && !age.equals(null)) {
            //get the state form the state field
            State state = State.valueOf(stateField.getValue().toString());
            //get the status from the status field
            String status = statusField.getText();

            //check to see if new profile is a child
            if (isChild) {
                //check to see if the profile has valid parents
                ArrayList<Profile> parents = checkParentFields();
                if (!parents.equals(null)) {
                    //add profile to the network
                    Network.addProfile(name, "", status, gender, age, state, parents);
                    //add relation to database
                    Database.insertRelationShips(name,parents.get(0).getName(),Relationship.PARENT);
                    Database.insertRelationShips(name,parents.get(1).getName(),Relationship.PARENT);
                } else {
                    uploadError.setText("Error Requires Parents");
                }
            } else {
                Network.addProfile(name, "", status, gender, age, state);
            }
            Database.insertProfiles(name, null, status, gender.toString(), age, state.toString());
            goBack();
        }
    }


    @FXML
    private void goBack() throws IOException {
        navigation.navToNetwork();
    }

    @FXML
    private void configGenderCheckBoxes(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(maleCheckBox)) {
            femaleCheckbox.setSelected(false);
            gender = Gender.valueOf("M");
        }

        if (actionEvent.getSource().equals(femaleCheckbox)) {
            maleCheckBox.setSelected(false);
            gender = Gender.valueOf("F");
        }
    }


    public Integer checkAgeField() {

        Integer age = null;
        //check to make sure the age is a number
        try {
            //convert age to integer
            age = Integer.parseInt(ageField.getText());
            //check to make sure the age is not negative or over 150
            if (age < 0 || age > Profile.MAX_AGE) {
                throw new Exceptions.NoSuchAgeException(Exceptions.NO_SUCH_AGE_EXCEPTION);
            } else if (age < Adult.MIN_AGE) {
                //set visiblity of parents to true
                parentsVisible(true);
            } else {
                //set visibility to false
                parentsVisible(false);
            }
        } catch (NumberFormatException e) {
            parentsVisible(false);
            uploadError.setText("age must be a number");
        } catch (Exceptions.NoSuchAgeException e) {
            parentsVisible(false);
            age = null;
            uploadError.setText(e.getMessage());
        } finally {
            return age;
        }

    }

    private void parentsVisible(boolean visible) {
        //set the child flag to the visibility of parent fields
        isChild = visible;
        parentFieldA.setVisible(visible);
        parentFieldB.setVisible(visible);
    }

    private String checkNameField() {
        String name = null;
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        //check that both name fields are not empty
        if (!firstName.isEmpty() && !lastName.isEmpty()) {
            name = firstName + " " + lastName;
        }
        return name;
    }

    private ArrayList<Profile> checkParentFields() {
        ArrayList<Profile> parents = null;
        try {
            Adult parentA = (Adult) Network.getProfile(parentFieldA.getValue().toString());
        Adult parentB = (Adult) Network.getProfile(parentFieldB.getValue().toString());
        Adult parentA_Spouse = (Adult) parentA.getRelationShips(Relationship.SPOUSE).get(0);
        Adult parentB_Spouse = (Adult) parentB.getRelationShips(Relationship.SPOUSE).get(0);
            //check make sure the parents are not the same
            if (parentA.equals(parentB)) {
                throw new Exceptions.NoParentException("Parents cannot be the same person");
            }
            //check to mke sure the parents are spouses
            else if (!parentA_Spouse.equals(parentB) || !parentB_Spouse.equals(parentA)) {
                throw new Exceptions.NoParentException("Profiles are not couples and cannot be parents.");
            } else {
                parents = new ArrayList<>();
                parents.add(parentA);
                parents.add(parentB);
            }
        } catch (Exceptions.NoParentException e) {
            uploadError.setText(e.getMessage());
        } catch (NullPointerException e) {
            uploadError.setText("Parents are required for children");
        } finally {
            return parents;
        }
    }

    public void uploadImage(ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open File");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files",
                        "*.bmp", "*.png", "*.jpg", "*.gif")); // limit chooser options to image files
        File file = chooser.showOpenDialog(new Stage());
        try {
            photoUrl = file.toURI().toURL().toString();
            Image image = new Image(photoUrl);
            profileImg.setImage(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please Select a File");
            /*alert.setContentText("You didn't select a file!");*/
            alert.showAndWait();
        }
    }
}
