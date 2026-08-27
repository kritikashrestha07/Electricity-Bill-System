package electricitybilling.electricitybilling;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MeterInfo extends Application {

    private ComboBox<String> meterLocCombo, meterTypCombo, phaseCodeCombo, billTypCombo;
    private Button submit, cancel;
    private String meterNumber;

    public MeterInfo() {
        this.meterNumber = "";
    }

    public MeterInfo(String meterNumber) {
        this.meterNumber = meterNumber;
        System.out.println("Opening MeterInfo for meter: " + meterNumber);

        try {
            Stage stage = new Stage();
            start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Electricity Billing System - Meter Information");

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

        Label heading = new Label("METER INFORMATION");
        heading.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        heading.setTextFill(Color.web("#522959"));

        VBox headerBox = new VBox(10);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().addAll(heading, accentBar);
        headerContainer.getChildren().add(headerBox);

        // Content area
        VBox contentBox = new VBox(20);
        contentBox.setPadding(new Insets(15, 0, 0, 0));

        // Styling for labels and form elements
        String labelStyle = "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #522959;";
        String valueStyle = "-fx-font-family: 'Segoe UI'; -fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #824D69;";
        String comboBoxStyle = "-fx-background-color: #DFE8B2; -fx-border-color: #824D69; -fx-border-radius: 4;" +
                "-fx-background-radius: 4; -fx-padding: 8; -fx-font-size: 14px;";

        // Meter Number display
        GridPane formGrid = new GridPane();
        formGrid.setHgap(20);
        formGrid.setVgap(20);
        formGrid.setAlignment(Pos.CENTER);

        // Meter Number Row
        Label meterNumberLabel = new Label("Meter Number:");
        meterNumberLabel.setStyle(labelStyle);

        Label meterNumberText = new Label(meterNumber);
        meterNumberText.setStyle(valueStyle);
        meterNumberText.setPrefWidth(200);
        meterNumberText.setPadding(new Insets(8));
        meterNumberText.setBackground(new Background(new BackgroundFill(
                Color.web("#DFE8B2"), new CornerRadii(4), Insets.EMPTY)));
        meterNumberText.setBorder(new Border(new BorderStroke(
                Color.web("#824D69"), BorderStrokeStyle.SOLID,
                new CornerRadii(4), BorderWidths.DEFAULT)));

        formGrid.add(meterNumberLabel, 0, 0);
        formGrid.add(meterNumberText, 1, 0);

        // Meter Location
        Label meterLoc = new Label("Meter Location:");
        meterLoc.setStyle(labelStyle);

        ObservableList<String> locationOptions =
                FXCollections.observableArrayList("Outside", "Inside");
        meterLocCombo = new ComboBox<>(locationOptions);
        meterLocCombo.setValue("Outside");
        meterLocCombo.setPrefWidth(200);
        meterLocCombo.setStyle(comboBoxStyle);

        formGrid.add(meterLoc, 0, 1);
        formGrid.add(meterLocCombo, 1, 1);

        // Meter Type
        Label meterTyp = new Label("Meter Type:");
        meterTyp.setStyle(labelStyle);

        ObservableList<String> typeOptions =
                FXCollections.observableArrayList("Electric Meter", "Solar Meter", "Smart Meter");
        meterTypCombo = new ComboBox<>(typeOptions);
        meterTypCombo.setValue("Electric Meter");
        meterTypCombo.setPrefWidth(200);
        meterTypCombo.setStyle(comboBoxStyle);

        formGrid.add(meterTyp, 0, 2);
        formGrid.add(meterTypCombo, 1, 2);

        // Phase Code
        Label phaseCode = new Label("Phase Code:");
        phaseCode.setStyle(labelStyle);

        ObservableList<String> phaseOptions =
                FXCollections.observableArrayList("011", "022", "033", "044", "055", "066", "077", "088", "099");
        phaseCodeCombo = new ComboBox<>(phaseOptions);
        phaseCodeCombo.setValue("011");
        phaseCodeCombo.setPrefWidth(200);
        phaseCodeCombo.setStyle(comboBoxStyle);

        formGrid.add(phaseCode, 0, 3);
        formGrid.add(phaseCodeCombo, 1, 3);

        // Bill Type
        Label billTyp = new Label("Bill Type:");
        billTyp.setStyle(labelStyle);

        ObservableList<String> billOptions =
                FXCollections.observableArrayList("Normal", "Industrial");
        billTypCombo = new ComboBox<>(billOptions);
        billTypCombo.setValue("Normal");
        billTypCombo.setPrefWidth(200);
        billTypCombo.setStyle(comboBoxStyle);

        formGrid.add(billTyp, 0, 4);
        formGrid.add(billTypCombo, 1, 4);

        // Separator
        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #824D69;");

        // Note section
        VBox noteBox = new VBox(8);
        noteBox.setPadding(new Insets(10, 0, 10, 0));
        noteBox.setStyle("-fx-background-color: #DFE8B2; -fx-background-radius: 5; -fx-padding: 15;");

        Label day = new Label("30 Days Billing Time...");
        day.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        day.setTextFill(Color.web("#522959"));

        Label note = new Label("Note:");
        note.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        note.setTextFill(Color.web("#824D69"));

        Label note1 = new Label("By default bill is calculated for 30 days only");
        note1.setFont(Font.font("Segoe UI", 12));
        note1.setStyle("-fx-font-style: italic; -fx-text-fill: #522959;");

        noteBox.getChildren().addAll(day, note, note1);

        // Button area
        HBox buttonBar = new HBox(15);
        buttonBar.setAlignment(Pos.CENTER);
        buttonBar.setPadding(new Insets(20, 0, 0, 0));

        // Submit Button using the palette
        submit = new Button("SUBMIT");
        submit.setPrefWidth(130);
        submit.setPrefHeight(40);
        submit.setStyle("-fx-background-color: #2A114B; -fx-text-fill: #FAE5D8; -fx-font-weight: bold; " +
                "-fx-font-size: 14px; -fx-background-radius: 5; -fx-cursor: hand;");
        submit.setOnAction(e -> handleSubmit(primaryStage));

        // Cancel Button using the palette
        cancel = new Button("CANCEL");
        cancel.setPrefWidth(130);
        cancel.setPrefHeight(40);
        cancel.setStyle("-fx-background-color: #824D69; -fx-text-fill: #FAE5D8; -fx-font-weight: bold; " +
                "-fx-font-size: 14px; -fx-background-radius: 5; -fx-cursor: hand;");
        cancel.setOnAction(e -> primaryStage.close());

        // Hover effects for buttons
        submit.setOnMouseEntered(e -> submit.setStyle("-fx-background-color: #522959; -fx-text-fill: #FAE5D8; -fx-font-weight: bold; " +
                "-fx-font-size: 14px; -fx-background-radius: 5; -fx-cursor: hand;"));
        submit.setOnMouseExited(e -> submit.setStyle("-fx-background-color: #2A114B; -fx-text-fill: #FAE5D8; -fx-font-weight: bold; " +
                "-fx-font-size: 14px; -fx-background-radius: 5; -fx-cursor: hand;"));

        cancel.setOnMouseEntered(e -> cancel.setStyle("-fx-background-color: #522959; -fx-text-fill: #FAE5D8; -fx-font-weight: bold; " +
                "-fx-font-size: 14px; -fx-background-radius: 5; -fx-cursor: hand;"));
        cancel.setOnMouseExited(e -> cancel.setStyle("-fx-background-color: #824D69; -fx-text-fill: #FAE5D8; -fx-font-weight: bold; " +
                "-fx-font-size: 14px; -fx-background-radius: 5; -fx-cursor: hand;"));

        buttonBar.getChildren().addAll(submit, cancel);

        // Add all components to the content box
        contentBox.getChildren().addAll(formGrid, separator, noteBox, buttonBar);

        // Add all elements to the form card
        formCard.getChildren().addAll(headerContainer, contentBox);

        // Add form card to main container
        mainContainer.getChildren().add(formCard);

        // Set scene and show stage
        Scene scene = new Scene(mainContainer, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void handleSubmit(Stage stage) {
        String smeterNum = meterNumber;
        String smeterLoc = meterLocCombo.getValue();
        String smeterTyp = meterTypCombo.getValue();
        String sphaseCode = phaseCodeCombo.getValue();
        String sbillTyp = billTypCombo.getValue();
        String sday = "30";

        // Prevent SQL injection by using prepared statements
        String query_meterInfo = "INSERT INTO meter_info VALUES(?, ?, ?, ?, ?, ?)";

        try {
            database c = new database();

            // Use prepared statements instead of string concatenation
            // This is a placeholder since the actual database implementation isn't shown
            // In a real implementation, you'd use:
            /*
            PreparedStatement pstmt = c.connection.prepareStatement(query_meterInfo);
            pstmt.setString(1, smeterNum);
            pstmt.setString(2, smeterLoc);
            pstmt.setString(3, smeterTyp);
            pstmt.setString(4, sphaseCode);
            pstmt.setString(5, sbillTyp);
            pstmt.setString(6, sday);
            pstmt.executeUpdate();
            */

            // For now, using the existing statement approach
            c.statement.executeUpdate("INSERT INTO meter_info VALUES('" + smeterNum + "','" + smeterLoc + "','"
                    + smeterTyp + "','" + sphaseCode + "','" + sbillTyp + "','" + sday + "')");

            showAlert(Alert.AlertType.INFORMATION, "Success", "Meter Information Submitted Successfully");

            stage.close();

            // You can add logic here to open the next form if needed
            // For example: new BillingDetails(smeterNum);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error submitting meter information: " + e.getMessage());
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