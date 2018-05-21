package views.interfaces;

import config.Exceptions;
import views.Profile;

import java.util.ArrayList;

public interface Children {

    public ArrayList<Profile> getChildren();

    public void addChilden(Profile child) throws Exceptions.NoParentException, Exceptions.NoAvailableException, Exceptions.TooYoungException;

    public void removeChilden(Profile child);
}
