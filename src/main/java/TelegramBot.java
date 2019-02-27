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
        if (update.getMessage().getText().equals("/stop")){
            System.exit(0);
        }
    }

    @Override
    public String getBotUsername() {
        return "BetsAPI_bot";
    }

    @Override
    public String getBotToken() {
        return "752131033:AAHymxVu8_nqPRvloTob3W30WkFl91jU9bQ";
    }
}
