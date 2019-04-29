package by.vision.betsapicrawler;

import by.vision.betsapicrawler.FXMLControllers.FXMLController;
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

    public static Settings settings;
    public static final String title = "BetsAPI crawler v. 0.1.2";
    public static TelegramBotsApi botsApi;
    public static BotSession botSession;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init(){
        initTgBotsAPI();
    }


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scene.fxml"));
        Parent root = fxmlLoader.load();
        FXMLController controller = fxmlLoader.<FXMLController>getController();
        settings = new Settings();
        settings.deserialize();
        setSettings(controller, settings);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        setTitle(stage);
        stage.setScene(scene);
        stage.setMinHeight(480);
        stage.setMinWidth(800);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/icon.png")));
        stage.show();
        MyLogger.ROOT_LOGGER.debug("Application launched");
    }

    private void setSettings(FXMLController controller, Settings settings) {
        controller.logicFX.setValue(settings.getLogic());
        controller.tgChatIDFX.setText(String.valueOf(settings.getChatID()));
        controller.timeMinFX.setText(String.valueOf(settings.getTimeSelectMin()));
        controller.timeMaxFX.setText(String.valueOf(settings.getTimeSelectMax()));
        controller.onTargetMinFx.setText(String.valueOf(settings.getOnTargetMin()));
        controller.offTargetMinFX.setText(String.valueOf(settings.getOffTargetMin()));
        controller.rateMinFx.setText(String.valueOf(settings.getRateMin()));
        controller.possessionMinFX.setText(String.valueOf(settings.getPossessionMin()));
        controller.proxyTimeOutFX.setText(String.valueOf(settings.getProxyTimeout()));
    }

    public void setTitle(Stage stage) {
        stage.setTitle(title);
    }

    @Override
    public void stop(){
        MyLogger.ROOT_LOGGER.debug("Application closed.");
        System.exit(0);
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
    //TODO make this method work

    public static String getPath() {
        String jarDir = null;
        try {
            jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(jarDir);
        //"C:\Users\Vision-PC\IdeaProjects\BetsAPI-crawler\target\classes"
        return jarDir;//.replaceFirst("(\\\\[\\w]+){2}$", "");
        //return "c:\\temp\\";
    }

}
