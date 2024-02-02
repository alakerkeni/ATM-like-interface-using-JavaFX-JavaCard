package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {
	
    @Override
    public void start(Stage primaryStage) throws IOException {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
    	Parent root = loader.load();
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);   
        primaryStage.setTitle("login");
        primaryStage.show();
        
    }

    
}
