package views.interfaces;

import config.Exceptions;
import views.Profile;

import java.util.ArrayList;

public interface Colleague {

    public ArrayList<Profile> getColleagues();

    public void addColleague(Profile colleague) throws Exceptions.NotToBeColleaguesException;

    public void removeColleague(Profile colleague);
}
