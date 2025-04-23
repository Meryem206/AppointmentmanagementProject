package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupForm extends Application {
	
	

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CrÃ©er un compte");

        // Chargement des icÃ´nes
        Image userIcon = new Image(getClass().getResource("/application/stylo.png").toExternalForm());
        Image messageIcon = new Image(getClass().getResource("/application/Message.png").toExternalForm());
        Image lockIcon = new Image(getClass().getResource("/application/Group.png").toExternalForm());
        Image jobIcon = new Image(getClass().getResource("/application/Bag_alt.png").toExternalForm());
        Image eyeOpenIcon = new Image(getClass().getResource("/application/open.png").toExternalForm());
        Image eyeClosedIcon = new Image(getClass().getResource("/application/close.png").toExternalForm());

        // Titre et sous-titre
        Label titleLabel = new Label("Créer un compte");
        titleLabel.getStyleClass().add("titleLabel");
        titleLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/application/Roboto.ttf"), 20));

        Label subtitleLabel = new Label("Entrez vos données personnelles et organisez ");
        Label subtitleLabel2 = new Label("   vos rendez-vous en toute efficacité");

        subtitleLabel.getStyleClass().add("subtitleLabel");
        subtitleLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/application/Italiana.ttf"), 20));
        subtitleLabel2.getStyleClass().add("subtitleLabel");
        subtitleLabel2.setFont(Font.loadFont(getClass().getResourceAsStream("/application/Italiana.ttf"), 20));

        VBox formBox2 = new VBox(10, titleLabel, subtitleLabel, subtitleLabel2);
        formBox2.setAlignment(Pos.CENTER_LEFT);
        formBox2.setPadding(new Insets(15));

        // Champs
        VBox fullNameBox = createTextFieldWithIcon(userIcon, "   Nom complet");
        VBox idBox = createTextFieldWithIcon(messageIcon, "   Entrez votre E-mail");
        VBox passwordBox = createPasswordFieldWithIcon(lockIcon, "   Mot de passe", eyeOpenIcon, eyeClosedIcon);
        VBox confirmPasswordBox = createPasswordFieldWithIcon(lockIcon, "    Confirmez mot de passe", eyeOpenIcon, eyeClosedIcon);
        VBox specialtyBox = createComboBoxWithIcon(jobIcon, "   Profession");

        // Messages d'erreur
        Label passwordError = new Label();
        passwordError.setStyle("-fx-text-fill: red;");
        Label confirmPasswordError = new Label();
        confirmPasswordError.setStyle("-fx-text-fill: red;");
        Label emailError = new Label();
        emailError.setStyle("-fx-text-fill: red;");

        // Bouton d'inscription
        Button signupButton = new Button("S'inscrire");
        signupButton.setPrefSize(200, 45);
        signupButton.getStyleClass().add("signup-button");

        signupButton.setOnAction(event -> {
            boolean isValid = true;

            if (!validatePassword(passwordBox)) {
                passwordError.setText("Le mot de passe doit contenir au moins un caractére spécial (max 8 caractéres).");
                isValid = false;
            } else {
                passwordError.setText("");
            }

            if (!confirmPassword(passwordBox, confirmPasswordBox)) {
                confirmPasswordError.setText("Les mots de passe ne correspondent pas.");
                isValid = false;
            } else {
                confirmPasswordError.setText("");
            }

            if (!validateEmail(idBox)) {
                emailError.setText("Email invalide.");
                isValid = false;
            } else {
                emailError.setText("");
            }

        
            	if (isValid) {
            		 redirectToDashboard((Stage) ((Button) event.getSource()).getScene().getWindow());
            	}
            
        });

        HBox buttonBox = new HBox(signupButton);
        buttonBox.setAlignment(Pos.BASELINE_LEFT);
        buttonBox.setPadding(new Insets(30, 20, 30, 20));

        VBox formBox = new VBox(1, fullNameBox, idBox, passwordBox, confirmPasswordBox, specialtyBox,
                passwordError, confirmPasswordError, emailError, buttonBox);
        formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.setPadding(new Insets(20, 50, 50, 30));

        // Fond d'Ã©cran
        Image backgroundImage = new Image(getClass().getResource("/application/back2.png").toExternalForm());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
        );

        VBox leftContent = new VBox(10, formBox2, formBox);
        leftContent.setAlignment(Pos.TOP_LEFT);
        leftContent.setPadding(new Insets(30, 0, 0, 30)); // padding Ã  gauche

        HBox mainContent = new HBox(leftContent);
        mainContent.setAlignment(Pos.TOP_LEFT); // Aligner tout Ã  gauche


        BorderPane root = new BorderPane();
        root.setLeft(mainContent);        root.setBackground(new Background(background));

        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(getClass().getResource("/application/style2.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createTextFieldWithIcon(Image icon, String placeholder) {
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(20);
        iconView.setFitHeight(20);

        TextField textField = new TextField();
        textField.setPromptText(placeholder);
        textField.setPadding(new Insets(12, 12, 12, 45));
        textField.setPrefWidth(403       		); // Largeur personnalisÃ©e
        textField.getStyleClass().add("text-field");

        StackPane stackPane = new StackPane(textField, iconView);
        StackPane.setAlignment(iconView, Pos.CENTER_LEFT);
        StackPane.setMargin(iconView, new Insets(0, 0, 0, 5));

        VBox vbox = new VBox(stackPane);
        vbox.setPadding(new Insets(5));
        return vbox;
    }

    private VBox createPasswordFieldWithIcon(Image icon, String placeholder, Image eyeOpenIcon, Image eyeClosedIcon) {
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(20);
        iconView.setFitHeight(20);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(placeholder);
        passwordField.setPadding(new Insets(12, 12, 12, 45));
        passwordField.setPrefWidth(403);

        TextField visibleField = new TextField();
        visibleField.setPromptText(placeholder);
        visibleField.setPadding(new Insets(12, 12, 12, 45));
        visibleField.setPrefWidth(403);
        visibleField.setVisible(false);

        visibleField.textProperty().bindBidirectional(passwordField.textProperty());

        ImageView eyeIcon = new ImageView(eyeClosedIcon);
        eyeIcon.setFitWidth(20);
        eyeIcon.setFitHeight(20);

        eyeIcon.setOnMouseClicked(event -> {
            boolean isVisible = visibleField.isVisible();
            visibleField.setVisible(!isVisible);
            visibleField.setManaged(!isVisible);
            passwordField.setVisible(isVisible);
            passwordField.setManaged(isVisible);
            eyeIcon.setImage(isVisible ? eyeClosedIcon : eyeOpenIcon);
        });

        StackPane stackPane = new StackPane(passwordField, visibleField, iconView, eyeIcon);
        StackPane.setAlignment(iconView, Pos.CENTER_LEFT);
        StackPane.setMargin(iconView, new Insets(0, 0, 0, 10));
        StackPane.setAlignment(eyeIcon, Pos.CENTER_RIGHT);
        StackPane.setMargin(eyeIcon, new Insets(0, 10, 0, 0));

        VBox vbox = new VBox(stackPane);
        vbox.setPadding(new Insets(5));
        return vbox;
    }

    private VBox createComboBoxWithIcon(Image icon, String placeholder) {
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(20);
        iconView.setFitHeight(20);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Généraliste / Infirmier", "Dentiste", "Psychologue");
        comboBox.setPromptText(placeholder);
        comboBox.setPadding(new Insets(5, 5, 5, 45));
        comboBox.setPrefWidth(403); // Largeur personnalisÃ©e

        StackPane stackPane = new StackPane(comboBox, iconView);
        StackPane.setAlignment(iconView, Pos.CENTER_LEFT);
        StackPane.setMargin(iconView, new Insets(0, 0, 0, 10));

        VBox vbox = new VBox(stackPane);
        vbox.setPadding(new Insets(5));
        return vbox;
    }

    private boolean validatePassword(VBox passwordBox) {
        TextField field = (TextField) ((StackPane) passwordBox.getChildren().get(0)).getChildren().get(0);
        String password = field.getText();
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9 ]");
        Matcher matcher = pattern.matcher(password);
        return matcher.find() && password.length() >= 6 && password.length() <= 8;
    }

    private boolean confirmPassword(VBox passwordBox, VBox confirmPasswordBox) {
        TextField passwordField = (TextField) ((StackPane) passwordBox.getChildren().get(0)).getChildren().get(0);
        TextField confirmPasswordField = (TextField) ((StackPane) confirmPasswordBox.getChildren().get(0)).getChildren().get(0);
        return passwordField.getText().equals(confirmPasswordField.getText());
    }

    private boolean validateEmail(VBox idBox) {
        TextField emailField = (TextField) ((StackPane) idBox.getChildren().get(0)).getChildren().get(0);
        String email = emailField.getText();
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }
    
    private void redirectToDashboard(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainlayout.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 650);
            stage.setScene(scene);
            stage.setTitle("Accueil"); // Optional: Set a new title for the main app
        } catch (IOException e) {
            e.printStackTrace();
           
        }
    }

   
}
