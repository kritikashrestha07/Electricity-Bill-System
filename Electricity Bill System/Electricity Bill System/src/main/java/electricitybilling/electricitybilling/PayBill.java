package electricitybilling.electricitybilling;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.ResultSet;

public class PayBill extends Application {

    private ComboBox<String> monthComboBox;
    private String meter;
    private Label meterNumberText, nameText, unitText, totalBillText, statusText;
    private Button payButton, backButton;

    // Mindazzle color palette
    private final String COLOR_CREAM = "#FAE5D8";
    private final String COLOR_LIGHT_PINK = "#DFB6B2";
    private final String COLOR_MAUVE = "#824D69";
    private final String COLOR_PURPLE = "#522959";
    private final String COLOR_DEEP_PURPLE = "#2A114B";
    private final String COLOR_BLACK_PURPLE = "#18001B";

    public PayBill() {
        this.meter = "";
    }

    public PayBill(String meter) {
        this.meter = meter;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Pay Bill");

        // Create main layout with purple gradient background
        VBox mainLayout = new VBox(20);
        mainLayout.setPadding(new Insets(30));
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to right, " + COLOR_DEEP_PURPLE + ", " + COLOR_PURPLE + ");");

        // Header with improved styling
        Label heading = new Label("Pay Bill");
        heading.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        heading.setTextFill(Color.web(COLOR_CREAM));
        heading.setPadding(new Insets(0, 0, 15, 0));

        // Card-like container for form
        VBox formContainer = new VBox(20);
        formContainer.setStyle("-fx-background-color: rgba(250, 229, 216, 0.95); -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 3); -fx-background-radius: 12;");
        formContainer.setPadding(new Insets(25));

        // Form layout
        GridPane formGrid = new GridPane();
        formGrid.setHgap(20);
        formGrid.setVgap(20);
        formGrid.setAlignment(Pos.CENTER);

        // Labels for form fields with improved styling
        Label meterNumberLabel = createStyledLabel("Meter Number:");
        Label nameLabel = createStyledLabel("Name:");
        Label monthLabel = createStyledLabel("Month:");
        Label unitLabel = createStyledLabel("Unit:");
        Label totalBillLabel = createStyledLabel("Total Bill:");
        Label statusLabel = createStyledLabel("Status:");

        // Text fields for displaying information with improved styling
        meterNumberText = createValueLabel();
        nameText = createValueLabel();
        unitText = createValueLabel();
        totalBillText = createValueLabel();
        statusText = createValueLabel();

        // Month selection dropdown with improved styling
        monthComboBox = new ComboBox<>();
        monthComboBox.getItems().addAll(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );
        monthComboBox.setPrefWidth(250);
        monthComboBox.getSelectionModel().selectFirst();
        monthComboBox.setStyle("-fx-font-size: 14px; -fx-background-radius: 6; -fx-background-color: white; -fx-text-fill: " + COLOR_DEEP_PURPLE + ";");

        // Add components to the grid
        formGrid.add(meterNumberLabel, 0, 0);
        formGrid.add(meterNumberText, 1, 0);
        formGrid.add(nameLabel, 0, 1);
        formGrid.add(nameText, 1, 1);
        formGrid.add(monthLabel, 0, 2);
        formGrid.add(monthComboBox, 1, 2);
        formGrid.add(unitLabel, 0, 3);
        formGrid.add(unitText, 1, 3);
        formGrid.add(totalBillLabel, 0, 4);
        formGrid.add(totalBillText, 1, 4);
        formGrid.add(statusLabel, 0, 5);
        formGrid.add(statusText, 1, 5);

        // Add a separator before buttons
        Separator separator = new Separator();
        separator.setPadding(new Insets(10, 0, 10, 0));
        separator.setStyle("-fx-background-color: " + COLOR_MAUVE + ";");

