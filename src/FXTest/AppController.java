package FXTest;
 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
 
public class AppController {
    @FXML Label label1;
    @FXML TextField field1;
     
    @FXML
    protected void doAction(ActionEvent ev){
        String str = field1.getText();
        str = "あなたは、「" + str + "」と書いた。";
        label1.setText(str);
    }
}