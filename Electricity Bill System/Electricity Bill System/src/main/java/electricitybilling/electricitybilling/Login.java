package electricitybilling.electricitybilling;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.sql.ResultSet;

public class Login extends Application {

    private TextField userText;
    private PasswordField passwordText;
    private ComboBox<String> loginChoice;
    private Button loginButton, cancelButton, signupButton;
    private Stage primaryStage;

    // Color palette from the image
    private static final String LIGHTEST_COLOR = "#FAE5D8";
    private static final String LIGHT_COLOR = "#DFB6B2";
    private static final String MEDIUM_COLOR = "#824D69";
    private static final String DARK_COLOR = "#522959";
    private static final String DARKER_COLOR = "#2A114B";
    private static final String DARKEST_COLOR = "#18001B";

    public Login() {
        // Constructor is needed for when it's called from Splash screen
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Electricity Billing System - Login");

        // Create main layout
        BorderPane root = new BorderPane();

        // Create a stylish left panel with gradient background based on new color palette
        VBox leftPanel = new VBox(20);
        leftPanel.setPrefWidth(280);
        leftPanel.setStyle("-fx-background-color: linear-gradient(to bottom, " + DARK_COLOR + ", " + DARKER_COLOR + "); -fx-padding: 40;");
        leftPanel.setAlignment(Pos.CENTER);

        // App title and description
        Label appTitle = new Label("Electricity\nBilling System");
        appTitle.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        appTitle.setTextFill(Color.WHITE);
        appTitle.setTextAlignment(TextAlignment.CENTER);
        appTitle.setWrapText(true);

        Label appDescription = new Label("Manage your electricity bills efficiently and securely");
        appDescription.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        appDescription.setTextFill(Color.WHITE);
        appDescription.setWrapText(true);
        appDescription.setTextAlignment(TextAlignment.CENTER);

        // Company name display
        Label companyLabel = new Label("Power Distribution Inc.");
        companyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        companyLabel.setTextFill(Color.WHITE);
        companyLabel.setTextAlignment(TextAlignment.CENTER);

        // Add a separator
        Separator separator = new Separator();
        separator.setMaxWidth(200);
        separator.setStyle("-fx-background-color: " + LIGHT_COLOR + ";");

        VBox.setMargin(appTitle, new Insets(20, 0, 0, 0));
        leftPanel.getChildren().addAll(appTitle, appDescription, separator, companyLabel);

        // Create a clean right panel for login form with new color scheme
        VBox rightPanel = new VBox(15);
        rightPanel.setPadding(new Insets(40, 40, 30, 40));
        rightPanel.setStyle("-fx-background-color: " + LIGHTEST_COLOR + ";");
        rightPanel.setAlignment(Pos.CENTER);

        // Login form title
        Label loginTitle = new Label("User Login");
        loginTitle.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        loginTitle.setTextFill(Color.web(DARKEST_COLOR));

        // Username field
        VBox usernameBox = new VBox(5);

        Label username = new Label("Username");
        username.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        username.setTextFill(Color.web(DARKEST_COLOR));

        userText = new TextField();
        userText.setPromptText("Enter username");
        userText.setPrefHeight(35);
        userText.setPrefWidth(250);
        userText.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: " + MEDIUM_COLOR + ";");

        usernameBox.getChildren().addAll(username, userText);

        // Password field
        VBox passwordBox = new VBox(5);

        Label password = new Label("Password");
        password.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        password.setTextFill(Color.web(DARKEST_COLOR));

        passwordText = new PasswordField();
        passwordText.setPromptText("Enter password");
        passwordText.setPrefHeight(35);
        passwordText.setPrefWidth(250);
        passwordText.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: " + MEDIUM_COLOR + ";");

        passwordBox.getChildren().addAll(password, passwordText);

        // User type selection
        VBox userTypeBox = new VBox(5);

        Label login = new Label("Login as");
        login.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        login.setTextFill(Color.web(DARKEST_COLOR));

        loginChoice = new ComboBox<>();
        loginChoice.getItems().addAll("Admin", "Customer");
        loginChoice.setValue("Admin");
        loginChoice.setPrefHeight(35);
        loginChoice.setPrefWidth(250);
        loginChoice.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: " + MEDIUM_COLOR + ";");

        userTypeBox.getChildren().addAll(login, loginChoice);

        // Action buttons with styling using new color palette
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(15, 0, 0, 0));

        loginButton = new Button("Login");
        loginButton.setPrefWidth(120);
        loginButton.setPrefHeight(40);
        loginButton.setStyle("-fx-background-color: " + DARK_COLOR + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        loginButton.setOnAction(e -> handleLogin());

        cancelButton = new Button("Cancel");
        cancelButton.setPrefWidth(120);
        cancelButton.setPrefHeight(40);
        cancelButton.setStyle("-fx-background-color: " + MEDIUM_COLOR + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
        cancelButton.setOnAction(e -> primaryStage.close());

        buttonBox.getChildren().addAll(loginButton, cancelButton);

        // Signup link
        HBox signupBox = new HBox();
        signupBox.setAlignment(Pos.CENTER);
        signupBox.setPadding(new Insets(10, 0, 0, 0));

        Label noAccount = new Label("Don't have an account? ");
        noAccount.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        noAccount.setTextFill(Color.web(DARKEST_COLOR));

        signupButton = new Button("Sign up");
        signupButton.setStyle("-fx-background-color: transparent; -fx-text-fill: " + DARKER_COLOR + "; -fx-font-weight: bold; -fx-cursor: hand;");
        signupButton.setOnAction(e -> {
            primaryStage.close();
            openSignup();
        });

        signupBox.getChildren().addAll(noAccount, signupButton);

        // Add all components to the right panel
        rightPanel.getChildren().addAll(
                loginTitle,
                usernameBox,
                passwordBox,
                userTypeBox,
                buttonBox,
                signupBox
        );

        // Add panels to the root
        root.setLeft(leftPanel);
        root.setCenter(rightPanel);

        // Create and set the scene
        Scene scene = new Scene(root, 680, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void handleLogin() {
        String susername = userText.getText();
        String spassword = passwordText.getText();
        String suser = loginChoice.getValue();

        // Input validation - fix: added validation to prevent empty inputs
        if (susername.isEmpty() || spassword.isEmpty()) {
            showAlert("Please enter both username and password");
            return;
        }

        try {
            database c = new database();
            // Fix: SQL Injection vulnerability by using prepared statements
            String query = "SELECT * FROM Signup WHERE username = ? AND password = ? AND usertype = ?";
            try (java.sql.PreparedStatement pstmt = c.connection.prepareStatement(query)) {
                pstmt.setString(1, susername);
                pstmt.setString(2, spassword);
                pstmt.setString(3, suser);

                ResultSet resultSet = pstmt.executeQuery();
                if (resultSet.next()) {
                    String meter = resultSet.getString("meter_no");
                    primaryStage.close();
                    openMainClass(suser, meter);
                } else {
                    showAlert("Invalid Login");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Database error: " + ex.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        // Set alert dialog styling
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: " + LIGHTEST_COLOR + ";");
        dialogPane.lookupButton(ButtonType.OK).setStyle("-fx-background-color: " + DARK_COLOR + "; -fx-text-fill: white;");
        alert.showAndWait();
    }

    private void openSignup() {
        try {
            new Signup().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error opening signup: " + e.getMessage());
        }
    }

    private void openMainClass(String userType, String meter) {
        try {
            new main_class(userType, meter).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error opening main screen: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    // This method helps when called from other classes like Splash
    public void showLogin() {
        try {
            start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}