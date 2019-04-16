package by.vision.betsapiparser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sun.misc.Unsafe;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.net.URISyntaxException;


public class App extends Application {

    public static Settings settings = new Settings();
    public static final String SETTINGS_FILE_NAME = "Settings.ser";
    public static final String title = "BetsAPI parser v. 0.1.0";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init(){
        //disable warning "An illegal reflective access operation has occurred"
        disableWarning();
        //initTelegBotsAPI();
    }


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("scene.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        FXMLController controller = fxmlLoader.<FXMLController>getController();
        //settings.serialise();
        //setSettings(controller, settings);
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        setTitle(stage);
        stage.setScene(scene);
        stage.setMinHeight(480);
        stage.setMinWidth(800);
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/images/icon.png")));
        stage.show();
        MyLogger.ROOT_LOGGER.debug("Application launched");
    }

    private void setSettings(FXMLController controller, Settings settings) {
        controller.logicFX.setValue(settings.getLogic());

    }

    public void setTitle(Stage stage) {
        stage.setTitle(title);
    }

    @Override
    public void stop(){
        MyLogger.ROOT_LOGGER.debug("Application closed.");
        System.exit(0);
    }

    public static void initTelegBotsAPI() {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new TelegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void disableWarning() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe u = (Unsafe) theUnsafe.get(null);
            Class cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception e) {
            // ignore
        }
    }

    public static String getPath() {
        String jarDir = null;
        try {
            jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        int index = jarDir.indexOf(App.title);
        return jarDir.substring(index);
    }

}
