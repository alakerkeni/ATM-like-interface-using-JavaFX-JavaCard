// DepositController.java
package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DepositController {
    private Stage mainMenuStage;

    public void setMainMenuStage(Stage mainMenuStage) {
        this.mainMenuStage = mainMenuStage;
    }
    
    @FXML
    String balance = "Balance: " + MenuController.test_Montant;
    
    @FXML
    private Text balanceText;

    @FXML
    private TextField depositField;
    
    private void updateBalanceText() {
    	balanceText.setText("Balance: " + MenuController.test_Montant); 
    }
    
    @FXML
    private void initialize() {
    	balanceText.setText("Balance: " + MenuController.test_Montant);
    }

    @FXML
    private void handleDeposit() {
        String input = depositField.getText();
        if (input.isEmpty()) {
            showAlert("Empty Field", "Please enter an amount to deposit.");
            return;
        }

        try {
            double amount = Double.parseDouble(input);
            
            
            if(amount>33000)
            {
            	showAlert("impossible", "deposit not possible");
                return;
            }
            
            showAlert("Success", "Deposited: " + amount);
            MenuController.test_Montant+=amount;
            System.out.println("Updated test_Montant: " + MenuController.test_Montant);
            Stage depositStage = (Stage) depositField.getScene().getWindow();
            depositStage.close();

            mainMenuStage.show();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid numeric amount.");
        }
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
    	Stage transferStage = (Stage) depositField.getScene().getWindow();
        transferStage.close();

        mainMenuStage.show();
    }

}