        // Buttons with improved styling
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 5, 0));

        payButton = new Button("Pay");
        payButton.setPrefWidth(120);
        payButton.setPrefHeight(40);
        payButton.setStyle("-fx-background-color: " + COLOR_DEEP_PURPLE + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 6;");
        payButton.setOnMouseEntered(e -> payButton.setStyle("-fx-background-color: " + COLOR_BLACK_PURPLE + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 6;"));
        payButton.setOnMouseExited(e -> payButton.setStyle("-fx-background-color: " + COLOR_DEEP_PURPLE + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 6;"));

        backButton = new Button("Back");
        backButton.setPrefWidth(120);
        backButton.setPrefHeight(40);
        backButton.setStyle("-fx-background-color: " + COLOR_MAUVE + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 6;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: " + COLOR_PURPLE + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 6;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: " + COLOR_MAUVE + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; -fx-background-radius: 6;"));

        buttonBox.getChildren().addAll(payButton, backButton);

        // Add form elements to container
        formContainer.getChildren().addAll(formGrid, separator, buttonBox);

        // Add all components to main layout
        mainLayout.getChildren().addAll(heading, formContainer);

        // Set up the scene
        Scene scene = new Scene(mainLayout, 650, 550);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load customer details
        loadCustomerDetails();

        // Set up event handlers
        setupEventHandlers(primaryStage);
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        label.setTextFill(Color.web(COLOR_PURPLE));
        return label;
    }

    private Label createValueLabel() {
        Label label = new Label();
        label.setFont(Font.font("Arial", 14));
        label.setTextFill(Color.web(COLOR_DEEP_PURPLE));
        label.setMinWidth(250);
        label.setStyle("-fx-padding: 5 10; -fx-background-color: white; -fx-background-radius: 6;");
        return label;
    }

    private void loadCustomerDetails() {
        Database db = null;
        try {
            db = new Database();
            if (db.statement != null) {
                ResultSet resultSet = db.statement.executeQuery("SELECT * FROM new_customer WHERE meter_no = '" + meter + "'");
                if (resultSet.next()) {
                    meterNumberText.setText(meter);
                    nameText.setText(resultSet.getString("name"));
                }

                // Load initial bill details for the selected month
                loadBillDetails();
            } else {
                showAlert("Database Error", "Failed to establish database connection.");
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to load customer details: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null && db.connection != null) {
                try {
                    db.connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadBillDetails() {
        Database db = null;
        try {
            db = new Database();
            if (db.statement != null) {
                ResultSet resultSet = db.statement.executeQuery(
                        "SELECT * FROM bill WHERE meter_no = '" + meter + "' AND month = '" +
                                monthComboBox.getValue() + "'"
                );

                if (resultSet.next()) {
                    unitText.setText(resultSet.getString("unit"));
                    totalBillText.setText(resultSet.getString("total_bil"));
                    String status = resultSet.getString("status");
                    statusText.setText(status);

                    // Set status text color based on payment status
                    if ("Paid".equals(status)) {
                        statusText.setTextFill(Color.web("#27ae60"));
                        statusText.setStyle("-fx-padding: 5 10; -fx-background-color: #e8f8f5; -fx-background-radius: 6;");
                        payButton.setDisable(true);
                    } else {
                        statusText.setTextFill(Color.web("#e74c3c"));
                        statusText.setStyle("-fx-padding: 5 10; -fx-background-color: #fdedec; -fx-background-radius: 6;");
                        payButton.setDisable(false);
                    }
                } else {
                    unitText.setText("-");
                    totalBillText.setText("-");
                    statusText.setText("No Bill Found");
                    statusText.setTextFill(Color.web(COLOR_MAUVE));
                    statusText.setStyle("-fx-padding: 5 10; -fx-background-color: white; -fx-background-radius: 6;");
                    payButton.setDisable(true);
                }
            } else {
                showAlert("Database Error", "Failed to establish database connection.");
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to load bill details: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null && db.connection != null) {
                try {
                    db.connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setupEventHandlers(Stage primaryStage) {
        // Month selection listener
        monthComboBox.setOnAction(e -> loadBillDetails());

        // Pay button action
        payButton.setOnAction(e -> {
            Database db = null;
            try {
                db = new Database();
                if (db.statement != null) {
                    int result = db.statement.executeUpdate(
                            "UPDATE bill SET status = 'Paid' WHERE meter_no = '" + meter +
                                    "' AND month = '" + monthComboBox.getValue() + "'"
                    );

                    if (result > 0) {
                        statusText.setText("Paid");
                        statusText.setTextFill(Color.web("#27ae60"));
                        statusText.setStyle("-fx-padding: 5 10; -fx-background-color: #e8f8f5; -fx-background-radius: 6;");
                        payButton.setDisable(true);
                        showSuccessAlert("Success", "Payment Successful!");
                        primaryStage.close();
                    } else {
                        showAlert("Error", "Payment failed. Please try again.");
                    }
                } else {
                    showAlert("Database Error", "Failed to establish database connection.");
                }
            } catch (Exception ex) {
                showAlert("Error", "Payment processing error: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                if (db != null && db.connection != null) {
                    try {
                        db.connection.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Back button action
        backButton.setOnAction(e -> primaryStage.close());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Style the alert dialog
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: " + COLOR_CREAM + ";");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: " + COLOR_DEEP_PURPLE + ";");
        dialogPane.getButtonTypes().stream()
                .map(dialogPane::lookupButton)
                .forEach(button -> button.setStyle("-fx-background-color: " + COLOR_DEEP_PURPLE + "; -fx-text-fill: white;"));

        alert.showAndWait();
    }

    private void showSuccessAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Style the alert dialog
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: " + COLOR_CREAM + ";");
        dialogPane.lookup(".content.label").setStyle("-fx-text-fill: " + COLOR_DEEP_PURPLE + ";");
        dialogPane.getButtonTypes().stream()
                .map(dialogPane::lookupButton)
                .forEach(button -> button.setStyle("-fx-background-color: " + COLOR_DEEP_PURPLE + "; -fx-text-fill: white;"));

        alert.showAndWait();
    }

    // Database helper class (modified to handle connection errors)
    public static class Database {
        java.sql.Connection connection;
        java.sql.Statement statement;

        public Database() {
            try {
                // Fix: Added password to MySQL connection
                // You may need to update these credentials to match your database configuration
                connection = java.sql.DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Bill_system", "root", "Nirush");
                statement = connection.createStatement();
            } catch (Exception e) {
                System.err.println("Database connection error: " + e.getMessage());
                e.printStackTrace();
                connection = null;
                statement = null;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}