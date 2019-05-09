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
        if (checkInput()) {

            Integer checkedId = checkChatIdPrefix();

            settings.setChatID(checkedId);
            settings.setBotToken(botTokenText);
            settings.setBotName(botNameText);

            notification.setText("Настройки применены");
            notification.setTextFill(Color.GREEN);

            MyLogger.ROOT_LOGGER.info("Telegram settings was applied");
        } else {
            notification.setText("Неправильно введены настройки");
            notification.setTextFill(Color.RED);
            MyLogger.ROOT_LOGGER.debug("Wrong user input");
        }
    }

    /**
     * Assume, that user can input chatID without "minus" prefix, we will be adding it at the beginning
     * if it's needed
     *
     * @return correct chatID
     */
    private Integer checkChatIdPrefix() {
        String prefix = "-";
        int id = Integer.parseInt(chatIdText);
        return chatIdText.startsWith(prefix) ? id : -id;
    }

    private boolean checkInput() {
        return (chatIdText.matches("[-]\\d+|\\d+") &&
                botTokenText.matches("\\d{9}[:]\\w{35}") &&
                botNameText.matches("(?i).+(_bot)$"));
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
        //do nothing
    }

    /**
     * Make current settings visible in GUI
     */
    public void showSettings() {

        if (settings.checkNotNullTgSettings()) {
            chatId.setText(String.valueOf(settings.getChatID()));
            botName.setText(String.valueOf(settings.getBotName()));
            botToken.setText(String.valueOf(settings.getBotToken()));
        }

    }

    private void getUserInput() {
        chatIdText = chatId.getText();
        botTokenText = botToken.getText();
        botNameText = botName.getText();
    }

    public void applySettings() {
        settings.setChatID(Long.parseLong(chatIdText));
        settings.setBotToken(botTokenText);
        settings.setBotName(botNameText);
    }
}
