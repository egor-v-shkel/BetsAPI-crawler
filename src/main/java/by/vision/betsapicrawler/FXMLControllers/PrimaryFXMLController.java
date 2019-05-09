package by.vision.betsapicrawler.FXMLControllers;

import by.vision.betsapicrawler.*;
import by.vision.betsapicrawler.Crawler.CrawlerThread;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

import static by.vision.betsapicrawler.StageBuilder.*;

public class PrimaryFXMLController implements SettingsController {
    //flag var which used for stopping crawl session
    public static volatile boolean bStop = false;

    public static ObservableList<Hyperlink> hyperlinkObservableList = FXCollections.observableArrayList();
    @FXML
    public ListView linkList;
    @FXML
    public ChoiceBox<Settings.Logic> logicFX;
    @FXML
    public TextField rateMinFx;
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
    public Button startStopBtn;
    @FXML
    public MenuItem tgBotSetup;
    private CrawlerThread crawlerThread;
    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem save;
    @FXML
    private MenuItem load;
    @FXML
    private MenuItem exit;
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
                settings.checkNotNullTgSettings();
                saveSettings();
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
    void handleSave(ActionEvent event) {
        applySettings();
        saveSettings();
    }

    @FXML
    public void handleSaveAs(ActionEvent event) {
        FileChooser fileChooser = predefineFileChooser();
        File file = fileChooser.showSaveDialog(StageBuilder.getPrimaryStage());
        if (file != null) {
            applySettings();
            settings.serialize(file.getAbsolutePath());
            settings.setCurrentFile(file);

            MyLogger.ROOT_LOGGER.debug("Settings was saved in: "+settings.getCurrentFile().getAbsolutePath());
        }
    }

    @FXML
    void handleLoad(ActionEvent event) {
        FileChooser fileChooser = predefineFileChooser();
        File file = fileChooser.showOpenDialog(StageBuilder.getPrimaryStage());
        if (file != null) {
            settings.deserialize(file.getAbsolutePath());
            //applySettings();

            showSettings();

            MyLogger.ROOT_LOGGER.debug("Settings was loaded from: "+ settings.getCurrentFile().getAbsolutePath());
        }
    }

    @FXML
    void handleExit(ActionEvent event) {
        MyLogger.ROOT_LOGGER.debug("Closing app through menu bar");
        Platform.exit();
    }

    @FXML
    void handleTgSettings(ActionEvent event) {
        StageBuilder.getTgSettingsStage().showAndWait();
    }

    @FXML
    public void handleAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(about.getText());
        alert.setHeaderText("BetsApi crawler");
        alert.setContentText("Версия:\t0.1.2\n"
                + "Автор:\tVision_606\n"
                + "Дата релиза:\tN/A\n");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(getIcon()); // add a custom icon
        alert.initOwner(StageBuilder.getPrimaryStage());
        alert.showAndWait();
    }

    /**
     * Predefine FileChooser to load only *.ser files.
     *
     * @return FileChooser
     */
    private FileChooser predefineFileChooser() {
        FileChooser fileChooser = new FileChooser();
        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SER (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(new File(settings.getCurrentFile().getParent()));
        return fileChooser;
    }

    private void startCrawlSession() {
        crawlerThread = new CrawlerThread("Crawler thread");
        //handleSave settings
        saveSettings();
        //start new bot session
        Main.startBotSession(new TelegramBot());
    }

    private void stopCrawlSession() {
        crawlerThread.stop();
        Main.botSession.stop();
    }

    /**
     * Current method gets all values from all GUI text fields (including "Telegam settings" text fields)
     * and apply it to current {@link Settings}.
     *
     * @see by.vision.betsapicrawler.Settings;
     */
    public void applySettings() {
        settings.setLogic(logicFX.getValue());
        settings.setTimeSelectMin(Integer.parseInt(timeMinFX.getText()));
        settings.setTimeSelectMax(Integer.parseInt(timeMaxFX.getText()));
        settings.setPossessionMin(Integer.parseInt(possessionMinFX.getText()));
        settings.setOnTargetMin(Integer.parseInt(onTargetMinFx.getText()));
        settings.setOffTargetMin(Integer.parseInt(offTargetMinFX.getText()));
        settings.setRateMin(Double.parseDouble(rateMinFx.getText()));
        //TODO apply TG settings?
        MyLogger.ROOT_LOGGER.debug("Settings was applied");
    }

    public void initialize() {
        logicFX.setValue(logicFXList.get(0));
        logicFX.setItems(logicFXList);

        linkList.setItems(hyperlinkObservableList);

    }

    public void showSettings() {
        logicFX.setValue(settings.getLogic());
        timeMinFX.setText(String.valueOf(settings.getTimeSelectMin()));
        timeMaxFX.setText(String.valueOf(settings.getTimeSelectMax()));
        onTargetMinFx.setText(String.valueOf(settings.getOnTargetMin()));
        offTargetMinFX.setText(String.valueOf(settings.getOffTargetMin()));
        rateMinFx.setText(String.valueOf(settings.getRateMin()));
        possessionMinFX.setText(String.valueOf(settings.getPossessionMin()));
    }

}