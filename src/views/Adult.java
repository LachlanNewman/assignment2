package views;

import config.Exceptions;
import views.enums.Gender;
import views.enums.Relationship;
import views.enums.State;
import views.interfaces.*;

import java.util.ArrayList;

/**
 * Adult Class
 * @version 1.0
 * @author Lachlan Newman s3691320
 *
 *
 */
public class Adult extends Profile implements Friend, ClassMate {

    public static final int MIN_AGE = 16;

    ArrayList<Profile> friends;
    ArrayList<Profile> classMates;
    ArrayList<Profile> colleagues;
    ArrayList<Profile> children;
    ArrayList<Profile> spouse;
    boolean hasSpouse;

    public Adult(String name, String photoUrl, String status, Gender gender, int age, State state) {
        super(name, photoUrl, status, gender, age, state);
        friends = new ArrayList<>();
        colleagues = new ArrayList<>();
        classMates = new ArrayList<>();
        children = new ArrayList<>();
        spouse = new ArrayList<>();
        hasSpouse = false;
    }

    /**
     * @param relationshipType ArrayList of relationships for the profile based on the relationshipType and
     *                         the instance of the profile, will return null if not a corrent relationship type or
     * @return relationships
     */
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

    public ArrayList<Profile> getColleagues() {
        return colleagues;
    }

    /**
     * @return ArrayList<Profile> containing the implementing profiles Friends
     */
    private ArrayList<Profile> getSpouse() {
        return spouse;
    }

    /**
     * {@inheritDoc}
     * @return children
     */
    private ArrayList<Profile> getChildren() {
        return children;
    }

    /**
     * @param relationship
     * @param relationshipType Remove a relationship from thrs profile dpending on the profile instance type
     *                         Relationship type PARENT will have no effect
     *                         Relationship type SPOUSE will have no effect
     */
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

    public void removeColleague(Profile colleague) {
        colleagues.remove(colleague);
    }

    private void removeChilden(Profile child) {
        children.remove(child);
    }



    /**
     * @param relationship
     * @param relationshipType Add a new reationship depending on the instance of profile
     */
    @Override
    public void addRelationship(Profile relationship, Relationship relationshipType) throws

            Exceptions.NotToBeFriendsException,
            Exceptions.TooYoungException,
            Exceptions.NotToBeColleaguesException,
            Exceptions.NotToBeClassmatesException,
            Exceptions.NoAvailableException,
            Exceptions.NotCoupledException {

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
        }
    }

    /**
     * {@inheritDoc}
     * if friend is not of type Adult the following exceptions will be thrown
     *
     * @param friend
     * @throws Exceptions.TooYoungException
     * @throws Exceptions.NotToBeFriendsException
     */
    @Override
    public void addFriend(Profile friend) throws Exceptions.TooYoungException, Exceptions.NotToBeFriendsException {
        if (friend instanceof YoungChild)
            /* Young child cannot have friends */
            throw new Exceptions.TooYoungException(Exceptions.TOO_YOUNG_EXCEPTION);
        else if (friend instanceof Child)
            /* Child cannot be friends with an adult*/
            throw new Exceptions.NotToBeFriendsException(Exceptions.NOT_TO_BE_FRIENDS_EXCEPTION);
        else friends.add(friend); /*add friend*/
    }





    /**
     * {@inheritDoc}
     * if colleague is not of type Adult the following exceptions will be thrown
     *
     * @param colleague
     * @throws Exceptions.NotToBeColleaguesException
     */
    public void addColleague(Profile colleague) throws Exceptions.NotToBeColleaguesException {
        if (!(colleague instanceof Adult))

            /* Only adults can have a colleague relationship */
            throw new Exceptions.NotToBeColleaguesException(Exceptions.NOT_TO_BE_COLLEAGUES_EXCEPTION);
        else

            /* add colleague */
            colleagues.add(colleague);
    }



    /**
     * {@inheritDoc}
     * if colleague is not of type Adult or Child the following exceptions will be thrown
     *
     * @param classMate
     * @throws Exceptions.NotToBeClassmatesException
     */
    @Override
    public void addClassMate(Profile classMate) throws Exceptions.NotToBeClassmatesException {
        if (classMate instanceof YoungChild)

            /* classMates cannot occur between a young child */
            throw new Exceptions.NotToBeClassmatesException(Exceptions.NOT_TO_BE_CLASSMATE_EXCEPTION);
        else

            /* add classmate */
            classMates.add(classMate);
    }




    /**
     * {@inheritDoc}
     * if colleague is not of type Adult the following exceptions will be thrown
     *
     * @param child
     * @throws Exceptions.NoAvailableException
     * @throws Exceptions.TooYoungException
     */
    private void addChilden(Profile child) throws Exceptions.TooYoungException, Exceptions.NotCoupledException {

        if (spouse == null)

            /* an adult cannot have a child if they do not have a spouse */
            throw new Exceptions.NotCoupledException(Exceptions.NOT_COUPLED_EXCEPTION);
        else if (child instanceof Adult || this.getAge() < child.getAge())

            /* Adult cannot be a child in this world */
            /*child cannot be older than the parent */
            throw new Exceptions.TooYoungException(Exceptions.TOO_YOUNG_EXCEPTION);
        else

            /* add child */
            children.add(child);
    }




    /**
     * @param spouse Adds a Profile to the the ArrayList friends
     */
    private void addSpouse(Profile spouse) throws Exceptions.NoAvailableException {
        if (hasSpouse)

            /*a profile cannot have more than 1 spouse */
            throw new Exceptions.NoAvailableException(Exceptions.NO_AVAILABLE_EXCEPTION);
        else {

            /*add spouse */
            this.spouse.add(spouse);
            hasSpouse = true;
        }
    }


    /*public boolean hasSpouse() {
        return hasSpouse;
    }
    */
}
