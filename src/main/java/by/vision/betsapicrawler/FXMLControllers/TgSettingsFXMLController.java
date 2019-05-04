package by.vision.betsapicrawler.FXMLControllers;

import by.vision.betsapicrawler.Main;
import by.vision.betsapicrawler.MyLogger;
import by.vision.betsapicrawler.Scenes.TgSettings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class TgSettingsFXMLController {
    @FXML
    private Button OK;

    @FXML
    private Button Apply;

    @FXML
    private Button Cancel;

    @FXML
    public TextField chatId;

    @FXML
    public TextField botToken;

    @FXML
    public TextField botName;

    @FXML
    private Label notification;

    private String chatIdText;
    private String botTokenText;
    private String botNameText;


    @FXML
    void apply(ActionEvent event) {
        getUserInput();
        if (checkInput()){
            //apply settings
            Main.settings.setChatID(Integer.parseInt(chatIdText));
            Main.settings.setBotToken(botTokenText);
            Main.settings.setBotName(botNameText);
            notification.setText("Настройки применены");
            notification.setTextFill(Color.GREEN);
            MyLogger.ROOT_LOGGER.debug("TgSettings was applied");
        } else {
            notification.setText("Неправильно введены настройки");
            notification.setTextFill(Color.RED);
            MyLogger.ROOT_LOGGER.debug("Wrong input in TgSettings");
        }
    }

    private boolean checkInput() {
        return (chatIdText.matches("[-]\\d+") &&
                botTokenText.matches("\\d{9}[:]\\w{35}") &&
                botNameText.matches("(?i).+(_bot)$"));
    }

    private void getUserInput(){
        chatIdText = chatId.getText();
        botTokenText = botToken.getText();
        botNameText = botName.getText();
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
        assert notification != null : "fx:id=\"notification\" was not injected: check your FXML file 'TgSettings.fxml'.";

    }
}
