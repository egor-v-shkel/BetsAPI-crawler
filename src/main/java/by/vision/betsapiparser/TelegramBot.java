package by.vision.betsapiparser;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {

/*
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText(update.getMessage().getText());
            try {
                execute(message); // Call method to send the message
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
*/
        switch (update.getMessage().getText()){
            case "/id":
            SendMessage sendMessage = new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText(String.valueOf(update.getMessage().getChatId()));
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            break;
            case "pause":
                try {
                    FXMLController.getParserThread().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread was paused");
                break;
            case "continue":
                FXMLController.getParserThread().notify();
            break;
            case "/exit":
                System.exit(0);
                break;
        }
    }

    @Override
    public String getBotUsername() {
        /*return "BetsAPI_bot";*/
        return "BetsAPI_2_bot";
    }

    @Override
    public String getBotToken() {
        /*return "752131033:AAHymxVu8_nqPRvloTob3W30WkFl91jU9bQ";//BetsAPI_bot*/
        return "700196610:AAEplpWHPp7rUqOnp7BiYly3K_2dgbctL_I";//BetsAPI_2_bot
    }
}
