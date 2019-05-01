package by.vision.betsapicrawler.FXMLControllers;

import by.vision.betsapicrawler.Crawler.CrawlerThread;
import by.vision.betsapicrawler.Main;
import by.vision.betsapicrawler.MyLogger;
import by.vision.betsapicrawler.Scenes.TgSettings;
import by.vision.betsapicrawler.Settings;
import by.vision.betsapicrawler.TelegramBot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class MainFXMLController {
    //flag var which used for stopping crawl session
    public static volatile boolean bStop = false;
    TgSettings tgSettings = new TgSettings();

    public static ObservableList<Hyperlink> hyperlinkObservableList = FXCollections.observableArrayList();
    @FXML
    public ListView linkList;
    @FXML
    public ChoiceBox<Settings.Logic> logicFX;
    @FXML
    public TextField rateMinFx;
    @FXML
    public TextField tgChatIDFX;
    @FXML
    public TextField timeMinFX;
    @FXML
    public TextField timeMaxFX;
    @FXML
    public TextField possessionMinFX;
    @FXML
    public TextField onTargetMinFx;
    @FXML
    public TextField offTargetMinFX;
    @FXML
    public TextField proxyTimeOutFX;
    @FXML
    public Button startStopBtn;
    private CrawlerThread crawlerThread;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem saveSet;
    @FXML
    private MenuItem loadSet;
    @FXML
    private MenuItem exit;
    @FXML
    private MenuItem tgBotSetup;
    @FXML
    private MenuItem about;
    private ObservableList<Settings.Logic> logicFXList = FXCollections.observableArrayList(Settings.Logic.values());

    @FXML
    private void handleButtonAction(ActionEvent event) {
        final String START = "Старт";
        final String STOP = "Стоп";
        switch (startStopBtn.getText()) {
            case START:
                applySettings();
                startCrawlSession();
                startStopBtn.setText(STOP);
                break;
            case STOP:
                stopCrawlSession();
                startStopBtn.setText(START);
                break;
        }
    }

    @FXML
    void save(ActionEvent event) {
        Main.settings.serialize();
    }

    @FXML
    void load(ActionEvent event) {
        Main.settings.deserialize();
    }

    @FXML
    void exit(ActionEvent event) {
        MyLogger.ROOT_LOGGER.debug("Closing application");
        System.exit(0);
    }

    @FXML
    void openTgSettings(ActionEvent event) {
        tgSettings.show();
    }

    private void startCrawlSession() {
        crawlerThread = new CrawlerThread("Crawler thread");
        //save settings
        Main.settings.serialize();
        //start new bot session
        Main.startBotSession(new TelegramBot());
    }

    private void stopCrawlSession() {
        crawlerThread.stop();
        Main.botSession.stop();
    }

    private void applySettings() {
        Main.settings.setLogic(logicFX.getValue());
        Main.settings.setChatID(Long.parseLong(tgChatIDFX.getText()));
        Main.settings.setTimeSelectMin(Integer.parseInt(timeMinFX.getText()));
        Main.settings.setTimeSelectMax(Integer.parseInt(timeMaxFX.getText()));
        Main.settings.setPossessionMin(Integer.parseInt(possessionMinFX.getText()));
        Main.settings.setOnTargetMin(Integer.parseInt(onTargetMinFx.getText()));
        Main.settings.setOffTargetMin(Integer.parseInt(offTargetMinFX.getText()));
        Main.settings.setProxyTimeout(Integer.parseInt(proxyTimeOutFX.getText()));
        Main.settings.setRateMin(Double.parseDouble(rateMinFx.getText()));
    }

    public void initialize() {
        logicFX.setValue(logicFXList.get(0));
        logicFX.setItems(logicFXList);

        linkList.setItems(hyperlinkObservableList);

    }


}