package controllers;

import config.Database;
import config.Exceptions;
import config.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import views.*;
import views.enums.Relationship;

import java.io.IOException;
import java.util.ArrayList;

public class RelationShipController implements Navigation.Nav {

    @FXML
    private TilePane relationShipContainer;
    @FXML
    private VBox profileSearchContainer;
    @FXML
    private ImageView profileImg;
    @FXML
    Text profileTypeField, nameFieldProfile, ageFieldProfile, genderFieldProfile, stateFieldProfile, statusFieldProfile, errorMessage,errorImg;
    @FXML
    private CheckBox showFamily,showFriends,showColleages,showClassMates;

    private Profile profile;

    public void initController(Profile profile) {
        this.profile = profile;
        try {
            profileImg.setImage(new Image(profile.getPhotoUrl()));
        }
        catch (IllegalArgumentException e){
            errorImg.setText("No Image");
        }
        profileTypeField.setText(profile.getClass().getName());
        nameFieldProfile.setText(profile.getName());
        ageFieldProfile.setText("Age:" + profile.getAge() + "");
        genderFieldProfile.setText("Gender: " + profile.getGender().name());
        stateFieldProfile.setText("State: " + profile.getState().name());
        setRelationshipsContainer();


    }

    private void pushAll() {
        //show parents
        pushParents();
        //show spouse
        pushSpouse();
        //show colleagues
        pushColleagues();
        //show class mates
        pushClassMates();
        //show friedns
        pushFriends();
    }

    public void addSpouseAction() {
        profileSearchContainer.getChildren().clear();
        ArrayList<Profile> relationships = this.profile.getRelationShips(Relationship.SPOUSE);
        Network.getNetwork().forEach((s, p) -> {
            if (!p.equals(profile) && !relationships.contains(p)) {
                pushProfileSearchContainer(p, Relationship.SPOUSE);
            }
        });
    }

    public void addChildrenAction() throws IOException {
        navigation.navToAddNewProfile();
    }

    public void addFriendAction() {
        profileSearchContainer.getChildren().clear();
        ArrayList<Profile> relationships = this.profile.getRelationShips(Relationship.FRIEND);
        Network.getNetwork().forEach((s, p) -> {
            if (!p.equals(profile) && !relationships.contains(p)) {
                pushProfileSearchContainer(p, Relationship.FRIEND);
            }
        });
    }

    public void addColleagueAction() {
        profileSearchContainer.getChildren().clear();
        ArrayList<Profile> relationships = this.profile.getRelationShips(Relationship.COLLEAGUE);
        Network.getNetwork().forEach((s, p) -> {
            if (!p.equals(profile) && !relationships.contains(p)) {
                pushProfileSearchContainer(p, Relationship.COLLEAGUE);
            }
        });
    }

    public void addClassMateAction() {
        profileSearchContainer.getChildren().clear();
        ArrayList<Profile> relationships = this.profile.getRelationShips(Relationship.CLASSMATE);
        Network.getNetwork().forEach((s, p) -> {
            if (!p.equals(profile) && !relationships.contains(p)) {
                pushProfileSearchContainer(p, Relationship.CLASSMATE);
            }
        });
    }

    private void pushProfileSearchContainer(Profile profile, Relationship relationship) {
        Group profileContainer = new Group();
        VBox profileBox = new VBox();
        Node imgDisplay;
        try {
            imgDisplay = new ImageView(new Image(profile.getPhotoUrl()));
        } catch (IllegalArgumentException e) {
            imgDisplay = new Text("No Profile Image");
        }
        Text nameText = new Text("Name: " + profile.getName());
        Text ageText = new Text("Age: " + profile.getAge() + "");
        Text statusText = new Text("Status: " + profile.getStatus());
        Text genderText = new Text("Gender: " + profile.getGender().toString());
        Text stateText = new Text("State: " + profile.getState().toString());
        Button viewProfileButton = new Button("Add Profile");
        viewProfileButton.setOnAction(event -> {
            addRelationship(relationship, profileContainer, profile);
        });
        profileBox.getChildren().addAll(
                imgDisplay,
                nameText,
                ageText,
                statusText,
                genderText,
                stateText,
                viewProfileButton
        );

        profileContainer.getChildren().add(profileBox);
        profileSearchContainer.getChildren().add(profileContainer);
    }

    private void pushSpouse() {
        try {
            profile.getRelationShips(Relationship.SPOUSE).forEach(p -> pushProfile(p, Relationship.SPOUSE));
        } catch (NullPointerException e) {
        }
    }

    private void pushColleagues() {
        try {
            profile.getRelationShips(Relationship.COLLEAGUE).forEach(p -> pushProfile(p, Relationship.COLLEAGUE));
        } catch (NullPointerException e) {
        }

    }

