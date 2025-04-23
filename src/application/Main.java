package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Create an instance of LoginForm and start it using the same primaryStage
    	primaryStage.setResizable(false);
        LoginForm loginForm = new LoginForm();
        loginForm.start(primaryStage);  // Pass the primary stage
    }

    public static void main(String[] args) {
        launch(args);
    }
}