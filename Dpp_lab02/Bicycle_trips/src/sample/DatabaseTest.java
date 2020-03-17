package sample;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    Database db = new Database("testDB");


    @Test
    void createTableFirstTime() {
        assertTrue(db.createTable());
    }

    @Test
    void createTableOneMoreTime(){
        db.createTable();
        assertFalse(db.createTable());
    }

    @Test
    void dropExistingTable() {
        assertTrue(db.dropTable());
    }

    @Test
    void dropTableThatDoesntExist(){
        db.dropTable();
        assertFalse(db.dropTable());
    }

    @Test
    void addEntry() {
        db.createTable();
        assertTrue(db.addEntry(new BicycleTrip("test", "details", LocalDate.now(), ".\\someURL")));
    }

    @Test
    void deleteEntry() {
        assertTrue(db.deleteEntry("test"));
    }
}