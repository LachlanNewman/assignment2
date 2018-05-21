package config;

public class Exceptions {

    public static final String NOT_TO_BE_FRIENDS_EXCEPTION = "Age difference is to great";
    public static final String NOT_TO_BE_COLLEAGUES_EXCEPTION = "Not to be colllegaues";
    public static final String NOT_TO_BE_CLASSMATE_EXCEPTION = "Cannot be classmate";
    public static final String NOT_TO_BE_COUPLED_EXCEPTION = "Cannot be coupled";
    public static final String NO_PARENT_EXCEPTION = "Cannot be parent";
    public static final String NO_AVAILABLE_EXCEPTION = "Not available";
    public static final String NO_SUCH_AGE_EXCEPTION = "Invalid age";
    public static final String TOO_YOUNG_EXCEPTION = "Too Youg";


    public static class TooYoungException extends Throwable {
        public TooYoungException(String e) {
            super(e);
        }
    }

    public static class NotToBeFriendsException extends Exception {
        public NotToBeFriendsException(String e) {
            super(e);
        }
    }

    public static class NoParentException extends Throwable {
        public NoParentException(String e) {
            super(e);
        }
    }

    public static class NoAvailableException extends Throwable {
        public NoAvailableException(String e) {
            super(e);
        }
    }

    public static class NotToBeCoupledException extends Throwable {
        public NotToBeCoupledException(String e) {
            super(e);
        }
    }

    public static class NoSuchAgeException extends Throwable {
        public NoSuchAgeException(String e) {
            super(e);
        }
    }

    public static class NotToBeColleaguesException extends Throwable {
        public NotToBeColleaguesException(String e) {
            super(e);
        }
    }

    public static class NotToBeClassmatesException extends Throwable {
        public NotToBeClassmatesException(String e) {
            super(e);
        }
    }
}
