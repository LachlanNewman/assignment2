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


    /**
     *
     * @param id
     * @param firstName
     * @param lastName
     * @param photoUrl
     * @param status
     * @param gender
     * @param age
     * @param state
     */
    public void addProfile(String id, String firstName, String lastName, String photoUrl, String status, Gender gender, int age, State state) {
        Profile profile;

        if (age > 16)     profile = new Adult(id, firstName, lastName, photoUrl, status, gender, age, state     );
        else if (age > 2) profile = new Child(id, firstName, lastName, photoUrl, status, gender, age, state     );
        else              profile = new YoungChild(id, firstName, lastName, photoUrl, status, gender, age, state);

        network.put(profile.getId(),profile);
    }

    public Profile getProfile(String id) {

        for (Profile p : network.values())
            if (p.getId().equals(id)) return p;
        return null;
    }

    /**
     * Returns a new Map with the profiles of users whos data matches or contains the parameters
     * pass from the network controller
     * @param firstName
     * @param lastName
     * @return
     */
    public Map<String,Profile> searchNetwork(String firstName,String lastName) {
        //TODO Search by genderField, stateField, min-max ageField
        Map<String,Profile> profiles = new HashMap<>();

        network.forEach((s, p) -> {
            //if the profiles first name  contains the search string add it to the new profiles array
            if(p.getFirstName().contains(firstName))  profiles.put(s,p);
            //if the profiles last name contains the search string add it to the new profiles array
            if(p.getLastName().contains(lastName)  )  profiles.put(s, p);
        });

        return profiles;
    }

    public Map<String, Profile> getNetwork() {
        return network;
    }
}
