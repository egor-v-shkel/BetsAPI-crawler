package by.vision.betsapiparser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML
    public ChoiceBox logicFX;

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

        MyRunnable myRunnable = null;

        switch (startStopBtn.getText()) {
            case "Старт":
                startStopBtn.setText("Стоп");
                //Settings.logic = Settings.Logic.AND;
                Settings.tgChatID = Long.parseLong(tgChatIDFX.getText());
                Settings.timeSelectMin = Integer.parseInt(timeMinFX.getText());
                Settings.timeSelectMax = Integer.parseInt(timeMaxFX.getText());
                Settings.possessionMin = Integer.parseInt(possessionMinFX.getText());
                Settings.targetOnMin = Integer.parseInt(targetOnFX.getText());
                Settings.targetOffMin = Integer.parseInt(targetOffFX.getText());
                Settings.proxyTimeout = Integer.parseInt(proxyTimeOutFX.getText());

                myRunnable = new MyRunnable();
                myRunnable.run();
                break;
            case "Стоп":
                startStopBtn.setText("Старт");
                try {
                    myRunnable.wait(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.exit(0);
                break;
        }

        //Initialise.start();

    }

    public void initialize() {
        // TODO
    }
}
