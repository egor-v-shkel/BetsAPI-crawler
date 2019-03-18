package by.vision.betsapiparser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FXMLController {

    public static volatile boolean bStop = false;

    private static ParserThread parserThread = null;

    private final String start = "Старт";
    private final String stop = "Стоп";

    public static ParserThread getParserThread() {
        return parserThread;
    }

    @FXML
    public ListView linkList;
    public static ObservableList<Hyperlink> hyperlinkObservableList = FXCollections.observableArrayList();

    @FXML
    public ChoiceBox<Settings.Logic> logicFX;
    public ObservableList<Settings.Logic> logicFXList = FXCollections.observableArrayList(Settings.Logic.values());


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
                bStop = false;
                startStopBtn.setText(stop);
                //Settings.logic = Settings.Logic.AND;
                Settings.tgChatID = Long.parseLong(tgChatIDFX.getText());
                Settings.timeSelectMin = Integer.parseInt(timeMinFX.getText());
                Settings.timeSelectMax = Integer.parseInt(timeMaxFX.getText());
                Settings.possessionMin = Integer.parseInt(possessionMinFX.getText());
                Settings.targetOnMin = Integer.parseInt(targetOnFX.getText());
                Settings.targetOffMin = Integer.parseInt(targetOffFX.getText());
                Settings.proxyTimeout = Integer.parseInt(proxyTimeOutFX.getText());
                //start parsing thread
                parserThread = new ParserThread("Thread_0");
                break;
            case stop:
                bStop = true;
                startStopBtn.setText(start);
                break;
        }

        //Initialise.start();

    }

    public void initialize() {
        logicFX.setValue(logicFXList.get(0));
        logicFX.setItems(logicFXList);
        logicFX.setOnAction(event -> Settings.logic = logicFX.getValue());

        linkList.setItems(hyperlinkObservableList);

    }
}