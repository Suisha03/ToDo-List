import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FXMLController {
    @FXML
    private VBox checkBoxContainer;

    private Stage stage;
    private List<CheckBox> checkBoxes = new ArrayList<>();

    public void setStage(Stage stage){
        this.stage = stage;
        // Stageが表示された後にウィンドウサイズを取得
        stage.setOnShown(event -> {
            double windowWidth = stage.getWidth();
            // CheckBoxの設定
            setupCheckBoxes(windowWidth);
        });

        // ウィンドウの幅が変更された場合に改行位置を調節
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            double newWidth = newValue.doubleValue();
            updateWrappingWidth(newWidth);
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
    }

    

    public void setupCheckBox(CheckBox checkBox, double windowWidth){
        Text text = new Text(checkBox.getText());
        text.setWrappingWidth(windowWidth-50); //自動改行のための幅を設定
        HBox hbox = new HBox(text);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(0,0,0,5)); //左側に余白を設定
        checkBox.setGraphic(hbox);
        checkBox.setText(""); // CheckBoxのテキストを空にする

        // Textノードにクリックイベントを設定し、CheckBoxの選択状態を変更しないようにする
        hbox.setOnMouseClicked(event -> {
            // CheckBoxの選択状態を反転(クリックイベントを無効化したいため)
            boolean stateCheckbox = !checkBox.isSelected();
            checkBox.setSelected(stateCheckbox);
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
        for(CheckBox checkBox : checkBoxes){
            HBox hbox = (HBox) checkBox.getGraphic();
            if(hbox != null){
                Text text = (Text) hbox.getChildren().get(0);
                text.setWrappingWidth(windowWidth - 50); // 自動改行のための幅を更新（余白を考慮）
            }   
        }
    }
}