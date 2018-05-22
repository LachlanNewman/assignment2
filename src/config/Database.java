package config;

import views.Network;
import views.Profile;
import views.enums.Gender;
import views.enums.Relationship;
import views.enums.State;

import java.io.*;
import java.sql.*;


public class Database {
    private static final String DB_CONNECTION = "jdbc:sqlite:socialnetwork";
    private static final String DB_PROFILES = "SELECT * FROM PROFILE";
    private static final String DB_RELATIONSHIPS = "SELECT * FROM RELATIONSHIP";
    private static final String PROFILES_INSERT_SQL = "INSERT INTO PROFILE(name, photoUrl, status, gender, age, state ) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String PROFILES_UPDATE_SQL = "UPDATE PROFILE SET photoUrl = ? , status = ? , gender = ? , age = ?, state = ? WHERE name = ?;";
    private static final String PROFILES_DELETE_SQL_1 = "DELETE FROM RELATIONSHIP WHERE nameB = ? OR nameA = ?";
    public static final String  PROFILES_DELETE_SQL_2 = "DELETE FROM PROFILE WHERE name = ?";
    private static final String RELATIONSHIPS_INSERT_SQL = "INSERT INTO RELATIONSHIP(nameA,nameB,relationship) VALUES(?,?,?)";
    private static final String RELATIONSHIP_DELETE_SQL = "DELETE FROM RELATIONSHIP WHERE RELATIONSHIP.nameA != nameB AND (nameA = ? OR nameB = ?) AND (nameA = ? OR nameB = ?) AND relationship = ?";
    private static Connection connection;

    public static boolean tryConnnection(){
        connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DB_CONNECTION);
            connection.prepareStatement("DROP TABLE IF EXISTS RELATIONSHIP;").execute();
            connection.prepareStatement("DROP TABLE IF EXISTS PROFILE;").execute();
            connection.prepareStatement("create table RELATIONSHIP\n" +
                    "(\n" +
                    "  nameA        text not null\n" +
                    "    references PROFILE,\n" +
                    "  nameB        text not null\n" +
                    "    references PROFILE,\n" +
                    "  relationship text not null\n" +
                    "    references RELATIONSHIPTYPES,\n" +
                    "  primary key (nameA, nameB, relationship)\n" +
                    ");\n").execute();

            connection.prepareStatement("create table PROFILE\n" +
                    "(\n" +
                    "  name     text not null\n" +
                    "    primary key,\n" +
                    "  photoUrl text,\n" +
                    "  status   text,\n" +
                    "  gender   text not null,\n" +
                    "  age      int  not null,\n" +
                    "  state    text not null\n" +
                    ");\n").execute();

            readPeopleTextFileIntoDatabase();
            readRelationsTextFileIntoDatabase();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    private static void readRelationsTextFileIntoDatabase() {
        String fileName = "./resources/data/relations.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            String[] values = null;
            while ((line = bufferedReader.readLine()) != null) {
                values = line.split(", ");
                insertRelationShips(
                        values[0],//nameA
                        values[1],//nameB
                        Relationship.fromString(values[2])//status
                );
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

    }

    public static void readPeopleTextFileIntoDatabase(){
        String fileName = "./resources/data/people.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            String [] values = null;
            while((line = bufferedReader.readLine()) != null) {
                line = line.replace("\"","");
                values = line.split(", ");
                insertProfiles(
                        values[0],//name
                        values[1],//img
                        values[2],//status
                        values[3],//gender
                        Integer.parseInt(values[4]),//age
                        values[5]//state
                );
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }

    public static void getProfiles() throws SQLException {
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery(DB_PROFILES);
        while (results.next()) {
            Network.addProfile(
                    results.getString("name"),
                    results.getString("photoUrl"),
                    results.getString("status"),
                    Gender.valueOf(results.getString("gender")),
                    results.getInt("age"),
                    State.valueOf(results.getString("state"))
            );
        }
    }

    public static void getRelationships() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet results = statement.executeQuery(DB_RELATIONSHIPS);
        while (results.next()) {
            String nameA = results.getString("nameA");
            String nameB = results.getString("nameB");
            Relationship relationship = Relationship.fromString(results.getString("relationship"));
            try {
                Network.getNetwork().get(nameA).addRelationship(Network.getNetwork().get(nameB), relationship);
                Network.getNetwork().get(nameB).addRelationship(Network.getNetwork().get(nameA), relationship);
            } catch (Exceptions.TooYoungException e) {
                e.printStackTrace();
            } catch (Exceptions.NotToBeFriendsException e) {
                e.printStackTrace();
            } catch (Exceptions.NotToBeColleaguesException e) {
                e.printStackTrace();
            } catch (Exceptions.NoAvailableException e) {
                e.printStackTrace();
            } catch (Exceptions.NotToBeClassmatesException e) {
                e.printStackTrace();
            } catch (Exceptions.NotToBeCoupledException e) {
                e.printStackTrace();
            } catch (Exceptions.NoParentException e) {
                e.printStackTrace();
            } catch (Exceptions.NotCoupledException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateProfilesTable(Profile profile) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(PROFILES_UPDATE_SQL);
            statement.setString(1,profile.getPhotoUrl());
            statement.setString(2,profile.getStatus());
            statement.setString(3,profile.getGender().name());
            statement.setInt(4,profile.getAge());
            statement.setString(5,profile.getState().name());
            statement.setString(6,profile.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertProfiles(String name, String photoUrl, String status, String gender, int age, String state) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(PROFILES_INSERT_SQL);
            statement.setString(1,name);
            statement.setString(2,photoUrl);
            statement.setString(3,status);
            statement.setString(4,gender);
            statement.setInt(5,age);
            statement.setString(6,state);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertRelationShips(String nameA, String nameB, Relationship relationship) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(RELATIONSHIPS_INSERT_SQL);
            statement.setString(1, nameA);
            statement.setString(2, nameB);
            statement.setString(3,relationship.getRelationshipType());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProfile(Profile profile) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(PROFILES_DELETE_SQL_1);
            statement.setString(1,profile.getName());
            statement.setString(2,profile.getName());
            statement.executeUpdate();
            statement = connection.prepareStatement(PROFILES_DELETE_SQL_2);
            statement.setString(1,profile.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeRelationShip(Profile friend, Profile profile, Relationship relationShipType) {
        String relationship = relationShipType.getRelationshipType();
        PreparedStatement statement;
        try {
            statement=connection.prepareStatement(RELATIONSHIP_DELETE_SQL);
            statement.setString(1,profile.getName());
            statement.setString(2,profile.getName());
            statement.setString(3,friend.getName());
            statement.setString(4,friend.getName());
            statement.setString(5,relationship);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
