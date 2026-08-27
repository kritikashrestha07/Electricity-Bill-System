package electricitybilling.electricitybilling;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Splash extends Application {

    // Color palette from the image
    private static final String DARKEST_PURPLE = "#180018";
    private static final String DARK_PURPLE = "#2A114B";
    private static final String MEDIUM_PURPLE = "#522959";
    private static final String MAUVE_PURPLE = "#824D69";
    private static final String LIGHT_PINK = "#DFB8B2";
    private static final String LIGHTEST_CREAM = "#FAE5D8";

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create a rectangle for background with gradient
            Rectangle background = new Rectangle(600, 400);
            background.setFill(Color.web(DARK_PURPLE));

            // Create title text
            Text titleText = new Text("ELECTRICITY BILLING SYSTEM");
            titleText.setFont(Font.font("Montserrat", FontWeight.BOLD, 28));
            titleText.setFill(Color.web(LIGHTEST_CREAM));
            titleText.setTextAlignment(TextAlignment.CENTER);
            titleText.setEffect(new DropShadow(5, Color.web(DARKEST_PURPLE)));

            // Create subtitle
            Label subtitleLabel = new Label("Smart Energy Management Solution");
            subtitleLabel.setFont(Font.font("Montserrat", FontWeight.NORMAL, 16));
            subtitleLabel.setTextFill(Color.web(LIGHT_PINK));

            // Create version label
            Label versionLabel = new Label("Version 1.0");
            versionLabel.setFont(Font.font("Montserrat", FontWeight.NORMAL, 14));
            versionLabel.setTextFill(Color.web(LIGHT_PINK, 0.9));

            // Create progress bar with styled appearance
            ProgressBar progressBar = new ProgressBar(0);
            progressBar.setPrefWidth(350);
            progressBar.setStyle("-fx-accent: " + MAUVE_PURPLE + "; " +
                    "-fx-background-color: " + DARKEST_PURPLE + ", " + DARKEST_PURPLE + "; " +
                    "-fx-background-radius: 20; " +
                    "-fx-background-insets: 0, 1; " +
                    "-fx-control-inner-background: " + DARKEST_PURPLE + ";");

            // Loading label
            Label loadingLabel = new Label("Initializing System...");
            loadingLabel.setFont(Font.font("Montserrat", FontWeight.NORMAL, 12));
            loadingLabel.setTextFill(Color.web(LIGHT_PINK, 0.9));

            // Create copyright info
            Label copyrightLabel = new Label("© 2025 Electricity Billing System");
            copyrightLabel.setFont(Font.font("Montserrat", FontWeight.NORMAL, 10));
            copyrightLabel.setTextFill(Color.web(LIGHT_PINK, 0.7));

            // Create a VBox for all content
            VBox contentBox = new VBox(15);
            contentBox.setAlignment(Pos.CENTER);
            contentBox.setPadding(new Insets(20));
            contentBox.getChildren().addAll(titleText, subtitleLabel, versionLabel, progressBar, loadingLabel);

            // Create a VBox for copyright at bottom
            VBox bottomBox = new VBox();
            bottomBox.setAlignment(Pos.BOTTOM_CENTER);
            bottomBox.setPadding(new Insets(0, 0, 15, 0));
            bottomBox.getChildren().add(copyrightLabel);

            // Create rounded rectangle for card-like appearance
            Rectangle cardBackground = new Rectangle(560, 360);
            cardBackground.setArcWidth(20);
            cardBackground.setArcHeight(20);
            cardBackground.setFill(Color.web(MEDIUM_PURPLE, 0.7));
            cardBackground.setEffect(new DropShadow(10, Color.web(DARKEST_PURPLE, 0.5)));

            // Create a StackPane layout and add all elements
            StackPane root = new StackPane();
            root.getChildren().addAll(background, cardBackground, contentBox, bottomBox);
            StackPane.setAlignment(bottomBox, Pos.BOTTOM_CENTER);

            // Create and configure the Scene
            Scene scene = new Scene(root, 600, 400);
            scene.getRoot().setStyle("-fx-background-color: linear-gradient(to bottom right, " +
                    DARK_PURPLE + ", " + DARKEST_PURPLE + "); " +
                    "-fx-background-radius: 5;");

            // Configure the Stage
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.centerOnScreen(); // Center the window instead of fixed position
            primaryStage.show();

            // Create a thread to simulate loading and then transition to Login screen
            new Thread(() -> {
                try {
                    // Simulate progress with different loading messages
                    String[] loadingMessages = {
                            "Initializing System...",
                            "Loading Configuration...",
                            "Connecting to Database...",
                            "Preparing User Interface...",
                            "Starting Application..."
                    };

                    // Fix the loop to go from 0 to 100 instead of 150
                    for (int i = 0; i <= 100; i++) {
                        final int progress = i;
                        Platform.runLater(() -> {
                            progressBar.setProgress(progress / 100.0);

                            // Update loading message at certain progress points
                            if (progress % 20 == 0 && progress / 20 < loadingMessages.length) {
                                loadingLabel.setText(loadingMessages[progress / 20]);
                            }
                        });
                        Thread.sleep(30); // Slightly slower for better visual effect
                    }

                    // Additional delay before closing
                    Thread.sleep(500);

                    // Close splash screen and open login screen on JavaFX application thread
                    Platform.runLater(() -> {
                        primaryStage.close();
                        openLoginScreen();
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openLoginScreen() {
        try {
            Login loginScreen = new Login();
            Stage loginStage = new Stage();
            loginScreen.start(loginStage);
        } catch (Exception e) {
            e.printStackTrace();
            // Graceful error handling
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}