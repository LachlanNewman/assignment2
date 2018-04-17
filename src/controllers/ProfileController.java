package controllers;

import javafx.scene.control.TextField;
import views.Network;
import views.Profile;

public class ProfileController {
    public TextField firstName;
    Profile profile;

    public void setProfile(Profile profile, Network network) {
        this.profile = profile;
        firstName.setText(profile.getFirstName());
    }
}
