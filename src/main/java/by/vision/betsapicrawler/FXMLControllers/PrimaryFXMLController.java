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
    private MenuBar menuBar;

    @FXML
    public Menu file;
    @FXML
    private MenuItem save;
    @FXML
    public MenuItem saveAs;
    @FXML
    private MenuItem load;
    @FXML
    private MenuItem exit;

    @FXML
    public Menu settingsMenu;
    @FXML
    public MenuItem tgBotSetup;

    @FXML
    public Menu help;
    @FXML
    private MenuItem about;

    private CrawlerThread crawlerThread;
    private ObservableList<Settings.Logic> logicFXList = FXCollections.observableArrayList(Settings.Logic.values());

    @FXML
    private void handleButtonAction(ActionEvent event) {
        final String START = "Старт";
        final String STOP = "Стоп";
        switch (startStopBtn.getText()) {
            case START:
                applySettings();
                if (!SettingsModel.currentSettings.checkNotNullTgSettings()) {
                    alert();
                    break;
                }
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

    private void alert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Внимание");
        alert.setHeaderText("Неверно введены настройки телеграм");
        alert.setContentText(String.format("Проверьте правильность ввода в %s\\%s"
                ,settingsMenu.getText(), tgBotSetup.getText()));

        alert.showAndWait();
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
            SettingsModel.currentSettings.serialize(file.getAbsolutePath());
            SettingsModel.currentSettings.setCurrentFile(file);

            MyLogger.ROOT_LOGGER.debug("Settings was saved in: "+ SettingsModel.currentSettings.getCurrentFile().getAbsolutePath());
        }
    }

    @FXML
    void handleLoad(ActionEvent event) {
        FileChooser fileChooser = predefineFileChooser();
        File file = fileChooser.showOpenDialog(StageBuilder.getPrimaryStage());
        if (file != null) {
            SettingsModel.currentSettings.deserialize(file.getAbsolutePath());
            //applySettings();

            showSettings();

            MyLogger.ROOT_LOGGER.debug("Settings was loaded from: "+ SettingsModel.currentSettings.getCurrentFile().getAbsolutePath());
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
                + "Автор:\tVision666\n"
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
        fileChooser.setInitialDirectory(new File(SettingsModel.currentSettings.getCurrentFile().getParent()));
        return fileChooser;
    }

    private void startCrawlSession() {
        crawlerThread = new CrawlerThread("Crawler thread");
        //handleSave currentSettings
        saveSettings();
        //start new bot session
        TelegramBotModel.currentBot = new TelegramBot();
        Main.startBotSession(TelegramBotModel.currentBot);
    }

    private void stopCrawlSession() {
        crawlerThread.stop();
        Main.botSession.stop();
    }

    /**
     * Current method gets all values from all GUI text fields (including "Telegam currentSettings" text fields)
     * and apply it to current {@link Settings}.
     *
     * @see by.vision.betsapicrawler.Settings;
     */
    public void applySettings() {
        SettingsModel.currentSettings.setLogic(logicFX.getValue());
        SettingsModel.currentSettings.setTimeSelectMin(Integer.parseInt(timeMinFX.getText()));
        SettingsModel.currentSettings.setTimeSelectMax(Integer.parseInt(timeMaxFX.getText()));
        SettingsModel.currentSettings.setPossessionMin(Integer.parseInt(possessionMinFX.getText()));
        SettingsModel.currentSettings.setOnTargetMin(Integer.parseInt(onTargetMinFx.getText()));
        SettingsModel.currentSettings.setOffTargetMin(Integer.parseInt(offTargetMinFX.getText()));
        SettingsModel.currentSettings.setRateMin(Double.parseDouble(rateMinFx.getText()));
        MyLogger.ROOT_LOGGER.debug("Settings was applied");
    }

    public void initialize() {
        logicFX.setValue(logicFXList.get(0));
        logicFX.setItems(logicFXList);

        linkList.setItems(hyperlinkObservableList);

    }

    public void showSettings() {
        logicFX.setValue(SettingsModel.currentSettings.getLogic());
        timeMinFX.setText(String.valueOf(SettingsModel.currentSettings.getTimeSelectMin()));
        timeMaxFX.setText(String.valueOf(SettingsModel.currentSettings.getTimeSelectMax()));
        onTargetMinFx.setText(String.valueOf(SettingsModel.currentSettings.getOnTargetMin()));
        offTargetMinFX.setText(String.valueOf(SettingsModel.currentSettings.getOffTargetMin()));
        rateMinFx.setText(String.valueOf(SettingsModel.currentSettings.getRateMin()));
        possessionMinFX.setText(String.valueOf(SettingsModel.currentSettings.getPossessionMin()));
    }

}