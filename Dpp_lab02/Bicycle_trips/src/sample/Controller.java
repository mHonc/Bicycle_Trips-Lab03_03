package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;


public class Controller {

    private List<BicycleTrip> bicycleTrips;
    private File loadedFile;

    @FXML
    private ListView<BicycleTrip> bicycleTripListView;

    @FXML
    private TextArea itemDetailsTextArea;
    @FXML
    private TextField titleInputTextField;
    @FXML
    private TextArea detailsInputTextArea;
    @FXML
    private DatePicker dateInputDatePicker;
    @FXML
    private ImageView imageInputImageView;
    @FXML
    private ImageView imageDetailsImageView;
    @FXML
    private TextField titleEditTextField;
    @FXML
    private TextArea detailsEditTextArea;
    @FXML
    private DatePicker dateEditDatePicker;
    @FXML
    private ImageView imageEditImageView;

    private Database db = new Database("database_trips"); // pobieramy baze z pliku lub tworzymy nowy plik z baza
    private String oldName;

    public Controller() {
    }

    public void initialize() {
        //sluzy do zresetowania bazy, wtedy trzeba odkomentowac
        defaultResetDatabse();

        //zamiast dodawania wpisow do listy, najpierw dodaje je do bazy, a pozniej pobiera gotowa liste z bazy

        refreshTripList();
    }

    public void defaultResetDatabse() {
        db.dropTable();     // --------- UWAGA KASUJE BAZE!!! ---------
        db.createTable();   // stworzenie tabeli TRIPS, wymagane tylko raz

        db.addEntry(new BicycleTrip("Krakow", "Opis wycieczki rowerowej w Krakowie",
                LocalDate.of(2020, Month.APRIL, 25), new File("img/Krakow.jpg").getAbsolutePath()));
        db.addEntry(new BicycleTrip("Barcelona", "Opis wycieczki rowerowej w Barcelonie",
                LocalDate.of(2020, Month.MAY, 23), new File("img/Barcelona.jpg").getAbsolutePath()));
        db.addEntry(new BicycleTrip("Praga", "Opis wycieczki rowerowej w Pradze",
                LocalDate.of(2020, Month.APRIL, 22), new File("img/Praga.jpg").getAbsolutePath()));
        db.addEntry(new BicycleTrip("Wroclaw", "Opis wycieczki rowerowej we Wroclawiu",
                LocalDate.of(2020, Month.MARCH, 23), new File("img/Wroclaw.jpg").getAbsolutePath()));
        db.addEntry(new BicycleTrip("Sopot", "Opis wycieczki rowerowej w Sopocie",
                LocalDate.of(2020, Month.APRIL,20), new File("img/Sopot.jpg").getAbsolutePath()));

        refreshTripList();
    }

    public void refreshTripList() {
        bicycleTrips = db.getEntriesFromDB();
        bicycleTripListView.getItems().setAll(bicycleTrips);
        bicycleTripListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    public void handleClickListView() {
        BicycleTrip item = bicycleTripListView.getSelectionModel().getSelectedItem();
        StringBuilder sb = new StringBuilder(item.getDetails());

        sb.append("\n\n\n\n");
        sb.append("Due: ");
        sb.append(item.getTripData().toString());

        Image tripImage = null;
        try {
            loadedFile = new File(item.getImageURL());
             tripImage = new Image(new FileInputStream(loadedFile));
        } catch (FileNotFoundException e) {
            loadedFile = null;
            e.printStackTrace();
        }

        itemDetailsTextArea.setText(sb.toString());
        imageDetailsImageView.setImage(tripImage);

        titleEditTextField.setText(item.getShortDescription());
        oldName = item.getShortDescription();
        detailsEditTextArea.setText(item.getDetails());
        dateEditDatePicker.setValue(item.getTripData());
        imageEditImageView.setImage(tripImage);
    }

    @FXML
    public void handleClickSubmitInputButton() throws FileNotFoundException {
        String shortDescription = titleInputTextField.getText();
        String details = detailsInputTextArea.getText();
        LocalDate date = dateInputDatePicker.getValue();

        if(shortDescription == null || shortDescription == "" || details == null || details == "" ||
                date == null || date.toString() == "" || loadedFile == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("All fields must be filled!");
            alert.showAndWait();
            return;
        }

        BicycleTrip newTrip = new BicycleTrip(shortDescription, details, date, loadedFile.getAbsolutePath());
        db.addEntry(newTrip); //wpis dodany do bazy
        bicycleTrips = db.getEntriesFromDB(); //pobieramy zaktualizowana baze
        bicycleTripListView.getItems().setAll(bicycleTrips);
        bicycleTripListView.refresh();

        titleInputTextField.setText(null);
        detailsInputTextArea.setText(null);
        dateInputDatePicker.setValue(null);
        imageInputImageView.setImage(null);

    }

    @FXML
    public void handleClickSubmitEditButton() throws FileNotFoundException {
        String shortDescription = titleEditTextField.getText();
        String details = detailsEditTextArea.getText();
        LocalDate date = dateEditDatePicker.getValue();

        Image image = imageEditImageView.getImage();
        if(shortDescription == null || shortDescription == "" || details == null || details == "" ||
                date == null || date.toString() == "" || loadedFile == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("All fields must be filled!");
            alert.showAndWait();
            return;
        }

        int tripIndex = bicycleTripListView.getSelectionModel().getSelectedIndex();

        db.editEntry(oldName, shortDescription, details, date, loadedFile.getAbsolutePath());

        bicycleTrips = db.getEntriesFromDB();
        bicycleTripListView.getItems().setAll(bicycleTrips);
        bicycleTripListView.getSelectionModel().select(tripIndex);
    }

    @FXML
    private void handleClickDeleteButton() throws FileNotFoundException {
        String name = titleEditTextField.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Ostrzeżenie!");
        alert.setContentText("Czy na pewno chcesz usunąć?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            db.deleteEntry(name);
            bicycleTrips = db.getEntriesFromDB();
            bicycleTripListView.getItems().setAll(bicycleTrips);
            bicycleTripListView.refresh();
        }

    }

    @FXML
    private File handleClickLoadButton() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórz Plik");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        loadedFile = fileChooser.showOpenDialog(null);
        return loadedFile;
    }

    @FXML
    public void handleInputClickLoadButton() {
        try {
            imageInputImageView.setImage(new Image(new FileInputStream(handleClickLoadButton())));
        } catch (FileNotFoundException e) {
            loadedFile = null;
            e.printStackTrace();
        }
    }

    @FXML
    public void handleEditClickLoadButton() {
        handleClickLoadButton();
        try {
            imageEditImageView.setImage(new Image(new FileInputStream(loadedFile)));
        } catch (FileNotFoundException e) {
            loadedFile = null;
            e.printStackTrace();
        }
    }
}
