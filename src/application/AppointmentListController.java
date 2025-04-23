package application;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class AppointmentListController implements BaseLayoutInjectable {

	@FXML
	private TableView<Rendezvous> rendezVousTable; // make sure RendezVous is your model class

    private BaseLayoutController baseLayoutController;

    @Override
    public void setBaseLayoutController(BaseLayoutController baseLayoutController) {
        this.baseLayoutController = baseLayoutController;
    }
    public Rendezvous getSelectedRendezVous() {
        return rendezVousTable.getSelectionModel().getSelectedItem();
    }
    @FXML
    private void initialize() {
        // Assuming you have a method to fetch Rendezvous objects
        ObservableList<Rendezvous> rendezvousList = FXCollections.observableArrayList(fetchRendezvousList());
        rendezVousTable.setItems(rendezvousList);
    }
    
    //now you just need to replace the mock data in fetchRendezvousList() with real data from your database
    private ObservableList<Rendezvous> fetchRendezvousList() {
        // For now, returning a mock list of Rendezvous objects
        ObservableList<Rendezvous> rendezvousList = FXCollections.observableArrayList();

        // Example of creating mock Rendezvous objects
        rendezvousList.add(new Rendezvous("John", "Doe", "123 Street", "john.doe@example.com", "Male", 
                                          LocalDate.of(1990, 1, 1), LocalDate.of(2025, 5, 1), LocalTime.of(10, 0),LocalTime.of(10, 9)));
        rendezvousList.add(new Rendezvous("Jane", "Doe", "456 Avenue", "jane.doe@example.com", "Female", 
                                          LocalDate.of(1985, 6, 15), LocalDate.of(2025, 5, 2), LocalTime.of(11, 30), LocalTime.of(11, 30)));

        return rendezvousList;
    }

    @FXML
    private void handleModifierButton(ActionEvent event) {
        Rendezvous selectedRdv = rendezVousTable.getSelectionModel().getSelectedItem();

        if (selectedRdv != null && baseLayoutController != null) {
            baseLayoutController.updateActiveMenu(baseLayoutController.ajouterText);
            baseLayoutController.loadPageWithControllerForModifier("appointmentMaking.fxml", selectedRdv);
        } else {
            System.out.println("Rendezvous non sélectionné ou BaseLayoutController est null");
        }
    }
    
    @FXML
    private void handleSupprimerButton() {
        Rendezvous selectedAppointment = rendezVousTable.getSelectionModel().getSelectedItem();

        if (selectedAppointment != null) {
            // Confirm the deletion if needed (optional)
          
        } else {
            // Handle case where no appointment is selected
            System.out.println("No appointment selected.");
        }
    }


    }

    


