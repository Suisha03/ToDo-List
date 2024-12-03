import java.net.URL;
 
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL location = getClass().getResource("MainFX.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(location);
        Pane root = (Pane) fxmlLoader.load();
        
        // コントローラーを取得して Stage を渡す
        FXMLController controller = fxmlLoader.getController();
        controller.setStage(primaryStage);

        Scene scene = new Scene(root, 300, 300);

        //ステージの最小サイズを設定
        primaryStage.minWidthProperty().set(300);
        primaryStage.minHeightProperty().set(300);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}