import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URISyntaxException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


public class FXMLController {
    @FXML
    private VBox checkBoxContainer;

    @FXML
    private ScrollPane fxScrollPane;

    @FXML
    private AnchorPane fxAnchorPane;

    @FXML
    private TextField fxTaskAddingField;

    @FXML
    private Button fxTaskAddingButton;

    @FXML
    private VBox FinishedTaskContainer;

    @FXML
    private Timeline timeline;

    private Stage stage;
    private List<HBox> checkBoxHboxList = new ArrayList<>();

    //個々の横幅変更でhboxの横幅も変わる
    int CheckBoxWidthWrap = 45;
    int TextNodeWidthWrap = 135;

    public void setStage(Stage stage){
        this.stage = stage;
        int windowHeightMargin = 30;
        // Stageが表示された後にウィンドウサイズを取得
        stage.setOnShown(event -> {
            double windowWidth = stage.getWidth();
            //System.out.println(windowWidth);
            // ScrollPaneの設定
            double windowHeight = stage.getHeight();
            fxScrollPane.setPrefSize(windowWidth-100,windowHeight-windowHeightMargin);

            fxAnchorPane.setPrefWidth(windowWidth-90);            // AnchorPaneの設定(機能してなさそう)

            initialize(windowWidth);             // initializeメソッドを呼び出し、windowWidthを渡す
        });

        // ウィンドウの幅が変更された場合に改行位置を調節
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            double newWidth = newValue.doubleValue();
            setCheckBoxWidth(newWidth);
        });

        // ウィンドウの高さが変更された場合にScrollPaneの高さを調節
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            double newHeight = newValue.doubleValue();
            fxScrollPane.setPrefHeight(newHeight-windowHeightMargin);
        });
    }

    @FXML
    public void initialize(double windowWidth){
        //動的にcheckBoxを生成してリストに追加
        addCheckBox("めっちゃ長い文章の場合どうなるかのテスト，aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", windowWidth);
        addCheckBox("task2", windowWidth);
        for(int i = 0; i < 10; i++){
            addCheckBox("task" + i, windowWidth);
        }
        SaveandLoadTasks saveAndLoadTasks = new SaveandLoadTasks();
        saveAndLoadTasks.loadTasks();
        setCheckBoxWidth(windowWidth);
        fxTaskAddingField.getStyleClass().add("custom-taskAddingField"); // styleClassの設定
        //updateWrappingWidth(windowWidth);    // CheckBoxの自動改行のための幅を設定
        // TextFieldにエンターキーが押されたときのイベントハンドラを設定
        fxTaskAddingField.setOnAction(event -> {
            String text = fxTaskAddingField.getText();
            if(text.isEmpty()) return;
            addCheckBox(text, windowWidth);
            setCheckBoxWidth(windowWidth);
            fxTaskAddingField.clear(); // テキストフィールドをクリア
        });

        //タスク追加ボタンを画像に設定
        Image image = new Image(getClass().getResourceAsStream("/lib/img/TaskAddingButtonImageCustom.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        fxTaskAddingButton.setGraphic(imageView);
        fxTaskAddingButton.getStyleClass().add("custom-taskAddingButton");

        // タスク追加ボタンにイベントハンドラを設定
        fxTaskAddingButton.setOnAction(event->{
            String text = fxTaskAddingField.getText();
            if(text.isEmpty()) return;
            addCheckBox(text, windowWidth);
            setCheckBoxWidth(windowWidth);
            fxTaskAddingField.clear(); // テキストフィールドをクリア
        });
    }

    //以降は関数内容のみ

    //CheckBoxを追加する関数
    private void addCheckBox(String text, double windowWidth){
        HBox hbox = new HBox(5);
        CheckBox checkBox = new CheckBox();
        Text textNode = new Text(text);
        StopWatchButton timelabel = new StopWatchButton("00:00");
        timelabel.tf.setPrefWidth(80);
        hbox.getChildren().addAll(checkBox, textNode, timelabel);
        checkBoxHboxList.add(hbox);

        //ここからレイアウト設定
        checkBox.getStyleClass().add("custom-task");  // styleClassの設定
        hbox.getStyleClass().add("custom-task-hbox");  // styleClassの設定
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(0,5,0,5));
        textNode.getStyleClass().add("custom-checkbox-text");  // styleClassの設定

        // CheckBoxにマージンを設定
        //HBox.setMargin(hbox, new Insets(100, 0, 100, 0));

        //CheckBox関係のクリックイベントを設定(個々の部分クラスにまとめるとか無理？)
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                textNode.setStrikethrough(true);
                textNode.setFill(Color.rgb(110, 108, 108)); // テキストの色を設定

                //タスクを削除
                checkBoxContainer.getChildren().remove(hbox);

                //完了済みタスクに飛ばす
                FinishedTaskContainer.getChildren().add(hbox);
                VBox.setMargin(hbox, new Insets(3, 0, 0, 3)); //マージンを再変更

                //音声を再生(再生されない，原因不明)
                //playSound("/lib/sound/taskCompleteSound.mp3");
            } else{
                textNode.setStrikethrough(false);
                textNode.setFill(Color.BLACK); // テキストの色を元に戻す
                FinishedTaskContainer.getChildren().remove(hbox);
                checkBoxContainer.getChildren().add(hbox);
                VBox.setMargin(hbox, new Insets(3, 0, 0, 3)); //マージンを再変更
            }
        });

        //checkBoxContainerに追加することで画面に表示
        checkBoxContainer.getChildren().add(hbox);
        VBox.setMargin(hbox, new Insets(3, 0, 0, 5));
    }

    //CheckBoxの幅を設定する関数
    private void setCheckBoxWidth(double windowWidth){
        for(HBox hbox : checkBoxHboxList){
            //ここがタスクの横幅ところっぽい
            hbox.setPrefWidth(windowWidth-CheckBoxWidthWrap);
            Text textNode = (Text) hbox.getChildren().get(1);
            textNode.setWrappingWidth(windowWidth - TextNodeWidthWrap);
        }
    }

    // 音声を再生するメソッド(再生されない，原因不明，使用していない)
    private void playSound(String soundFilePath) {
        URL soundURL = getClass().getResource(soundFilePath);
        if(soundURL != null){
            try {
                Media sound = new Media(soundURL.toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);

                // MediaPlayerの状態を監視
                mediaPlayer.setOnReady(() -> {
                    System.out.println("Media is ready to play.");
                    mediaPlayer.play();
                });

                mediaPlayer.setOnPlaying(() -> {
                    System.out.println("Media is playing.");
                });

                mediaPlayer.setOnEndOfMedia(() -> {
                    System.out.println("Media playback finished.");
                    mediaPlayer.dispose();
                });

                mediaPlayer.setOnError(() -> {
                    System.err.println("Media error occurred: " + mediaPlayer.getError());
                });

                System.out.println("音声を再生しました: " + soundFilePath);
            } catch (URISyntaxException e) {
                System.err.println("Invalid URI: " + soundURL);
                e.printStackTrace();
            }
        }
        else{
            System.out.println("音声ファイルが見つかりません: " + soundFilePath);
        }
    }

    //ストップウォッチボタンを追加
    class StopWatchButton extends Button{
        TextField tf = new TextField();
        // 配列にしておかないとラムダ式内での変数の変更ができない
        final boolean[] timelabelbool = {false};
        // ストップウォッチのためのタイマーを設定
        final int[] seconds = {0};
        Timeline timeline;

        public StopWatchButton(String text){
            setText(text);
            getChildren().add(tf);

            timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                seconds[0]++;
                int minutes = seconds[0] / 60;
                int remainingSeconds = seconds[0] % 60;
                setText(String.format("%02d:%02d", minutes, remainingSeconds));
            }));
            //無限にTimelineを繰り返せるようにする
            timeline.setCycleCount(Timeline.INDEFINITE);

            setOnMouseClicked(event -> {
                if(timelabelbool[0]){
                    timelabelbool[0] = false;
                    setStyle("-fx-text-fill: black;");
                    timeline.stop();
                } else {
                    timelabelbool[0] = true;
                    setStyle("-fx-text-fill: red;");
                    timeline.play();
                }
            });
        }
    }

    class SaveandLoadTasks{
        private String taskText;
        private boolean taskStatus;
        private int taskTime;

        public SaveandLoadTasks(){
        }

        public SaveandLoadTasks(String taskText, boolean taskStatus, int taskTime){
            this.taskText = taskText;
            this.taskStatus = taskStatus;
            this.taskTime = taskTime;
        }

        public void saveTasks(){
            Gson gson = new Gson();
            try(FileWriter writer = new FileWriter("tasksSaveData.json")){
                gson.toJson(this, writer);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
        
        public void loadTasks(){
            Gson gson = new Gson();
            try(FileReader reader = new FileReader("tasksSaveData.json")){
                Type taskListType = new TypeToken<List<Task>>() {}.getType();
                tasks = gson.fromJson(reader, taskListType);
                // タスクをUIに反映する
                for (Task task : tasks) {
                    addCheckBox(task.getText(), task.isCompleted());
                }
            } catch(IOException e){
                e.printStackTrace();
                System.out.println("タスクの読み込みに失敗しました");
            }
        }
    }

}