package electricitybilling.electricitybilling;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class main_class extends Application {

    private String acctype;
    private String meter_pass;
    private Stage primaryStage;

    // Colors from the provided palette
    private final String CREAM = "#FAE5D8";
    private final String LIGHT_PINK = "#DFB6B2";
    private final String MAUVE = "#824D69";
    private final String PURPLE = "#522959";
    private final String DARK_PURPLE = "#2A114B";
    private final String BLACK_PURPLE = "#18001B";

    // Styles using the color palette
    private final String MENU_STYLE = "-fx-font-family: 'Segoe UI', sans-serif; -fx-font-size: 14px; -fx-font-weight: bold;";
    private final String MENU_ITEM_STYLE = "-fx-font-family: 'Segoe UI', sans-serif; -fx-font-size: 13px;";
    private final String BACKGROUND_GRADIENT = "-fx-background-color: linear-gradient(to right, " + DARK_PURPLE + ", " + PURPLE + ", " + MAUVE + ");";
    private final String CARD_STYLE = "-fx-background-color: " + CREAM + "; -fx-background-radius: 15px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 5);";

    // Default constructor for JavaFX
    public main_class() {
        // Required empty constructor for JavaFX Application
    }

    // Constructor with parameters
    public main_class(String acctype, String meter_pass) {
        this.acctype = acctype;
        this.meter_pass = meter_pass;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Electricity Billing System");
        primaryStage.setMaximized(true);

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle(BACKGROUND_GRADIENT);

        // Create welcome content
        VBox centerContent = new VBox(25);
        centerContent.setPadding(new Insets(40));
        centerContent.setMaxWidth(800);
        centerContent.setMaxHeight(500);
        centerContent.setStyle(CARD_STYLE);

        Text welcomeText = new Text("Welcome to Electricity Billing System");
        welcomeText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        welcomeText.setFill(Color.web(DARK_PURPLE));

        Text userText = new Text("Logged in as: " + acctype);
        userText.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));
        userText.setFill(Color.web(MAUVE));

        Text instructionText = new Text("Please use the menu above to navigate through the system.");
        instructionText.setFont(Font.font("Segoe UI", 14));
        instructionText.setFill(Color.web(PURPLE));
        instructionText.setWrappingWidth(700);

        // Create accent rectangle
        Rectangle accentLine = new Rectangle(150, 5);
        accentLine.setFill(Color.web(MAUVE));
        accentLine.setArcWidth(5);
        accentLine.setArcHeight(5);

        // Create feature cards
        HBox featureCards = new HBox(20);
        featureCards.setPadding(new Insets(20, 0, 0, 0));

        // Add feature cards
        featureCards.getChildren().addAll(
                createFeatureCard("Manage Customers", "Add, update, and view customer information"),
                createFeatureCard("Billing", "Generate and manage electricity bills"),
                createFeatureCard("Reports", "View usage statistics and payment history")
        );

        // Add all components to the center content
        centerContent.getChildren().addAll(welcomeText, accentLine, userText, instructionText, featureCards);

        // Center the content in the window
        StackPane centeredContent = new StackPane(centerContent);
        centeredContent.setPadding(new Insets(20));
        borderPane.setCenter(centeredContent);

        // Create menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: " + BLACK_PURPLE + "; -fx-padding: 5px;");

        // Menu
        Menu menu = new Menu("Menu");
        menu.setStyle(MENU_STYLE + "-fx-text-fill: " + CREAM + ";");
        styleMenuItems(menu, createMenuItem("New Customer", e -> openNewCustomer()),
                createMenuItem("Customer Details", e -> openCustomerDetails()),
                createMenuItem("Deposit Details", e -> openDepositDetails()),
                createMenuItem("Calculate Bill", e -> openCalculateBill()));

        // Information Menu
        Menu info = new Menu("Information");
        info.setStyle(MENU_STYLE + "-fx-text-fill: " + CREAM + ";");
        styleMenuItems(info, createMenuItem("Update Information", e -> openUpdateInfo()),
                createMenuItem("View Information", e -> openViewInfo()));

        // User Menu
        Menu user = new Menu("User");
        user.setStyle(MENU_STYLE + "-fx-text-fill: " + CREAM + ";");
        styleMenuItems(user, createMenuItem("Pay Bill", e -> openPayBill()),
                createMenuItem("Bill Details", e -> openBillDetails()));

        // Bill Menu
        Menu bill = new Menu("Bill");
        bill.setStyle(MENU_STYLE + "-fx-text-fill: " + CREAM + ";");
        styleMenuItems(bill, createMenuItem("Generate Bill", e -> openGenerateBill()));

        // Utility Menu
        Menu utility = new Menu("Utility");
        utility.setStyle(MENU_STYLE + "-fx-text-fill: " + CREAM + ";");
        styleMenuItems(utility, createMenuItem("Notepad", e -> openNotepad()),
                createMenuItem("Calculator", e -> openCalculator()));

        // Exit Menu
        Menu exit = new Menu("Exit");
        exit.setStyle(MENU_STYLE + "-fx-text-fill: " + CREAM + ";");
        MenuItem eexit = createMenuItem("Exit", e -> exitApplication());
        eexit.setStyle(MENU_ITEM_STYLE + "-fx-text-fill: " + LIGHT_PINK + ";");
        exit.getItems().add(eexit);

        // Add menus to the menu bar based on account type
        if ("Admin".equals(acctype)) {
            menuBar.getMenus().add(menu);
        } else {
            menuBar.getMenus().addAll(bill, user, info);
        }
        menuBar.getMenus().addAll(utility, exit);

        // Add a footer with status
        Label footerLabel = new Label("© " + java.time.Year.now().getValue() + " | Electricity Billing System | User: " + acctype);
        footerLabel.setStyle("-fx-font-family: 'Segoe UI', sans-serif; -fx-font-size: 12px; -fx-text-fill: " + CREAM + "; -fx-padding: 5px 10px;");

        StackPane footer = new StackPane(footerLabel);
        footer.setStyle("-fx-background-color: " + BLACK_PURPLE + ";");

        // Set the menu bar to the top of the border pane
        borderPane.setTop(menuBar);
        borderPane.setBottom(footer);

        // Create and set the scene
        Scene scene = new Scene(borderPane, 1530, 830);

        // Add CSS for hover effects
        String css =
                ".menu-item:hover { -fx-background-color: " + PURPLE + "; }" +
                        ".menu-item .label { -fx-text-fill: " + CREAM + "; }" +
                        ".menu-bar .menu:hover { -fx-background-color: " + DARK_PURPLE + "; }" +
                        ".menu-bar .menu-item:focused { -fx-background-color: " + MAUVE + "; }";

        scene.getStylesheets().add(createStylesheet(css));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Helper method to create feature cards
    private VBox createFeatureCard(String title, String description) {
        VBox card = new VBox(10);
        card.setPrefWidth(220);
        card.setPrefHeight(150);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: " + LIGHT_PINK + "; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        Text titleText = new Text(title);
        titleText.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        titleText.setFill(Color.web(DARK_PURPLE));

        Text descText = new Text(description);
        descText.setFont(Font.font("Segoe UI", 13));
        descText.setFill(Color.web(PURPLE));
        descText.setWrappingWidth(190);

        card.getChildren().addAll(titleText, descText);

        return card;
    }

    // Helper method to create inline CSS
    private String createStylesheet(String css) {
        return css;
    }

    private MenuItem createMenuItem(String text, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        MenuItem item = new MenuItem(text);
        item.setStyle(MENU_ITEM_STYLE);
        item.setOnAction(action);
        return item;
    }

    private void styleMenuItems(Menu menu, MenuItem... items) {
        for (MenuItem item : items) {
            menu.getItems().add(item);
        }
    }

    // Methods to handle menu actions
    private void openNewCustomer() {
        try {
            new NewCustomer().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCustomerDetails() {
        try {
            new CustomerDetails().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openDepositDetails() {
        try {
            new DepositDetails().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCalculateBill() {
        try {
            new CalculateBill().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openViewInfo() {
        try {
            new ViewInformation(meter_pass).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openUpdateInfo() {
        try {
            new UpdateInformation(meter_pass).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openBillDetails() {
        try {
            new BillDetails(meter_pass).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openCalculator() {
        try {
            Runtime.getRuntime().exec("calc.exe");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openNotepad() {
        try {
            Runtime.getRuntime().exec("notepad.exe");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void exitApplication() {
        primaryStage.close();
        try {
            new Login().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openPayBill() {
        try {
            new PayBill(meter_pass).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openGenerateBill() {
        try {
            new GenerateBill(meter_pass).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}