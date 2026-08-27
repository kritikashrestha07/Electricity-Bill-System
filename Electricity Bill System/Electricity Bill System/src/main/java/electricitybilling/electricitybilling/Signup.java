package electricitybilling.electricitybilling;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.ResultSet;

public class Signup extends Application {

    private ComboBox<String> loginASCho;
    private TextField meterText, employerText, userText, nameText;
    private PasswordField passText;
    private Button signUpButton, backButton;
    private Label meterNum, employer;
    private Stage primaryStage;

    // Color palette from the image
    private final String PRIMARY_COLOR = "#522959";       // Deep purple
    private final String SECONDARY_COLOR = "#824D69";     // Mauve
    private final String BACKGROUND_COLOR = "#FAE5D8";    // Light cream
    private final String ACCENT_COLOR = "#DFB6B2";        // Soft pink
    private final String DARK_COLOR = "#2A114B";          // Dark purple
    private final String TEXT_COLOR = "#18001B";          // Black purple

    public Signup() {
        // Default constructor for when called from other classes
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Electricity Billing System");
        primaryStage.initStyle(StageStyle.DECORATED);

        // Create main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        // Header section
        VBox headerBox = createHeader();
        mainLayout.setTop(headerBox);

        // Form section
        VBox formBox = createForm();
        mainLayout.setCenter(formBox);

        // Button section
        HBox buttonBox = createButtons();
        mainLayout.setBottom(buttonBox);

        // Add a subtle drop shadow to the entire layout
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.web(DARK_COLOR + "80")); // Adding transparency
        mainLayout.setEffect(dropShadow);

        // Create and set the scene
        Scene scene = new Scene(mainLayout, 550, 650);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private VBox createHeader() {
        VBox headerBox = new VBox(12);
        headerBox.setPadding(new Insets(30, 0, 25, 0));
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setStyle("-fx-background-color: linear-gradient(to right, " + PRIMARY_COLOR + ", " + DARK_COLOR + ");");

        Label title = new Label("ELECTRICITY BILLING SYSTEM");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        title.setTextFill(Color.WHITE);

        Label subtitle = new Label("Create New Account");
        subtitle.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
        subtitle.setTextFill(Color.web(ACCENT_COLOR));

        headerBox.getChildren().addAll(title, subtitle);
        return headerBox;
    }

