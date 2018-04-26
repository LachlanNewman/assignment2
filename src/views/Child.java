package views;

import java.util.ArrayList;

public class Child extends Profile {

    private ArrayList<Profile> classMates;


    public Child(String id, String firstName, String lastName, String photoUrl, String status, Gender gender, int age, State state) {
        super(id,firstName, lastName, photoUrl, status, gender, age, state);
        classMates = new ArrayList<>();
    }

    @Override
    public void addFriend(Profile friend) {
        if (Math.abs(this.getAge() - friend.getAge()) > 3) {
            //throw ageField difference error
        } else if (this.getLastName() == friend.getLastName()) {
            //throw sameFamily Error
        } else {
            super.addFriend(friend);
        }
    }

    public void addClassMate(Profile classMate) {
        if (classMate instanceof Adult || classMate instanceof Child) {
            classMates.add(classMate);
        }
        //else throw error
    }
}
