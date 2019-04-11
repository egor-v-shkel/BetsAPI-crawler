package by.vision.betsapiparser;

import by.vision.betsapiparser.Crawler.CrawlerStarter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FXMLController {

    private final String start = "Старт";
    private final String stop = "Стоп";
    private CrawlerStarter crawlerStarter = new CrawlerStarter();

    @FXML
    public ListView linkList;
    public static ObservableList<Hyperlink> hyperlinkObservableList = FXCollections.observableArrayList();

    @FXML
    public ChoiceBox<Settings.Logic> logicFX;
    public ObservableList<Settings.Logic> logicFXList = FXCollections.observableArrayList(Settings.Logic.values());

    @FXML
    private TextField coefMinFX;

    @FXML
    public TextField tgChatIDFX;

    @FXML
    public TextField timeMinFX;

    @FXML
    public TextField timeMaxFX;

    @FXML
    public TextField possessionMinFX;

    @FXML
    public TextField targetOnFX;

    @FXML
    public TextField targetOffFX;

    @FXML
    public TextField proxyTimeOutFX;

    @FXML
    public Button startStopBtn;

    @FXML
    private void handleButtonAction(ActionEvent event) {

        switch (startStopBtn.getText()) {
            case start:
                Settings.logic = logicFX.getValue();
                Settings.tgChatID = Long.parseLong(tgChatIDFX.getText());
                Settings.timeSelectMin = Integer.parseInt(timeMinFX.getText());
                Settings.timeSelectMax = Integer.parseInt(timeMaxFX.getText());
                Settings.possessionMin = Integer.parseInt(possessionMinFX.getText());
                Settings.targetOnMin = Integer.parseInt(targetOnFX.getText());
                Settings.targetOffMin = Integer.parseInt(targetOffFX.getText());
                Settings.proxyTimeout = Integer.parseInt(proxyTimeOutFX.getText());
                Settings.coefMin = Double.parseDouble(coefMinFX.getText());

                try {
                    crawlerStarter.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                startStopBtn.setText(stop);
                break;
            case stop:
                crawlerStarter.stop();
                startStopBtn.setText(start);
                break;
        }

        //Initialise.start();

    }

    public void initialize() {
        logicFX.setValue(logicFXList.get(0));
        logicFX.setItems(logicFXList);

        linkList.setItems(hyperlinkObservableList);

    }
}