    private VBox createForm() {
        VBox formBox = new VBox(22);
        formBox.setPadding(new Insets(40, 60, 40, 60));
        formBox.setAlignment(Pos.TOP_CENTER);
        formBox.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        Label formTitle = new Label("Registration Details");
        formTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        formTitle.setTextFill(Color.web(PRIMARY_COLOR));
        formTitle.setStyle("-fx-padding: 0 0 15 0;");

        // Style for form field labels
        String labelStyle = "-fx-font-family: 'Segoe UI'; -fx-font-weight: bold; -fx-text-fill: " + TEXT_COLOR + ";";

        // Style for form field inputs
        String fieldStyle = "-fx-font-size: 14px; -fx-background-color: white; " +
                "-fx-border-color: " + SECONDARY_COLOR + "; " +
                "-fx-border-width: 0 0 2 0; " + // Bottom border only
                "-fx-background-radius: 5px; " +
                "-fx-border-radius: 5px; " +
                "-fx-padding: 8px;";

        // Account type selection
        HBox accountTypeBox = new HBox(15);
        accountTypeBox.setAlignment(Pos.CENTER_LEFT);

        Label createAs = new Label("Account Type:");
        createAs.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        createAs.setPrefWidth(140);
        createAs.setStyle(labelStyle);

        loginASCho = new ComboBox<>();
        loginASCho.getItems().addAll("Admin", "Customer");
        loginASCho.setValue("Admin");
        loginASCho.setPrefWidth(250);
        loginASCho.setStyle("-fx-font-size: 14px; -fx-background-color: white; " +
                "-fx-border-color: " + SECONDARY_COLOR + "; " +
                "-fx-border-radius: 5px; " +
                "-fx-background-radius: 5px;");

        accountTypeBox.getChildren().addAll(createAs, loginASCho);

        // Meter Number field
        HBox meterBox = new HBox(15);
        meterBox.setAlignment(Pos.CENTER_LEFT);

        meterNum = new Label("Meter Number:");
        meterNum.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        meterNum.setPrefWidth(140);
        meterNum.setStyle(labelStyle);
        meterNum.setVisible(false);

        meterText = new TextField();
        meterText.setPrefWidth(250);
        meterText.setStyle(fieldStyle);
        meterText.setVisible(false);

        meterBox.getChildren().addAll(meterNum, meterText);

        // Employer ID field
        HBox employerBox = new HBox(15);
        employerBox.setAlignment(Pos.CENTER_LEFT);

        employer = new Label("Employer ID:");
        employer.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        employer.setPrefWidth(140);
        employer.setStyle(labelStyle);

        employerText = new TextField();
        employerText.setPrefWidth(250);
        employerText.setStyle(fieldStyle);

        employerBox.getChildren().addAll(employer, employerText);

        // Username field
        HBox usernameBox = new HBox(15);
        usernameBox.setAlignment(Pos.CENTER_LEFT);

        Label userName = new Label("Username:");
        userName.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        userName.setPrefWidth(140);
        userName.setStyle(labelStyle);

        userText = new TextField();
        userText.setPrefWidth(250);
        userText.setStyle(fieldStyle);

        usernameBox.getChildren().addAll(userName, userText);

        // Full Name field
        HBox nameBox = new HBox(15);
        nameBox.setAlignment(Pos.CENTER_LEFT);

        Label name = new Label("Full Name:");
        name.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        name.setPrefWidth(140);
        name.setStyle(labelStyle);

        nameText = new TextField();
        nameText.setPrefWidth(250);
        nameText.setStyle(fieldStyle);

        nameBox.getChildren().addAll(name, nameText);

        // Password field
        HBox passwordBox = new HBox(15);
        passwordBox.setAlignment(Pos.CENTER_LEFT);

        Label pass = new Label("Password:");
        pass.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        pass.setPrefWidth(140);
        pass.setStyle(labelStyle);

        passText = new PasswordField();
        passText.setPrefWidth(250);
        passText.setStyle(fieldStyle);

        passwordBox.getChildren().addAll(pass, passText);

        // Create a separator for visual division
        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: " + ACCENT_COLOR + ";");
        separator.setPadding(new Insets(10, 0, 10, 0));

        // Add all form elements
        formBox.getChildren().addAll(
                formTitle,
                accountTypeBox,
                meterBox,
                employerBox,
                usernameBox,
                nameBox,
                passwordBox
        );

        // Add hover effect to input fields
        addHoverEffectToFields(userText, nameText, passText, employerText, meterText);

        // Add item listener to change visibility based on selection
        loginASCho.setOnAction(e -> {
            if (loginASCho.getValue().equals("Admin")) {
                meterNum.setVisible(false);
                meterText.setVisible(false);
                employer.setVisible(true);
                employerText.setVisible(true);
                nameText.setEditable(true);
            } else {
                meterNum.setVisible(true);
                meterText.setVisible(true);
                employer.setVisible(false);
                employerText.setVisible(false);
                nameText.setEditable(false);
            }
        });

        // Set up the automatic name lookup when meter number is entered
        setupMeterNumberLookup();

        return formBox;
    }

    private void addHoverEffectToFields(TextField... fields) {
        for (TextField field : fields) {
            String originalStyle = field.getStyle();
            field.setOnMouseEntered(e ->
                    field.setStyle(originalStyle + "-fx-border-color: " + PRIMARY_COLOR + ";")
            );
            field.setOnMouseExited(e -> field.setStyle(originalStyle));
        }
    }

