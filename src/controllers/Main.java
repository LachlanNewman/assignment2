package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.State;

import java.sql.*;

public class Main extends Application {

    private static Stage primaryStage; // **Declare static Stage**
    private static Connection connection;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        tryConnection();
        setPrimaryStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        this.primaryStage.setTitle("Hello World");
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return Main.primaryStage;
    }

    private void setPrimaryStage(Stage stage) {
        Main.primaryStage = stage;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void tryConnection() {
        String url = "jdbc:sqlite:C://sqlite/socialnetwork.db";
        connection = null;
        try {
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateConnection(String update) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
