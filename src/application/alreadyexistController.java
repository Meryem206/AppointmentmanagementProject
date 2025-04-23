package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class alreadyexistController implements BaseLayoutInjectable {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField Searchpatient;

    @FXML
    private ListView<String> PatientList;

    @FXML
    private Spinner<LocalTime> startTimeSpinner;

    @FXML
    private Spinner<LocalTime> endTimeSpinner;

    @FXML
    private Button addPatientButton;
    @FXML
    private DatePicker datePicker;

    @FXML
    private Text timeError;
    @FXML
    private Text dateError;
    @FXML
    private Text patientError;

    private ObservableList<String> patientNames = FXCollections.observableArrayList();
    private FilteredList<String> filteredNames;

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    private BaseLayoutController baseLayoutController;

    @Override
    public void setBaseLayoutController(BaseLayoutController baseLayoutController) {
        this.baseLayoutController = baseLayoutController;
    }

    @FXML
    public void initialize() {
        setupPatientSearch();
        setupTimeSpinners();
    }
    @FXML
    private void handleAjouterunpatientClick(ActionEvent event) {
        if (baseLayoutController != null) {
            baseLayoutController.loadPageWithController("appointmentmaking.fxml");
        } else {
            System.out.println("BaseLayoutController is null in alreadyexistController");
        }
    }
    
    private void fetchPatientsFromDatabase() {
        // Placeholder: simulate fetching from DB
        patientNames.clear(); 
        patientNames.addAll("Alice", "Bob", "Charlie", "David", "Eve");

        // When you're ready to use JDBC, replace the above with something like:
        /*
        String query = "SELECT nom FROM patients";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String nom = rs.getString("nom");
                patientNames.add(nom);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
    }

    private void setupPatientSearch() {
        // TODO: Replace this with a real database fetch
        fetchPatientsFromDatabase();

        filteredNames = new FilteredList<>(patientNames, p -> true);
        PatientList.setItems(filteredNames);

        Searchpatient.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isEmpty()) {
                PatientList.setVisible(false);
                filteredNames.setPredicate(p -> false);
            } else {
                filteredNames.setPredicate(name -> name.toLowerCase().contains(newVal.toLowerCase()));
                if (filteredNames.isEmpty()) {
                    PatientList.setItems(FXCollections.observableArrayList("Patient introuvable"));
                    PatientList.setMouseTransparent(true);
                    PatientList.setFocusTraversable(false);
                } else {
                    PatientList.setItems(filteredNames);
                    PatientList.setMouseTransparent(false);
                    PatientList.setFocusTraversable(true);
                }
                PatientList.setVisible(true);
            }
        });

        PatientList.setOnMouseClicked(event -> {
            String selected = PatientList.getSelectionModel().getSelectedItem();
            if (selected != null && !selected.equals("Patient introuvable")) {
                Searchpatient.setText(selected);
                PatientList.setVisible(false);
            }
        });
    }



    private void setupTimeSpinners() {
        SpinnerValueFactory<LocalTime> startFactory = createTimeFactory(LocalTime.of(9, 0));
        SpinnerValueFactory<LocalTime> endFactory = createTimeFactory(LocalTime.of(10, 0));

        startTimeSpinner.setValueFactory(startFactory);
        endTimeSpinner.setValueFactory(endFactory);
    }

    private SpinnerValueFactory<LocalTime> createTimeFactory(LocalTime defaultTime) {
        return new SpinnerValueFactory<LocalTime>() {
            {
                setConverter(new StringConverter<LocalTime>() {
                    @Override
                    public String toString(LocalTime time) {
                        return time != null ? time.format(timeFormatter) : "";
                    }

                    @Override
                    public LocalTime fromString(String string) {
                        return LocalTime.parse(string, timeFormatter);
                    }
                });
                setValue(defaultTime);
            }

            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps));
            }

            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps));
            }
        };
    }

  
    @FXML
    private void handleAjouterClick(ActionEvent event) {
        boolean valid = true;

        String patientName = Searchpatient.getText();
        LocalTime startTime = startTimeSpinner.getValue();
        LocalTime endTime = endTimeSpinner.getValue();
        LocalDate appointmentDate = datePicker.getValue();

        // Hide previous error messages
        patientError.setVisible(false);
        timeError.setVisible(false);
        dateError.setVisible(false);

        // Validate patient name
        if (patientName == null || patientName.trim().isEmpty()) {
            patientError.setVisible(true);  // Show error under the patient name field
            valid = false;
        }

        // Validate time range
        if (startTime == null || endTime == null || endTime.isBefore(startTime)) {
            timeError.setVisible(true);  // Show error under the time fields
            valid = false;
        }

        // Validate appointment date
        if (appointmentDate == null) {
            dateError.setVisible(true);  // Show error under the date field
            valid = false;
        }

        // Stop if validation failed
        if (!valid) return;

        // Clear fields
        Searchpatient.clear();
        startTimeSpinner.getValueFactory().setValue(LocalTime.of(9, 0));
        endTimeSpinner.getValueFactory().setValue(LocalTime.of(10, 0));
        PatientList.setVisible(false);
        datePicker.setValue(null);

        // Refresh the TableView or whatever component you're using to show the saved appointments
    }


        // Refresh the TableView or whatever component you're using to show the saved appointments
    


    @FXML
    private void handleAnnulerClick(ActionEvent event) {
        if (baseLayoutController != null) {
            baseLayoutController.updateActiveMenu(baseLayoutController.accueilText);
            baseLayoutController.loadPageWithController("Sample.fxml");
        } else {
            System.out.println("BaseLayoutController is null in alreadyexistController (Annuler)");
        }
    }

    private void saveAppointment(String patientName, LocalTime startTime, LocalTime endTime) {
        // This is a placeholder. You'd usually send this to your DB.
        System.out.println("Saving appointment:");
        System.out.println("Patient: " + patientName);
        System.out.println("Heure de début: " + startTime);
        System.out.println("Heure de fin: " + endTime);
    }
}
