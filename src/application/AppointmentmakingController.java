package application;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

public class AppointmentmakingController implements BaseLayoutInjectable {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private DatePicker dateNaissancePicker;
    @FXML private TextField adresseField;
    @FXML private TextField emailField;
    @FXML private RadioButton hommeRadio;
    @FXML private RadioButton femmeRadio;
    @FXML private DatePicker rendezVousDatePicker;

    @FXML private Label nomWarningLabel;
    @FXML private Label prenomWarningLabel;
    @FXML private Label adresseWarningLabel;
    @FXML private Label emailWarningLabel;
    @FXML private Label DdnWarningLabel;
    @FXML private Label DdRWarningLabel;
    @FXML private Label genderWarningLabel;
    @FXML private Label timeError;

    @FXML private Spinner<LocalTime> startTimeSpinner;
    @FXML private Spinner<LocalTime> endTimeSpinner;

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    @FXML private ToggleGroup genderGroup;
    private BaseLayoutController baseLayoutController;

    @FXML
    public void initialize() {
        setupTimeSpinners();
        genderGroup = new ToggleGroup(); // Create the ToggleGroup manually
        hommeRadio.setToggleGroup(genderGroup);
        femmeRadio.setToggleGroup(genderGroup);
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
                        if (string == null || string.trim().isEmpty()) {
                            return getValue();
                        }
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
    private void handleEnregistrerButtonAction(ActionEvent event) {
        boolean valid = true;

        // Clear warnings and hide them
        nomWarningLabel.setText("");
        nomWarningLabel.setVisible(false);

        prenomWarningLabel.setText("");
        prenomWarningLabel.setVisible(false);

        adresseWarningLabel.setText("");
        adresseWarningLabel.setVisible(false);

        emailWarningLabel.setText("");
        emailWarningLabel.setVisible(false);

        DdnWarningLabel.setText("");
        DdnWarningLabel.setVisible(false);

        DdRWarningLabel.setText("");
        DdRWarningLabel.setVisible(false);

        genderWarningLabel.setText("");
        genderWarningLabel.setVisible(false);

        timeError.setText("");
        timeError.setVisible(false);

        // Validate each field and show relevant labels
        if (nomField.getText().trim().isEmpty()) {
            nomWarningLabel.setText("Veuillez entrer le nom.");
            nomWarningLabel.setVisible(true);
            valid = false;
        }

        if (prenomField.getText().trim().isEmpty()) {
            prenomWarningLabel.setText("Veuillez entrer le prénom.");
            prenomWarningLabel.setVisible(true);
            valid = false;
        }

        if (adresseField.getText().trim().isEmpty()) {
            adresseWarningLabel.setText("Veuillez entrer l'adresse.");
            adresseWarningLabel.setVisible(true);
            valid = false;
        }

        String email = emailField.getText().trim();
        if (email.isEmpty()) {
            emailWarningLabel.setText("Veuillez entrer l'email.");
            emailWarningLabel.setVisible(true);
            valid = false;
        } else if (!isValidEmail(email)) {
            emailWarningLabel.setText("Email invalide.");
            emailWarningLabel.setVisible(true);
            valid = false;
        }

        if (dateNaissancePicker.getValue() == null) {
            DdnWarningLabel.setText("Veuillez choisir une date.");
            DdnWarningLabel.setVisible(true);
            valid = false;
        }

        if (rendezVousDatePicker.getValue() == null) {
            DdRWarningLabel.setText("Veuillez choisir une date.");
            DdRWarningLabel.setVisible(true);
            valid = false;
        }

        if (genderGroup.getSelectedToggle() == null) {
            genderWarningLabel.setText("Veuillez sélectionner un genre.");
            genderWarningLabel.setVisible(true);
            valid = false;
        }

        LocalTime startTime = startTimeSpinner.getValue();
        LocalTime endTime = endTimeSpinner.getValue();
        if (startTime == null || endTime == null || endTime.isBefore(startTime)) {
            timeError.setText("Plage horaire invalide.");
            timeError.setVisible(true);
            valid = false;
        }

        if (!valid) return;

        processAppointmentSubmission();
        clearForm();
    }


    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    private void clearForm() {
        nomField.clear();
        prenomField.clear();
        adresseField.clear();
        emailField.clear();
        dateNaissancePicker.setValue(null);
        rendezVousDatePicker.setValue(null);
        genderGroup.selectToggle(null);
        startTimeSpinner.getValueFactory().setValue(LocalTime.of(9, 0));
        endTimeSpinner.getValueFactory().setValue(LocalTime.of(10, 0));
    }

    private void processAppointmentSubmission() {
        // Print user inputs (for debug or logging purposes)
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String adresse = adresseField.getText();
        String email = emailField.getText();
        String sexe = hommeRadio.isSelected() ? "Homme" : "Femme";
        LocalDate dateNaissance = dateNaissancePicker.getValue();
        LocalDate dateRdv = rendezVousDatePicker.getValue();
        LocalTime startTime = startTimeSpinner.getValue();
        LocalTime endTime = endTimeSpinner.getValue();

        System.out.println("Nom: " + nom);
        System.out.println("Prénom: " + prenom);
        System.out.println("Adresse: " + adresse);
        System.out.println("Email: " + email);
        System.out.println("Sexe: " + sexe);
        System.out.println("Date de Naissance: " + dateNaissance);
        System.out.println("Date de Rendez-vous: " + dateRdv);
        System.out.println("Heure de début: " + timeFormatter.format(startTime));
        System.out.println("Heure de fin: " + timeFormatter.format(endTime));

        // Save to database
        Rendezvous rdv = new Rendezvous(nom, prenom, adresse, email, sexe, dateNaissance, dateRdv, startTime, endTime);

 // You'll need to implement this
    }


    public void setBaseLayoutController(BaseLayoutController baseLayoutController) {
        this.baseLayoutController = baseLayoutController;
    }

    @FXML
    private void handleAnnulerButtonAction(ActionEvent event) {
        if (baseLayoutController != null) {
            baseLayoutController.updateActiveMenu(baseLayoutController.accueilText);
            baseLayoutController.loadPageWithController("Sample.fxml");
        }
    }
    public void populateForm(Rendezvous rdv) {
        nomField.setText(rdv.getNom());
        prenomField.setText(rdv.getPrenom());
        adresseField.setText(rdv.getAdresse());
        emailField.setText(rdv.getEmail());

        if ("Homme".equalsIgnoreCase(rdv.getSexe())) {
            hommeRadio.setSelected(true);
        } else if ("Femme".equalsIgnoreCase(rdv.getSexe())) {
            femmeRadio.setSelected(true);
        }

        dateNaissancePicker.setValue(rdv.getDateNaissance());
        rendezVousDatePicker.setValue(rdv.getDateRendezVous());
        startTimeSpinner.getValueFactory().setValue(rdv.getStartTime());
        endTimeSpinner.getValueFactory().setValue(rdv.getEndTime());
    }
    
}

