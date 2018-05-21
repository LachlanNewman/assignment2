package views;

import java.util.ArrayList;

import config.Exceptions;
import views.enums.Gender;
import views.enums.Relationship;
import views.enums.State;
import views.interfaces.ClassMate;
import views.interfaces.Friend;
import views.interfaces.Parent;

public class Child extends Profile implements Friend, ClassMate, Parent {

    public static final int MIN_AGE = 4;

    private ArrayList<Profile> friends;
    private ArrayList<Profile> classMates;
    private ArrayList<Profile> parents;

    public Child(String name, String photoUrl, String status, Gender gender, int age, State state) {
        super(name, photoUrl, status, gender, age, state);
        this.friends = new ArrayList<>();
        this.classMates = new ArrayList<>();
        this.parents = new ArrayList<>();
    }

    public Child(String name, String photoUrl, String status, Gender gender, int age, State state, ArrayList<Profile> parents) {
        this(name,photoUrl,status,gender,age,state);
        this.parents = parents;
    }

    @Override
    public ArrayList<Profile> getRelationShips(Relationship relationshipType) {
        ArrayList<Profile> relationships = null;
            switch (relationshipType) {
                case FRIEND:
                    relationships = getFriends();
                    break;
                case COLLEAGUE:
                    break;
                case CLASSMATE:
                    relationships = getClassMates();
                    break;
                case SPOUSE:
                    break;
                case PARENT:
                    relationships = getParents();
                    break;
            }
            return relationships;
    }

    @Override
    public void addRelationship(Profile relationship, Relationship relationshipType) throws Exceptions.TooYoungException, Exceptions.NotToBeFriendsException, Exceptions.NotToBeClassmatesException, Exceptions.NotToBeCoupledException, Exceptions.NotToBeColleaguesException, Exceptions.NoAvailableException, Exceptions.NoParentException {
            switch (relationshipType) {
                case FRIEND:
                    addFriend(relationship);
                    break;
                case COLLEAGUE:
                    throw new Exceptions.NotToBeColleaguesException(Exceptions.NOT_TO_BE_COLLEAGUES_EXCEPTION);
                case CLASSMATE:
                    addClassMate(relationship);
                    break;
                case SPOUSE:
                    throw new Exceptions.NotToBeCoupledException(Exceptions.NOT_TO_BE_COUPLED_EXCEPTION);
                case PARENT:
                    //add new parent
                    addParent(relationship);
            }
    }

    @Override
    public void removeRelationship(Profile relationship, Relationship relationshipType) throws Exceptions.NoParentException {
        switch (relationshipType) {
            case FRIEND:
                removeFriend(relationship);
                break;
            case COLLEAGUE:
                break;
            case CLASSMATE:
                break;
            case SPOUSE:
                throw new Exceptions.NoParentException(Exceptions.NO_PARENT_EXCEPTION);
            case PARENT:
                throw new Exceptions.NoParentException(Exceptions.NO_PARENT_EXCEPTION);
        }
    }


    @Override
    public ArrayList<Profile> getFriends() {
        return friends;
    }

    @Override
    public void addFriend(Profile friend) throws Exceptions.NotToBeFriendsException, Exceptions.TooYoungException {
        //check to see if the profile is a young child instannce
        if (friend instanceof YoungChild) throw new Exceptions.TooYoungException(Exceptions.TOO_YOUNG_EXCEPTION);
        //check to see if the profile is a child
        //check to see if the age difference in less 4
        else if (!(friend instanceof Child) || Math.abs(this.getAge() - friend.getAge()) > FRIEND_AGE_DIFFERENCE)
            throw new Exceptions.NotToBeFriendsException(Exceptions.NOT_TO_BE_FRIENDS_EXCEPTION);
        else friends.add(friend);
    }

    @Override
    public void removeFriend(Profile friend) {
        friends.remove(friend);
    }

    @Override
    public ArrayList<Profile> getClassMates() {
        return classMates;
    }

    @Override
    public void addClassMate(Profile classMate) throws Exceptions.NotToBeClassmatesException {
        //check to make sure profile is not youngchild
        if (classMate instanceof YoungChild) throw new Exceptions.NotToBeClassmatesException(Exceptions.NOT_TO_BE_CLASSMATE_EXCEPTION);
        else classMates.add(classMate);
    }

    @Override
    public void removeClassMate(Profile classMate) {
        classMates.add(classMate);
    }

    @Override
    public ArrayList<Profile> getParents() {
        return parents;
    }

    @Override
    public void addParent(Profile parent) throws Exceptions.NoAvailableException, Exceptions.NoParentException {
        //check to see if there are not already 2 par
        if (parents.size() >= Parent.NUM_PARENTS)
            throw new Exceptions.NoAvailableException(Exceptions.NO_AVAILABLE_EXCEPTION);
        else if (!(parent instanceof Adult)) throw new Exceptions.NoParentException(Exceptions.NO_PARENT_EXCEPTION);
        else parents.add(parent);
    }

}
