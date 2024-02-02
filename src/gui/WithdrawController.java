// WithdrawController.java
package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WithdrawController {
    private Stage mainMenuStage;

    public void setMainMenuStage(Stage mainMenuStage) {
        this.mainMenuStage = mainMenuStage;
    }

    @FXML
    private TextField withdrawField;

    @FXML
    private void handleWithdraw() {
        String input = withdrawField.getText();
        if (input.isEmpty()) {
            showAlert("Empty Field", "Please enter an amount to withdraw.");
            return;
        }
        try {
            double amount = Double.parseDouble(input);
            if (amount % 5 != 0) {
                showAlert("Invalid Amount", "Withdrawal amount should be in increments of 5.");
                return;
            }
            
            if(MenuController.test_Montant-amount<-200)
            {
            	showAlert("impossible", "withdraw not possible");
                return;
            }
            
            MenuController.test_Montant-=amount;
            System.out.println("Updated test_Montant: " + MenuController.test_Montant);
            showAlert("Success", "Withdrawn: " + amount);
            
            Stage withdrawStage = (Stage) withdrawField.getScene().getWindow();
            withdrawStage.close();

            mainMenuStage.show();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid numeric amount.");
        }
        
        try {
            double amount = Double.parseDouble(input);
            showAlert("Success", "Withdrawn: " + amount);

            Stage withdrawStage = (Stage) withdrawField.getScene().getWindow();
            withdrawStage.close();

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
    	Stage transferStage = (Stage) withdrawField.getScene().getWindow();
        transferStage.close();

        mainMenuStage.show();
    }
}
