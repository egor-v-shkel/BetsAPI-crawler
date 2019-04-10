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
                System.out.println("Thread was paused");
                break;
            case "continue":
            break;
            case "/exit":
                System.exit(0);
                break;
        }
    }

    @Override
    public String getBotUsername() {
        return "BetsAPI_bot";
        //return "BetsAPI_2_bot";
    }

    @Override
    public String getBotToken() {
        return "752131033:AAHymxVu8_nqPRvloTob3W30WkFl91jU9bQ";//BetsAPI_bot
        //return "700196610:AAEplpWHPp7rUqOnp7BiYly3K_2dgbctL_I";//BetsAPI_2_bot
    }

    public void interestingMatch(String url, TeamInfo leftMatch, TeamInfo rightMatch) {
        StringBuilder stringBuilder = new StringBuilder();
        String newLine = System.lineSeparator();
        stringBuilder.append("<b>").append(leftMatch.getLeague()).append("</b>").append(newLine)
                .append(leftMatch.getClubName()).append(" (").append(leftMatch.getRateL()).append(") - ").append(rightMatch.getClubName()).append(" (").append(leftMatch.getRateR()).append(")").append(newLine)
                .append("<i>").append(leftMatch.getTime()).append(" мин.</i>").append(newLine)
                .append("<b>").append(leftMatch.getScore()).append("</b>").append(newLine)
                .append("АТ (атаки): [").append(leftMatch.getAttacks()).append(", ").append(rightMatch.getAttacks()).append("]").append(newLine)
                .append("ОАТ (опасные атаки): [").append(leftMatch.getAttacksDangerous()).append(", ").append(rightMatch.getAttacksDangerous()).append("]").append(newLine)
                .append("В (владение мячем): [").append(leftMatch.getPossession()).append(", ").append(rightMatch.getPossession()).append("]").append(newLine)
                .append("У (угловые): [").append(leftMatch.getCorners()).append(", ").append(rightMatch.getCorners()).append("]").append(newLine)
                .append("УВ (удары в створ): [").append(leftMatch.getTargetOn()).append(", ").append(rightMatch.getTargetOn()).append("]").append(newLine)
                .append("УМ (удары мимо ворот): [").append(leftMatch.getTargetOff()).append(", ").append(rightMatch.getTargetOff()).append("]").append(newLine)
                .append(url);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(Settings.tgChatID).setParseMode("html").setText(stringBuilder.toString());
        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        this.onClosing();
    }
}
