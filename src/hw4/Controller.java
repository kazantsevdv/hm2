package hw4;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    public TextArea textArea;
    @FXML
    public TextField inputText;

    public void sendMsg() {
        if (inputText.getText().length() > 0) {
            textArea.appendText(inputText.getText() + "\n");
            inputText.setText("");
        }

    }
}
