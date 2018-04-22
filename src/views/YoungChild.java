package views;

public class YoungChild extends Profile {


    public YoungChild(String id, String firstName, String lastName, String photoUrl, String status, Gender gender, int age, State state) {
        super(id,firstName, lastName, photoUrl, status, gender, age, state);
    }

    @Override
    public void addFriend(Profile friend) {
        //throw agae error
    }
}
