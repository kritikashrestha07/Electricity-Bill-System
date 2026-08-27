package electricitybilling.electricitybilling;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class ViewInformation extends Application implements EventHandler<ActionEvent> {

    private String view;
    private Button cancel;
    private Button backButton;

    // Color palette from the image
    private static final String COLOR_LIGHT_CREAM = "#FAE5D8";
    private static final String COLOR_LIGHT_PINK = "#DFB6B2";
    private static final String COLOR_MAUVE = "#824D69";
    private static final String COLOR_DARK_PURPLE = "#522959";
    private static final String COLOR_DEEP_PURPLE = "#2A114B";
    private static final String COLOR_BLACK_PURPLE = "#18001B";

    // Constructor for when called from another class
    public ViewInformation(String meterNo) {
        this.view = meterNo;
    }

    // Default constructor for JavaFX launch
    public ViewInformation() {
        this.view = "";
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Customer Information");

        // Create main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setStyle("-fx-background-color: " + COLOR_LIGHT_CREAM + ";");

        // Header section with gradient effect
        HBox headerBox = new HBox();
        headerBox.setPadding(new Insets(25, 30, 25, 30));
        headerBox.setStyle("-fx-background-color: linear-gradient(to right, " + COLOR_DEEP_PURPLE + ", " + COLOR_BLACK_PURPLE + ");" +
                "-fx-border-width: 0 0 3 0;" +
                "-fx-border-color: " + COLOR_MAUVE + ";");
        headerBox.setAlignment(Pos.CENTER_LEFT);

        Label heading = new Label("Customer Information");
        heading.setFont(Font.font("Helvetica", FontWeight.BOLD, 26));
        heading.setStyle("-fx-text-fill: white;");
        headerBox.getChildren().add(heading);

        mainLayout.setTop(headerBox);

        // Create an info container with padding
        VBox infoContainer = new VBox(25);
        infoContainer.setPadding(new Insets(40, 50, 40, 50));
        infoContainer.setStyle("-fx-background-color: " + COLOR_LIGHT_CREAM + ";");

        // Content section - using GridPane for better alignment
        // Personal Info Section
        Label personalInfoLabel = new Label("PERSONAL INFORMATION");
        personalInfoLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
        personalInfoLabel.setStyle("-fx-text-fill: " + COLOR_DEEP_PURPLE + ";");

        GridPane personalGrid = new GridPane();
        personalGrid.setHgap(30);
        personalGrid.setVgap(20);
        personalGrid.setPadding(new Insets(15, 0, 20, 10));

        // Style for labels
        String labelStyle = "-fx-font-weight: bold; -fx-text-fill: " + COLOR_DARK_PURPLE + "; -fx-font-size: 14px;";
        String valueStyle = "-fx-text-fill: " + COLOR_MAUVE + "; -fx-font-size: 14px;";

        // Name field
        Label nameLabel = new Label("Name:");
        nameLabel.setStyle(labelStyle);
        personalGrid.add(nameLabel, 0, 0);

        Label nameLabelText = new Label("");
        nameLabelText.setStyle(valueStyle);
        personalGrid.add(nameLabelText, 1, 0);

        // Meter Number field
        Label meterno = new Label("Meter Number:");
        meterno.setStyle(labelStyle);
        personalGrid.add(meterno, 0, 1);

        Label meternoText = new Label("");
        meternoText.setStyle(valueStyle);
        personalGrid.add(meternoText, 1, 1);

        // Contact Info Section
        Label contactInfoLabel = new Label("CONTACT INFORMATION");
        contactInfoLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
        contactInfoLabel.setStyle("-fx-text-fill: " + COLOR_DEEP_PURPLE + ";");

        // Add separator between sections
        Separator separator1 = new Separator();
        separator1.setStyle("-fx-background-color: " + COLOR_LIGHT_PINK + ";");

        GridPane contactGrid = new GridPane();
        contactGrid.setHgap(30);
        contactGrid.setVgap(20);
        contactGrid.setPadding(new Insets(15, 0, 20, 10));

        // Phone field
        Label phone = new Label("Phone:");
        phone.setStyle(labelStyle);
        contactGrid.add(phone, 0, 0);

        Label phoneText = new Label("");
        phoneText.setStyle(valueStyle);
        contactGrid.add(phoneText, 1, 0);

        // Email field
        Label email = new Label("Email:");
        email.setStyle(labelStyle);
        contactGrid.add(email, 0, 1);

        Label emailText = new Label("");
        emailText.setStyle(valueStyle);
        contactGrid.add(emailText, 1, 1);

        // Address Section
        Label addressSectionLabel = new Label("ADDRESS INFORMATION");
        addressSectionLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
        addressSectionLabel.setStyle("-fx-text-fill: " + COLOR_DEEP_PURPLE + ";");

        // Add separator between sections
        Separator separator2 = new Separator();
        separator2.setStyle("-fx-background-color: " + COLOR_LIGHT_PINK + ";");

        GridPane addressGrid = new GridPane();
        addressGrid.setHgap(30);
        addressGrid.setVgap(20);
        addressGrid.setPadding(new Insets(15, 0, 20, 10));

        // Address field
        Label address = new Label("Street Address:");
        address.setStyle(labelStyle);
        addressGrid.add(address, 0, 0);

        Label addressText = new Label("");
        addressText.setStyle(valueStyle);
        addressGrid.add(addressText, 1, 0);

        // City field
        Label city = new Label("City:");
        city.setStyle(labelStyle);
        addressGrid.add(city, 0, 1);

        Label cityText = new Label("");
        cityText.setStyle(valueStyle);
        addressGrid.add(cityText, 1, 1);

        // State/Ward field
        Label state = new Label("Ward No:");
        state.setStyle(labelStyle);
        addressGrid.add(state, 0, 2);

        Label stateText = new Label("");
        stateText.setStyle(valueStyle);
        addressGrid.add(stateText, 1, 2);

        // Add all elements to the info container
        infoContainer.getChildren().addAll(
                personalInfoLabel, personalGrid,
                separator1, contactInfoLabel, contactGrid,
                separator2, addressSectionLabel, addressGrid
        );

        mainLayout.setCenter(infoContainer);

        // Fetch and display customer information
        try {
            database c = new database();
            ResultSet resultSet = c.statement.executeQuery("select * from new_customer where meter_no = '" + view + "'");
            if (resultSet.next()) {
                nameLabelText.setText(resultSet.getString("name"));
                meternoText.setText(resultSet.getString("meter_no"));
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
        buttonBox.setStyle("-fx-background-color: " + COLOR_LIGHT_CREAM + ";");

        backButton = new Button("Back");
        styleButton(backButton, COLOR_MAUVE);
        backButton.setOnAction(this);

        cancel = new Button("Close");
        styleButton(cancel, COLOR_DEEP_PURPLE);
        cancel.setOnAction(this);

        buttonBox.getChildren().addAll(backButton, cancel);
        mainLayout.setBottom(buttonBox);

        // Set scene and show stage
        Scene scene = new Scene(mainLayout, 700, 820);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    private void styleButton(Button button, String bgColor) {
        button.setStyle(
                "-fx-background-color: " + bgColor + ";" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-font-size: 14px;" +
                        "-fx-padding: 10 30;" +
                        "-fx-cursor: hand;" +
                        "-fx-background-radius: 5px;"
        );

        // Add hover effect
        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: " + COLOR_BLACK_PURPLE + ";" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-font-size: 14px;" +
                                "-fx-padding: 10 30;" +
                                "-fx-cursor: hand;" +
                                "-fx-background-radius: 5px;"
                )
        );

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: " + bgColor + ";" +
                                "-fx-text-fill: white;" +
                                "-fx-font-weight: bold;" +
                                "-fx-font-size: 14px;" +
                                "-fx-padding: 10 30;" +
                                "-fx-cursor: hand;" +
                                "-fx-background-radius: 5px;"
                )
        );
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == cancel || event.getSource() == backButton) {
            Stage stage = (Stage) cancel.getScene().getWindow();
            stage.close();
        }
    }

    // Method to launch the application with a specific meter number
    public void showStage(String meterNo) {
        this.view = meterNo;
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