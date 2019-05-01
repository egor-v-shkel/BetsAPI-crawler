package by.vision.betsapicrawler;

import by.vision.betsapicrawler.FXMLControllers.MainFXMLController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;

import java.io.File;
import java.net.URISyntaxException;


public class Main extends Application {

    public static final String title = "BetsAPI crawler v. 0.1.2";
    public static Settings settings;
    public static TelegramBotsApi botsApi;
    public static BotSession botSession;
    public static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    public static void initTgBotsAPI() {
        ApiContextInitializer.init();
        botsApi = new TelegramBotsApi();
    }

    public static void startBotSession(TelegramBot tgBot){
        try {
            botSession = botsApi.registerBot(tgBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static String jarDir() {
        String jarDir = null;
        try {
            jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        MyLogger.STDOUT_LOGGER.debug("Jar dir: " + jarDir);
        //"C:\Users\Vision-PC\IdeaProjects\BetsAPI-crawler\target\classes"
        return jarDir;
        //return "c:\\temp\\";
    }

    @Override
    public void init(){
        initTgBotsAPI();
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Scenes/Main.fxml"));
        Parent root = fxmlLoader.load();
        MainFXMLController controller = fxmlLoader.getController();
        settings = new Settings();
        settings.deserialize();
        setSettings(controller, settings);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setTitle(title);
        stage.setScene(scene);
        stage.setMinHeight(480);
        stage.setMinWidth(800);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/icon.png")));
        stage.show();
        MyLogger.ROOT_LOGGER.debug("Application launched");
    }

    @Override
    public void stop(){
        MyLogger.ROOT_LOGGER.debug("Application closed.");
        System.exit(0);
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

}
