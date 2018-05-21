package views;

import config.Database;
import config.Exceptions;
import views.enums.Gender;
import views.enums.Relationship;
import views.enums.State;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Network {

    private static Map<String, Profile> network;
    private static boolean dbFlag = false;

    public Network() {
        if (!dbFlag) {
            network = new HashMap<>();
            try {
                readProfiles();
                readRelationships();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dbFlag = true;
        }
    }

    private void readProfiles() throws SQLException {
        ResultSet results = Database.getProfiles();
        while (results.next()) {
            addProfile(
                    results.getString("name"),
                    results.getString("photoUrl"),
                    results.getString("status"),
                    Gender.valueOf(results.getString("gender")),
                    results.getInt("age"),
                    State.valueOf(results.getString("state"))
            );
        }
    }

    private void readRelationships() throws SQLException {
        ResultSet results = Database.getRelationships();
        while (results.next()) {
            String nameA = results.getString("nameA");
            String nameB = results.getString("nameB");
            Relationship relationship = Relationship.fromString(results.getString("relationship"));
            try {
                network.get(nameA).addRelationship(network.get(nameB), relationship);
                network.get(nameB).addRelationship(network.get(nameA), relationship);
            } catch (Exceptions.TooYoungException e) {
                e.printStackTrace();
            } catch (Exceptions.NotToBeFriendsException e) {
                e.printStackTrace();
            } catch (Exceptions.NotToBeColleaguesException e) {
                e.printStackTrace();
            } catch (Exceptions.NoAvailableException e) {
                e.printStackTrace();
            } catch (Exceptions.NotToBeClassmatesException e) {
                e.printStackTrace();
            } catch (Exceptions.NotToBeCoupledException e) {
                e.printStackTrace();
            } catch (Exceptions.NoParentException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addProfile(String name, String photoUrl, String status, Gender gender, int age, State state) {
        Profile profile;
        if (age < Child.MIN_AGE) profile = new YoungChild(name, photoUrl, status, gender, age, state);
        else if (age < Adult.MIN_AGE) profile = new Child(name, photoUrl, status, gender, age, state);
        else profile = new Adult(name, photoUrl, status, gender, age, state);
        network.put(profile.getName(), profile);
    }

    public static void addProfile(String name, String photoUrl, String status, Gender gender, int age, State state, ArrayList<Profile> parents) {
        Profile profile;
        if (age < Child.MIN_AGE) profile = new YoungChild(name, photoUrl, status, gender, age, state, parents);
        else profile = new Child(name, photoUrl, status, gender, age, state, parents);
        network.put(profile.getName(), profile);
    }


    /**
     * Returns a new Map with the profiles of users whos data matches or contains the parameters
     * pass from the network controller
     ** @return
     */
    public Map<String, Profile> searchNetwork(String nameSearch) {
        //TODO Search by genderField, stateField, min-max ageField
        Map<String, Profile> profiles = new HashMap<>();
        network.forEach((s, p) -> {
            String name = p.getName();
            //if the profiles first name  contains the search string add it to the new profiles array
            if (name.contains(nameSearch)) profiles.put(s, p);
        });

        return profiles;
    }

    public static Map<String, Profile> getNetwork() {
        return network;
    }

    public static void deleteProfile(Profile profile) {
        network.remove(profile.getName());
    }

    public static void updateProfile(Profile profile) {
        network.put(profile.getName(), profile);
    }

    public static Profile getProfile(String s) {
        return network.get(s);
    }
}
