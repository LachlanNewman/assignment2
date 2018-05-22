package views;

import java.util.ArrayList;

import config.Exceptions;
import views.enums.Gender;
import views.enums.Relationship;
import views.enums.State;
import views.interfaces.ClassMate;
import views.interfaces.Friend;

/**
 * Child Class
 * @version 1.0
 * @author Lachlan Newman s3691320
 *
 */
public class Child extends Profile implements Friend, ClassMate {

    public static final int NUM_PARENTS = 2;
    public static final int MIN_AGE = 4;

    private ArrayList<Profile> friends;
    private ArrayList<Profile> classMates;
    private ArrayList<Profile> parents;

    public Child(String name, String photoUrl, String status, Gender gender, int age, State state) {
        super(name, photoUrl, status, gender, age, state);
        friends = new ArrayList<>();
        classMates = new ArrayList<>();
        parents = new ArrayList<>();
    }

    public Child(String name, String photoUrl, String status, Gender gender, int age, State state, ArrayList<Profile> parents) throws Exceptions.NoParentException, Exceptions.NoAvailableException {
        this(name,photoUrl,status,gender,age,state);
        for (Profile parent : parents) {
            addParent(parent);
        }
    }

    /**
     *
     * @param relationshipType
     * ArrayList of relationships for the profile based on the relationshipType and
     * the instance of the profile, will return null if not a correct relationship type or
     * @return relationships
     */
    @Override
    public ArrayList<Profile> getRelationShips(Relationship relationshipType) {
        ArrayList<Profile> relationships = null;
            switch (relationshipType) {
                case FRIEND:
                    relationships = getFriends();
                    break;
                case COLLEAGUE:
                    /*child cannot have colleages not ignore case */
                    break;
                case CLASSMATE:
                    relationships = getClassMates();
                    break;
                case SPOUSE:
                    /*chaild cannot have spouse so ignore case */
                    break;
                case PARENT:
                    relationships = getParents();
                    break;
            }
            return relationships;
    }

    @Override
    public ArrayList<Profile> getFriends() {
        return friends;
    }

    @Override
    public ArrayList<Profile> getClassMates() {
        return classMates;
    }

    private ArrayList<Profile> getParents() {
        return parents;
    }

    /**
     *
     * @param relationship
     * @param relationshipType
     * Remove a relationship from thrs profile dpending on the profile instance type
     */
    @Override
    public void removeRelationship(Profile relationship, Relationship relationshipType) throws Exceptions.NoParentException {
        switch (relationshipType) {
            case FRIEND:
                removeFriend(relationship);
                break;
            case COLLEAGUE:
                /* child cannot have colleagues */
                break;
            case CLASSMATE:
                removeClassMate(relationship);
                break;
            case SPOUSE:
                /*child cannot have a spouse */
                throw new Exceptions.NoParentException(Exceptions.NO_PARENT_EXCEPTION);
            case PARENT:
                /* a childs parents cannot be removed in this world*/
                throw new Exceptions.NoParentException(Exceptions.NO_PARENT_EXCEPTION);
        }
    }

    @Override
    public void removeFriend(Profile friend) {
        friends.remove(friend);
    }

    @Override
    public void removeClassMate(Profile classMate) {
        classMates.remove(classMate);
    }

    /**
     *
     * @param relationship
     * @param relationshipType
     * Add a new reationship depending on the instance of profile
     */
    @Override
    public void addRelationship(Profile relationship, Relationship relationshipType) throws
            Exceptions.TooYoungException,
            Exceptions.NotToBeFriendsException,
            Exceptions.NotToBeClassmatesException,
            Exceptions.NotToBeCoupledException,
            Exceptions.NotToBeColleaguesException,
            Exceptions.NoAvailableException,
            Exceptions.NoParentException {
            switch (relationshipType) {
                case FRIEND:
                    addFriend(relationship);
                    break;
                case COLLEAGUE:
                    /* child cannot have colleagues */
                    throw new Exceptions.NotToBeColleaguesException(Exceptions.NOT_TO_BE_COLLEAGUES_EXCEPTION);
                case CLASSMATE:
                    addClassMate(relationship);
                    break;
                case SPOUSE:
                    /*child cannot have a spouse */
                    throw new Exceptions.NotToBeCoupledException(Exceptions.NOT_TO_BE_COUPLED_EXCEPTION);
                case PARENT:
                    addParent(relationship);
            }
    }

    /**
     * if friend is not of type Child or the age difference is to great the following exceptions will be thrown
     * @param friend
     * @throws Exceptions.NotToBeFriendsException
     * @throws Exceptions.TooYoungException
     */
    @Override
    public void addFriend(Profile friend) throws Exceptions.NotToBeFriendsException, Exceptions.TooYoungException {
        if (friend instanceof YoungChild)
            /* Young child cannot have friends */
            throw new Exceptions.TooYoungException(Exceptions.TOO_YOUNG_EXCEPTION);
        else if (!(friend instanceof Child) || Math.abs(this.getAge() - friend.getAge()) > CHILD_AGE_DIFFERENCE)
            /* Child friends cannot be of age difference greater than 3 */
            throw new Exceptions.NotToBeFriendsException(Exceptions.NOT_TO_BE_FRIENDS_EXCEPTION);
        else friends.add(friend);   /* add friend */
    }

    /**
     * {@inheritDoc}
     * if classmate is not of type Adult or Child the following exceptions will be thrown
     * @param classMate
     * @throws Exceptions.NotToBeClassmatesException
     */
    @Override
    public void addClassMate(Profile classMate) throws Exceptions.NotToBeClassmatesException {
        if (classMate instanceof YoungChild)
            /* classMates cannot occur between a young child */
            throw new Exceptions.NotToBeClassmatesException(Exceptions.NOT_TO_BE_CLASSMATE_EXCEPTION);
        else classMates.add(classMate); /* add classmate */
    }


    /**
     * {@inheritDoc}
     * if parent is not of type Adult or the num of parents
     * is alread more than 2 the following exceptions will be thrown
     * @param parent
     * @throws Exceptions.NoAvailableException
     * @throws Exceptions.NoParentException
     */
    private void addParent(Profile parent) throws Exceptions.NoAvailableException, Exceptions.NoParentException {
        if (parents.size() >= NUM_PARENTS)

            /*child cannot have more than 2 parents */
            throw new Exceptions.NoAvailableException(Exceptions.NO_AVAILABLE_EXCEPTION);
        else if (!(parent instanceof Adult))

            /* a child cannot be a parent in t5his world */
            throw new Exceptions.NoParentException(Exceptions.NO_PARENT_EXCEPTION);
        else

            /* add parent */
            parents.add(parent);
    }

}
