package views;

import java.util.ArrayList;

public class Adult extends Profile {

    private ArrayList<Profile> colleagues;
    private ArrayList<Profile> classMates;

    public Adult(String firstName, String lastName, String photoUrl, String status, Gender gender, int age, State state) {
        super(firstName, lastName, photoUrl, status, gender, age, state);
        this.colleagues = new ArrayList<>();
        classMates = new ArrayList<>();
    }

    public void addColleage(Profile colleage) {
        if (colleage instanceof Adult) {
            colleagues.add(colleage);
        }
        //else throw error
    }

    public void addClassMate(Profile classMate) {
        if (classMate instanceof Adult || classMate instanceof Child) {
            classMates.add(classMate);
        }
        //else throw error
    }

    public ArrayList<Profile> getColleagues() {
        return colleagues;
    }

    public ArrayList<Profile> getClassMates() {
        return classMates;
    }
}
