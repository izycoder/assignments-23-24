    package assignments.assignment4.components;

    import javafx.beans.property.SimpleStringProperty;
    import javafx.beans.property.StringProperty;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;
    import javafx.geometry.Insets;
    import javafx.geometry.Pos;
    import javafx.scene.Scene;
    import javafx.scene.control.*;
    import javafx.scene.text.Text;
    import javafx.scene.text.*;
    import javafx.scene.paint.Color;
    import javafx.scene.layout.GridPane;
    import javafx.scene.layout.VBox;
    import javafx.stage.Stage;
    import assignments.assignment3.DepeFood;
    import assignments.assignment3.Menu;
    import assignments.assignment3.Order;
    import assignments.assignment3.User;
    import javafx.scene.control.Alert;
    import javafx.scene.control.Alert.AlertType;
    import assignments.assignment4.MainApp;

    public class BillPrinter {
        private Stage stage;
        private MainApp mainApp;
        private User user;
        private Scene customerMenuScene;

        public BillPrinter(Stage stage, MainApp mainApp, User user, Scene customerMenuScene) {
            this.stage = stage;
            this.mainApp = mainApp;
            this.user = user;
            this.customerMenuScene = customerMenuScene;
        }

        private Scene createBillPrinterForm(String orderId){
            //TODO: Implementasi untuk menampilkan komponen hasil cetak bill
            VBox layout = new VBox(10);
            
            layout.setStyle("-fx-background-color: rgb(116, 105, 182);");
            layout.setAlignment(Pos.CENTER);
            
            printBill(orderId);
            Order order = DepeFood.getOrderOrNull(orderId);
            Text billText = new Text("Bill");
            billText.setFill(Color.rgb(255, 230, 230));
            billText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
            

            Text orderIdText = new Text("Order ID: " + order.getOrderId());
            orderIdText.setFill(Color.rgb(255, 230, 230));
            orderIdText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
            

            Text tanggalText = new Text("Tanggal Pemesanan: " + order.getTanggal());
            tanggalText.setFill(Color.rgb(255, 230, 230));
            tanggalText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        
            
            Text restaurantText = new Text("Restaurant: " + order.getRestaurant().getNama());
            restaurantText.setFill(Color.rgb(255, 230, 230));
            restaurantText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        
            Text lokasiText = new Text("Lokasi Pengiriman: " + user.getLokasi());
            lokasiText.setFill(Color.rgb(255, 230, 230));
            lokasiText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
            
            Text statusText;
            if (order.getOrderFinished()){
                statusText = new Text("Status Pengiriman: Finished");
            }
            else{
                statusText = new Text("Status Pengiriman: Not Finished");
            }
            statusText.setFill(Color.rgb(255, 230, 230));
            statusText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        
            Text pesananText = new Text("Pesanan: ");
            pesananText.setFill(Color.rgb(255, 230, 230));
            pesananText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
            
            VBox menuItemsBox = new VBox(10);
            menuItemsBox.setAlignment(Pos.CENTER);
            String menuString = order.getRestaurant().printMenu();
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
                Text menuText = new Text(formattedMenu.toString()); // Create a Text object for the formatted menu item
                menuText.setFill(Color.rgb(255, 230, 230));
                menuText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                menuItemsBox.getChildren().add(menuText); // Add the Text object to the VBox
                }
            }
        
            Text ongkosText = new Text("Biaya Ongkos Kirim: " + order.getOngkir());
            ongkosText.setFill(Color.rgb(255, 230, 230));
            ongkosText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        
            Text totalText = new Text("Total Biaya: " + String.format("%.0f",order.getTotalHarga()));
            totalText.setFill(Color.rgb(255, 230, 230));
            totalText.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
            
            Button kembaliButton = new Button("Kembali");
            kembaliButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
            
            kembaliButton.setOnAction(event -> mainApp.setScene(customerMenuScene));
            layout.getChildren().addAll(billText, orderIdText, tanggalText, restaurantText, lokasiText, statusText, pesananText , menuItemsBox, ongkosText, totalText, kembaliButton);        
            return new Scene(layout, 800, 600);
        }

        private void printBill(String orderId) {
            //TODO: Implementasi validasi orderID
            Order order = DepeFood.getOrderOrNull(orderId);
            if (order != null) {
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Invalid OrderID. Please try again.");
                alert.showAndWait();
            }
        }

        public Scene getScene(String orderId) {
            return this.createBillPrinterForm(orderId);
        }

        // Class ini opsional
        public class MenuItem {
            private final StringProperty itemName;
            private final StringProperty price;

            public MenuItem(String itemName, String price) {
                this.itemName = new SimpleStringProperty(itemName);
                this.price = new SimpleStringProperty(price);
            }

            public StringProperty itemNameProperty() {
                return itemName;
            }

            public StringProperty priceProperty() {
                return price;
            }

            public String getItemName() {
                return itemName.get();
            }

            public String getPrice() {
                return price.get();
            }
        }
    }
