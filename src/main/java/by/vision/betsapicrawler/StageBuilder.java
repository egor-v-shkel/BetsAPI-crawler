package by.vision.betsapicrawler;

import by.vision.betsapicrawler.FXMLControllers.MainFXMLController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class StageBuilder {
    private static Stage primaryStage;
    public static final String title = "BetsAPI crawler v. 0.1.2";
    public static Settings settings;

    public StageBuilder(Stage primaryStage) {
        this.primaryStage = primaryStage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/Main.FXML"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            MyLogger.ROOT_LOGGER.error("Unsuccessful attempt to load Main.FXML", e);
            e.printStackTrace();
        }
        MainFXMLController controller = fxmlLoader.getController();
        settings = new Settings();
        settings.deserialize();
        setSettings(controller, settings);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(480);
        primaryStage.setMinWidth(800);
        primaryStage.getIcons().add(getIcon());
        primaryStage.show();
        MyLogger.ROOT_LOGGER.debug("Application launched");

    }

    private void setSettings(MainFXMLController controller, Settings settings) {
        controller.logicFX.setValue(settings.getLogic());
        controller.timeMinFX.setText(String.valueOf(settings.getTimeSelectMin()));
        controller.timeMaxFX.setText(String.valueOf(settings.getTimeSelectMax()));
        controller.onTargetMinFx.setText(String.valueOf(settings.getOnTargetMin()));
        controller.offTargetMinFX.setText(String.valueOf(settings.getOffTargetMin()));
        controller.rateMinFx.setText(String.valueOf(settings.getRateMin()));
        controller.possessionMinFX.setText(String.valueOf(settings.getPossessionMin()));
    }

    public static Image getIcon() {
        return new Image(Main.class.getResourceAsStream("/images/icon.png"));
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
