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
import java.util.Optional;

public class FXMLController {

    public static volatile boolean bStop = false;
    private MyThread mt = null;
    private final String start = "Старт";
    private final String stop = "Стоп";

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
                new MyThread("Thread_0");
                break;
            case stop:
                startStopBtn.setText(start);
                bStop = true;
                break;
        }

        //Initialise.start();

    }

    public void initialize() {
        logicFX.setValue(logicFXList.get(0));
        logicFX.setItems(logicFXList);
        logicFX.setOnAction(event -> Settings.logic = logicFX.getValue());

        linkList.setItems(hyperlinkObservableList);

        Hyperlink hyperlink0 = new Hyperlink("https://docs.oracle.com/javase/8/docs/api/java/util/List.html#contains-java.lang.Object-");
        Hyperlink hyperlink1 = new Hyperlink("https://docs.oracle.com/javase/8/docs/api/java/util/class-use/List.html");

        hyperlinkObservableList.addAll(hyperlink0, hyperlink1);

        Hyperlink hyperlink2 = new Hyperlink("https://docs.oracle.com/javase/8/docs/api/java/util/List.html#contains-java.lang.Object-");

        boolean inList = hyperlinkObservableList.stream().anyMatch(hyperlink -> hyperlink.getText().equals(hyperlink2.getText()));
        if (inList)
    }
}