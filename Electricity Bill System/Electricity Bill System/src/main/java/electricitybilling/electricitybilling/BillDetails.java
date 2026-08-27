package electricitybilling.electricitybilling;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.ResultSetMetaData;

public class BillDetails extends Application {

    private String meter;

    // Constructor for when called from another class
    public BillDetails(String meter) {
        this.meter = meter;
    }

    // Default constructor for JavaFX launch
    public BillDetails() {
        this.meter = "";
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Electricity Billing - Bill Details");

        // Create root pane
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #FAE5D8;");

        // Create header
        Label headerLabel = new Label("Bill Details");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        headerLabel.setTextFill(Color.web("#2A114B"));

        Label meterLabel = new Label("Meter No: " + meter);
        meterLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        meterLabel.setTextFill(Color.web("#522959"));

        VBox headerBox = new VBox(10);
        headerBox.setPadding(new Insets(20, 20, 10, 20));
        headerBox.getChildren().addAll(headerLabel, meterLabel);

        root.setTop(headerBox);

        // Create table with custom styling
        TableView<ObservableList<String>> table = new TableView<>();
        table.setStyle("-fx-background-color: white; -fx-table-cell-border-color: #DFB5B2;");

        // Load data into table
        try {
            database c = new database();
            String query_bill = "select * from bill where meter_no = '" + meter + "'";
            ResultSet resultSet = c.statement.executeQuery(query_bill);

            // Get column names
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Create columns
            for (int i = 1; i <= columnCount; i++) {
                final int j = i - 1;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(formatColumnName(metaData.getColumnName(i)));
                column.setCellValueFactory(param ->
                        new SimpleStringProperty(param.getValue().get(j).toString())
                );
                column.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
                column.setPrefWidth(700.0 / columnCount);
                table.getColumns().add(column);
            }

            // Add data to table
            ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }
            table.setItems(data);

            // Set row factory for alternating row colors
            table.setRowFactory(tv -> {
                TableRow<ObservableList<String>> row = new TableRow<>();
                row.setStyle("-fx-background-color: white;");
                row.setOnMouseEntered(event -> {
                    if (!row.isEmpty()) {
                        row.setStyle("-fx-background-color: #DFB5B2;");
                    }
                });
                row.setOnMouseExited(event -> {
                    if (!row.isEmpty()) {
                        if (row.getIndex() % 2 == 0) {
                            row.setStyle("-fx-background-color: white;");
                        } else {
                            row.setStyle("-fx-background-color: #FAE5D8;");
                        }
                    }
                });
                return row;
            });

        } catch (Exception e) {
            e.printStackTrace();

            // Show error message if database connection fails
            Label errorLabel = new Label("Error loading data: " + e.getMessage());
            errorLabel.setTextFill(Color.web("#522959"));
            root.setCenter(errorLabel);
        }

        // Create scroll pane and add table
        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setStyle("-fx-background: #FAE5D8; -fx-border-color: #824D69;");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        // Add padding around the table
        BorderPane tableContainer = new BorderPane(scrollPane);
        tableContainer.setPadding(new Insets(0, 20, 20, 20));
        root.setCenter(tableContainer);

        // Create footer with buttons
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: #2A114B; -fx-text-fill: white;");
        closeButton.setOnAction(e -> primaryStage.close());

        Button printButton = new Button("Print");
        printButton.setStyle("-fx-background-color: #824D69; -fx-text-fill: white;");
        printButton.setOnAction(e -> System.out.println("Print functionality would be implemented here"));

        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10, 20, 20, 20));
        buttonBox.setStyle("-fx-background-color: #FAE5D8;");
        buttonBox.getChildren().addAll(printButton, closeButton);

        root.setBottom(buttonBox);

        // Set scene and show stage
        Scene scene = new Scene(root, 700, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to format column names
    private String formatColumnName(String columnName) {
        String[] words = columnName.split("_");
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                formattedName.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return formattedName.toString().trim();
    }

    // Method to launch the application with a specific meter number
    public void showStage(String meterNo) {
        this.meter = meterNo;
        Stage stage = new Stage();
        try {
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}