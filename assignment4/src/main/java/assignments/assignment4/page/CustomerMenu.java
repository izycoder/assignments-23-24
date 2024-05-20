package assignments.assignment4.page;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.GridPane;
import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment3.Order;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.BillPrinter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private BillPrinter billPrinter; // Instance of BillPrinter
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ComboBox<String> paymentComboBox = new ComboBox<>();
    private static Label label = new Label();
    private MainApp mainApp;
    private List<Restaurant> restoList = new ArrayList<>();
    private User user;
    private ListView<String> menuItemsListView = new ListView<>();

    public CustomerMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addOrderScene = createTambahPesananForm();
        this.printBillScene = createBillPrinter();
        this.billPrinter = new BillPrinter(stage, mainApp, this.user, this.printBillScene); // Pass user to BillPrinter constructor
        this.payBillScene = createBayarBillForm();
        this.cekSaldoScene = createCekSaldoScene();
    }

    @Override
    public Scene createBaseMenu() {
        // TODO: Implementasikan method ini untuk menampilkan menu untuk Customer
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
        Button buatPesanan = new Button("Buat Pesanan");
        buatPesanan.setMinWidth(150);
        Button cetakBill = new Button("Cetak Bill");
        cetakBill.setMinWidth(150);
        Button bayarBill = new Button("Bayar Bill");
        bayarBill.setMinWidth(150);
        Button cekSaldo = new Button("Cek Saldo");
        cekSaldo.setMinWidth(150);
        Button logoutButton = new Button("Log Out");
        logoutButton.setMinWidth(150);
    
        // Set button styles
        buatPesanan.setStyle("-fx-background-color: rgb(225, 175, 209);");
        cetakBill.setStyle("-fx-background-color: rgb(225, 175, 209);");
        bayarBill.setStyle("-fx-background-color: rgb(225, 175, 209);");
        cekSaldo.setStyle("-fx-background-color: rgb(225, 175, 209);");
        logoutButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        
        buatPesanan.setOnAction(event -> mainApp.setScene(addOrderScene));
        cetakBill.setOnAction(event -> mainApp.setScene(printBillScene));
        bayarBill.setOnAction(event -> mainApp.setScene(payBillScene));
        cekSaldo.setOnAction(event -> mainApp.setScene(cekSaldoScene));
        logoutButton.setOnAction(event -> mainApp.logout());

        buttonVBox.getChildren().addAll(buatPesanan, cetakBill, bayarBill, cekSaldo, logoutButton);
        grid.add(buttonVBox, 1, 0);
        return new Scene(grid, 800, 600);
    }

    private Scene createTambahPesananForm() {
        // TODO: Implementasikan method ini untuk menampilkan page tambah pesanan
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: rgb(116, 105, 182);");
        layout.setAlignment(Pos.CENTER);

        Text restaurantText = new Text("Restaurant:");
        restaurantText.setFill(Color.rgb(255, 230, 230));
        restaurantText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        // Restaurant combobox field
        ObservableList<String> restaurantNames = FXCollections.observableArrayList();
        for (Restaurant resto : DepeFood.getRestoList()){
            restaurantNames.add(resto.getNama());
        }
        restaurantComboBox.setItems(restaurantNames);
        restaurantComboBox.setPrefWidth(200);
        restaurantComboBox.setPromptText("Select a restaurant");
        
        Text dateText = new Text("Date (DD/MM/YYYY): ");
        dateText.setFill(Color.rgb(255, 230, 230));
        dateText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        TextField dateField = new TextField();
        dateField.setPromptText("Enter date (DD/MM/YYYY)");


        menuItemsListView.setPrefHeight(350);

        Button menuButton = new Button("Menu");
        menuButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        menuButton.setOnAction(event -> {
        Restaurant restaurant = DepeFood.findRestaurant(restaurantComboBox.getValue());
        if (restaurant != null){
        menuItemsListView.getItems().clear();
        menuItemsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            String menuString = restaurant.printMenu();
            String[] menu = menuString.split("\n");
            for (int i = 0; i < menu.length; i++) {
                if (i > 0) {
                    String menuPrint = menu[i];
                    String[] menuParts = menuPrint.substring(2).split(" ");
                    StringBuilder formattedMenu = new StringBuilder();
                    for (int j = 0; j < menuParts.length; j++) {
                        if (j == menuParts.length - 1) {
                            break;
                        } else {
                            formattedMenu.append(menuParts[j]);
                            formattedMenu.append(" ");
                        }
                    }
                menuItemsListView.getItems().add(formattedMenu.toString().trim());
                }
            }
        }
        else{
            showAlert("Error", null, "Restaurant is Empty Pick One!", Alert.AlertType.ERROR);
        }
        });

        Text menuText = new Text("Menu Items:");
        menuText.setFill(Color.rgb(255, 230, 230));
        menuText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        
        
        Button buatPesanan = new Button("Buat Pesanan");
        buatPesanan.setMinWidth(150);

        buatPesanan.setOnAction(event -> {
        ObservableList<String> selectedItems = menuItemsListView.getSelectionModel().getSelectedItems();
        List<String> menuItems = new ArrayList<>(selectedItems);
        handleBuatPesanan(restaurantComboBox.getValue(),dateField.getText(), menuItems);
        });

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        kembaliButton.setOnAction(event -> mainApp.setScene(scene));
        layout.getChildren().addAll(restaurantText, restaurantComboBox, dateText, dateField, menuButton, menuText, menuItemsListView, buatPesanan, kembaliButton);        
        return new Scene(layout, 800, 600);
    }

    private Scene createBillPrinter(){
        // TODO: Implementasikan method ini untuk menampilkan page cetak bill
        VBox menuLayout = new VBox(10);
        menuLayout.setStyle("-fx-background-color: rgb(116, 105, 182);");
        menuLayout.setAlignment(Pos.CENTER);

        TextField orderField = new TextField();
        orderField.setPromptText("Enter Order ID");

        Button printButton = new Button("Print Bill");
        printButton.setMinWidth(150);
        printButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        printButton.setOnAction(event -> mainApp.setScene(billPrinter.getScene(orderField.getText())));

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setMinWidth(150);
        kembaliButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        kembaliButton.setOnAction(event -> mainApp.setScene(scene));

        menuLayout.getChildren().addAll(orderField, printButton, kembaliButton);   
        return new Scene(menuLayout, 800,600);
    }

    private Scene createBayarBillForm() {
        // TODO: Implementasikan method ini untuk menampilkan page bayar bill
        VBox menuLayout = new VBox(10);
        menuLayout.setStyle("-fx-background-color: rgb(116, 105, 182);");
        menuLayout.setAlignment(Pos.CENTER);

        TextField orderField = new TextField();
        orderField.setPromptText("Enter Order ID");

        
        paymentComboBox.getItems().add("Credit Card");
        paymentComboBox.getItems().add("Debit");
        paymentComboBox.setPrefWidth(200);
        paymentComboBox.setPromptText("Select payment method");


        Button bayarButton = new Button("Bayar");
        bayarButton.setMinWidth(150);
        bayarButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        bayarButton.setOnAction(event -> {
            String metode = paymentComboBox.getValue();
            handleBayarBill(orderField.getText(), metode);
        });

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setMinWidth(150);
        kembaliButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        kembaliButton.setOnAction(event -> mainApp.setScene(scene));

        menuLayout.getChildren().addAll(orderField, paymentComboBox, bayarButton, kembaliButton);           
        return new Scene(menuLayout, 800,600);
    }


    private Scene createCekSaldoScene() {
        // TODO: Implementasikan method ini untuk menampilkan page cetak saldo
        VBox menuLayout = new VBox(10);
        menuLayout.setStyle("-fx-background-color: rgb(116, 105, 182);");
        menuLayout.setAlignment(Pos.CENTER);

        Text namaUser = new Text(user.getName());
        namaUser.setFill(Color.rgb(255, 230, 230));
        namaUser.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        
        Button kembaliButton = new Button("Kembali");
        kembaliButton.setMinWidth(150);
        kembaliButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        kembaliButton.setOnAction(event -> mainApp.setScene(scene));
       
        Text saldoUser = new Text("Saldo : Rp " + user.getSaldo());
        saldoUser.setFill(Color.rgb(255, 230, 230));
        saldoUser.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        
        menuLayout.getChildren().addAll(namaUser, saldoUser, kembaliButton);   
        return new Scene(menuLayout, 800,600);
    }

    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, List<String> menuItems) {
        //TODO: Implementasi validasi isian pesanan
        namaRestoran = namaRestoran.toUpperCase();

        String orderId = DepeFood.handleBuatPesanan(namaRestoran, tanggalPemesanan, menuItems.size() , menuItems);
        if (orderId != null){
            String pesan = "Order dengan ID " + orderId + " " + "berhasil ditambahkan";
            showAlert("Pesanan Berhasil",null,pesan, Alert.AlertType.INFORMATION);
        }
        else{
            showAlert("Error", null, "Format Tanggal Salah!", Alert.AlertType.ERROR);
        }
    
    }

    private void handleBayarBill(String orderID, String pilihanPembayaran) {
        //TODO: Implementasi validasi pembayaran
        Order order = DepeFood.getOrderOrNull(orderID);
        if (order != null){
            String pesan;
            if (pilihanPembayaran == "Credit Card"){
                pesan = "Berhasil Membayar Bill sebesar Rp " + order.getTotalHarga() + " dengan biaya transaksi sebesar Rp " + (order.getTotalHarga()*2/100) ;
            }
            else{
                pesan = "Berhasil membayar bill sebesar Rp " + order.getTotalHarga();
            }
            DepeFood.handleBayarBill(orderID, pilihanPembayaran);
            showAlert("Pembayaran Berhasil",null, pesan, Alert.AlertType.INFORMATION);
            mainApp.setScene(payBillScene);
        }
        else {
            showAlert("Error", null, "Invalid OrderID. Please try again.", Alert.AlertType.ERROR);
        }
    }
}