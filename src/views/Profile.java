package views;

import java.util.ArrayList;
import java.util.UUID;

public class Profile {

    private String id;
    private String firstName;
    private String lastName;
    private String status;
    private String photoUrl;
    private Gender gender;
    private int age;
    private State state;

    private ArrayList<Profile> friends;

    public Profile(String firstName, String lastName, String photoUrl, String status,Gender gender, int age, State state) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.photoUrl = photoUrl;
        this.gender = gender;
        this.age = age;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public ArrayList<Profile> getFriends() {
        return friends;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void addFriend(Profile friend) {
        this.friends.add(friend);
    }
}
