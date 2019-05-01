package by.vision.betsapicrawler.Scenes;

import by.vision.betsapicrawler.FXMLControllers.TgSettingsFXMLController;
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TgSettings.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TgSettingsFXMLController controller = fxmlLoader.<TgSettingsFXMLController>getController();
        Scene scene = new Scene(root);
        stage.setTitle("Настройки телеграм");
        stage.setScene(scene);
        // Specifies the modality for new window.
        stage.initModality(Modality.WINDOW_MODAL);
        // Specifies the owner Window (parent) for new window
        stage.initOwner(primaryStage);
        stage.centerOnScreen();
        stage.initStyle(StageStyle.UNDECORATED);
    }

    public static void hide() {
        stage.hide();
    }

    public void show(){
        stage.show();
    }
}
