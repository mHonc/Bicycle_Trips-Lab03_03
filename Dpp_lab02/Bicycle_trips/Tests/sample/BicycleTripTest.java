package sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

public class BicycleTripTest {
    public static String longText = "LoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsum";
    public static String shortText = "LoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsumLoremIpsum";
    public static String url = "C:/Users/someuser/work/DPPLab3/Lab03_03/Dpp_lab02/Bicycle_trips/img";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void constructorTestGood() {
        LocalDate testDate = LocalDate.of(2020, Month.APRIL, 25);
        BicycleTrip trip = new BicycleTrip(shortText, longText, testDate, url);
        assertEquals(trip.getShortDescription(), shortText);
        assertEquals(trip.getDetails(), longText);
        assertEquals(trip.getTripData(), testDate);
        assertEquals(trip.getImageURL(), url);
    }

    @Test
    public void constructorTestBad() {
        LocalDate testDate = LocalDate.of(2020, Month.APRIL, 25);
        BicycleTrip trip = new BicycleTrip(shortText, longText, testDate, url);
        assertNotEquals(trip.getShortDescription(), longText);
        assertNotEquals(trip.getDetails(), shortText);
        assertNotEquals(trip.getTripData(), LocalDate.of(2020, Month.APRIL, 27));
        assertEquals(trip.getImageURL(), url);
    }

    @Test
    public void settersTest() {
        LocalDate testDate = LocalDate.of(2020, Month.APRIL, 25);
        BicycleTrip trip = new BicycleTrip(shortText, longText, testDate, url);
        trip.setTripData(LocalDate.of(2021, Month.MAY, 28));
        trip.setDetails("Some details");
        trip.setShortDescription("Some short description");
        trip.setImageURL("/home/account/files");

        assertEquals(trip.getShortDescription(), "Some short description");
        assertEquals(trip.getDetails(), "Some details");
        assertEquals(trip.getTripData(), LocalDate.of(2021, Month.MAY, 28));
        assertEquals(trip.getImageURL(), "/home/account/files");
    }
}