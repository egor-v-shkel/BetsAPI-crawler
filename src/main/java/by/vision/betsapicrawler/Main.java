package by.vision.betsapicrawler;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;


public class Main extends Application {

    private static TelegramBotsApi botsApi;
    public static BotSession botSession;
    private static StageBuilder stageBuilder;

    public static StageBuilder getStageBuilder() {
        return stageBuilder;
    }

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

    @Override
    public void init() {
        initTgBotsAPI();
    }

    @Override
    public void start(Stage stage){
        stageBuilder = new StageBuilder(stage);
    }

    @Override
    public void stop() {
        MyLogger.STDOUT_LOGGER.debug("Application closed.");
        System.exit(0);
    }
}
