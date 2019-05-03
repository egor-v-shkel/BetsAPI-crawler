package by.vision.betsapicrawler.Scenes;

import by.vision.betsapicrawler.FXMLControllers.TgSettingsFXMLController;
import by.vision.betsapicrawler.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static by.vision.betsapicrawler.Main.primaryStage;

public class TgSettings {
    private static Stage stage = new Stage();

    public TgSettings() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML/TgSettings.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TgSettingsFXMLController tgController = fxmlLoader.getController();
        Scene scene = new Scene(root);
        stage.setTitle("Настройки телеграм");
        stage.setScene(scene);
        // Specifies the modality for new window.
        stage.initModality(Modality.WINDOW_MODAL);
        // Specifies the owner Window (parent) for new window.
        stage.initOwner(primaryStage);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);
        //if settings was successfully loaded, show Telegram bot settings in GUI
        if (Main.settings.getSerStat()){
            tgController.chatId.setText(String.valueOf(Main.settings.getChatID()));
            tgController.botName.setText(String.valueOf(Main.settings.getBotName()));
            tgController.botToken.setText(String.valueOf(Main.settings.getBotToken()));
        }
    }

    public static void hide() {
        stage.hide();
    }

    public void show(){
        stage.show();
    }
}
