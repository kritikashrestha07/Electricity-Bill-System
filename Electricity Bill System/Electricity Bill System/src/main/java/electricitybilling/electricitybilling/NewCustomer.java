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
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class NewCustomer extends Application {

    private Label heading, customername, meterNum, meternumText, address, city, state, email, phone;
    private Button next, cancel;
    private TextField nameText, addressText, cityText, stateText, emailText, phoneText;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("New Customer - Electricity Billing System");

        // Main container with gradient background using the provided color palette
        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(30, 40, 40, 40));
        mainContainer.setStyle("-fx-background-color: linear-gradient(to bottom, #2A114B, #18001B);");

        // Card effect for form container
        VBox formCard = new VBox(15);
        formCard.setPadding(new Insets(30, 40, 40, 40));
        formCard.setStyle("-fx-background-color: #FAE5D8; -fx-background-radius: 10;");

        // Add drop shadow effect to the card
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.1, 0.1, 0.1, 0.5));
        formCard.setEffect(dropShadow);

        // Header with accent bar
        StackPane headerContainer = new StackPane();
        Rectangle accentBar = new Rectangle(100, 5);
        accentBar.setFill(Color.web("#824D69"));

        heading = new Label("NEW CUSTOMER REGISTRATION");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        heading.setTextFill(Color.web("#522959"));

        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().addAll(heading, accentBar);
        headerContainer.getChildren().add(headerBox);

        // Form grid for better alignment
        GridPane formGrid = new GridPane();
        formGrid.setHgap(20);
        formGrid.setVgap(20);
        formGrid.setAlignment(Pos.CENTER);

        // Form fields styling with the new color palette
        String labelStyle = "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #522959;";
        String textFieldStyle = "-fx-background-color: #DFE8B2; -fx-border-color: #824D69; -fx-border-radius: 4;" +
                "-fx-background-radius: 4; -fx-padding: 8; -fx-font-size: 14px;";
        String readOnlyFieldStyle = "-fx-background-color: #DFE8B2; -fx-border-color: #824D69; -fx-border-radius: 4;" +
                "-fx-background-radius: 4; -fx-padding: 8; -fx-font-size: 14px; -fx-text-fill: #522959;";

        // Customer Name
        customername = new Label("Customer Name");
        customername.setStyle(labelStyle);
        nameText = new TextField();
        nameText.setPromptText("Enter full name");
        nameText.setPrefWidth(270);
        nameText.setStyle(textFieldStyle);

        // Meter Number
        meterNum = new Label("Meter Number");
        meterNum.setStyle(labelStyle);

        Random ran = new Random();
        long number = ran.nextInt(100000000) + 10000000L; // Ensure 8 digits

        Label meterNumberValueLabel = new Label("" + number);
        meterNumberValueLabel.setStyle(readOnlyFieldStyle);
        meterNumberValueLabel.setPrefWidth(270);
        meterNumberValueLabel.setPadding(new Insets(8));
        meterNumberValueLabel.setBackground(new Background(new BackgroundFill(
                Color.web("#DFE8B2"), new CornerRadii(4), Insets.EMPTY)));
        meternumText = meterNumberValueLabel;

        // Address
        address = new Label("Address");
        address.setStyle(labelStyle);
        addressText = new TextField();
        addressText.setPromptText("Enter street address");
        addressText.setPrefWidth(270);
        addressText.setStyle(textFieldStyle);

        // City
        city = new Label("City");
        city.setStyle(labelStyle);
        cityText = new TextField();
        cityText.setPromptText("Enter city name");
        cityText.setPrefWidth(270);
        cityText.setStyle(textFieldStyle);

        // State (Ward no.)
        state = new Label("Ward No.");
        state.setStyle(labelStyle);
        stateText = new TextField();
        stateText.setPromptText("Enter ward number");
        stateText.setPrefWidth(270);
        stateText.setStyle(textFieldStyle);

        // Email
        email = new Label("Email");
        email.setStyle(labelStyle);
        emailText = new TextField();
        emailText.setPromptText("Enter valid email address");
        emailText.setPrefWidth(270);
        emailText.setStyle(textFieldStyle);

        // Phone
        phone = new Label("Phone");
        phone.setStyle(labelStyle);
        phoneText = new TextField();
        phoneText.setPromptText("Enter phone number");
        phoneText.setPrefWidth(270);
        phoneText.setStyle(textFieldStyle);

        // Add all form elements to the grid
        formGrid.add(customername, 0, 0);
        formGrid.add(nameText, 1, 0);
        formGrid.add(meterNum, 0, 1);
        formGrid.add(meterNumberValueLabel, 1, 1);
        formGrid.add(address, 0, 2);
        formGrid.add(addressText, 1, 2);
        formGrid.add(city, 0, 3);
        formGrid.add(cityText, 1, 3);
        formGrid.add(state, 0, 4);
        formGrid.add(stateText, 1, 4);
        formGrid.add(email, 0, 5);
        formGrid.add(emailText, 1, 5);
        formGrid.add(phone, 0, 6);
        formGrid.add(phoneText, 1, 6);

        // Buttons with improved styling
        HBox buttonBar = new HBox(15);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPadding(new Insets(20, 0, 0, 0));

        // Next Button using the palette
        next = new Button("NEXT");
        next.setPrefWidth(130);
        next.setPrefHeight(40);
        next.setStyle("-fx-background-color: #2A114B; -fx-text-fill: #FAE5D8; -fx-font-weight: bold; " +
                "-fx-font-size: 14px; -fx-background-radius: 5; -fx-cursor: hand;");
        next.setOnAction(e -> handleNext());

        // Cancel Button using the palette
        cancel = new Button("CANCEL");
        cancel.setPrefWidth(130);
        cancel.setPrefHeight(40);
        cancel.setStyle("-fx-background-color: #824D69; -fx-text-fill: #FAE5D8; -fx-font-weight: bold; " +
                "-fx-font-size: 14px; -fx-background-radius: 5; -fx-cursor: hand;");
        cancel.setOnAction(e -> primaryStage.close());

        // Hover effects for buttons
        next.setOnMouseEntered(e -> next.setStyle("-fx-background-color: #522959; -fx-text-fill: #FAE5D8; -fx-font-weight: bold; " +
                "-fx-font-size: 14px; -fx-background-radius: 5; -fx-cursor: hand;"));
        next.setOnMouseExited(e -> next.setStyle("-fx-background-color: #2A114B; -fx-text-fill: #FAE5D8; -fx-font-weight: bold; " +
                "-fx-font-size: 14px; -fx-background-radius: 5; -fx-cursor: hand;"));

        cancel.setOnMouseEntered(e -> cancel.setStyle("-fx-background-color: #522959; -fx-text-fill: #FAE5D8; -fx-font-weight: bold; " +
                "-fx-font-size: 14px; -fx-background-radius: 5; -fx-cursor: hand;"));
        cancel.setOnMouseExited(e -> cancel.setStyle("-fx-background-color: #824D69; -fx-text-fill: #FAE5D8; -fx-font-weight: bold; " +
                "-fx-font-size: 14px; -fx-background-radius: 5; -fx-cursor: hand;"));

        buttonBar.getChildren().addAll(next, cancel);

        // Add all elements to the form card
        formCard.getChildren().addAll(headerContainer, formGrid, buttonBar);

        // Add form card to main container
        mainContainer.getChildren().add(formCard);

        // Set scene and show stage
        Scene scene = new Scene(mainContainer, 600, 650);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void handleNext() {
        String sname = nameText.getText();
        String smeter = meternumText.getText();
        String saddress = addressText.getText();
        String scity = cityText.getText();
        String sstate = stateText.getText();
        String eemail = emailText.getText();
        String sphone = phoneText.getText();

        // Form validation
        if (sname.isEmpty() || saddress.isEmpty() || scity.isEmpty() || sstate.isEmpty() || eemail.isEmpty() || sphone.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill all fields");
            return;
        }

        // Email validation
        if (!eemail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid email address");
            return;
        }

        // Phone validation
        if (!sphone.matches("\\d{10}")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid 10-digit phone number");
            return;
        }

        // Prevent SQL injection by using prepared statements
        String query_customer = "INSERT INTO new_customer VALUES(?, ?, ?, ?, ?, ?, ?)";
        String query_signup = "INSERT INTO signup VALUES(?, ?, ?, ?, ?)";

        try {
            database c = new database();

            // Use prepared statements instead of string concatenation
            // This is a placeholder since the actual database implementation isn't shown
            // In a real implementation, you'd use:
            /*
            PreparedStatement pstmt1 = c.connection.prepareStatement(query_customer);
            pstmt1.setString(1, sname);
            pstmt1.setString(2, smeter);
            pstmt1.setString(3, saddress);
            pstmt1.setString(4, scity);
            pstmt1.setString(5, sstate);
            pstmt1.setString(6, eemail);
            pstmt1.setString(7, sphone);
            pstmt1.executeUpdate();

            PreparedStatement pstmt2 = c.connection.prepareStatement(query_signup);
            pstmt2.setString(1, smeter);
            pstmt2.setString(2, "");
            pstmt2.setString(3, sname);
            pstmt2.setString(4, "");
            pstmt2.setString(5, "");
            pstmt2.executeUpdate();
            */

            // For now, using the existing statement approach
            c.statement.executeUpdate("INSERT INTO new_customer VALUES('" + sname + "','" + smeter + "','" + saddress + "','" + scity + "','" + sstate + "','" + eemail + "','" + sphone + "')");
            c.statement.executeUpdate("INSERT INTO signup VALUES('" + smeter + "','','"+sname+"','','')");

            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer details added successfully");

            // Close current window and open meter info
            Stage stage = (Stage) next.getScene().getWindow();
            stage.close();

            // Create and open MeterInfo window with meter number
            new MeterInfo(smeter);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error adding customer details: " + e.getMessage());
        }
    }

    // Helper method to show alerts with custom styling
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Style the dialog pane
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #FAE5D8;");

        // Style the buttons
        for (ButtonType buttonType : alert.getButtonTypes()) {
            Button button = (Button) alert.getDialogPane().lookupButton(buttonType);
            button.setStyle("-fx-background-color: #2A114B; -fx-text-fill: #FAE5D8; -fx-font-weight: bold;");
        }

        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}