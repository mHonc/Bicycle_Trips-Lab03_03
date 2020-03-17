package sample;

import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;

public class BicycleTrip {

    private String shortDescription;
    private String details;
    private LocalDate tripData;
    private String imageURL;

    public BicycleTrip(String shortDescription, String details, LocalDate tripData, String imageURL) {
        this.shortDescription = shortDescription;
        this.details = details;
        this.tripData = tripData;
        this.imageURL = imageURL;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDetails() {return details; }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getTripData() {
        return tripData;
    }

    public void setTripData(LocalDate tripData) {
        this.tripData = tripData;
    }

    public String getImageURL(){ return imageURL; }

    public void setImageURL(String imageURL){ this.imageURL = imageURL;}

    @Override
    public String toString() {
        return shortDescription;
    }
}