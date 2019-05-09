package by.vision.betsapicrawler;

import by.vision.betsapicrawler.FXMLControllers.PrimaryFXMLController;
import by.vision.betsapicrawler.FXMLControllers.TgSettingsFXMLController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class StageBuilder {
    private static final String title = "BetsAPI crawler v. 0.1.2";
    public static Settings settings;
    private static Stage primaryStage;
    private static Stage tgSettingsStage;
    private PrimaryFXMLController primaryController;
    private TgSettingsFXMLController tgController;

    StageBuilder(Stage primaryStage) {
        StageBuilder.primaryStage = primaryStage;
        buildPrimaryStage(primaryStage);
        buildTgSettingsStage(primaryStage);
    }

    private void buildPrimaryStage(Stage primaryStage) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/Primary.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            MyLogger.ROOT_LOGGER.error("Unsuccessful attempt to load Main.FXML", e);
            e.printStackTrace();
        }
        primaryController = fxmlLoader.getController();
        settings = new Settings();
        settings.deserialize();
        settings.serialize();
        primaryController.showSettings();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(480);
        primaryStage.setMinWidth(800);
        primaryStage.getIcons().add(getIcon());
        primaryStage.centerOnScreen();
        primaryStage.show();
        MyLogger.ROOT_LOGGER.debug("Application launched");
    }

    private void buildTgSettingsStage(Stage primaryStage) {
        tgSettingsStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/TgSettings.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tgController = fxmlLoader.getController();
        assert root != null;
        Scene scene = new Scene(root);
        tgSettingsStage.setTitle(primaryController.tgBotSetup.getText());
        tgSettingsStage.setScene(scene);
        // Specifies the modality for new window.
        tgSettingsStage.initModality(Modality.WINDOW_MODAL);
        // Specifies the owner Window (parent) for new window.
        tgSettingsStage.initOwner(primaryStage);
        tgSettingsStage.centerOnScreen();
        tgSettingsStage.initStyle(StageStyle.UTILITY);
        //if settings was successfully loaded, show Telegram bot settings in GUI
        tgController.showSettings();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Stage getTgSettingsStage() {
        return tgSettingsStage;
    }

    public static Image getIcon() {
        return new Image(Main.class.getResourceAsStream("/images/icon.png"));
    }
}
