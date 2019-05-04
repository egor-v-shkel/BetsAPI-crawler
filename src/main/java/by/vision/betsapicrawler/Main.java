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
    public static final String JAR_DIR = jarDir();
    public static TelegramBotsApi botsApi;
    public static BotSession botSession;

    public static void main(String[] args) {
        launch(args);
    }

    public static void initTgBotsAPI() {
        ApiContextInitializer.init();
        botsApi = new TelegramBotsApi();
    }

    public static void startBotSession(TelegramBot tgBot) {
        try {
            botSession = botsApi.registerBot(tgBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static String jarDir() {
        String jarDir = null;
        try {
            jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        MyLogger.STDOUT_LOGGER.debug("Jar dir: " + jarDir);
        return jarDir;
    }

    @Override
    public void init() {
        initTgBotsAPI();
    }

    @Override
    public void start(Stage stage){
        new StageBuilder(stage);
    }

    @Override
    public void stop() {
        MyLogger.ROOT_LOGGER.debug("Application closed.");
        System.exit(0);
    }
}
