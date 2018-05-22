import config.Database;
import config.Navigation;
import javafx.application.Application;
import javafx.stage.Stage;

public class Mininet extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if(Database.tryConnnection()) {
            Navigation navigation = new Navigation();
            navigation.setStage(primaryStage);
            navigation.navToMain();
        }
        else {
            System.out.println("could not connect the database");
            System.exit(0);
        }
    }

}
