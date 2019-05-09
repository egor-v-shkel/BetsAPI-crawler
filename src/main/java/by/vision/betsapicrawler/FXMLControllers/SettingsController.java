package by.vision.betsapicrawler.FXMLControllers;

import by.vision.betsapicrawler.StageBuilder;

public interface SettingsController {

    void applySettings();

    default void saveSettings(){
        StageBuilder.settings.serialize();
    }

    void showSettings();

}
