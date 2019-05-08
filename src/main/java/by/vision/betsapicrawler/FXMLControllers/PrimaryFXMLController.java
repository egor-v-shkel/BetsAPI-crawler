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

public class PrimaryFXMLController {
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
        settings.serialize(settings.getCurrentFile().getAbsolutePath());
    }

    @FXML
    public void handleSaveAs(ActionEvent actionEvent) {
        FileChooser fileChooser = predefineFileChooser();
        MyLogger.STDOUT_LOGGER.debug("Saving settings in: "+settings.getCurrentPath());
        File file = fileChooser.showSaveDialog(StageBuilder.getPrimaryStage());
        if (file != null) {
            settings.serialize(file.getAbsolutePath());
            MyLogger.STDOUT_LOGGER.debug("!!!Inside HandleAs "+file.getParent());
            settings.setCurrentPath(file.getParent());
        }
    }

    @FXML
    void handleLoad(ActionEvent event) {
        FileChooser fileChooser = predefineFileChooser();
        MyLogger.STDOUT_LOGGER.debug("Trying to load settings from: "+settings.getCurrentPath());
        File file = fileChooser.showOpenDialog(StageBuilder.getPrimaryStage());
        if (file != null) {
            settings.deserialize(file.getAbsolutePath());
            applySettings();
            StageBuilder stageBuilder = Main.getStageBuilder();
            stageBuilder.showSettings(settings);
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
        fileChooser.setInitialDirectory(new File(settings.getCurrentPath()));
        return fileChooser;
    }

    private void startCrawlSession() {
        crawlerThread = new CrawlerThread("Crawler thread");
        //handleSave settings
        settings.serialize();
        //start new bot session
        Main.startBotSession(new TelegramBot());
    }

    private void stopCrawlSession() {
        crawlerThread.stop();
        Main.botSession.stop();
    }

    /**
     * Current method will save all changes made in GUI that refer to {@link Settings} class
     * and apply it to current settings.
     *
     * @see by.vision.betsapicrawler.Settings;
     */
    private void applySettings() {
        settings.setLogic(logicFX.getValue());
        settings.setTimeSelectMin(Integer.parseInt(timeMinFX.getText()));
        settings.setTimeSelectMax(Integer.parseInt(timeMaxFX.getText()));
        settings.setPossessionMin(Integer.parseInt(possessionMinFX.getText()));
        settings.setOnTargetMin(Integer.parseInt(onTargetMinFx.getText()));
        settings.setOffTargetMin(Integer.parseInt(offTargetMinFX.getText()));
        settings.setRateMin(Double.parseDouble(rateMinFx.getText()));
        MyLogger.ROOT_LOGGER.debug("Settings was successfully applied");
    }

    public void initialize() {
        logicFX.setValue(logicFXList.get(0));
        logicFX.setItems(logicFXList);

        linkList.setItems(hyperlinkObservableList);

    }
}