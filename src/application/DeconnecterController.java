package application;

import java.io.IOException;
import java.lang.classfile.components.ClassPrinter.Node;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class DeconnecterController {
	 @FXML
	  private AnchorPane mainPane;
	 @FXML
	 private Button annulerButton;

	 @FXML
	 private void handleAnnuler(ActionEvent event) {
	     try {
	         FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml")); // or whatever scene you want
	         Parent root = loader.load();

	         // Get the stage from the button itself
	         Stage stage = (Stage) annulerButton.getScene().getWindow();
	         stage.setScene(new Scene(root));
	         stage.show();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	 }


	  @FXML
	    private void goToAccueil(MouseEvent event) {
	        loadPage("Sample.fxml");  // Load the Accueil content only into mainPane
	    }

	    @FXML
	    private void goToAjouter(MouseEvent event) {
	        loadPage("alreadyexists.fxml");  // Load the Add Appointment content only into mainPane
	    }

	    @FXML
	    private void goToListe(MouseEvent event) {
	        loadPage("AppointmentList.fxml");  // Load the List Appointments content only into mainPane
	    }

	    @FXML
	    private void goToDeconnecter(MouseEvent event) {
	    	  loadPage("Deconnecter.fxml"); 
	    }

	    // Utility method to load different pages into the mainPane
	    private void loadPage(String fxmlFile) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
	            Parent content = loader.load();
	            mainPane.getChildren().setAll(content);  // Only replace the content inside mainPane
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
