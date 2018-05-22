package views.interfaces;


import config.Exceptions;
import views.Profile;

import java.util.ArrayList;

/**
 * Profile Class
 * @version 1.0
 * @author Lachlan Newman s3691320
 */
public interface Friend {

    /*Minimum age difference for Child friends */
    int CHILD_AGE_DIFFERENCE = 3;

    /**
     * @return ArrayList<Profile> containing the implementing profiles Friends
     */
    ArrayList<Profile> getFriends();

    /**
     * @param friend
     * Adds a Profile to the the ArrayList friends
     */
    void addFriend(Profile friend) throws Exceptions.NotToBeFriendsException, Exceptions.TooYoungException;

    /**
     * @param friend
     * Removes a Profile from the ArrayList friend
     */
    void removeFriend(Profile friend);
}
