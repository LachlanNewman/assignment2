package views;

public enum  Gender {
    MALE("M"),
    FEMALE("F"),
    UNKNOWN("");

    private String gender;

    Gender(String gender){
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}

