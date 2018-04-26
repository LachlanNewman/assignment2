package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.State;

import java.sql.*;

public class Main extends Application {
    private static final String INSERT_SQL = "INSERT INTO PROFILES(firstName, lastName, age, status, gender, state ) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE PROFILES SET firstName = ? , lastName = ? , age = ? , status = ? WHERE id = ?;";

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
        this.primaryStage.setScene(new Scene(root,600,400));
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

    public static void updateConnection(int id, String firstName, String lastName, int age, String status) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(UPDATE_SQL);
            statement.setString(1,firstName);
            statement.setString(2,lastName);
            statement.setInt(3,age);
            statement.setString(4,status);
            statement.setInt(5,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insertConnection(String firstName, String lastName, int age, String status, String gender, String state) {
            PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(INSERT_SQL);
            statement.setString(1,firstName);
            statement.setString(2,lastName);
            statement.setInt(3,age);
            statement.setString(4,status);
            statement.setString(5,gender);
            statement.setString(6,state);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
