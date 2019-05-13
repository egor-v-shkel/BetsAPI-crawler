package by.vision.betsapicrawler.FXMLControllers;

import by.vision.betsapicrawler.MyLogger;
import by.vision.betsapicrawler.StageBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import static by.vision.betsapicrawler.SettingsModel.currentSettings;

public class TgSettingsFXMLController implements SettingsController {
    @FXML
    public TextField chatId;
    @FXML
    public TextField botToken;
    @FXML
    public TextField botName;
    @FXML
    private Button OK;
    @FXML
    private Button Apply;
    @FXML
    private Button Cancel;
    @FXML
    private Label notification;

    private String chatIdText;
    private String botTokenText;
    private String botNameText;


    @FXML
    void apply(ActionEvent event) {
        getUserInput();
        if (checkUserInput()) {

            long chatID = Long.parseLong(chatIdText);

            currentSettings.setChatID(chatID);
            currentSettings.setBotToken(botTokenText);
            currentSettings.setBotName(botNameText);

            notification.setText("Настройки применены");
            notification.setTextFill(Color.GREEN);

            MyLogger.ROOT_LOGGER.info("Telegram currentSettings was applied");
        } else {
            notification.setText("Неправильно введены настройки");
            notification.setTextFill(Color.RED);
            MyLogger.ROOT_LOGGER.debug("Wrong user input");
        }
    }

    private boolean checkUserInput() {
        return (chatIdText.matches("[-]\\d+|\\d+") &&
                botTokenText.matches("\\d{9}[:]\\w{35}") &&
                botNameText.matches("(?i).+(bot)$"));
    }

    @FXML
    void cancel(ActionEvent event) {
        notification.setText("");
        StageBuilder.getTgSettingsStage().hide();
    }

    @FXML
    void ok(ActionEvent event) {
        this.apply(event);
        this.cancel(event);
    }

    @FXML
    void initialize() {
        //do nothing
    }

    /**
     * Make current currentSettings visible in GUI
     */
    public void showSettings() {

        if (currentSettings.checkNotNullTgSettings()) {
            chatId.setText(String.valueOf(currentSettings.getChatID()));
            botName.setText(String.valueOf(currentSettings.getBotName()));
            botToken.setText(String.valueOf(currentSettings.getBotToken()));
        }

    }

    private void getUserInput() {
        chatIdText = chatId.getText();
        botTokenText = botToken.getText();
        botNameText = botName.getText();
    }

    public void applySettings() {
        currentSettings.setChatID(Long.parseLong(chatIdText));
        currentSettings.setBotToken(botTokenText);
        currentSettings.setBotName(botNameText);
    }
}
