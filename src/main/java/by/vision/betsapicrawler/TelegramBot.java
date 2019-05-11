package by.vision.betsapicrawler;

import by.vision.betsapicrawler.MatchInfo.TeamInfo;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static by.vision.betsapicrawler.StageBuilder.settings;

public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {

        switch (update.getMessage().getText()){
            case "/id":
                Long chatId = update.getMessage().getChatId();
                SendMessage sendChatID = new SendMessage()
                        .setChatId(chatId)
                        .setText(String.valueOf(chatId));
            try {
                execute(sendChatID);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            break;
            case "/pause":
                System.out.println("Thread was paused");
                break;
            case "/continue":
            break;
            case "/exit":
                System.exit(0);
                break;
        }
    }

    @Override
    public String getBotUsername() {
        return settings.getBotName();
    }

    @Override
    public String getBotToken() {
        return settings.getBotToken();
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
        sendMessage.setChatId(settings.getChatID()).setText(message.toString());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        //this.onClosing();
    }
}
