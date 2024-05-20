package assignments.assignment4.components.form;

import assignments.assignment3.DepeFood;
import assignments.assignment3.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;

import java.util.function.Consumer;

public class LoginForm {
    private Stage stage;
    private MainApp mainApp; // MainApp instance
    private TextField nameInput;
    private TextField phoneInput;

    public LoginForm(Stage stage, MainApp mainApp) { // Pass MainApp instance to constructor
        this.stage = stage;
        this.mainApp = mainApp; // Store MainApp instance
    }

    private Scene createLoginForm() {
        //TODO: Implementasi method untuk menampilkan komponen form login
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: rgb(116, 105, 182);");
        grid.setHgap(50);
        grid.setAlignment(Pos.CENTER);
        

        // Add "Welcome to DepeFood" text on the left
        VBox welcomeText = new VBox();
        welcomeText.setAlignment(Pos.CENTER); // Align to bottom left
        welcomeText.setSpacing(5); // Spacing between texts
        Text welcome = new Text("Welcome to");
        welcome.setFill(Color.rgb(255, 230, 230));
        Text depe = new Text("DepeFood");
        depe.setFill(Color.rgb(255, 230, 230));
        welcome.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        depe.setFont(Font.font("Verdana", FontWeight.BOLD, 30));
        welcomeText.getChildren().addAll(welcome, depe);
        grid.add(welcomeText, 0, 0);

        // Create the login form (text fields and button) on the right
        nameInput = new TextField();
        nameInput.setPrefWidth(250);
        nameInput.setPromptText("Enter Your Name");
        phoneInput = new TextField();
        phoneInput.setPrefWidth(250);
        phoneInput.setPromptText("Enter Your Phone Number");
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: rgb(225, 175, 209);");
        loginButton.setOnAction(event -> handleLogin());

        // Create a VBox to hold the login form elements vertically
        VBox loginFormVBox = new VBox(10); // Spacing between elements
        loginFormVBox.setAlignment(Pos.CENTER_LEFT);
        Text name = new Text("Name:");
        Text phone = new Text("Phone Number:");
        name.setFill(Color.rgb(255, 230, 230));
        phone.setFill(Color.rgb(255, 230, 230));
        loginFormVBox.getChildren().addAll(name, nameInput, phone, phoneInput, loginButton);

        grid.add(loginFormVBox, 1, 0); // Add to the second column, first row

        return new Scene(grid, 800, 600);
    }


    private void handleLogin() {
        //TODO: Implementasi validasi isian form login
        String nama = nameInput.getText();
        String noTelp = phoneInput.getText();
        User userLoggedIn = DepeFood.handleLogin(nama, noTelp);
        if (userLoggedIn != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText(null);
            alert.setContentText("Login successful. Welcome, " + userLoggedIn.getName() + "!");
            alert.showAndWait();
            if (userLoggedIn.role.equals("Admin")){
                AdminMenu adminMenu = new AdminMenu(stage, mainApp, userLoggedIn);
                nameInput.clear();
                phoneInput.clear();
                mainApp.setScene(adminMenu.createBaseMenu());
            }
            else{
                CustomerMenu customerMenu = new CustomerMenu(stage, mainApp, userLoggedIn);
                nameInput.clear();
                phoneInput.clear();
                mainApp.setScene(customerMenu.createBaseMenu());
            }
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid ID or password. Please try again.");
            alert.showAndWait();
        }
    }

    public Scene getScene(){
        return this.createLoginForm();
    }

}
