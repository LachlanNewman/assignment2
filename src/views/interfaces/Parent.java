package views.interfaces;

import config.Exceptions;
import views.Profile;

import java.util.ArrayList;

public interface Parent {

    public static final int NUM_PARENTS = 2;

    public ArrayList<Profile> getParents();

    public void addParent(Profile parent) throws Exceptions.NoAvailableException, Exceptions.NoParentException;
}
