package sample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BicycleTripTest {

    @BeforeEach
    public void beforeTest(){
        System.out.println("Test incoming");
    }

    @Test
    void nullDescriptionTest() {
        BicycleTrip b = new BicycleTrip(null, "details", LocalDate.now(), ".\\someURL");
        assertNull(b.getShortDescription());
    }

    @Test
    void veryLongDescriptionTest() {
        BicycleTrip b = new BicycleTrip("thisIsVeryLongShortDescriptionThatProbablyWontFitInDatabaseButHereItsTotallyOK", "details", LocalDate.now(), ".\\someURL");
        assertEquals("thisIsVeryLongShortDescriptionThatProbablyWontFitInDatabaseButHereItsTotallyOK", b.getShortDescription());
    }

}