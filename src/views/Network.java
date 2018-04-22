package views;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static views.Gender.FEMALE;
import static views.Gender.MALE;
import static views.State.VIC;


public class Network {

    private Map<String,Profile> network;

    public Network(Connection connection) {

        this.network = new HashMap<>();
        String select = "SELECT * FROM PROFILES";
        try {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(select);
            while (results.next()) {
                addProfile(
                        results.getString("id"),
                        results.getString("firstName"),
                        results.getString("lastName"),
                        "",
                        results.getString("status"),
                        Gender.valueOf(results.getString("gender")),
                        results.getInt("age"),
                        State.valueOf(results.getString("state"))
                );

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public void addProfile(String id, String firstName, String lastName, String photoUrl, String status, Gender gender, int age, State state) {
        Profile profile;
        if (age > 16) {
            profile = new Adult(id,firstName, lastName, photoUrl, status, gender, age, state);
        } else if (age > 2) {
            profile = new Child(id,firstName, lastName, photoUrl, status, gender, age, state);
        } else {
            profile = new YoungChild(id,firstName, lastName, photoUrl, status, gender, age, state);
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

    public Map<String,Profile> searchNetwork(String firstName) {
        Map<String,Profile> profiles = new HashMap<>();
        network.forEach((s, p) -> {
            if(p.getFirstName().contains(firstName)){
                profiles.put(s,p);
            }
        });
        return profiles;
    }

    public Map<String, Profile> getNetwork() {
        return network;
    }
}
