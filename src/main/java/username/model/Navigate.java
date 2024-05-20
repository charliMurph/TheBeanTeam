package username.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class Navigate {
    public static void goTo(String fxmlFileName, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(Navigate.class.getResource(fxmlFileName));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any potential IOException, such as FXML file not found
        }
    }
}