package views;

import config.Exceptions;
import views.enums.Gender;
import views.enums.Relationship;
import views.enums.State;

import java.util.ArrayList;

/**
 * Profile Class
 * @version 1.0
 * @author Lachlan Newman s3691320
 *
 * The Profile class is a abstract class for Adult and Child
 *
 */
public abstract class Profile {

    public final static int MAX_AGE = 150; //maximum age of a profile

    private String photoUrl;    /**url of the profile photo*/
    private String status;      /**status for the profile*/
    private Gender gender;      /**gender of the profile*/
    private String name;        /**name of the profile*/
    private State state;        /**state of the profile*/
    private int age;            /**age of the profile*/


    /**
     *
     * @param name
     * @param photoUrl
     * @param status
     * @param gender
     * @param age
     * @param state
     *
     * Constructor for Class Profile
     */
    public Profile(String name, String photoUrl, String status, Gender gender, int age, State state) {
        this.name = name;
        this.status = status;
        this.photoUrl = photoUrl;
        this.gender = gender;
        this.age = age;
        this.state = state;
    }

    /**
     * @return Gets the value of photoUrl and returns photoUrl
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * Sets the photoUrl
     * You can use getPhotoUrl() to get the value of photoUrl
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * @return Gets the value of status and returns status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status
     * You can use getStatus() to get the value of status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return Gets the value of gender and returns gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender
     * You can use getGender() to get the value of gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * @return Gets the value of name and returns name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Gets the value of state and returns state
     */
    public State getState() {
        return state;
    }

    /**
     * Sets the state
     * You can use getState() to get the value of state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * @return Gets the value of age and returns age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the age
     * You can use getAge() to get the value of age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     *
     * @param relationshipType
     * @return ArrayList of relationships for the profile based on the relationshipType and
     * the instance of the profile
     */
    public abstract ArrayList<Profile> getRelationShips(Relationship  relationshipType);

    /**
     * @param relationship
     * @param relationshipType
     * Add a new reationship depending on the instance of profile
     * and the type of Relationship
     */
    public abstract void addRelationship(Profile relationship, Relationship relationshipType) throws Exceptions.NotToBeFriendsException, Exceptions.TooYoungException, Exceptions.NotToBeColleaguesException, Exceptions.NotToBeClassmatesException, Exceptions.NoAvailableException, Exceptions.NotToBeCoupledException, Exceptions.NoParentException;

    /**
     *
     * @param relationship
     * @param relationshipType
     * Remove a relationship from thrs profile dpending on the profile instance type
     * and the type of relationship
     */
    public abstract void removeRelationship(Profile relationship, Relationship relationshipType) throws Exceptions.NoParentException;


}
