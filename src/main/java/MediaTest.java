import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.URISyntaxException;
import java.net.URL;
import java.io.File;

public class MediaTest extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.show();

        Media m = new Media(new File("lib/sound/taskCompleteSound.mp3").toURI().toString());

        MediaPlayer mp = new MediaPlayer(m);
        mp.play();
        System.out.println("再生中");
    }

    public static void main(String[] args) {
        launch(args);
    }
}