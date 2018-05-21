package views;

import views.enums.Gender;
import views.enums.Relationship;
import views.enums.State;

import java.util.ArrayList;

public class YoungChild extends Child {

    public static final int AGE = 3;

    public YoungChild(String name, String photoUrl, String status, Gender gender, int age, State state) {
        super(name, photoUrl, status, gender, age, state);
    }


    public YoungChild(String name, String photoUrl, String status, Gender gender, int age, State state, ArrayList<Profile> parents) {
        super(name, photoUrl, status, gender, age, state,parents);
    }
}
