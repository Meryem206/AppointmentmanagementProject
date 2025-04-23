package application;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;



public class SampleController implements BaseLayoutInjectable {

    private BaseLayoutController baseLayoutController;

    @Override
    public void setBaseLayoutController(BaseLayoutController baseLayoutController) {
        this.baseLayoutController = baseLayoutController;
    }

    @FXML
    private void goToPage1(MouseEvent event) {
        if (baseLayoutController != null) {
            baseLayoutController.updateActiveMenu(baseLayoutController.ajouterText);
            baseLayoutController.loadPageWithController("alreadyexists.fxml"); // cleaner!
        }
    }

    @FXML
    private void goToPage2(MouseEvent event) {
        if (baseLayoutController != null) {
            baseLayoutController.updateActiveMenu(baseLayoutController.listeText);
            baseLayoutController.loadPageWithController("AppointmentList.fxml");
        }
    }
}
