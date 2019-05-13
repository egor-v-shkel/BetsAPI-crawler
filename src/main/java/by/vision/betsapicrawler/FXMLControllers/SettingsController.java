package by.vision.betsapicrawler.FXMLControllers;

import by.vision.betsapicrawler.SettingsModel;

public interface SettingsController {

    void applySettings();

    default void saveSettings(){
        SettingsModel.currentSettings.serialize();
    }

    void showSettings();

}
