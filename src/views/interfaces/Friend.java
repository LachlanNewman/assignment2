package views.interfaces;


import config.Exceptions;
import views.Profile;

import java.util.ArrayList;

public interface Friend {

    public static final int FRIEND_AGE_DIFFERENCE = 3;

    public ArrayList<Profile> getFriends();

    public void addFriend(Profile friend) throws Exceptions.NotToBeFriendsException, Exceptions.TooYoungException;

    public void removeFriend(Profile friend);
}
