package sample;

import javafx.scene.image.Image;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private Connection c = null;
    private Statement stmt = null;
    private String query = "";

    public Database(String name){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + name + ".db");
            c.setAutoCommit(false);
            System.out.println("Database opened succesfully!\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createTable(){
        try {
            stmt = c.createStatement();
            query = "CREATE TABLE TRIPS" +
                    "(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "DESC TEXT NOT NULL," +
                    "DETAILS TEXT NOT NULL," +
                    "DATE TEXT NOT NULL," +
                    "IMG_URL TEXT NOT NULL);";
            stmt.executeUpdate(query);
            stmt.close();
            c.commit();
            System.out.println("Table created!\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropTable(){
        try {
            stmt = c.createStatement();
            query = "DROP TABLE TRIPS;";
            stmt.executeUpdate(query);
            stmt.close();
            c.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addEntry(BicycleTrip trip){
        try {
            stmt = c.createStatement();
            query = "INSERT INTO TRIPS (DESC, DETAILS, DATE, IMG_URL)" +
                    "VALUES ('" + trip.getShortDescription() + "', '" +
                    trip.getDetails() + "', '" + trip.getTripData().toString() + "', " +
                    "'" + trip.getImageURL() + "');";
            stmt.executeUpdate(query);
            stmt.close();
            c.commit();
            System.out.println("Entry added successfully!\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<BicycleTrip> getEntriesFromDB(){
        List<BicycleTrip> result = new ArrayList<BicycleTrip>();
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM TRIPS;");
            while (rs.next()){
                result.add(new BicycleTrip(rs.getString("desc"), rs.getString("details"), LocalDate.parse(rs.getString("date")), rs.getString("img_url")));
            }
            rs.close();
            stmt.close();
            System.out.println("Entries loaded!\n");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public void editEntry(String oldName, String desc, String details, LocalDate date, String imageURL){
        try {
            stmt = c.createStatement();
            query = "UPDATE TRIPS SET (DESC, DETAILS, DATE, IMG_URL) = ('" + desc + "', '" + details + "', '" + date.toString() + "', '" + imageURL + "') WHERE DESC='" + oldName + "';";
            stmt.executeUpdate(query);
            stmt.close();
            c.commit();
            System.out.println("Edited successfully!\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteEntry(String name){
        try {
            stmt = c.createStatement();
            query = "DELETE FROM TRIPS WHERE DESC='" + name + "';";
            stmt.executeUpdate(query);
            stmt.close();
            c.commit();
            System.out.println("Entry deleted!\n");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
