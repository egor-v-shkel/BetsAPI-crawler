package by.vision.betsapicrawler.FXMLControllers;

import by.vision.betsapicrawler.Crawler.CrawlerThread;
import by.vision.betsapicrawler.Main;
import by.vision.betsapicrawler.Settings;
import by.vision.betsapicrawler.TelegramBot;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FXMLController {

    private CrawlerThread crawlerThread;
    public static volatile boolean bStop = false;

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

    @FXML
    public ListView linkList;
    public static ObservableList<Hyperlink> hyperlinkObservableList = FXCollections.observableArrayList();

    @FXML
    public ChoiceBox<Settings.Logic> logicFX;
    private ObservableList<Settings.Logic> logicFXList = FXCollections.observableArrayList(Settings.Logic.values());

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

    @FXML
    private void handleButtonAction(ActionEvent event) {

        final String START = "Старт";
        final String STOP = "Стоп";
        switch (startStopBtn.getText()) {
            case START:
                applySettings();
                crawlerThread = new CrawlerThread("Crawler thread");
                //save settings
                Main.settings.serialize();
                //start new bot session
                Main.startBotSession(new TelegramBot());
                startStopBtn.setText(STOP);
                break;
            case STOP:
                crawlerThread.stop();

                Main.botSession.stop();

                startStopBtn.setText(START);
                break;
        }

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
        //TODO add here fields for telegram bot settings apply
    }

    public void initialize() {
        logicFX.setValue(logicFXList.get(0));
        logicFX.setItems(logicFXList);

        linkList.setItems(hyperlinkObservableList);

    }


}