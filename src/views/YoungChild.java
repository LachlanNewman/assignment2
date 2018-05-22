package views;

import config.Exceptions;
import views.enums.Gender;
import views.enums.State;

import java.util.ArrayList;

public class YoungChild extends Child {

    public static final int AGE = 3;

    public YoungChild(String name, String photoUrl, String status, Gender gender, int age, State state) {
        super(name, photoUrl, status, gender, age, state);
    }


    public YoungChild(String name, String photoUrl, String status, Gender gender, int age, State state, ArrayList<Profile> parents) throws Exceptions.NoParentException, Exceptions.NoAvailableException {
        super(name, photoUrl, status, gender, age, state,parents);
    }

    /**
     * Young child cannot have friends so the folowing exceptions will be shown
     * @param friend
     * @throws Exceptions.TooYoungException
     */
    @Override
    public void addFriend(Profile friend) throws Exceptions.TooYoungException {
        throw new Exceptions.TooYoungException(Exceptions.TOO_YOUNG_EXCEPTION);
    }

    /**
     * Young child cannot be classmate so the sollowing excaptions are thrown
     * @param classMate
     * @throws Exceptions.NotToBeClassmatesException
     */
    @Override
    public void addClassMate(Profile classMate) throws Exceptions.NotToBeClassmatesException {
        throw new Exceptions.NotToBeClassmatesException(Exceptions.NOT_TO_BE_CLASSMATE_EXCEPTION);
    }
}
