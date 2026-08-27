package electricitybilling.electricitybilling;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class UpdateInformation extends Application implements EventHandler<ActionEvent> {

    private Label nametext;
    private TextField addressText, cityText, stateText, emailText, phoneText;
    private String meter;
    private Button update, cancel;

    // Color palette
    private static final String COLOR_LIGHT_CREAM = "#FAE5D8";
    private static final String COLOR_LIGHT_PINK = "#DFB6B2";
    private static final String COLOR_MAUVE = "#824D69";
    private static final String COLOR_DARK_PURPLE = "#522959";
    private static final String COLOR_DEEP_PURPLE = "#2A114B";
    private static final String COLOR_BLACK_PURPLE = "#18001B";

    // Constructor for when called from another class
    public UpdateInformation(String meter) {
        this.meter = meter;
    }

    // Default constructor for JavaFX launch
    public UpdateInformation() {
        this.meter = "";
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Update Customer Information");

        // Create main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + COLOR_LIGHT_CREAM + ";");

        // Header section
        HBox headerBox = new HBox();
        headerBox.setPadding(new Insets(20, 20, 20, 20));
        headerBox.setStyle("-fx-background-color: " + COLOR_DEEP_PURPLE + ";");

        Label heading = new Label("Update Customer Information");
        heading.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
        heading.setStyle("-fx-text-fill: white;");
        headerBox.getChildren().add(heading);

        mainLayout.setTop(headerBox);

        // Content section - using GridPane for better alignment
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setStyle("-fx-background-color: " + COLOR_LIGHT_CREAM + ";");

        // Style for labels
        String labelStyle = "-fx-font-weight: bold; -fx-text-fill: " + COLOR_DARK_PURPLE + ";";
        String valueStyle = "-fx-text-fill: " + COLOR_MAUVE + ";";

        // Non-editable information section
        Label personalInfoLabel = new Label("CUSTOMER INFORMATION");
        personalInfoLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
        personalInfoLabel.setStyle("-fx-text-fill: " + COLOR_DEEP_PURPLE + ";");
        gridPane.add(personalInfoLabel, 0, 0, 2, 1);

        // Name field - non-editable
        Label name = new Label("Name:");
        name.setStyle(labelStyle);
        gridPane.add(name, 0, 1);

        nametext = new Label("");
        nametext.setStyle(valueStyle);
        gridPane.add(nametext, 1, 1);

        // Meter Number field - non-editable
        Label meterNo = new Label("Meter Number:");
        meterNo.setStyle(labelStyle);
        gridPane.add(meterNo, 0, 2);

        Label meterText = new Label("");
        meterText.setStyle(valueStyle);
        gridPane.add(meterText, 1, 2);

        // Editable information section
        Label editableInfoLabel = new Label("EDITABLE INFORMATION");
        editableInfoLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
        editableInfoLabel.setStyle("-fx-text-fill: " + COLOR_DEEP_PURPLE + ";");
        gridPane.add(editableInfoLabel, 0, 3, 2, 1);

        // Address field
        Label address = new Label("Address:");
        address.setStyle(labelStyle);
        gridPane.add(address, 0, 4);

        addressText = new TextField();
        styleTextField(addressText);
        gridPane.add(addressText, 1, 4);

        // City field
        Label city = new Label("City:");
        city.setStyle(labelStyle);
        gridPane.add(city, 0, 5);

        cityText = new TextField();
        styleTextField(cityText);
        gridPane.add(cityText, 1, 5);

        // State/Ward field
        Label state = new Label("Ward No:");
        state.setStyle(labelStyle);
        gridPane.add(state, 0, 6);

        stateText = new TextField();
        styleTextField(stateText);
        gridPane.add(stateText, 1, 6);

        // Contact information
        Label contactInfoLabel = new Label("CONTACT INFORMATION");
        contactInfoLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
        contactInfoLabel.setStyle("-fx-text-fill: " + COLOR_DEEP_PURPLE + ";");
        gridPane.add(contactInfoLabel, 0, 7, 2, 1);

        // Email field
        Label email = new Label("Email:");
        email.setStyle(labelStyle);
        gridPane.add(email, 0, 8);

        emailText = new TextField();
        styleTextField(emailText);
        gridPane.add(emailText, 1, 8);

        // Phone field
        Label phone = new Label("Phone:");
        phone.setStyle(labelStyle);
        gridPane.add(phone, 0, 9);

        phoneText = new TextField();
        styleTextField(phoneText);
        gridPane.add(phoneText, 1, 9);

        mainLayout.setCenter(gridPane);

        // Fetch and display customer information
        try {
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from new_customer where meter_no = '" + meter + "'");
            if (resultSet.next()) {
                nametext.setText(resultSet.getString("name"));
                meterText.setText(resultSet.getString("meter_no"));
                addressText.setText(resultSet.getString("address"));
                cityText.setText(resultSet.getString("city"));
                stateText.setText(resultSet.getString("ward_no"));
                emailText.setText(resultSet.getString("email"));
                phoneText.setText(resultSet.getString("phone_no"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Buttons section
        HBox buttonBox = new HBox(20);
        buttonBox.setPadding(new Insets(20, 0, 30, 0));
        buttonBox.setAlignment(Pos.CENTER);

        update = new Button("Update");
        styleButton(update, COLOR_MAUVE);
        update.setOnAction(this);

        cancel = new Button("Cancel");
        styleButton(cancel, COLOR_DEEP_PURPLE);
        cancel.setOnAction(this);

        buttonBox.getChildren().addAll(update, cancel);
        mainLayout.setBottom(buttonBox);

        // Set scene and show stage
        Scene scene = new Scene(mainLayout, 650, 680);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void styleTextField(TextField textField) {
        textField.setPrefWidth(250);
        textField.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: " + COLOR_MAUVE + ";" +
                        "-fx-border-radius: 3;" +
                        "-fx-padding: 5;" +
                        "-fx-text-fill: " + COLOR_DARK_PURPLE + ";"
        );

        // Add focus effect
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                textField.setStyle(
                        "-fx-background-color: white;" +
                                "-fx-border-color: " + COLOR_DEEP_PURPLE + ";" +
                                "-fx-border-width: 2;" +
                                "-fx-border-radius: 3;" +
                                "-fx-padding: 5;" +
                                "-fx-text-fill: " + COLOR_DARK_PURPLE + ";"
                );
            } else {
                textField.setStyle(
                        "-fx-background-color: white;" +
                                "-fx-border-color: " + COLOR_MAUVE + ";" +
                                "-fx-border-radius: 3;" +
                                "-fx-padding: 5;" +
                                "-fx-text-fill: " + COLOR_DARK_PURPLE + ";"
                );
            }
        });
    }

    private void styleButton(Button button, String bgColor) {
        button.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 8 25;" +
                        "-fx-cursor: hand;"
        );

        // Add hover effect
        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: " + COLOR_BLACK_PURPLE + ";" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-font-size: 14px;" +
                                "-fx-padding: 8 25;" +
                                "-fx-cursor: hand;"
                )
        );

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: " + bgColor + ";" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-font-size: 14px;" +
                                "-fx-padding: 8 25;" +
                                "-fx-cursor: hand;"
                )
        );
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == update) {
            String saddress = addressText.getText();
            String scity = cityText.getText();
            String sstate = stateText.getText();
            String semail = emailText.getText();
            String sphone = phoneText.getText();

            try {
                database c = new database();
                c.statement.executeUpdate("update new_customer set address ='" + saddress + "', city = '" + scity +
                        "', ward_no = '" + sstate + "', email = '" + semail +
                        "', phone_no ='" + sphone + "' where meter_no = '" + meter + "'");

                // Styled alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Customer information updated successfully");

                // Apply custom styling to the dialog
                DialogStyler.style(alert, COLOR_DEEP_PURPLE);

                alert.showAndWait();

                // Close the window
                Stage stage = (Stage) update.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update customer information. Please try again.");

                // Apply custom styling to the dialog
                DialogStyler.style(alert, COLOR_DEEP_PURPLE);

                alert.showAndWait();
                e.printStackTrace();
            }
        } else if (event.getSource() == cancel) {
            // Close the window
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
        }
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

    // Helper class for dialog styling
    private static class DialogStyler {
        public static void style(Alert alert, String color) {
            // Apply custom styling to the dialog
            alert.getDialogPane().setStyle(
                    "-fx-background-color: " + COLOR_LIGHT_CREAM + ";" +
                            "-fx-border-color: " + color + ";" +
                            "-fx-border-width: 2px;"
            );

            // Style the header
            alert.getDialogPane().lookupButton(alert.getButtonTypes().get(0)).setStyle(
                    "-fx-background-color: " + color + ";" +
                            "-fx-text-fill: white;" +
                            "-fx-font-weight: bold;" +
                            "-fx-cursor: hand;"
            );
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}