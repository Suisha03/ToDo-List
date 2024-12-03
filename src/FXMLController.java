import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FXMLController {
    @FXML
    private CheckBox taskBox1;
    @FXML
    private CheckBox taskBox2;
    @FXML
    private HBox container;

    private Stage stage;

    //ウィンドウの幅を保持する変数
    double windowWidth;

    public void setStage(Stage stage){
        this.stage = stage;
        // Stageが表示された後にウィンドウサイズを取得
        stage.setOnShown(event -> {
            windowWidth = stage.getWidth();
            System.out.println("Window width: " + windowWidth);
            // CheckBoxの設定
            setupCheckBox(taskBox1);
            setupCheckBox(taskBox2);
        });

        // ウィンドウの幅が変更された場合に改行位置を調節
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            double newWidth = newValue.doubleValue();
            System.out.println("New window width: " + newWidth);
            updateWrappingWidth(taskBox1, newWidth);
            updateWrappingWidth(taskBox2, newWidth);
        });
    }

    

    public void setupCheckBox(CheckBox checkBox){
        Text text = new Text(checkBox.getText());
        text.setWrappingWidth(windowWidth); //自動改行のための幅を設定
        HBox hbox = new HBox(text);
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(0,0,0,5)); //左側に余白を設定
        checkBox.setGraphic(hbox);
        checkBox.setText(""); // CheckBoxのテキストを空にする
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

    public void updateWrappingWidth(CheckBox checkBox, double windowWidth) {
        HBox hbox = (HBox) checkBox.getGraphic();
        if(hbox != null){
            Text text = (Text) hbox.getChildren().get(0);
            text.setWrappingWidth(windowWidth - 50); // 自動改行のための幅を更新（余白を考慮）
        }
    }
}