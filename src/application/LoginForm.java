package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginForm extends Application {

    private TextField emailField;
    private PasswordField passwordField;
    private TextField passwordVisibleField;
    private boolean isPasswordVisible = false;

    private Text errorMessageEmail = new Text();
    private Text errorMessagePassword = new Text();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connexion");

        Image messageIcon = new Image(getClass().getResource("/application/Message.png").toExternalForm());
        Image lockIcon = new Image(getClass().getResource("/application/Group.png").toExternalForm());
        Image eyeOpenIcon = new Image(getClass().getResource("/application/open.png").toExternalForm());
        Image eyeOffIcon = new Image(getClass().getResource("/application/close.png").toExternalForm());

        Label titleLabel = new Label("                             Bienvenue");
        titleLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/application/Roboto.ttf"), 20));
        titleLabel.getStyleClass().add("titleLabel");

        Label subtitleLabel1 = new Label("Dans l'application de gestion des rendez-vous.");
        subtitleLabel1.setFont(Font.loadFont(getClass().getResourceAsStream("/application/Italiana.ttf"), 20));
        subtitleLabel1.getStyleClass().add("subtitleLabel");

        Label subtitleLabel = new Label("                      Connectez-vous a  votre compte pour continuer");
        subtitleLabel.setFont(Font.loadFont(getClass().getResourceAsStream("/application/Italiana.ttf"), 20));
        subtitleLabel.getStyleClass().add("subtitleLabel");

        VBox messageBox = createTextFieldWithIcon(messageIcon, "Adresse e-mail");
        VBox passwordBox = createPasswordFieldWithIcon(lockIcon, eyeOpenIcon, eyeOffIcon, "Mot de passe");

        errorMessageEmail.setFill(Color.RED);
        errorMessagePassword.setFill(Color.RED);

        Button loginButton = new Button("Se connecter");
        loginButton.setPrefSize(200, 45);
        loginButton.getStyleClass().add("login-button");

        Button signupButton = new Button("Créer un nouveau compte");
        signupButton.setPrefSize(200, 45);
        signupButton.getStyleClass().add("signup-button");
        signupButton.setOnAction(e -> new SignupForm().start(primaryStage));

        VBox buttonBox = new VBox(30, loginButton, signupButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(50));

        VBox formBox = new VBox(10, messageBox, passwordBox, errorMessageEmail, errorMessagePassword, buttonBox);
        formBox.setAlignment(Pos.TOP_LEFT);
        formBox.setPadding(new Insets(20, 50, 50, 190));

        VBox textContainer1 = new VBox(20, subtitleLabel1, subtitleLabel);
        textContainer1.setAlignment(Pos.TOP_RIGHT);
        textContainer1.setPadding(new Insets(20));

        VBox textContainer = new VBox(10, titleLabel, textContainer1);
        textContainer.setAlignment(Pos.TOP_CENTER);
        textContainer.setPadding(new Insets(20));

        Image backgroundImage = new Image(getClass().getResource("/application/back3.png").toExternalForm());
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)
        );

        BorderPane root = new BorderPane();
        root.setRight(formBox);
        root.setTop(textContainer);
        root.setBackground(new Background(background));

        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(getClass().getResource("/application/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        loginButton.setOnAction(e -> handleLogin(primaryStage));
    }

    private VBox createTextFieldWithIcon(Image icon, String placeholder) {
        ImageView iconView = new ImageView(icon);
        iconView.setFitWidth(20);
        iconView.setFitHeight(20);

        emailField = new TextField();
        emailField.setPromptText(placeholder);
        emailField.setPrefSize(403, 70);
        emailField.setPadding(new Insets(12,12,12,40));

        StackPane stackPane = new StackPane(emailField, iconView);
        StackPane.setAlignment(iconView, Pos.CENTER_LEFT);
        StackPane.setMargin(iconView, new Insets(0, 0, 0, 5));

        return new VBox(stackPane);
    }

    private VBox createPasswordFieldWithIcon(Image lockIcon, Image eyeOpen, Image eyeOff, String placeholder) {
        ImageView lockView = new ImageView(lockIcon);
        lockView.setFitWidth(20);
        lockView.setFitHeight(20);

        passwordField = new PasswordField();
        passwordField.setPromptText(placeholder);
        passwordField.setPrefSize(403, 70);
        passwordField.setPadding(new Insets(12,12,12,45));

        passwordVisibleField = new TextField();
        passwordVisibleField.setPromptText(placeholder);
        passwordVisibleField.setPrefSize(403, 12);
        passwordVisibleField.setPadding(new Insets(12, 12, 12, 45));
        passwordVisibleField.setVisible(false);
        passwordVisibleField.setManaged(false);

        ImageView eyeView = new ImageView(eyeOff);
        eyeView.setFitWidth(20);
        eyeView.setFitHeight(20);

        eyeView.setOnMouseClicked(e -> togglePasswordVisibility(eyeView, eyeOpen, eyeOff));

        StackPane stackPane = new StackPane(passwordField, passwordVisibleField, lockView, eyeView);
        StackPane.setAlignment(lockView, Pos.CENTER_LEFT);
        StackPane.setAlignment(eyeView, Pos.CENTER_RIGHT);
        StackPane.setMargin(lockView, new Insets(0, 0, 0, 5));
        StackPane.setMargin(eyeView, new Insets(0, 5, 0, 0));

        return new VBox(stackPane);
    }

    private void togglePasswordVisibility(ImageView eyeView, Image eyeOpen, Image eyeOff) {
        if (isPasswordVisible) {
            passwordField.setText(passwordVisibleField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            passwordVisibleField.setVisible(false);
            passwordVisibleField.setManaged(false);
            eyeView.setImage(eyeOff);
        } else {
            passwordVisibleField.setText(passwordField.getText());
            passwordVisibleField.setVisible(true);
            passwordVisibleField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            eyeView.setImage(eyeOpen);
        }
        isPasswordVisible = !isPasswordVisible;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean isValidPassword(String password) {
        return password.length() <= 8 && password.matches(".*[!@#$%^&*()_+=<>?{}\\[\\]~`|:;\"',./\\\\-].*");
    }

    private void handleLogin(Stage primaryStage) {
        String email = emailField.getText().trim();
        String password = isPasswordVisible ? passwordVisibleField.getText() : passwordField.getText();

        errorMessageEmail.setText("");
        errorMessagePassword.setText("");

        boolean valid = true;

        if (!isValidEmail(email)) {
            errorMessageEmail.setText("Email invalide. Veuillez saisir un email valide.");
            valid = false;
        }

        if (!isValidPassword(password)) {
            errorMessagePassword.setText("Mot de passe invalide : max 8 caractactere, 1 caractére special requis.");
            valid = false;
        }

        if (valid) {
            showAlert("Connexion réussie", "Bienvenue !");
            clearFields();
            redirectToDashboard(primaryStage);
        }
    }

    private void showAlert(String title, String message) {
        System.out.println(title + ": " + message);
    }

    private void clearFields() {
        emailField.clear();
        passwordField.clear();
        passwordVisibleField.clear();
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
            showAlert("Erreur", "Impossible de charger la page d'accueil.");
        }
    }


}