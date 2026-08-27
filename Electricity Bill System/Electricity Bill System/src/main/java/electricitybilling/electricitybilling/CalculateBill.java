package electricitybilling.electricitybilling;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;

public class CalculateBill extends Application implements EventHandler<ActionEvent> {

    private Label nameText, addressText;
    private TextField unitText;
    private ComboBox<String> meternumCombo, monthCombo;
    private Button submit, cancel;

    // For window dragging
    private double xOffset = 0;
    private double yOffset = 0;

    // Color palette from the image
    private static final String CREAM = "#FAE5D8";
    private static final String LIGHT_PINK = "#DFB5B2";
    private static final String MAUVE = "#824D69";
    private static final String PURPLE = "#522959";
    private static final String DARK_PURPLE = "#2A114B";
    private static final String BLACK_PURPLE = "#18001B";

    @Override
    public void start(Stage primaryStage) {
        // Set stage style to remove default window decoration
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Electricity Billing System");

        // Create main layout with border
        BorderPane mainContainer = new BorderPane();
        mainContainer.setStyle("-fx-background-color: " + DARK_PURPLE + "; -fx-background-radius: 10;");

        // Add inner content with padding for border effect
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: " + CREAM + "; -fx-background-radius: 10;");

        // Create custom title bar
        HBox titleBar = createTitleBar(primaryStage);
        borderPane.setTop(titleBar);

        // Create header
        HBox header = createHeader();
        borderPane.setTop(header);

        // Create form panel
        VBox formPanel = createFormPanel();
        borderPane.setCenter(formPanel);

        // Create button panel
        HBox buttonPanel = createButtonPanel();
        borderPane.setBottom(buttonPanel);

        // Add padding to content
        borderPane.setPadding(new Insets(0, 0, 20, 0));

        // Add content to main container with border effect
        mainContainer.setCenter(borderPane);
        mainContainer.setPadding(new Insets(2));

        // Create scene
        Scene scene = new Scene(mainContainer, 550, 600);

        // Make window corners rounded
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // Enable window dragging
        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        primaryStage.show();
    }

