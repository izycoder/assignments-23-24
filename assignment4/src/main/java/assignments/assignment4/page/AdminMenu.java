package assignments.assignment4.page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private User user;
    private Scene addRestaurantScene;
    private Scene addMenuScene;
    private Scene viewRestaurantsScene;
    private static List<Restaurant> restoList = new ArrayList<>();
    private MainApp mainApp; // Reference to MainApp instance
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ListView<String> menuItemsListView = new ListView<>();

    public AdminMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addRestaurantScene = createAddRestaurantForm();
        this.addMenuScene = createAddMenuForm();
        this.viewRestaurantsScene = createViewRestaurantsForm();
    }

    @Override
    public Scene createBaseMenu() {
        // TODO: Implementasikan method ini untuk menampilkan menu untuk Admin
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: rgb(116, 105, 182);");
        grid.setHgap(50);
        grid.setAlignment(Pos.CENTER);

        // Add "Welcome to DepeFood" text on the left
        VBox welcomeText = new VBox();
        welcomeText.setAlignment(Pos.CENTER); // Align to bottom left
        welcomeText.setSpacing(5); // Spacing between texts
        Text welcome = new Text("Welcome");
        welcome.setFill(Color.rgb(255, 230, 230));
        Text depe = new Text(user.getName());
        depe.setFill(Color.rgb(255, 230, 230));
        welcome.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        depe.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        welcomeText.getChildren().addAll(welcome, depe);
        grid.add(welcomeText, 0, 0);
        VBox buttonVBox = new VBox(20);
        buttonVBox.setAlignment(Pos.CENTER_LEFT);
    
        // Buttons for admin actions
        Button addRestaurantButton = new Button("Add Restaurant");
        addRestaurantButton.setMinWidth(150);
        Button addMenuButton = new Button("Add Menu");
        addMenuButton.setMinWidth(150);
        Button viewRestaurantsButton = new Button("View Restaurants");
        viewRestaurantsButton.setMinWidth(150);
        Button logoutButton = new Button("Logout");
        logoutButton.setMinWidth(150);
    
        // Set button styles
        addRestaurantButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        addMenuButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        viewRestaurantsButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        logoutButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
    
        // Add button click handlers
        addRestaurantButton.setOnAction(event -> mainApp.setScene(addRestaurantScene));
        addMenuButton.setOnAction(event -> mainApp.setScene(addMenuScene));
        viewRestaurantsButton.setOnAction(event -> mainApp.setScene(viewRestaurantsScene));
        logoutButton.setOnAction(event -> mainApp.logout());
    
        // Add buttons to the VBox
        buttonVBox.getChildren().addAll(addRestaurantButton, addMenuButton, viewRestaurantsButton, logoutButton);
        // Add the button VBox to the GridPane, aligning to the right
        grid.add(buttonVBox, 1, 0);

        return new Scene(grid, 800, 600);
    }

    private Scene createAddRestaurantForm() {
        // TODO: Implementasikan method ini untuk menampilkan page tambah restoran
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: rgb(116, 105, 182);");
        layout.setAlignment(Pos.CENTER);
        
        Text labelRestaurant = new Text("Input Restaurant Name:");
        labelRestaurant.setFill(Color.rgb(255, 230, 230));
        labelRestaurant.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        // Create a Text field for entering the restaurant name
        TextField restaurantNameField = new TextField();
        restaurantNameField.setPromptText("Enter Restaurant Name");
        
        // Create a Button for submitting the form
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        Button kembaliButton = new Button("Kembali");
        kembaliButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        submitButton.setOnAction(event -> handleTambahRestoran(restaurantNameField.getText()));
        kembaliButton.setOnAction(event -> mainApp.setScene(scene));
        
        // Add the components to the layout
        layout.getChildren().addAll(labelRestaurant, restaurantNameField, submitButton, kembaliButton);
        return new Scene(layout, 800, 600);
    }

    private Scene createAddMenuForm() {
        // TODO: Implementasikan method ini untuk menampilkan page tambah menu restoran
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: rgb(116, 105, 182);");
        layout.setAlignment(Pos.CENTER);
        Text labelRestaurant = new Text("Restaurant Name:");
        labelRestaurant.setFill(Color.rgb(255, 230, 230));
        labelRestaurant.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        TextField restaurantNameField = new TextField();
        restaurantNameField.setPromptText("Enter Restaurant Name");

        Text labelItem = new Text("Menu Item Name:");
        labelItem.setFill(Color.rgb(255, 230, 230));
        labelItem.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        TextField itemNameField = new TextField();
        itemNameField.setPromptText("Enter Menu Item Name");

        Text labelPrice = new Text("Price");
        labelPrice.setFill(Color.rgb(255, 230, 230));
        labelPrice.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        TextField priceField = new TextField();
        priceField.setPromptText("Enter Price");

        Button addMenuItemButton = new Button("Add Menu Item");
        addMenuItemButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        addMenuItemButton.setOnAction(event -> {
        // Initialize Restaurant object here, capturing the current value of restaurantNameField
        // Restaurant restaurant = new Restaurant(restaurantNameField.getText());
        Restaurant restaurant = null;
        for (Restaurant resto : DepeFood.getRestoList()) {
            if (resto.getNama().equals(restaurantNameField.getText())) {
                restaurant = resto;
            }
        }   
        // Call the method to handle adding the menu item
        Double price;
        if (priceField.getText().trim().equals("")){
            price = 0.0;
        }
        else {
            price = Double.parseDouble(priceField.getText());
        }

        handleTambahMenuRestoran(restaurant, itemNameField.getText(), price);
        itemNameField.clear();
        priceField.clear();
        });

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        kembaliButton.setOnAction(event -> {
        restaurantNameField.clear();
        mainApp.setScene(scene);
        });

        layout.getChildren().addAll(labelRestaurant, restaurantNameField, labelItem, itemNameField, labelPrice, priceField, addMenuItemButton, kembaliButton);
        return new Scene(layout, 800, 600);
    }
    
    
    private Scene createViewRestaurantsForm() {
        // TODO: Implementasikan method ini untuk menampilkan page daftar restoran
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: rgb(116, 105, 182);");
        layout.setAlignment(Pos.CENTER);

        // Restaurant name input field
        TextField restaurantNameField = new TextField();
        restaurantNameField.setPromptText("Enter Restaurant Name");

        menuItemsListView.setPrefHeight(400);

        // Search button
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        searchButton.setOnAction(event -> {
        String searchKeyword = restaurantNameField.getText();
        Restaurant restaurant = null;
        for (Restaurant resto : DepeFood.getRestoList()) {
            if (resto.getNama().equals(searchKeyword)) {
                restaurant = resto;
                break;
            }
        }
        if (restaurant == null){
            showAlert("Error", null, "Restaurant not found!", Alert.AlertType.ERROR);
            restaurantNameField.clear();
            mainApp.setScene(viewRestaurantsScene);
        }
        else{
            menuItemsListView.getItems().clear();
            String menuString = restaurant.printMenu();
            String[] menu = menuString.split("\n");
            for (int i = 0; i < menu.length; i++) {
                if (i > 0) {
                    String menuPrint = menu[i];
                    String[] menuParts = menuPrint.substring(2).split(" ");
                    StringBuilder formattedMenu = new StringBuilder();
                    for (int j = 0; j < menuParts.length; j++) {
                        if (j > 0){
                            formattedMenu.append(" ");
                        }
                        if (j == menuParts.length - 1) {
                            formattedMenu.append(" Rp").append(menuParts[j]); // Add "Rp" before the price
                        } else {
                            formattedMenu.append(menuParts[j]);
                        }
                    }
                menuItemsListView.getItems().add(formattedMenu.toString());
                }
            }
        }
        });
        Text menuText = new Text("Menu:");
        menuText.setFill(Color.rgb(255, 230, 230));
        menuText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        kembaliButton.setOnAction(event -> mainApp.setScene(scene));
        layout.getChildren().addAll(restaurantNameField, searchButton, menuText, menuItemsListView, kembaliButton);        
        return new Scene(layout, 800, 600);
    }
    

    private void handleTambahRestoran(String nama) {
        //TODO: Implementasi validasi isian nama Restoran
        boolean restaurantTidakAda = true;
        if (nama.length() < 4){
            showAlert("Restaurant Name Error", null, "Please enter a valid restaurant name", Alert.AlertType.ERROR);
            mainApp.setScene(addRestaurantScene);
        }
        else{
            for (Restaurant resto : DepeFood.getRestoList()) {
                if (resto.getNama().equals(nama)) {
                    restaurantTidakAda = false;
                }
            }
            }
            if (restaurantTidakAda){
                DepeFood.handleTambahRestoran(nama);
                showAlert("Restaurant Successfully Added",null,"The restaurant has been added", Alert.AlertType.INFORMATION);
                mainApp.setScene(scene);
            }
            else{
                showAlert("Restaurant Name Already Exists", null, "Please enter other restaurant name", Alert.AlertType.ERROR);
                mainApp.setScene(addRestaurantScene);
            }
        }

    private void handleTambahMenuRestoran(Restaurant restaurant, String itemName, double price) {
        //TODO: Implementasi validasi isian menu Restoran
        boolean restaurantAda = false;
        if (restaurant == null){
            showAlert("Error", null, "Restaurant not found!", Alert.AlertType.ERROR);
            mainApp.setScene(addMenuScene);
        }
        else{
            for (Restaurant resto : DepeFood.getRestoList()) {
                if (resto.getNama().equals(restaurant.getNama())) {
                    restaurantAda = true;
                }
            }   
            if (restaurantAda) {
                if (itemName.isEmpty()){
                    showAlert("Restaurant Item Name is Empty", null, "Please enter restaurant item name", Alert.AlertType.ERROR);
                    mainApp.setScene(addMenuScene);
                }
                else {
                    String priceString = String.valueOf(price);
                    if (priceString.trim().isEmpty()) {
                        showAlert("Restaurant Item Price is Empty", null, "Please enter item price", Alert.AlertType.ERROR);
                        mainApp.setScene(addMenuScene);
                    }
                    else{
                        DepeFood.handleTambahMenuRestoran(restaurant, itemName, price);
                        showAlert("Menu Successfully Added",null,"The menu has been added", Alert.AlertType.INFORMATION);
                        mainApp.setScene(addMenuScene);
                    }
                }
            } 
            else {
                showAlert("Error", null, "Restaurant not found!", Alert.AlertType.ERROR);
                mainApp.setScene(addMenuScene);
            }
        }
    }

}
