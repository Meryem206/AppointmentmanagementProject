package application;

import javafx.fxml.FXML;
import java.util.Deque;
import java.util.ArrayDeque;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class BaseLayoutController {
	@FXML
	private Text doctorNameText;

	// This simulates loading the name (replace with actual DB call later)
	private String mockFetchDoctorNameFromDB() {
	    // Imagine this data comes from the DB based on the logged-in user
	    return "Dr. John Smith";
	}
	
    @FXML
    public AnchorPane mainPane;

    @FXML
    public Text accueilText;

    @FXML
    public Text ajouterText;

    @FXML
    public Text listeText;

    @FXML
    public Text deconnecterText;

    private Deque<String> pageHistory = new ArrayDeque<>();
    private String currentPage;
    private String previousPage;

    @FXML
    public void initialize() {
    	String doctorName = mockFetchDoctorNameFromDB();
        doctorNameText.setText(doctorName);
        loadPage("Sample.fxml");
        updateActiveMenu(accueilText);
        loadSamplePage();
    }

    public void updateActiveMenu(Text activeText) {
        accueilText.setFill(Color.WHITE);
        ajouterText.setFill(Color.WHITE);
        listeText.setFill(Color.WHITE);
        deconnecterText.setFill(Color.WHITE);

        activeText.setFill(Color.web("#045745")); 
    }

    public void loadSamplePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            AnchorPane samplePage = loader.load();

            SampleController sampleController = loader.getController();
            sampleController.setBaseLayoutController(this);

            mainPane.getChildren().setAll(samplePage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent content = loader.load();

            Object controller = loader.getController();

            // Inject base layout controller if applicable
            if (controller instanceof BaseLayoutInjectable) {
                ((BaseLayoutInjectable) controller).setBaseLayoutController(this);
            }

            mainPane.getChildren().setAll(content);
            currentPage = fxmlFile;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPageWithControllerForModifier(String fxmlFile, Rendezvous rdv) {
        try {
            // Load the AppointmentMaking.fxml page
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent page = loader.load();
            Object controller = loader.getController();

            // Inject the base layout controller into the page if applicable
            if (controller instanceof BaseLayoutInjectable) {
                ((BaseLayoutInjectable) controller).setBaseLayoutController(this);
            }

            // Now, handle the Rendezvous (which is specific to the modifier page)
            if (controller instanceof AppointmentmakingController) {
                ((AppointmentmakingController) controller).populateForm(rdv);
            }

            // Set the page content
            mainPane.getChildren().setAll(page);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPageWithController(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent page = loader.load();
            Object controller = loader.getController();

            if (controller != null) {
                System.out.println("Loaded controller: " + controller.getClass().getSimpleName());
            } else {
                System.out.println("Controller is null for: " + fxmlFile);
            }

            if (controller instanceof BaseLayoutInjectable) {
                ((BaseLayoutInjectable) controller).setBaseLayoutController(this);
                System.out.println("Injecting BaseLayoutController into: " + controller.getClass().getSimpleName());
            }

            mainPane.getChildren().setAll(page);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void goToAlreadyExists(MouseEvent event) {
        updateActiveMenu(ajouterText);
        loadPage("alreadyexists.fxml");
    }

    @FXML
    private void goToAppointmentList(MouseEvent event) {
        updateActiveMenu(listeText);
        loadPage("AppointmentList.fxml");
    }

    @FXML
    private void goToAccueil(MouseEvent event) {
        updateActiveMenu(accueilText);
        loadPageWithController("Sample.fxml"); 
    }

    @FXML
    private void goToAjouter(MouseEvent event) {
        updateActiveMenu(ajouterText);
        loadPage("alreadyexists.fxml");
    }

    @FXML
    private void goToListe(MouseEvent event) {
        updateActiveMenu(listeText);
        loadPage("AppointmentList.fxml");
    }

    @FXML
    private void goToDeconnecter(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation de Déconnexion");
        alert.setHeaderText("Êtes-vous sûr de vouloir vous déconnecter ?");
        alert.setContentText("Cliquez sur OK pour revenir à l'écran de connexion.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Close current stage
            Stage currentStage = (Stage) mainPane.getScene().getWindow();
            currentStage.close();

            // Launch login form again
            Stage loginStage = new Stage();
            new LoginForm().start(loginStage);
        }
    }
}
