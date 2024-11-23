import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class CheckBoxFunc {
    private VBox vbox;

    public CheckBoxExample() {
        // チェックボックスを作成
        CheckBox cb1 = new CheckBox(); // キャプションなしのチェックボックス
        CheckBox cb2 = new CheckBox("Second"); // キャプション付きのチェックボックス

        // cb1にテキストを設定
        cb1.setText("First");
        // cb1を選択状態に設定
        cb1.setSelected(true);

        // レイアウトにチェックボックスを追加
        vbox = new VBox(cb1, cb2);
    }

    public VBox getVBox() {
        return vbox;
    }
}