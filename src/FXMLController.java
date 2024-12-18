import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
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
import java.util.ArrayList;
import java.util.List;

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

    private Stage stage;
    private List<HBox> checkBoxes = new ArrayList<>();

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
            updateWrappingWidth(newWidth);
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
        fxTaskAddingField.getStyleClass().add("custom-taskAddingField"); // styleClassの設定
        updateWrappingWidth(windowWidth);    // CheckBoxの自動改行のための幅を設定
        // TextFieldにエンターキーが押されたときのイベントハンドラを設定
        fxTaskAddingField.setOnAction(event -> {
            String text = fxTaskAddingField.getText();
            if(text.isEmpty()) return;
            addCheckBox(text, windowWidth);
            fxTaskAddingField.clear(); // テキストフィールドをクリア
        });

        //タスク追加ボタンを画像に設定
        Image image = new Image(getClass().getResourceAsStream("/lib/TaskAddingButtonImageCustom.png"));
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
            fxTaskAddingField.clear(); // テキストフィールドをクリア
        });
    }

    //以降は関数内容のみ

    private void addCheckBox(String text, double windowWidth){
        HBox hbox = new HBox();
        CheckBox checkBox = new CheckBox();
        checkBox.getStyleClass().add("custom-task");  // styleClassの設定
        Label label = new Label();
        label.setText(text);
        hbox.getChildren().addAll(checkBox, label);
        checkBoxes.add(hbox);

        //ここからレイアウト設定
        

        
        //setupCheckBox(checkBox, windowWidth);           //CheckBoxのレイアウト設定
    }

    public void setupCheckBox(CheckBox checkBox, double windowWidth){
        Text text = new Text(checkBox.getText());
        HBox hbox = new HBox(text);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(0,0,0,5));      //テキスト左側に余白を設定
        checkBox.setGraphic(hbox);
        checkBox.setText("");                // CheckBoxのテキストを空にする
        checkBox.setPadding(new Insets(0,0,0,5));  // CheckBoxの左側に余白を設定

        // Textノードにクリックイベントを設定し、CheckBoxの選択状態を変更しないようにする
        hbox.setOnMouseClicked(event -> {
            // CheckBoxの選択状態を反転(クリックイベントを無効化したいため)
            checkBox.setSelected(!checkBox.isSelected());
        });

        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println("Observable: " + observable + ", Old Value: " + oldValue + ", New Value: " + newValue);
            if(newValue){
                text.setStrikethrough(true);
                text.setFill(Color.rgb(110, 108, 108)); // テキストの色を設定
            } else{
                text.setStrikethrough(false);
                text.setFill(Color.BLACK); // テキストの色を元に戻す
            }
        });
    }

    public void updateWrappingWidth(double windowWidth) {
        int windowWidthMargin = 65;
        //System.out.println(windowWidth);
        for(CheckBox checkBox : checkBoxes){
            HBox hbox = (HBox) checkBox.getGraphic();
            if(hbox != null){
                Text text = (Text) hbox.getChildren().get(0);
                text.setWrappingWidth(windowWidth - windowWidthMargin); // 自動改行のための幅を更新（余白を考慮）
                checkBox.setPrefWidth(windowWidth - windowWidthMargin+5);
            }   
        }
    }

}