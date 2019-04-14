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
        StringBuilder message = new StringBuilder();
        String end = "]\n";
        String league = leftMatch.getLeague();
        String clubL = leftMatch.getClubName();
        String rateL = leftMatch.getRateL();
        String clubR = rightMatch.getClubName();
        String rateR = leftMatch.getRateR();
        int timeL = leftMatch.getTime();
        int attacksL = leftMatch.getAttacks();
        int attacksR = rightMatch.getAttacks();
        int atkDngL = leftMatch.getAttacksDangerous();
        int atkDngR = rightMatch.getAttacksDangerous();

        //TODO make better method construction for readability
        message.append("<b>").append(league).append("</b>\n")
                .append(clubL).append(" (").append(rateL).append(") - ").append(clubR).append(" (").append(rateR).append(")\n")
                .append("<i>").append(timeL).append(" мин.</i>\n")
                .append("<b>").append(leftMatch.getScore()).append("</b>\n")
                .append("АТ (атаки): [").append(attacksL).append(", ").append(attacksR).append(end)
                .append("ОАТ (опасные атаки): [").append(atkDngL).append(", ").append(atkDngR).append(end)
                .append("В (владение мячем): [").append(leftMatch.getPossession()).append(", ").append(rightMatch.getPossession()).append(end)
                .append("У (угловые): [").append(leftMatch.getCorners()).append(", ").append(rightMatch.getCorners()).append(end)
                .append("УВ (удары в створ): [").append(leftMatch.getTargetOn()).append(", ").append(rightMatch.getTargetOn()).append(end)
                .append("УМ (удары мимо ворот): [").append(leftMatch.getTargetOff()).append(", ").append(rightMatch.getTargetOff()).append(end)
                .append(url);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(App.settings.getTgChatID()).setParseMode("html").setText(message.toString());
        try {
            this.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        this.onClosing();
    }
}
