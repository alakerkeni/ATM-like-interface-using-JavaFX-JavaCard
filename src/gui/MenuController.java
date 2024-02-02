package gui;

import java.io.IOException;

import applicationClient.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Timer;
import java.util.TimerTask;


public class MenuController  {
	
	
	Client client;
	@FXML
    private TextField depositField, transferField;
	@FXML
    private Text title;
	
	MenuController menuController;
	
	/*@FXML
	private void handleDeposit() {
		String input = depositField.getText();
        if (input.isEmpty()) {
            showAlert("Empty Field", "Please enter an amount to deposit.");
            return;
        }

        try {
            double amount = Double.parseDouble(input);
            showAlert("Success", "Deposited: " + amount);
            //LoginController menuController = new LoginController();
            //menuController.openMenuInterface();
            
            Stage menuStage = getMainMenuStage();
            menuStage.show();
            Stage loginStage = (Stage) depositField.getScene().getWindow();
            loginStage.close();
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
    }*/
	 private Timer inactivityTimer;
	 private final long INACTIVITY_PERIOD = 10000; 

	    public void initialize() {
	        client = new Client();
	        client.connexion();
	        setupInactivityTimer();
	    }

	    private void setupInactivityTimer() {
	        inactivityTimer = new Timer();
	        inactivityTimer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	               logout();
	            }
	        }, INACTIVITY_PERIOD);
	    }

	    private void resetInactivityTimer() {
	        inactivityTimer.cancel(); 
	        setupInactivityTimer(); 
	    }


    @FXML
    private void openDepostitInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("deposit.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 600, 300);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("deposit your money");
            
            
            // Hide the main menu stage
            Stage menuStage = (Stage) title.getScene().getWindow();
            menuStage.hide();

            // Pass the main menu stage to the DepositController
            DepositController depositController = loader.getController();
            depositController.setMainMenuStage(menuStage);

            stage.show();
            resetInactivityTimer();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   /* @FXML
	private void handleTransfer() {
		String input = transferField.getText();
        if (input.isEmpty()) {
            showAlert("Empty Field", "Please enter an amount to transfer.");
            return;
        }

        try {
            double amount = Double.parseDouble(input);
            showAlert("Success", "transferred: " + amount);
            LoginController menuController = new LoginController();
            menuController.openMenuInterface();
            
            Stage loginStage = (Stage) transferField.getScene().getWindow();
            loginStage.close();
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Please enter a valid numeric amount.");
        }
    }*/
    
    @FXML
    private void openTransferInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("transfer.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 600, 446);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("transfer");
            
            // Hide the main menu stage
            Stage menuStage = (Stage) title.getScene().getWindow();
            menuStage.hide();

            // Pass the main menu stage to the DepositController
            TransferController transferController = loader.getController();
            transferController.setMainMenuStage(menuStage);
            
            stage.show();
            resetInactivityTimer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void openBalanceInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("withdraw.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 600, 446);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Menu");
            
            // Hide the main menu stage
            Stage menuStage = (Stage) title.getScene().getWindow();
            menuStage.hide();

            // Pass the main menu stage to the DepositController
            WithdrawController withdrawController = loader.getController();
            withdrawController.setMainMenuStage(menuStage);
            
            stage.show();
            resetInactivityTimer();

            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void logout() {
    	try {
            final Stage menuStage1 = (Stage) title.getScene().getWindow();
            final Stage menuStage2 = (Stage) title.getScene().getWindow();
            client.mise_hors_tension();

            Platform.runLater(new Runnable() {
				public void run() {
				    try {
				        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
				        Parent root = loader.load();
				        Scene scene = new Scene(root, 500, 500);
				        Stage primaryStage = new Stage();
				        primaryStage.setScene(scene);
				        primaryStage.setTitle("login");
				        primaryStage.show();
				        menuStage1.close();
				    } catch (IOException e) {
				        e.printStackTrace();
				    }
				}
			});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateTestMontant(double amount) {
        test_Montant += amount;
        System.out.println("Updated test_Montant: " + test_Montant); // For testing
    }

    static double test_Montant =0;
}
