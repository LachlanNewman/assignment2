package controllers;

import config.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import views.Network;

import javafx.scene.control.TextField;
import views.Profile;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class NetworkController implements Initializable, Navigation.Nav {

    public AnchorPane mainScene;
    private Network network;

    @FXML
    private VBox searchResults;
    @FXML
    private TextField nameSearchField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        network = new Network();
        pushProfiles(network.getNetwork());
    }


    public void searchNetwork(ActionEvent actionEvent) {
        searchResults.getChildren().clear();

        String name  = this.nameSearchField.getText();

        Map<String,Profile> profiles = this.network.searchNetwork(name);
        pushProfiles(profiles);
    }

    private void pushProfiles(Map<String, Profile> profiles) {
        if(!profiles.equals(null)) {
            for (Profile profile : profiles.values()) {
                Group profileBox = new Group();
                VBox profileContainer = new VBox();
                Node imgDisplay;
                try{
                    imgDisplay = new ImageView( new Image(profile.getPhotoUrl()));
                }
                catch (IllegalArgumentException e) {
                    imgDisplay = new Text("Image Not Found");
                }
                Text nameField = new Text("Name: " + profile.getName());
                Text ageField = new Text("Age: " + profile.getAge());
                Text statusField = new Text("Status: " + profile.getStatus());
                Text stateField = new Text("State: " + profile.getState().name());

                Button veiwProfile = new Button("View Profile");
                veiwProfile.setOnAction(event -> {
                    try {
                        navToProfile(profile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                profileContainer.getChildren().addAll(imgDisplay,nameField,ageField,statusField,stateField,veiwProfile);
                profileBox.getChildren().add(profileContainer);
                searchResults.getChildren().addAll(profileBox);
            }
        }
    }

    private void navToProfile(Profile profile) throws IOException { navigation.navToProfile(profile);}

    public void navToAddNewProfile() throws IOException { navigation.navToAddNewProfile();}
}
