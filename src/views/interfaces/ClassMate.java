package views.interfaces;

import config.Exceptions;
import views.Profile;

import java.util.ArrayList;

public interface ClassMate {

    /**
     * @return ArrayList<Profile> containing the implementing profiles classmates
     */
    ArrayList<Profile> getClassMates();

    /**
     * @param classMate
     * Adds a Profile to the the ArrayList classmates
     */
    void addClassMate(Profile classMate) throws Exceptions.TooYoungException, Exceptions.NotToBeClassmatesException;

    /**
     * @param classMate
     * Removes a Profile from the ArrayList colleague
     */
    void removeClassMate(Profile classMate);
}
