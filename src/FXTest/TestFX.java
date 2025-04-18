package FXTest;
import java.io.IOException;
 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
 
public class TestFX extends Application {
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage stage) {
            BorderPane root;
            try {
                root = (BorderPane)FXMLLoader.load(getClass().getResource("Main.fxml"));
                Scene scene = new Scene(root,200,100);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
 
}