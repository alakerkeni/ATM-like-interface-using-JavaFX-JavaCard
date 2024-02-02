package gui;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import applicationClient.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginController {

    private Client client;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidCreditCardNumber(username) && isValidPIN(password)) {
        	String hashedPIN = hashPIN(password);
            System.out.println("Login successful!");
            Stage loginStage = (Stage) usernameField.getScene().getWindow();
            loginStage.close();
            openMenuInterface();
        } else {
            displayErrorDialog("Invalid Login", "Invalid credit card number or PIN.");
        }
    }

    private boolean isValidCreditCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{16}"); 
    }

    private boolean isValidPIN(String pin) {
        return pin.matches("\\d{6}"); 
    }

    private String hashPIN(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(pin.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void displayErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLogin();
        }
    }

    protected void openMenuInterface() {
        try {
            // client = new Client();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 446);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Menu");

            // MenuController menuController = loader.getController();
            // menuController.setClient(client);

            stage.show();

            // client.connexion();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
