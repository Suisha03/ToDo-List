import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // CheckBoxExampleのインスタンスを作成
        CheckBoxFunc checkBoxExample = new CheckBoxExample();

        // シーンを作成し、ステージに設定
        Scene scene = new Scene(checkBoxExample.getVBox(), 200, 100);
        primaryStage.setScene(scene);
        primaryStage.setTitle("CheckBox Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}