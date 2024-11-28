import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class FXMLController {
    @FXML
    private CheckBox checkBox;

    @FXML
    public void initialize(){
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue){
                checkBox.setStyle("-fx-strikethrough: true;");
            } else{
                checkBox.setStyle("-fx-strikethrough: false;");
            }
        });
    }
}
