import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;

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

    private Stage stage;
    private List<CheckBox> checkBoxes = new ArrayList<>();

    public void setStage(Stage stage){
        this.stage = stage;
        int windowHeightMargin = 30;
        // Stageが表示された後にウィンドウサイズを取得
        stage.setOnShown(event -> {
            double windowWidth = stage.getWidth();
            // CheckBoxの設定
            setupCheckBoxes(windowWidth);
            // ScrollPaneの設定
            double windowHeight = stage.getHeight();
            fxScrollPane.setPrefSize(windowWidth-100,windowHeight-windowHeightMargin);
            // AnchorPaneの設定(機能してなさそう)
            fxAnchorPane.setPrefWidth(windowWidth-90);
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
    public void initialize(){
        //動的にcheckBoxを生成してリストに追加
        addCheckBox("めっちゃ長い文章の場合どうなるかのテスト，aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        addCheckBox("task2");
        for(int i = 0; i < 10; i++){
            addCheckBox("task" + i);
        }
    }

    private void addCheckBox(String text){
        CheckBox checkBox = new CheckBox();
        checkBox.setText(text);
        checkBox.getStyleClass().add("custom-task");  // styleClassの設定
        checkBoxes.add(checkBox);
        checkBoxContainer.getChildren().add(checkBox);
    }

    private void setupCheckBoxes(double windowWidth){
        for(CheckBox checkBox : checkBoxes){
            setupCheckBox(checkBox, windowWidth);
        }
        updateWrappingWidth(windowWidth); //幅変更はこっちで行う
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
        int windowWidthMargin = 60;
        for(CheckBox checkBox : checkBoxes){
            HBox hbox = (HBox) checkBox.getGraphic();
            if(hbox != null){
                Text text = (Text) hbox.getChildren().get(0);
                text.setWrappingWidth(windowWidth - windowWidthMargin); // 自動改行のための幅を更新（余白を考慮）
                checkBox.setPrefWidth(windowWidth - windowWidthMargin);
            }   
        }
    }

}