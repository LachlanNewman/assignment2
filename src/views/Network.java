package views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static views.Gender.FEMALE;
import static views.Gender.MALE;
import static views.State.VIC;
import static views.State.WA;


public class Network {

    private Map<String,Profile> network;

    public Network() {
        this.network = new HashMap<>();


        addProfile("Alex", "Smith", "", "student at RMIT", MALE, 21, WA);
        addProfile("Ben", "Turner", "BenPhoto.jpg", "manager at Coles", MALE, 35, VIC);
        addProfile("Hannah", "White", "Hannah.png", "student at PLC", FEMALE, 14, VIC);
        addProfile("Zoe", "Foster", "", "Founder of ZFX", FEMALE, 28, VIC);
        addProfile("Mark", "Turner", "Mark.jpeg", "", MALE, 2, VIC);
        addProfile("Matt", "Burner", "Mark.jpeg", "", MALE, 2, VIC);
    }

    public void addProfile(String firstName, String lastName, String photoUrl, String status, Gender gender, int age, State state) {
        Profile profile;
        if (age > 16) {
            profile = new Adult(firstName, lastName, photoUrl, status, gender, age, state);
        } else if (age > 2) {
            profile = new Child(firstName, lastName, photoUrl, status, gender, age, state);
        } else {
            profile = new YoungChild(firstName, lastName, photoUrl, status, gender, age, state);
        }
        network.put(profile.getId(),profile);
    }

    public Profile getProfile(String id) {

        for (Profile p : network.values())
            if (p.getId().equals(id)) {
                return p;
            }
        return null;
    }

    public ArrayList<Profile> searchNetwork(String firstName) {
        ArrayList<Profile> profiles = new ArrayList<>();
        for (Profile p : network.values()) {
            if(p.getFirstName().contains(firstName)){
                profiles.add(p);
            }
        }
        return profiles;
    }

}