    private void setupMeterNumberLookup() {
        // Add a focus-out event handler to the meter number text field
        meterText.focusedProperty().addListener((observable, oldValue, newValue) -> {
            // When focus is lost (user has finished typing the meter number)
            if (!newValue && !meterText.getText().isEmpty()) {
                String meterNo = meterText.getText();
                try {
                    // Create a database connection
                    database c = new database();
                    String query = "SELECT name FROM Signup WHERE meter_no = '" + meterNo + "'";
                    ResultSet rs = c.statement.executeQuery(query);

                    if (rs.next()) {
                        // If meter number exists in database, set the name field
                        String customerName = rs.getString("name");
                        nameText.setText(customerName);
                        // Make the name field non-editable for customers
                        nameText.setEditable(false);
                    } else {
                        // If meter number doesn't exist, clear the name field and show an alert
                        nameText.clear();
                        nameText.setEditable(false);
                        showAlert(Alert.AlertType.WARNING, "Invalid Meter Number",
                                "No customer found with this meter number. Please check and try again.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Database Error",
                            "Failed to retrieve customer information. Please try again.");
                }
            }
        });
    }

    private HBox createButtons() {
        HBox buttonBox = new HBox(25);
        buttonBox.setPadding(new Insets(0, 0, 45, 0));
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        String buttonStyle = "-fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-font-size: 14px; -fx-background-radius: 5px; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 4, 0, 0, 2);";

        signUpButton = new Button("SIGN UP");
        signUpButton.setPrefWidth(160);
        signUpButton.setPrefHeight(45);
        signUpButton.setStyle(buttonStyle + "-fx-background-color: " + PRIMARY_COLOR + ";");

        // Add hover effect
        signUpButton.setOnMouseEntered(e ->
                signUpButton.setStyle(buttonStyle + "-fx-background-color: " + DARK_COLOR + ";")
        );
        signUpButton.setOnMouseExited(e ->
                signUpButton.setStyle(buttonStyle + "-fx-background-color: " + PRIMARY_COLOR + ";")
        );
        signUpButton.setOnAction(e -> handleSignUp());

        backButton = new Button("BACK");
        backButton.setPrefWidth(160);
        backButton.setPrefHeight(45);
        backButton.setStyle(buttonStyle + "-fx-background-color: " + SECONDARY_COLOR + ";");

        // Add hover effect
        backButton.setOnMouseEntered(e ->
                backButton.setStyle(buttonStyle + "-fx-background-color: " + ACCENT_COLOR + "; -fx-text-fill: " + TEXT_COLOR + ";")
        );
        backButton.setOnMouseExited(e ->
                backButton.setStyle(buttonStyle + "-fx-background-color: " + SECONDARY_COLOR + ";")
        );
        backButton.setOnAction(e -> {
            primaryStage.close();
            openLogin();
        });

        buttonBox.getChildren().addAll(signUpButton, backButton);
        return buttonBox;
    }

    private void handleSignUp() {
        // Validate input fields
        if (userText.getText().isEmpty() || nameText.getText().isEmpty() || passText.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Incomplete Information", "Please fill all required fields.");
            return;
        }

        String sloginAs = loginASCho.getValue();
        String susername = userText.getText();
        String sname = nameText.getText();
        String spassword = passText.getText();
        String smeter = meterText.getText();

        // For Customer type, validate meter number
        if (sloginAs.equals("Customer") && smeter.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Information", "Please enter a meter number.");
            return;
        }

        // For Admin type, validate employer ID
        if (sloginAs.equals("Admin") && employerText.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Information", "Please enter an employer ID.");
            return;
        }

        try {
            database c = new database();
            String query;
            if (sloginAs.equals("Admin")) {
                query = "INSERT INTO Signup (meter_no, username, name, password, usertype) VALUES ('"
                        + employerText.getText() + "', '" + susername + "', '" + sname + "', '" + spassword + "', '" + sloginAs + "')";
            } else {
                query = "UPDATE Signup SET username = '" + susername + "', password = '"
                        + spassword + "', usertype = '" + sloginAs + "' WHERE meter_no = '" + smeter + "'";
            }
            c.statement.executeUpdate(query);

            showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!");
            primaryStage.close();
            openLogin();
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Registration Failed", "An error occurred during registration. Please try again.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Style the alert dialog
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        // Style the buttons
        dialogPane.lookupButton(ButtonType.OK).setStyle(
                "-fx-background-color: " + PRIMARY_COLOR + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold;"
        );

        alert.showAndWait();
    }

    private void openLogin() {
        try {
            new Login().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}