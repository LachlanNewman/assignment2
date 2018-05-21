package views;

import config.Exceptions;
import views.enums.Gender;
import views.enums.Relationship;
import views.enums.State;

import java.util.ArrayList;

public abstract class Profile {

    public final static int MAX_AGE = 150;

    private String name;
    private String photoUrl;
    private String status;
    private Gender gender;
    private int age;
    private State state;

    public Profile(String name, String photoUrl, String status, Gender gender, int age, State state) {
        this.name = name;
        this.status = status;
        this.photoUrl = photoUrl;
        this.gender = gender;
        this.age = age;
        this.state = state;
    }


    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public State getState() {
        return state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setState(State state) {
        this.state = state;
    }

    public abstract ArrayList<Profile> getRelationShips(Relationship  relationshipType);

    public abstract void addRelationship(Profile relationship, Relationship relationshipType) throws Exceptions.NotToBeFriendsException, Exceptions.TooYoungException, Exceptions.NotToBeColleaguesException, Exceptions.NotToBeClassmatesException, Exceptions.NoAvailableException, Exceptions.NotToBeCoupledException, Exceptions.NoParentException;

    public abstract void removeRelationship(Profile relationship, Relationship relationshipType) throws Exceptions.NoParentException;


}
