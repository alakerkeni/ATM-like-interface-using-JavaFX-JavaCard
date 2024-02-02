package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TransferController {
    private Stage mainMenuStage;

    public void setMainMenuStage(Stage mainMenuStage) {
        this.mainMenuStage = mainMenuStage;
    }

    @FXML
    private TextField transferField; // Amount of money to transfer

    @FXML
    private TextField cardNumberField; // Destination card number field
    
    @FXML
    private Text balanceText;
    
    @FXML
    private void initialize() {
    	balanceText.setText("Balance: " + MenuController.test_Montant);
    }

    @FXML
    private void handleTransfer() {
        String destinationCardNumber = cardNumberField.getText();
        String amountInput = transferField.getText();
        
        if (destinationCardNumber.isEmpty() || amountInput.isEmpty()) {
            showAlert("Empty Fields", "Please enter both destination card number and amount.");
            return;
        }

        try {
            double amount = Double.parseDouble(amountInput);
            
            if (amount <= 0) {
                showAlert("Invalid Amount", "Please enter a valid positive amount to transfer.");
                return;
            }

            if (!isValidCardNumber(destinationCardNumber)) {
                showAlert("Invalid Card Number", "Please enter a valid destination card number.");
                return;
            }
            
            if(MenuController.test_Montant<amount)
            {
            	showAlert("insufficient money", "transfer not possible");
                return;
            }
            
            MenuController.test_Montant-=amount;
            System.out.println("Updated test_Montant: " + MenuController.test_Montant);
            
            showAlert("Success", "Transferred: " + amount);

            Stage transferStage = (Stage) transferField.getScene().getWindow();
            transferStage.close();

            mainMenuStage.show();
        } catch (NumberFormatException e) {
            showAlert("Invalid Amount", "Please enter a valid numeric amount.");
        }
    }
    
    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.length() >= 16; 
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    @FXML
    private void cancel() {
    	Stage transferStage = (Stage) transferField.getScene().getWindow();
        transferStage.close();

        mainMenuStage.show();
    }
}
