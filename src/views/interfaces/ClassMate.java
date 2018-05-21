package views.interfaces;

import config.Exceptions;
import views.Profile;

import java.util.ArrayList;

public interface ClassMate {

    public ArrayList<Profile> getClassMates();

    public void addClassMate(Profile classMate) throws Exceptions.TooYoungException, Exceptions.NotToBeClassmatesException;

    public void removeClassMate(Profile classMate);
}
