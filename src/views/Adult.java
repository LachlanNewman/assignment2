package views;

import config.Exceptions;
import views.enums.Gender;
import views.enums.Relationship;
import views.enums.State;
import views.interfaces.*;

import java.util.ArrayList;

public class Adult extends Profile implements Friend, Colleague, ClassMate, Children {

    public static final int MIN_AGE = 18;

    private ArrayList<Profile> friends;
    private ArrayList<Profile> colleagues;
    private ArrayList<Profile> classMates;
    private ArrayList<Profile> children;
    private ArrayList<Profile> spouse;
    private boolean hasSpouse;

    public Adult(String name, String photoUrl, String status, Gender gender, int age, State state) {
        super(name, photoUrl, status, gender, age, state);
        this.colleagues = new ArrayList<>();
        friends = new ArrayList<>();
        classMates = new ArrayList<>();
        children = new ArrayList<>();
        spouse = new ArrayList<>();
        hasSpouse = false;
    }

    @Override
    public ArrayList<Profile> getRelationShips(Relationship relationshipType) {
        ArrayList<Profile> relationships = null;
        switch (relationshipType) {
            case FRIEND:
                relationships = getFriends();
                break;
            case CLASSMATE:
                relationships = getClassMates();
                break;
            case COLLEAGUE:
                relationships = getColleagues();
                break;
            case SPOUSE:
                relationships = getSpouse();
                break;
            case PARENT:
                relationships = getChildren();
                break;
            case CHILD:
                break;
        }

        return relationships;
    }

    @Override
    public void addRelationship(Profile relationship, Relationship relationshipType) throws Exceptions.NotToBeFriendsException, Exceptions.TooYoungException, Exceptions.NotToBeColleaguesException, Exceptions.NotToBeClassmatesException, Exceptions.NoAvailableException {
        switch (relationshipType) {
            case FRIEND:
                addFriend(relationship);
                break;
            case CLASSMATE:
                addClassMate(relationship);
                break;
            case COLLEAGUE:
                addColleague(relationship);
                break;
            case SPOUSE:
                addSpouse(relationship);
                break;
            case PARENT:
                addChilden(relationship);
                break;
            case CHILD:
                break;
        }
    }

    @Override
    public void removeRelationship(Profile relationship, Relationship relationshipType) {
        switch (relationshipType) {
            case FRIEND:
                removeFriend(relationship);
                break;
            case CLASSMATE:
                removeClassMate(relationship);
                break;
            case COLLEAGUE:
                removeColleague(relationship);
                break;
            case SPOUSE:
                break;
            case PARENT:
                break;
            case CHILD:
                removeChilden(relationship);
                break;
        }

    }

    @Override
    public ArrayList<Profile> getFriends() {
        return friends;
    }

    @Override
    public void addFriend(Profile friend) throws Exceptions.TooYoungException, Exceptions.NotToBeFriendsException {
        if (friend instanceof YoungChild) throw new Exceptions.TooYoungException(Exceptions.TOO_YOUNG_EXCEPTION);
        else if (friend instanceof Child)
            throw new Exceptions.NotToBeFriendsException(Exceptions.NOT_TO_BE_FRIENDS_EXCEPTION);
        friends.add(friend);
    }

    @Override
    public void removeFriend(Profile friend) {
        friends.remove(friend);
    }

    @Override
    public ArrayList<Profile> getColleagues() {
        return colleagues;
    }

    @Override
    public void addColleague(Profile colleague) throws Exceptions.NotToBeColleaguesException {
        //check to see if the colleague is a adult
        if (!(colleague instanceof Adult)) {
            throw new Exceptions.NotToBeColleaguesException(Exceptions.NOT_TO_BE_COLLEAGUES_EXCEPTION);
        } else {
            colleagues.add(colleague);
        }
    }

    @Override
    public void removeColleague(Profile colleague) {
        colleagues.remove(colleague);
    }

    @Override
    public ArrayList<Profile> getClassMates() {
        return classMates;
    }

    @Override
    public void addClassMate(Profile classMate) throws Exceptions.NotToBeClassmatesException {
        //check to make sure profile is not youngchild
        if (classMate instanceof YoungChild)
            throw new Exceptions.NotToBeClassmatesException(Exceptions.NOT_TO_BE_CLASSMATE_EXCEPTION);
        else classMates.add(classMate);
    }

    @Override
    public void removeClassMate(Profile classMate) {
        classMates.remove(classMate);
    }

    private ArrayList<Profile> getSpouse() {
        return spouse;
    }

    public void addSpouse(Profile spouse) throws Exceptions.NoAvailableException {
        if (hasSpouse) throw new Exceptions.NoAvailableException(
                Exceptions.NO_AVAILABLE_EXCEPTION
        );
        else {
            this.spouse.add(spouse);
            hasSpouse = true;
        }
    }

    @Override
    public ArrayList<Profile> getChildren() {
        return children;
    }

    @Override
    public void addChilden(Profile child) throws Exceptions.NoAvailableException, Exceptions.TooYoungException {
        //check if profile has spouse
        if (spouse == null) throw new Exceptions.NoAvailableException(Exceptions.NO_AVAILABLE_EXCEPTION);
            //check that the child is not an adult
        else if (child instanceof Adult || this.getAge() < child.getAge())
            throw new Exceptions.TooYoungException(Exceptions.TOO_YOUNG_EXCEPTION);
            //add child
        else children.add(child);
    }

    @Override
    public void removeChilden(Profile child) {
        children.remove(child);
    }

    public boolean hasSpouse(){
        return hasSpouse;
    }
}