    private void pushClassMates() {
        try {
            profile.getRelationShips(Relationship.CLASSMATE).forEach(p -> pushProfile(p, Relationship.CLASSMATE));
        } catch (NullPointerException e) {
        }
    }

    private void pushFriends() {
        try {
            profile.getRelationShips(Relationship.FRIEND).forEach(p -> pushProfile(p, Relationship.FRIEND));
        } catch (NullPointerException e) {
        }
    }

    private void pushParents() {
        try {
            profile.getRelationShips(Relationship.PARENT).forEach(p -> pushProfile(p, Relationship.PARENT));
        } catch (NullPointerException e) {
        }
    }

    private void pushProfile(Profile profile, Relationship relationship) {
        Group profileContainer = getProfileContainer(profile, relationship);
        profileContainer.getChildren().add(new Text(relationship.name()));
        relationShipContainer.getChildren().add(profileContainer);

    }

    private void addRelationship(Relationship relationship, Group profileContainer, Profile profile) {
        ArrayList<Profile> relationships = this.profile.getRelationShips(relationship);
        try {
            Group relationshipHolder = getProfileContainer(profile,relationship);
            Network.getNetwork().get(this.profile.getName()).addRelationship(profile, relationship);
            Network.getNetwork().get(profile.getName()).addRelationship(this.profile, relationship);
            relationShipContainer.getChildren().add(relationshipHolder);
            profileSearchContainer.getChildren().remove(profileContainer);
            Database.insertRelationShips(this.profile.getName(), profile.getName(), relationship);
            setRelationshipsContainer();
        } catch (Exceptions.NotToBeFriendsException e) {
            errorMessage.setText(e.getMessage());
        } catch (Exceptions.TooYoungException e) {
            errorMessage.setText(e.getMessage());
        } catch (Exceptions.NotToBeColleaguesException e) {
            errorMessage.setText(e.getMessage());
        } catch (Exceptions.NoAvailableException e) {
            errorMessage.setText(e.getMessage());
        } catch (Exceptions.NotToBeClassmatesException e) {
            errorMessage.setText(e.getMessage());
        } catch (Exceptions.NotToBeCoupledException e) {
            errorMessage.setText(e.getMessage());
        } catch (Exceptions.NoParentException e) {
            e.printStackTrace();
        } catch (Exceptions.NotCoupledException e) {
            e.printStackTrace();
        }
    }


    private Group getProfileContainer(Profile profile, Relationship relationship) {
        Group profileContainer = new Group();
        VBox profileBox = new VBox();
        Node imgDisplay;
        try {
            imgDisplay = new ImageView(new Image(profile.getPhotoUrl()));
        } catch (IllegalArgumentException e) {
            imgDisplay = new Text("No Profile Image");
        }
        Text nameText = new Text("Name: " + profile.getName());
        Text ageText = new Text("Age: " + profile.getAge() + "");
        Text statusText = new Text("Status: " + profile.getStatus());
        Text genderText = new Text("Gender: " + profile.getGender().toString());
        Text stateText = new Text("State: " + profile.getState().toString());
        Button viewProfileButton = new Button("Remove Profile");
        viewProfileButton.setOnAction(event -> {
            removeRelationShip(profileContainer, profile, relationship);
        });
        profileBox.getChildren().addAll(
                imgDisplay,
                nameText,
                ageText,
                statusText,
                genderText,
                stateText,
                viewProfileButton
        );

        profileContainer.getChildren().add(profileBox);
        return profileContainer;
    }

    private void removeRelationShip(Group relationshipGroup, Profile relationship, Relationship relationShipType) {

        try {
            Network.getNetwork().get(profile.getName()).removeRelationship(relationship, relationShipType);
            Network.getNetwork().get(relationship.getName()).removeRelationship(profile, relationShipType);
            //remove from database
            relationShipContainer.getChildren().remove(relationshipGroup);
            Database.removeRelationShip(relationship, profile, relationShipType);
        } catch (Exceptions.NoParentException e) {
            e.printStackTrace();
        }
    }


    public void goBack() throws IOException {
        navigation.navToProfile(profile);
    }

    public void setRelationshipsContainer() {
        relationShipContainer.getChildren().clear();
        if(!showFamily.isSelected() && !showFriends.isSelected() && !showColleages.isSelected() && !showClassMates.isSelected()){
            pushParents();
            pushSpouse();
            pushFriends();
            pushClassMates();
            pushColleagues();
        }
        else {
            if (showFamily.isSelected()) {
                pushParents();
                pushSpouse();
            }
            if (showFriends.isSelected()) {
                pushFriends();
            }
            if (showClassMates.isSelected()) {
                pushClassMates();
            }
            if (showColleages.isSelected()) {
                pushColleagues();
            }
        }
        }
}

