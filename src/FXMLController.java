import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class FXMLController {
    @FXML
    private CheckBox taskBox1;
    @FXML
    private CheckBox taskBox2;
    @FXML
    private HBox container;

    @FXML
    public void initialize(){
        setupCheckBox(taskBox1);
        setupCheckBox(taskBox2);
    }

    public void setupCheckBox(CheckBox checkBox){
        Text text = new Text(checkBox.getText());
        HBox hbox = new HBox(text);
        hbox.setSpacing(10); // テキストの左に余白を追加
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
}