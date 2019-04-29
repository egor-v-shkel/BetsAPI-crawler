package by.vision.betsapicrawler;

import by.vision.betsapicrawler.Crawler.CrawlerThread;
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
                App.settings.serialize();
                startStopBtn.setText(STOP);
                break;
            case STOP:
                crawlerThread.stop();
                startStopBtn.setText(START);
                break;
        }

    }

    private void applySettings() {
        App.settings.setLogic(logicFX.getValue());
        App.settings.setTgChatID(Long.parseLong(tgChatIDFX.getText()));
        App.settings.setTimeSelectMin(Integer.parseInt(timeMinFX.getText()));
        App.settings.setTimeSelectMax(Integer.parseInt(timeMaxFX.getText()));
        App.settings.setPossessionMin(Integer.parseInt(possessionMinFX.getText()));
        App.settings.setOnTargetMin(Integer.parseInt(onTargetMinFx.getText()));
        App.settings.setOffTargetMin(Integer.parseInt(offTargetMinFX.getText()));
        App.settings.setProxyTimeout(Integer.parseInt(proxyTimeOutFX.getText()));
        App.settings.setRateMin(Double.parseDouble(rateMinFx.getText()));
    }

    public void initialize() {
        logicFX.setValue(logicFXList.get(0));
        logicFX.setItems(logicFXList);

        linkList.setItems(hyperlinkObservableList);

    }
}