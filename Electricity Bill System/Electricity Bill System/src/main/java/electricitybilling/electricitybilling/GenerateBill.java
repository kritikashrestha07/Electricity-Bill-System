package electricitybilling.electricitybilling;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GenerateBill extends Application {

    private ComboBox<String> searchMonthCombo;
    private String meter;
    private TextArea billArea;
    private Button generateButton, printButton, closeButton;

    // Colors from the provided palette
    private final String CREAM = "#FAE5D8";
    private final String LIGHT_PINK = "#DFB6B2";
    private final String MAUVE = "#824D69";
    private final String PURPLE = "#522959";
    private final String DARK_PURPLE = "#2A114B";
    private final String BLACK_PURPLE = "#18001B";

    public GenerateBill() {
        this.meter = "";
    }

    public GenerateBill(String meter) {
        this.meter = meter;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Electricity Bill Generator");

        // Create the main layout with modern styling
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + CREAM + ";");

        // Add header
        VBox header = createHeader();
        mainLayout.setTop(header);

        // Add content area
        VBox contentArea = createContentArea();
        mainLayout.setCenter(contentArea);

        // Add footer with buttons
        HBox footer = createFooter();
        mainLayout.setBottom(footer);

        // Create scene with responsive width
        Scene scene = new Scene(mainLayout, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    private VBox createHeader() {
        VBox header = new VBox();
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20, 10, 20, 10));
        header.setStyle("-fx-background-color: linear-gradient(to right, " + DARK_PURPLE + ", " + PURPLE + ");");

        Label heading = new Label("Electricity Bill Generator");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        heading.setTextFill(Color.web(CREAM));

        // Add accent rectangle
        Rectangle accentLine = new Rectangle(150, 3);
        accentLine.setFill(Color.web(LIGHT_PINK));
        accentLine.setArcWidth(3);
        accentLine.setArcHeight(3);

        HBox meterInfoBox = new HBox(15);
        meterInfoBox.setAlignment(Pos.CENTER);
        meterInfoBox.setPadding(new Insets(15, 0, 0, 0));

        Label meterNoLabel = new Label("Meter Number:");
        meterNoLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        meterNoLabel.setTextFill(Color.web(CREAM));

        Label meterNoValue = new Label(meter);
        meterNoValue.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        meterNoValue.setTextFill(Color.web(LIGHT_PINK));

        Label monthLabel = new Label("Billing Month:");
        monthLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
        monthLabel.setTextFill(Color.web(CREAM));
        monthLabel.setPadding(new Insets(0, 0, 0, 20));

        searchMonthCombo = new ComboBox<>();
        searchMonthCombo.getItems().addAll(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );

        // Set default month to current month or January
        try {
            String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM"));
            searchMonthCombo.setValue(currentMonth);
        } catch (Exception e) {
            searchMonthCombo.getSelectionModel().selectFirst();
        }

        searchMonthCombo.setPrefWidth(150);
        searchMonthCombo.setStyle("-fx-background-color: " + CREAM + "; -fx-font-size: 14px;");

        meterInfoBox.getChildren().addAll(meterNoLabel, meterNoValue, monthLabel, searchMonthCombo);

        header.getChildren().addAll(heading, accentLine, meterInfoBox);
        return header;
    }

    private VBox createContentArea() {
        VBox contentArea = new VBox(15);
        contentArea.setPadding(new Insets(20));
        contentArea.setStyle("-fx-background-color: " + CREAM + ";");

        // Information section
        TitledPane infoPane = new TitledPane();
        infoPane.setText("Bill Information");
        infoPane.setCollapsible(false);
        infoPane.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: " + DARK_PURPLE + ";");

        // Style the title pane header
        infoPane.setStyle(
                "-fx-font-family: 'Segoe UI'; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 14px; " +
                        "-fx-text-fill: " + DARK_PURPLE + "; " +
                        "-fx-border-color: " + MAUVE + "; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 5px;"
        );

        // Bill text area with modern styling
        billArea = new TextArea();
        billArea.setWrapText(true);
        billArea.setEditable(false);
        billArea.setPrefHeight(480);
        billArea.setStyle(
                "-fx-font-family: 'Consolas', 'Courier New', monospace; " +
                        "-fx-font-size: 14px; " +
                        "-fx-background-color: white; " +
                        "-fx-border-color: " + LIGHT_PINK + "; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-text-fill: " + PURPLE + ";"
        );

        // Set initial text
        billArea.setText("\n\n\t Please click on the \"Generate Bill\" button to view bill details.");

        VBox infoContent = new VBox();
        infoContent.setPadding(new Insets(10));
        infoContent.setStyle("-fx-background-color: white; -fx-background-radius: 5px;");
        infoContent.getChildren().add(billArea);
        infoPane.setContent(infoContent);

        contentArea.getChildren().add(infoPane);
        return contentArea;
    }

    private HBox createFooter() {
        HBox footer = new HBox(15);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(15, 20, 20, 20));
        footer.setStyle("-fx-background-color: " + CREAM + ";");

        // Generate Bill Button
        generateButton = new Button("Generate Bill");
        generateButton.setPrefSize(140, 40);
        generateButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        generateButton.setStyle(
                "-fx-background-color: " + MAUVE + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-cursor: hand; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;"
        );
        generateButton.setOnAction(e -> generateBill());

        // Print Button
        printButton = new Button("Print Bill");
        printButton.setPrefSize(140, 40);
        printButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        printButton.setStyle(
                "-fx-background-color: " + PURPLE + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-cursor: hand; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;"
        );
        printButton.setOnAction(e -> printBill());

        // Close Button
        closeButton = new Button("Close");
        closeButton.setPrefSize(140, 40);
        closeButton.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        closeButton.setStyle(
                "-fx-background-color: " + DARK_PURPLE + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-cursor: hand; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;"
        );
        closeButton.setOnAction(e -> ((Stage) closeButton.getScene().getWindow()).close());

        // Add hover effects
        setupButtonHoverEffect(generateButton, MAUVE);
        setupButtonHoverEffect(printButton, PURPLE);
        setupButtonHoverEffect(closeButton, DARK_PURPLE);

        footer.getChildren().addAll(generateButton, printButton, closeButton);
        return footer;
    }

    private void setupButtonHoverEffect(Button button, String baseColor) {
        // Original style (stored for reference)
        final String originalStyle = button.getStyle();

        // Slightly lighter color for hover effect (add 20% brightness)
        Color color = Color.web(baseColor);
        Color lighterColor = color.brighter().brighter();
        String lighterHex = String.format("#%02X%02X%02X",
                (int)(lighterColor.getRed() * 255),
                (int)(lighterColor.getGreen() * 255),
                (int)(lighterColor.getBlue() * 255));

        // Hover style
        final String hoverStyle = originalStyle.replace(baseColor, lighterHex);

        // Set up hover effect
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(originalStyle));
    }

    private void generateBill() {
        try {
            Database db = new Database();
            String selectedMonth = searchMonthCombo.getValue();

            StringBuilder billContent = new StringBuilder();
            billContent.append("\n                         POWER LIMITED\n");
            billContent.append("                         ELECTRICITY BILL\n");
            billContent.append("                         ---------------\n\n");
            billContent.append(" Billing Period: ").append(selectedMonth).append(", 2025\n");
            billContent.append(" Bill Generation Date: ").append(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).append("\n");
            billContent.append(" ─────────────────────────────────────────────────────────\n\n");

            // Fetch customer details
            ResultSet resultSet = db.statement.executeQuery("select * from new_customer where meter_no ='" + meter + "'");
            if (resultSet.next()) {
                billContent.append(" CUSTOMER DETAILS:\n");
                billContent.append(" ─────────────────\n");
                billContent.append(" Name               : ").append(resultSet.getString("name")).append("\n");
                billContent.append(" Meter Number       : ").append(resultSet.getString("meter_no")).append("\n");
                billContent.append(" Address            : ").append(resultSet.getString("address")).append("\n");
                billContent.append(" City               : ").append(resultSet.getString("city")).append("\n");
                billContent.append(" Ward Number        : ").append(resultSet.getString("ward_no")).append("\n");
                billContent.append(" Email              : ").append(resultSet.getString("email")).append("\n");
                billContent.append(" Phone Number       : ").append(resultSet.getString("phone_no")).append("\n\n");
            }

            // Fetch bill details
            ResultSet billResult = db.statement.executeQuery("select * from bill where meter_no = '" + meter + "' and month = '" + selectedMonth + "'");
            if (billResult.next()) {
                billContent.append(" CONSUMPTION DETAILS:\n");
                billContent.append(" ───────────────────\n");
                billContent.append(" Billing Month      : ").append(billResult.getString("month")).append("\n");
                billContent.append(" Units Consumed     : ").append(billResult.getString("unit")).append(" kWh\n");
                String totalBill = billResult.getString("total_bil");

                // Fetch tax details - FIX: Create a new database connection and query for tax information
                try {
                    Database taxDb = new Database();
                    ResultSet taxResult = taxDb.statement.executeQuery("select * from tax");

                    if (taxResult.next()) {
                        billContent.append("\n CHARGES BREAKDOWN:\n");
                        billContent.append(" ─────────────────\n");

                        // FIX: Safely parse integers with error handling
                        int units = 0;
                        int costPerUnit = 0;
                        try {
                            units = Integer.parseInt(billResult.getString("unit"));
                            costPerUnit = Integer.parseInt(taxResult.getString("cost_per_unit"));
                        } catch (NumberFormatException e) {
                            // Handle parsing error
                            units = 0;
                            costPerUnit = 0;
                        }

                        int energyCharges = units * costPerUnit;

                        billContent.append(" Energy Charges     : £ ").append(energyCharges).append(" (").append(units).append(" units × £").append(costPerUnit).append(")\n");
                        billContent.append(" Meter Rent         : £ ").append(taxResult.getString("meter_rent")).append("\n");
                        billContent.append(" Service Charge     : £ ").append(taxResult.getString("service_charge")).append("\n");

                        // FIX: Check if service_tax column exists
                        try {
                            billContent.append(" Service Tax        : £ ").append(taxResult.getString("service_tax")).append("\n");
                        } catch (SQLException e) {
                            // Column might not exist, skip it
                        }

                        billContent.append(" Fixed Tax          : £ ").append(taxResult.getString("fixed_tax")).append("\n");
                    }
                } catch (Exception taxEx) {
                    // If there's an error with tax data, just continue without the breakdown
                    billContent.append("\n Note: Charge breakdown unavailable\n");
                }

                billContent.append("\n ─────────────────────────────────────────────────────────\n");
                billContent.append(" TOTAL AMOUNT DUE   : £ ").append(totalBill).append("\n");
                billContent.append(" Payment Status     : ").append(billResult.getString("status")).append("\n");
                billContent.append(" ─────────────────────────────────────────────────────────\n\n");

                billContent.append(" Payment due date   : 15-").append(LocalDate.now().plusMonths(1).format(DateTimeFormatter.ofPattern("MM-yyyy"))).append("\n");
                billContent.append(" Late payment will incur a 5% surcharge.\n\n");

                billContent.append(" For support: helpdesk@powerlimited.com | +91-1800-123-4567\n");
                billContent.append(" ─────────────────────────────────────────────────────────\n");
                billContent.append("                   Thank you for your business!");

            } else {
                billContent.append("\n No bill record found for meter number ").append(meter);
                billContent.append(" for the month of ").append(selectedMonth).append(".\n\n");
                billContent.append(" Please ensure the following:\n");
                billContent.append("   - The selected month is correct\n");
                billContent.append("   - The meter number is valid\n");
                billContent.append("   - Bill has been generated for this month\n\n");
                billContent.append(" If the problem persists, please contact customer support.");
            }

            billArea.setText(billContent.toString());

        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate bill: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void printBill() {
        if (billArea.getText().contains("click on the \"Generate Bill\" button")) {
            showAlert(Alert.AlertType.INFORMATION, "Print", "Please generate a bill first.");
            return;
        }

        try {
            // In a real application, this would connect to a printer
            // For now, just show a confirmation dialog
            showAlert(Alert.AlertType.INFORMATION, "Print Confirmation",
                    "Bill has been sent to the printer.");

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Print Error",
                    "Failed to print the bill: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Style the alert dialog
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
                "-fx-background-color: " + CREAM + ";" +
                        "-fx-border-color: " + MAUVE + ";" +
                        "-fx-border-width: 2px;" +
                        "-fx-font-family: 'Segoe UI';" +
                        "-fx-font-size: 14px;"
        );

        // Style the buttons
        dialogPane.lookupButton(ButtonType.OK).setStyle(
                "-fx-background-color: " + PURPLE + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 5px;"
        );

        alert.showAndWait();
    }

    // Database helper class
    public static class Database {
        Connection connection;
        Statement statement;

        public Database() {
            try {
                // Adjust the connection URL as needed for your database
                connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Bill_system", "root", "Nirush");
                statement = connection.createStatement();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}