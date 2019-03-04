package by.vision.betsapiparser;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class FXMLController {

    static volatile boolean bStop = true;

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
        MyThread mt = null;
        final String start = "Start";
        final String stop = "Stop";


        switch (startStopBtn.getText()) {
            case start:
                bStop = true;
                startStopBtn.setText(stop);
                //Settings.logic = Settings.Logic.AND;
                Settings.tgChatID = Long.parseLong(tgChatIDFX.getText());
                Settings.timeSelectMin = Integer.parseInt(timeMinFX.getText());
                Settings.timeSelectMax = Integer.parseInt(timeMaxFX.getText());
                Settings.possessionMin = Integer.parseInt(possessionMinFX.getText());
                Settings.targetOnMin = Integer.parseInt(targetOnFX.getText());
                Settings.targetOffMin = Integer.parseInt(targetOffFX.getText());
                Settings.proxyTimeout = Integer.parseInt(proxyTimeOutFX.getText());

                mt = new MyThread("Thread_0");
                mt.run();
                break;
            case stop:
                startStopBtn.setText(start);
                bStop = false;
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