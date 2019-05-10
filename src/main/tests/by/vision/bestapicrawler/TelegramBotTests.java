package by.vision.bestapicrawler;

import by.vision.betsapicrawler.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;

public class TelegramBotTests {
    private Settings settings;
    private TelegramBot tgBot;
    private final String TEST_MESSAGE = "Test message";
    private static TelegramBotsApi botsApi;
    private static BotSession botSession;

    @Test
    public void shouldSendMessageToGroup(){
        preparation((long) -244826427);
        sendTestMessage();
        Assertions.assertEquals(TEST_MESSAGE, TEST_MESSAGE);
        tgBot.onClosing();
    }

    @Test
    public void shouldSendPrivateMessage(){
        preparation((long) -149802553);
        sendTestMessage();
        Assertions.assertEquals(TEST_MESSAGE, TEST_MESSAGE);
        tgBot.onClosing();
    }

    public void preparation(Long chatID){
        Settings settings = new Settings();
        settings.deserialize();
        StageBuilder.settings = settings;
        StageBuilder.settings.setChatID(chatID);
        MyLogger.STDOUT_LOGGER.debug(StageBuilder.settings.toString());
        tgBot = new TelegramBot();
        initTgBotsAPI();
        startBotSession(tgBot);
    }

    private void sendTestMessage() {
        SendMessage message = new SendMessage(settings.getChatID(), TEST_MESSAGE);
        message.setParseMode("html");
        try {
            tgBot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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

}
