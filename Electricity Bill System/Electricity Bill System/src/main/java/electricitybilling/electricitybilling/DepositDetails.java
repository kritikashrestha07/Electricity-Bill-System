package electricitybilling.electricitybilling;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.ResultSet;

public class DepositDetails extends Application implements EventHandler<ActionEvent> {

    private ComboBox<String> searchMeterCombo, searchMonthCombo;
    private TableView<Bill> table;
    private Button search, print, close;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Electricity Billing System - Deposit Details");

        // Create root layout
        BorderPane mainLayout = new BorderPane();

        // Create header panel
        VBox headerPane = new VBox(10);
        headerPane.setPadding(new Insets(15, 15, 15, 15));
        headerPane.setStyle("-fx-background-color: linear-gradient(to right, #4568dc, #b06ab3);");

        Label titleLabel = new Label("Deposit Details");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.WHITE);
        headerPane.getChildren().add(titleLabel);

        // Create search panel
        HBox searchPane = new HBox(20);
        searchPane.setPadding(new Insets(10, 15, 10, 15));
        searchPane.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");

        // Search by meter section
        VBox meterBox = new VBox(5);
        Label searchMeter = new Label("Search By Meter Number");
        searchMeter.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        searchMeterCombo = new ComboBox<>();
        searchMeterCombo.setPrefWidth(180);
        searchMeterCombo.setPromptText("Select Meter Number");
        searchMeterCombo.setStyle("-fx-font-size: 12px;");
        meterBox.getChildren().addAll(searchMeter, searchMeterCombo);

        // Populate meter number combo box
        try {
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from bill");
            while (resultSet.next()) {
                searchMeterCombo.getItems().add(resultSet.getString("meter_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Search by month section
        VBox monthBox = new VBox(5);
        Label searchMonth = new Label("Search By Month");
        searchMonth.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        searchMonthCombo = new ComboBox<>();
        searchMonthCombo.getItems().addAll(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );
        searchMonthCombo.setPrefWidth(180);
        searchMonthCombo.setPromptText("Select Month");
        searchMonthCombo.setStyle("-fx-font-size: 12px;");
        monthBox.getChildren().addAll(searchMonth, searchMonthCombo);

        // Action buttons
        VBox buttonBox = new VBox(10);
        buttonBox.setPadding(new Insets(15, 0, 0, 0));

        HBox actionButtons = new HBox(10);

        search = new Button("Search");
        search.setStyle("-fx-background-color: #4568dc; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 15;");
        search.setPrefWidth(100);
        search.setOnAction(this);

        print = new Button("Print");
        print.setStyle("-fx-background-color: #5c6bc0; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 15;");
        print.setPrefWidth(100);
        print.setOnAction(this);

        close = new Button("Close");
        close.setStyle("-fx-background-color: #7986cb; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 5 15;");
        close.setPrefWidth(100);
        close.setOnAction(this);

        actionButtons.getChildren().addAll(search, print, close);
        buttonBox.getChildren().add(actionButtons);

        searchPane.getChildren().addAll(meterBox, monthBox, buttonBox);

        // Create and set up table
        table = new TableView<>();
        table.setPrefSize(700, 400);
        table.setStyle("-fx-font-size: 12px;");

        // Add table to a VBox with padding
        VBox tableContainer = new VBox(table);
        tableContainer.setPadding(new Insets(10));
        tableContainer.setStyle("-fx-background-color: white;");
        VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);

        // Create table columns
        populateTableWithData();

        // Assemble all parts
        mainLayout.setTop(headerPane);
        mainLayout.setCenter(tableContainer);
        mainLayout.setBottom(searchPane);

        // Create scene and add to stage
        Scene scene = new Scene(mainLayout, 850, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void populateTableWithData() {
        // Create table columns dynamically based on database schema
        try {
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from bill");

            // Get column names
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = resultSet.getMetaData().getColumnName(i);
                TableColumn<Bill, String> column = new TableColumn<>(formatColumnName(columnName));

                // FIX: Use custom cell value factory instead of PropertyValueFactory
                final String colName = columnName.toLowerCase();
                column.setCellValueFactory(cellData -> {
                    String value = cellData.getValue().getProperty(colName);
                    return new javafx.beans.property.SimpleStringProperty(value);
                });

                column.setPrefWidth(100);
                table.getColumns().add(column);
            }

            // Populate data
            ObservableList<Bill> data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Bill bill = new Bill();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSet.getMetaData().getColumnName(i);
                    bill.setProperty(columnName.toLowerCase(), resultSet.getString(columnName));
                }
                data.add(bill);
            }
            table.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper method to format column names for display
    private String formatColumnName(String columnName) {
        String[] parts = columnName.split("_");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (part.length() > 0) {
                sb.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == search) {
            // Validate selections
            if (searchMeterCombo.getValue() == null || searchMonthCombo.getValue() == null) {
                showAlert("Please select both meter number and month");
                return;
            }

            String querySearch = "select * from bill where meter_no = '" + searchMeterCombo.getValue() +
                    "' and month = '" + searchMonthCombo.getValue() + "'";
            try {
                database c = new database();
                ResultSet resultSet = c.statement.executeQuery(querySearch);

                // Clear existing data
                table.getItems().clear();

                // Populate with new data
                ObservableList<Bill> data = FXCollections.observableArrayList();
                while (resultSet.next()) {
                    Bill bill = new Bill();
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        String columnName = resultSet.getMetaData().getColumnName(i);
                        bill.setProperty(columnName.toLowerCase(), resultSet.getString(columnName));
                    }
                    data.add(bill);
                }
                table.setItems(data);

                if (data.isEmpty()) {
                    showAlert("No records found for the selected criteria");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error executing search: " + e.getMessage());
            }
        } else if (event.getSource() == print) {
            try {
                // Check if table has data
                if (table.getItems().isEmpty()) {
                    showAlert("No data to print");
                    return;
                }

                javafx.print.PrinterJob job = javafx.print.PrinterJob.createPrinterJob();
                if (job != null && job.showPrintDialog(table.getScene().getWindow())) {
                    boolean success = job.printPage(table);
                    if (success) {
                        job.endJob();
                        showInformation("Print job completed successfully");
                    } else {
                        showAlert("Printing failed");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert("Error during printing: " + e.getMessage());
            }
        } else if (event.getSource() == close) {
            Stage stage = (Stage) close.getScene().getWindow();
            stage.close();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper class to dynamically store bill data
    public static class Bill {
        private final java.util.Map<String, String> properties = new java.util.HashMap<>();

        public void setProperty(String key, String value) {
            properties.put(key, value);
        }

        public String getProperty(String key) {
            return properties.get(key);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}