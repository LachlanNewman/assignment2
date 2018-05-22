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

/**
 * Profile Class
 * @version 1.0
 * @author Lachlan Newman s3691320
 *
 *
 */
public class Network {

    private static Map<String, Profile> network;    /*holds network profiles */
    private static boolean dbFlag = false;          /* use to inform class that the network has been read from database */

    /**
     * Contructor for class Network
     */
    public Network() {
        if (!dbFlag)
            network = new HashMap<>();
            try {
                Database.getProfiles();
                Database.getRelationships();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            dbFlag = true;
    }

    /**
     * Adds a profile the network of type depending on age
     * @param name
     * @param photoUrl
     * @param status
     * @param gender
     * @param age
     * @param state
     */
    public static void addProfile(String name, String photoUrl, String status, Gender gender, int age, State state) {
        Profile profile;
        if (age < Child.MIN_AGE) profile = new YoungChild(name, photoUrl, status, gender, age, state);
        else if (age < Adult.MIN_AGE) profile = new Child(name, photoUrl, status, gender, age, state);
        else profile = new Adult(name, photoUrl, status, gender, age, state);
        network.put(profile.getName(), profile);
    }

    /**
     * Add a new profile to the network where the profile is a child an has been inserted byb the user
     * not the database
     * @param name
     * @param photoUrl
     * @param status
     * @param gender
     * @param age
     * @param state
     * @param parents
     * @throws Exceptions.NoParentException
     * @throws Exceptions.NoAvailableException
     */
    public static void addProfile(String name, String photoUrl, String status, Gender gender, int age, State state, ArrayList<Profile> parents) throws Exceptions.NoParentException, Exceptions.NoAvailableException {
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

    /**
     * return the network opf profiles
     * @return
     */
    public static Map<String, Profile> getNetwork() {
        return network;
    }

    /**
     * delete a profile from the network
     * @param profile
     */
    public static void deleteProfile(Profile profile) {
        network.remove(profile.getName());
    }

    /**
     * update a profile in the network
     * @param profile
     */
    public static void updateProfile(Profile profile) {
        network.put(profile.getName(), profile);
    }

    /**
     * get a profile from the network
     * @param name
     * @return
     */
    public static Profile getProfile(String name) {
        return network.get(name);
    }
}
