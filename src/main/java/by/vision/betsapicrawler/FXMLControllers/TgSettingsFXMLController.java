package by.vision.betsapicrawler.FXMLControllers;

import java.net.URL;
import java.util.ResourceBundle;

import by.vision.betsapicrawler.Main;
import by.vision.betsapicrawler.Scenes.TgSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class TgSettingsFXMLController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button OK;

    @FXML
    private Button Apply;

    @FXML
    private Button Cancel;

    @FXML
    private TextField chatId;
    private String chatIdText = chatId.getText();
    @FXML
    private TextField botToken;
    private String botTokenText = botToken.getText();
    @FXML
    private TextField botName;
    private String botNameText = botName.getText();

    @FXML
    void apply(ActionEvent event) {
        checkInput();
        Main.settings.setTimeSelectMax(Integer.parseInt(chatIdText));
        Main.settings.setTimeSelectMax(Integer.parseInt(botTokenText));
        Main.settings.setTimeSelectMax(Integer.parseInt(botNameText));
    }

    private boolean checkInput() {
        return (chatIdText.matches("") ||
                botTokenText.matches("") ||
                botNameText.matches(""));
    }

    @FXML
    void cancel(ActionEvent event) {
        TgSettings.hide();
    }

    @FXML
    void ok(ActionEvent event) {
        this.apply(event);
        this.cancel(event);
    }

    @FXML
    void initialize() {
        assert OK != null : "fx:id=\"OK\" was not injected: check your FXML file 'TgSettings.fxml'.";
        assert Apply != null : "fx:id=\"Apply\" was not injected: check your FXML file 'TgSettings.fxml'.";
        assert Cancel != null : "fx:id=\"Cancel\" was not injected: check your FXML file 'TgSettings.fxml'.";
        assert chatId != null : "fx:id=\"chatId\" was not injected: check your FXML file 'TgSettings.fxml'.";
        assert botToken != null : "fx:id=\"botToken\" was not injected: check your FXML file 'TgSettings.fxml'.";
        assert botName != null : "fx:id=\"botName\" was not injected: check your FXML file 'TgSettings.fxml'.";

    }
}