    private HBox createTitleBar(Stage stage) {
        HBox titleBar = new HBox();
        titleBar.setAlignment(Pos.CENTER_RIGHT);
        titleBar.setPadding(new Insets(10, 10, 10, 10));
        titleBar.setStyle("-fx-background-color: " + DARK_PURPLE + "; -fx-background-radius: 10 10 0 0;");

        // Create window controls
        Button minimizeBtn = new Button("—");
        minimizeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + CREAM + "; -fx-font-weight: bold;");
        minimizeBtn.setOnAction(e -> stage.setIconified(true));
        minimizeBtn.setOnMouseEntered(e -> minimizeBtn.setStyle("-fx-background-color: " + MAUVE + "; -fx-text-fill: white; -fx-background-radius: 3;"));
        minimizeBtn.setOnMouseExited(e -> minimizeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + CREAM + ";"));

        Button closeBtn = new Button("×");
        closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + CREAM + "; -fx-font-weight: bold; -fx-font-size: 14px;");
        closeBtn.setOnAction(e -> stage.close());
        closeBtn.setOnMouseEntered(e -> closeBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 3;"));
        closeBtn.setOnMouseExited(e -> closeBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: " + CREAM + ";"));

        // Create title
        Label titleLabel = new Label("Electricity Billing System");
        titleLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.web(CREAM));

        // Add title to left and controls to right
        HBox leftSection = new HBox();
        leftSection.setAlignment(Pos.CENTER_LEFT);
        leftSection.getChildren().add(titleLabel);

        HBox rightSection = new HBox(10);
        rightSection.setAlignment(Pos.CENTER_RIGHT);
        rightSection.getChildren().addAll(minimizeBtn, closeBtn);

        // Use region to push controls to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        titleBar.getChildren().addAll(leftSection, spacer, rightSection);

        return titleBar;
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(25, 10, 25, 10));
        header.setStyle("-fx-background-color: " + DARK_PURPLE + "; -fx-background-radius: 0;");

        Label heading = new Label("Electricity Billing System");
        heading.setFont(Font.font("Helvetica", FontWeight.BOLD, 26));
        heading.setTextFill(Color.web(CREAM));

        header.getChildren().add(heading);
        return header;
    }

    private VBox createFormPanel() {
        VBox formPanel = new VBox(20); // Spacing between rows
        formPanel.setPadding(new Insets(35, 45, 25, 45));
        formPanel.setStyle("-fx-background-color: " + CREAM + ";");

        Label formTitle = new Label("Calculate Bill");
        formTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
        formTitle.setTextFill(Color.web(MAUVE));
        formTitle.setPadding(new Insets(0, 0, 10, 0));

        // Meter Number
        HBox meterNumRow = new HBox(15);
        meterNumRow.setAlignment(Pos.CENTER_LEFT);

        Label meternum = new Label("Meter Number");
        meternum.setFont(Font.font("Helvetica", FontWeight.NORMAL, 14));
        meternum.setPrefWidth(140);
        meternum.setTextFill(Color.web(PURPLE));

        meternumCombo = new ComboBox<>();
        meternumCombo.setPrefWidth(250);
        meternumCombo.setStyle("-fx-font-size: 14px; -fx-background-color: white; -fx-border-color: " + LIGHT_PINK + "; -fx-border-radius: 4px;");

        meterNumRow.getChildren().addAll(meternum, meternumCombo);

        // Populate meter numbers
        try {
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from new_customer");
            while (resultSet.next()) {
                meternumCombo.getItems().add(resultSet.getString("meter_no"));
            }
            if (!meternumCombo.getItems().isEmpty()) {
                meternumCombo.setValue(meternumCombo.getItems().get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Name
        HBox nameRow = new HBox(15);
        nameRow.setAlignment(Pos.CENTER_LEFT);

        Label name = new Label("Name");
        name.setFont(Font.font("Helvetica", FontWeight.NORMAL, 14));
        name.setPrefWidth(140);
        name.setTextFill(Color.web(PURPLE));

        nameText = new Label("");
        nameText.setFont(Font.font("Helvetica", FontWeight.NORMAL, 14));
        nameText.setPrefWidth(250);
        nameText.setStyle("-fx-background-color: white; -fx-padding: 8px; -fx-border-color: " + LIGHT_PINK + "; -fx-border-radius: 4px;");

        nameRow.getChildren().addAll(name, nameText);

        // Address
        HBox addressRow = new HBox(15);
        addressRow.setAlignment(Pos.CENTER_LEFT);

        Label address = new Label("Address");
        address.setFont(Font.font("Helvetica", FontWeight.NORMAL, 14));
        address.setPrefWidth(140);
        address.setTextFill(Color.web(PURPLE));

        addressText = new Label("");
        addressText.setFont(Font.font("Helvetica", FontWeight.NORMAL, 14));
        addressText.setPrefWidth(250);
        addressText.setStyle("-fx-background-color: white; -fx-padding: 8px; -fx-border-color: " + LIGHT_PINK + "; -fx-border-radius: 4px;");

        addressRow.getChildren().addAll(address, addressText);

        // Load customer details for selected meter
        loadCustomerDetails();

        // Add listener for meter selection change
        meternumCombo.setOnAction(e -> loadCustomerDetails());

        // Unit Consumed
        HBox unitRow = new HBox(15);
        unitRow.setAlignment(Pos.CENTER_LEFT);

        Label unitconsumed = new Label("Unit Consumed");
        unitconsumed.setFont(Font.font("Helvetica", FontWeight.NORMAL, 14));
        unitconsumed.setPrefWidth(140);
        unitconsumed.setTextFill(Color.web(PURPLE));

        unitText = new TextField();
        unitText.setFont(Font.font("Helvetica", FontWeight.NORMAL, 14));
        unitText.setPrefWidth(250);
        unitText.setStyle("-fx-background-color: white; -fx-padding: 8px; -fx-border-color: " + LIGHT_PINK + "; -fx-border-radius: 4px;");

        unitRow.getChildren().addAll(unitconsumed, unitText);

        // Month
        HBox monthRow = new HBox(15);
        monthRow.setAlignment(Pos.CENTER_LEFT);

        Label month = new Label("Month");
        month.setFont(Font.font("Helvetica", FontWeight.NORMAL, 14));
        month.setPrefWidth(140);
        month.setTextFill(Color.web(PURPLE));

        // Month ComboBox
        ObservableList<String> months = FXCollections.observableArrayList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        );
        monthCombo = new ComboBox<>(months);
        monthCombo.setValue("January");
        monthCombo.setPrefWidth(250);
        monthCombo.setStyle("-fx-font-size: 14px; -fx-background-color: white; -fx-border-color: " + LIGHT_PINK + "; -fx-border-radius: 4px;");

        monthRow.getChildren().addAll(month, monthCombo);

        // Add all rows to the form
        formPanel.getChildren().addAll(formTitle, meterNumRow, nameRow, addressRow, unitRow, monthRow);

        return formPanel;
    }

    private HBox createButtonPanel() {
        HBox buttonPanel = new HBox(20);
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.setPadding(new Insets(20, 10, 25, 10));
        buttonPanel.setStyle("-fx-background-color: " + CREAM + "; -fx-background-radius: 0 0 10 10;");

        // Submit Button
        submit = new Button("Calculate Bill");
        submit.setPrefSize(160, 45);
        submit.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
        submit.setStyle(
                "-fx-background-color: " + MAUVE + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-cursor: hand; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;"
        );
        // Hover effect
        submit.setOnMouseEntered(e -> submit.setStyle(
                "-fx-background-color: " + PURPLE + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-cursor: hand; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;"
        ));
        submit.setOnMouseExited(e -> submit.setStyle(
                "-fx-background-color: " + MAUVE + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-cursor: hand; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;"
        ));
        submit.setOnAction(this);

        // Cancel Button
        cancel = new Button("Cancel");
        cancel.setPrefSize(160, 45);
        cancel.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
        cancel.setStyle(
                "-fx-background-color: " + DARK_PURPLE + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-cursor: hand; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;"
        );
        // Hover effect
        cancel.setOnMouseEntered(e -> cancel.setStyle(
                "-fx-background-color: " + BLACK_PURPLE + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-cursor: hand; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;"
        ));
        cancel.setOnMouseExited(e -> cancel.setStyle(
                "-fx-background-color: " + DARK_PURPLE + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-cursor: hand; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px;"
        ));
        cancel.setOnAction(this);

        buttonPanel.getChildren().addAll(submit, cancel);

        return buttonPanel;
    }

    private void loadCustomerDetails() {
        String selectedMeter = meternumCombo.getValue();
        if (selectedMeter == null) {
            return; // Exit if no meter is selected
        }

        try {
            database c = new database();
            String query = "select * from new_customer where meter_no = '" + selectedMeter + "'";
            ResultSet resultSet = c.statement.executeQuery(query);
            if (resultSet.next()) {
                nameText.setText(resultSet.getString("name"));
                addressText.setText(resultSet.getString("address"));
            } else {
                nameText.setText("Customer not found");
                addressText.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load customer details");
        }
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == submit) {
            if (unitText.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter units consumed");
                return;
            }

            try {
                Integer.parseInt(unitText.getText());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Units must be a valid number");
                return;
            }

            String smeterNo = meternumCombo.getValue();
            if (smeterNo == null || smeterNo.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please select a meter number");
                return;
            }

            String sunit = unitText.getText();
            String smonth = monthCombo.getValue();

            int totalBill = 0;
            int units = Integer.parseInt(sunit);
            String query_tax = "select * from tax";

            try {
                database c = new database();
                ResultSet resultSet = c.statement.executeQuery(query_tax);
                if (resultSet.next()) {
                    totalBill += units * Integer.parseInt(resultSet.getString("cost_per_unit"));
                    totalBill += Integer.parseInt(resultSet.getString("meter_rent"));
                    totalBill += Integer.parseInt(resultSet.getString("service_charge"));
                    totalBill += Integer.parseInt(resultSet.getString("fixed_tax"));
                } else {
                    showAlert(Alert.AlertType.ERROR, "Configuration Error", "Tax rates are not configured in the database");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Calculation Error", "Failed to calculate bill: " + e.getMessage());
                return;
            }

            String query_total_bill = "insert into bill values('" + smeterNo + "', '" + smonth + "','" + sunit + "', '" + totalBill + "','Not Paid')";

            try {
                database c = new database();
                c.statement.executeUpdate(query_total_bill);

                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer bill has been calculated and saved successfully");

                // Clear the unit field
                unitText.clear();

                // Close the window
                Stage stage = (Stage) submit.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to save bill: " + e.getMessage());
            }
        } else if (event.getSource() == cancel) {
            // Close the window
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Style the alert dialog
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: " + CREAM + "; -fx-background-radius: 8px;");
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 14px; -fx-text-fill: " + PURPLE + ";");

        // Style the buttons
        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setStyle(
                "-fx-background-color: " + MAUVE + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-border-radius: 3px; " +
                        "-fx-background-radius: 3px;"
        );

        // Get the Stage of the alert
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.initStyle(StageStyle.TRANSPARENT);
        alert.getDialogPane().getScene().setFill(Color.TRANSPARENT);

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}