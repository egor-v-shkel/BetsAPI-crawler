package by.vision.betsapicrawler.FXMLControllers;

import by.vision.betsapicrawler.MyLogger;
import by.vision.betsapicrawler.StageBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import static by.vision.betsapicrawler.StageBuilder.settings;

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
            settings.setChatID(Integer.parseInt(chatIdText));
            settings.setBotToken(botTokenText);
            settings.setBotName(botNameText);
            notification.setText("Настройки применены");
            notification.setTextFill(Color.GREEN);
            MyLogger.ROOT_LOGGER.debug("TgSettingsStage was applied");
        } else {
            notification.setText("Неправильно введены настройки");
            notification.setTextFill(Color.RED);
            MyLogger.ROOT_LOGGER.debug("Wrong input in TgSettingsStage");
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
        StageBuilder.getTgSettingsStage().hide();
    }

    @FXML
    void ok(ActionEvent event) {
        this.apply(event);
        this.cancel(event);
    }

    @FXML
    void initialize() {

    }
}
