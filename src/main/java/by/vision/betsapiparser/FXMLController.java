package by.vision.betsapiparser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML
    public ChoiceBox<Settings.Logic> logicFX;
    private ObservableList<Settings.Logic> logicFXList = FXCollections.observableArrayList(Settings.Logic.values());


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

        switch (startStopBtn.getText()) {
            case "Старт":
                startStopBtn.setText("Пауза");
                //Settings.logic = Settings.Logic.AND;
                Settings.tgChatID = Long.parseLong(tgChatIDFX.getText());
                Settings.timeSelectMin = Integer.parseInt(timeMinFX.getText());
                Settings.timeSelectMax = Integer.parseInt(timeMaxFX.getText());
                Settings.possessionMin = Integer.parseInt(possessionMinFX.getText());
                Settings.targetOnMin = Integer.parseInt(targetOnFX.getText());
                Settings.targetOffMin = Integer.parseInt(targetOffFX.getText());
                Settings.proxyTimeout = Integer.parseInt(proxyTimeOutFX.getText());

                mt = new MyThread("Thread_0");
                break;
            case "Пауза":
                startStopBtn.setText("Продолжить");
                try {
                    mt.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "Продолжить":
                startStopBtn.setText("Пауза");
                mt.notify();
                break;
        }

        //Initialise.start();

    }

    public void initialize() {
        logicFX.setValue(logicFXList.get(0));
        logicFX.setItems(logicFXList);
        logicFX.setOnAction(event -> Settings.logic = logicFX.getValue());
    }
}
