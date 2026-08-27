package electricitybilling.electricitybilling;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomerDetails extends Application {

    private ComboBox<String> searchMeterCombo, searchNameCombo;
    private TableView<Customer> table;
    private Button search, print, close, reset;

    // Color palette from the image
    private static final String COLOR_CREAM = "#FAE5D8";
    private static final String COLOR_LIGHT_PINK = "#DFB5B2";
    private static final String COLOR_MAUVE = "#82476D";
    private static final String COLOR_DEEP_PURPLE = "#522959";
    private static final String COLOR_DARK_NAVY = "#2A114B";
    private static final String COLOR_BLACK = "#18001B";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Customer Details - Electricity Billing System");

        // Create main layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, " + COLOR_DEEP_PURPLE + ", " + COLOR_LIGHT_PINK + ");");
        root.setPadding(new Insets(20));

        // Header
        Label headerLabel = new Label("Customer Information Portal");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setStyle("-fx-text-fill: " + COLOR_CREAM + ";");

        HBox headerBox = new HBox(headerLabel);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(0, 0, 15, 0));

        // Search panel
        VBox searchPanel = new VBox(15);
        searchPanel.setPadding(new Insets(15));
        searchPanel.setStyle("-fx-background-color: rgba(255, 255, 255, 0.15); -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");

        HBox searchControls = new HBox(20);
        searchControls.setAlignment(Pos.CENTER_LEFT);

        // Search by meter number
        VBox meterBox = new VBox(8);
        Label searchMeter = new Label("Search By Meter Number");
        searchMeter.setStyle("-fx-font-weight: bold; -fx-text-fill: " + COLOR_CREAM + ";");
        searchMeterCombo = new ComboBox<>();
        searchMeterCombo.setPrefWidth(200);
        searchMeterCombo.setPromptText("Select Meter Number");
        searchMeterCombo.setStyle("-fx-background-color: " + COLOR_CREAM + "; -fx-background-radius: 5;");
        meterBox.getChildren().addAll(searchMeter, searchMeterCombo);

        // Search by name
        VBox nameBox = new VBox(8);
        Label searchName = new Label("Search By Name");
        searchName.setStyle("-fx-font-weight: bold; -fx-text-fill: " + COLOR_CREAM + ";");
        searchNameCombo = new ComboBox<>();
        searchNameCombo.setPrefWidth(200);
        searchNameCombo.setPromptText("Select Customer Name");
        searchNameCombo.setStyle("-fx-background-color: " + COLOR_CREAM + "; -fx-background-radius: 5;");
        nameBox.getChildren().addAll(searchName, searchNameCombo);

        searchControls.getChildren().addAll(meterBox, nameBox);
        HBox.setHgrow(meterBox, Priority.ALWAYS);
        HBox.setHgrow(nameBox, Priority.ALWAYS);

        // Load meter numbers and names from database
        loadComboBoxData();

        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        search = createStyledButton("Search", COLOR_MAUVE);
        search.setOnAction(e -> searchCustomers());

        reset = createStyledButton("Reset", COLOR_DEEP_PURPLE);
        reset.setOnAction(e -> loadAllCustomers());

        print = createStyledButton("Print", COLOR_DARK_NAVY);
        print.setOnAction(e -> printTable());

        close = createStyledButton("Close", COLOR_BLACK);
        close.setOnAction(e -> primaryStage.close());

        buttonBox.getChildren().addAll(search, reset, print, close);

        searchPanel.getChildren().addAll(searchControls, buttonBox);

        // Create table with styling
        table = new TableView<>();
        table.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-background-radius: 8; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 8, 0, 0, 3);");
        table.setPrefSize(800, 500);

        // Style the table header
        table.getStylesheets().add("data:text/css," +
                ".table-view .column-header {" +
                "    -fx-background-color: " + COLOR_DARK_NAVY + ";" +
                "    -fx-text-fill: white;" +
                "}" +
                ".table-view .column-header .label {" +
                "    -fx-text-fill: " + COLOR_CREAM + ";" +
                "    -fx-font-weight: bold;" +
                "}" +
                ".table-row-cell:odd {" +
                "    -fx-background-color: rgba(250, 229, 216, 0.1);" +
                "}" +
                ".table-row-cell:even {" +
                "    -fx-background-color: rgba(223, 181, 178, 0.1);" +
                "}");

        // Add table columns
        TableColumn<Customer, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Customer, String> meterColumn = new TableColumn<>("Meter No");
        meterColumn.setCellValueFactory(new PropertyValueFactory<>("meterNo"));

        TableColumn<Customer, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Customer, String> cityColumn = new TableColumn<>("City");
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<Customer, String> wardColumn = new TableColumn<>("Ward No");
        wardColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

        TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Customer, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        table.getColumns().addAll(nameColumn, meterColumn, addressColumn, cityColumn,
                wardColumn, emailColumn, phoneColumn);

        // Set equal width for columns
        nameColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
        meterColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.12));
        addressColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.18));
        cityColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.12));
        wardColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.12));
        emailColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.18));
        phoneColumn.prefWidthProperty().bind(table.widthProperty().multiply(0.13));

        // Add table to a container with padding
        VBox tableContainer = new VBox(10);
        tableContainer.setPadding(new Insets(10, 0, 0, 0));
        tableContainer.getChildren().add(table);

        // Status bar
        Label statusLabel = new Label("Ready - Displaying all customer records");
        statusLabel.setStyle("-fx-text-fill: " + COLOR_CREAM + ";");
        HBox statusBar = new HBox(statusLabel);
        statusBar.setPadding(new Insets(10, 0, 0, 0));
        statusBar.setAlignment(Pos.CENTER_LEFT);

        // Combine all layout elements
        VBox mainContent = new VBox(10);
        mainContent.getChildren().addAll(headerBox, searchPanel, tableContainer, statusBar);
        root.setCenter(mainContent);

        // Load initial data
        loadAllCustomers();

        // Set scene
        Scene scene = new Scene(root, 990, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setPrefWidth(120);
        button.setPrefHeight(35);
        button.setStyle(
                "-fx-background-color: " + color + "; " +
                        "-fx-text-fill: " + COLOR_CREAM + "; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-radius: 5; " +
                        "-fx-cursor: hand;"
        );
        return button;
    }

    private void loadComboBoxData() {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Initialize database connection
            conn = getConnection();
            stmt = conn.createStatement();

            // Clear existing items
            searchMeterCombo.getItems().clear();
            searchNameCombo.getItems().clear();

            // Execute query
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM new_customer");
            while (resultSet.next()) {
                searchMeterCombo.getItems().add(resultSet.getString("meter_no"));
                searchNameCombo.getItems().add(resultSet.getString("name"));
            }
        } catch (Exception e) {
            showAlert("Error loading data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt);
        }
    }

    private void loadAllCustomers() {
        Connection conn = null;
        Statement stmt = null;
        try {
            // Initialize database connection
            conn = getConnection();
            stmt = conn.createStatement();

            // Execute query
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM new_customer");

            ObservableList<Customer> customers = FXCollections.observableArrayList();
            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getString("name"),
                        resultSet.getString("meter_no"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("ward_no"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_no")
                ));
            }

            table.setItems(customers);

            // Clear selections
            searchMeterCombo.setValue(null);
            searchNameCombo.setValue(null);
        } catch (Exception e) {
            showAlert("Error loading customers: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt);
        }
    }

    private void searchCustomers() {
        String selectedMeter = searchMeterCombo.getValue();
        String selectedName = searchNameCombo.getValue();

        if (selectedMeter == null && selectedName == null) {
            showAlert("Please select at least one search criteria");
            return;
        }

        Connection conn = null;
        Statement stmt = null;
        try {
            // Initialize database connection
            conn = getConnection();
            stmt = conn.createStatement();

            // Build query
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM new_customer WHERE ");

            if (selectedMeter != null && selectedName != null) {
                queryBuilder.append("meter_no = '").append(selectedMeter).append("' AND name = '").append(selectedName).append("'");
            } else if (selectedMeter != null) {
                queryBuilder.append("meter_no = '").append(selectedMeter).append("'");
            } else {
                queryBuilder.append("name = '").append(selectedName).append("'");
            }

            // Execute query
            ResultSet resultSet = stmt.executeQuery(queryBuilder.toString());

            ObservableList<Customer> customers = FXCollections.observableArrayList();
            while (resultSet.next()) {
                customers.add(new Customer(
                        resultSet.getString("name"),
                        resultSet.getString("meter_no"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("ward_no"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_no")
                ));
            }

            table.setItems(customers);

            if (customers.isEmpty()) {
                showAlert("No customers found matching the criteria");
            }
        } catch (Exception e) {
            showAlert("Error searching customers: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt);
        }
    }

    private void printTable() {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(table);
            if (success) {
                job.endJob();
                showAlert("Printing completed successfully");
            } else {
                showAlert("Printing failed");
            }
        } else {
            showAlert("Could not create printer job");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Style the alert
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: " + COLOR_CREAM + "; -fx-border-color: " + COLOR_MAUVE + "; -fx-border-width: 2px;");

        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        okButton.setStyle("-fx-background-color: " + COLOR_MAUVE + "; -fx-text-fill: " + COLOR_CREAM + ";");

        alert.showAndWait();
    }

    // Database helper methods
    private Connection getConnection() throws Exception {
        // Update these values according to your database settings
        String url = "jdbc:mysql://127.0.0.1:3306/Bill_system";
        String user = "root";
        String password = "Nirush";

        // Load the driver (may not be necessary in newer Java versions)
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Create the connection
        return DriverManager.getConnection(url, user, password);
    }

    private void closeResources(Connection conn, Statement stmt) {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Customer model class - renamed state field to wardNo for clarity
    public static class Customer {
        private String name;
        private String meterNo;
        private String address;
        private String city;
        private String state; // This actually holds ward_no data
        private String email;
        private String phone;

        public Customer(String name, String meterNo, String address, String city,
                        String state, String email, String phone) {
            this.name = name;
            this.meterNo = meterNo;
            this.address = address;
            this.city = city;
            this.state = state;
            this.email = email;
            this.phone = phone;
        }

        public String getName() { return name; }
        public String getMeterNo() { return meterNo; }
        public String getAddress() { return address; }
        public String getCity() { return city; }
        public String getState() { return state; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
    }

    public static void main(String[] args) {
        launch(args);
    }
}