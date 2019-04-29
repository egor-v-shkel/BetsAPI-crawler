package by.vision.betsapicrawler.Crawler;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.vision.betsapicrawler.*;

import by.vision.betsapicrawler.FXMLControllers.FXMLController;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import javafx.scene.control.Hyperlink;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyCrawler extends WebCrawler {

    private TelegramBot telegramBot = new TelegramBot();
    //List<CommonInfo> list = new ArrayList<>();
    private static HashMap<String, CommonInfo> hashMap = new HashMap<>();
    private TeamInfo leftTeamInfo;
    private TeamInfo rightTeamInfo;

    /**
     * You should implement this function to specify whether the given url
     * should be crawled or not (based on your crawling logic).
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        if (!href.startsWith("https://ru.betsapi.com/r/")) return false;
        HtmlParseData htmlParseData = (HtmlParseData) referringPage.getParseData();
        Document doc = Jsoup.parse(htmlParseData.getHtml());

        //TODO split this method
        return getCommonInfoAndDecide(doc, href);
    }

    //TODO split in two methods

    private boolean getCommonInfoAndDecide(Document doc, String url) {

        //link example https://ru.betsapi.com/r/1554160/Leixlip-United-v-Hartstown-Huntstown
        String href = url.replace("https://ru.betsapi.com", "");
        String CSSPattern = String.format("tr:has(a[href=%s])", href);

        logger.debug("CSS pattern "+CSSPattern);

        //selecting node , where needed information located
        Element node = doc.selectFirst(CSSPattern);
        //time
        //get time value and rid of unneeded '\'' character
        String timeStr = node.selectFirst("span.race-time").ownText().replace("'", "");
        int time = Integer.parseInt(timeStr);
        //do not add matches, that don't meet time value
        int settingsTimeMin = Main.settings.getTimeSelectMin();
        int settingsTimeMax = Main.settings.getTimeSelectMax();
        if(time < settingsTimeMin || time > settingsTimeMax) return false;

        //TODO process case, when dont need to get rate param
        String rateLStr = node.selectFirst("td[id$=_0]").ownText();
        String rateRStr = node.selectFirst("td[id$=_2]").ownText();
        if (rateLStr.equals("-") || rateRStr.equals("-")){
            rateLStr = "100";
            rateRStr = rateLStr;
        }
        double rateL = Double.parseDouble(rateLStr);
        double rateR = Double.parseDouble(rateRStr);

        //do not add matches, that don't meet min coefficient value
        double settingsRate = Main.settings.getRateMin();
        if(rateL < settingsRate || rateR < settingsRate) return false;

        //do not add matches, that already was sent to GUI/Telegram
        boolean inList = FXMLController.hyperlinkObservableList.stream()
                .anyMatch(hyperlink -> hyperlink.getText().toLowerCase().endsWith(url));
        if (inList) return false;

        MyLogger.ROOT_LOGGER.debug(url);

        //if match meet settings add them to hashmap
        CommonInfo cmn = new CommonInfo();
        //rate
        cmn.setRateL(rateLStr);
        cmn.setRateR(rateRStr);
        //time
        cmn.setTime(time);
        //league
        cmn.setLeague(node.selectFirst("td.league_n a").ownText());
        //matchId
        String id = getID(url);
        cmn.setIdMatch(id);
        //URL
        cmn.setUrlMatch(url);
        //score
        cmn.setScore(node.selectFirst("td.text-center a").ownText());
        //clubs names
        cmn.setClubL(node.selectFirst("td.text-right a").ownText());
        cmn.setClubR(node.selectFirst("td.text-truncate a").ownText());

        //list.add(cmn);
        hashMap.put(id, cmn);

        logger.debug("URL: "+url);
        return true;
    }

    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page) {

        if (page.getWebURL().getURL().toLowerCase().startsWith("https://ru.betsapi.com/r/")) {
            String id = getID(page.getWebURL().getURL());
            CommonInfo cmn = hashMap.get(id);
            leftTeamInfo = new TeamInfo(cmn);
            rightTeamInfo = new TeamInfo(cmn);

            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String url = page.getWebURL().toString();
            Document doc = Jsoup.parse(htmlParseData.getHtml());

            Elements infoInTr = doc.select("table.table-sm tr");
            //remove <span class="sr-only"> because of repetition parameters with <div ... role="progressbar"...>
            infoInTr.select("span.sr-only").remove();

            List<String[]> paramList = new ArrayList<>();

            for (Element trElement : infoInTr
            ) {
                Elements tdTagElements = trElement.select("td");
                int counter = 0;
                String[] paramInfoBlock = new String[3];
                for (Element elem : tdTagElements
                ) {
                    paramInfoBlock[counter] = elem.text();
                    counter++;
                    if (counter == 3) paramList.add(paramInfoBlock);
                }
            }

            for (String[] param : paramList
            ) {
                String header = param[1];
                String leftTeamParam = param[0];
                String rightTeamParam = param[2];

                switch (header) {
                    case "":
                        leftTeamInfo.setClubName(param[0]);
                        rightTeamInfo.setClubName(param[2]);
                        break;

                    case "Goals":
                        leftTeamInfo.setGoals(Integer.parseInt(leftTeamParam));
                        rightTeamInfo.setGoals(Integer.parseInt(rightTeamParam));
                        break;

                    case "Corners":
                        leftTeamInfo.setCorners(Integer.parseInt(leftTeamParam));
                        rightTeamInfo.setCorners(Integer.parseInt(rightTeamParam));
                        break;

                    case "Corners (Half)":
                        leftTeamInfo.setCornersHalf(Integer.parseInt(leftTeamParam));
                        rightTeamInfo.setCornersHalf(Integer.parseInt(rightTeamParam));
                        break;

                    case "Желтые карточки":
                        leftTeamInfo.setCardYellow(Integer.parseInt(leftTeamParam));
                        rightTeamInfo.setCardYellow(Integer.parseInt(rightTeamParam));
                        break;

                    case "Красные карточки":
                        leftTeamInfo.setCardRed(Integer.parseInt(leftTeamParam));
                        rightTeamInfo.setCardRed(Integer.parseInt(rightTeamParam));
                        break;

                    case "Пенальти":
                        leftTeamInfo.setPenalties(Integer.parseInt(leftTeamParam));
                        rightTeamInfo.setPenalties(Integer.parseInt(rightTeamParam));
                        break;

                    case "Замены":
                        leftTeamInfo.setSubstitutions(Integer.parseInt(leftTeamParam));
                        rightTeamInfo.setSubstitutions(Integer.parseInt(rightTeamParam));
                        break;

                    case "Атака":
                        leftTeamInfo.setAttacks(Integer.parseInt(leftTeamParam));
                        rightTeamInfo.setAttacks(Integer.parseInt(rightTeamParam));
                        break;

                    case "Опасная Атака":
                        leftTeamInfo.setAttacksDangerous(Integer.parseInt(leftTeamParam));
                        rightTeamInfo.setAttacksDangerous(Integer.parseInt(rightTeamParam));
                        break;

                    case "Удары в створ ворот":
                        leftTeamInfo.setTargetOn(Integer.parseInt(leftTeamParam));
                        rightTeamInfo.setTargetOn(Integer.parseInt(rightTeamParam));
                        break;

                    case "Удары мимо ворот":
                        leftTeamInfo.setTargetOff(Integer.parseInt(leftTeamParam));
                        rightTeamInfo.setTargetOff(Integer.parseInt(rightTeamParam));
                        break;

                    case "Владение мячом":
                        leftTeamInfo.setPossession(Integer.parseInt(leftTeamParam));
                        rightTeamInfo.setPossession(Integer.parseInt(rightTeamParam));
                        break;

                }
            }


            int settingsOnTargetMin = Main.settings.getOnTargetMin();
            int settingsOffTargetMin = Main.settings.getOffTargetMin();
            int settingsPossessionMin = Main.settings.getPossessionMin();
            boolean possessL = leftTeamInfo.getPossession() >= settingsPossessionMin;
            boolean onTargetL = leftTeamInfo.getTargetOn() >= settingsOnTargetMin;
            boolean offTargetL = leftTeamInfo.getTargetOff() >= settingsOffTargetMin;
            boolean possessionR = rightTeamInfo.getPossession() >= settingsPossessionMin;
            boolean onTargetR = rightTeamInfo.getTargetOn() >= settingsOnTargetMin;
            boolean offTargetR = rightTeamInfo.getTargetOff() >= settingsOffTargetMin;

            switch (Main.settings.getLogic()) {

                case OR:
                    if ((possessL || onTargetL || offTargetL) || (possessionR || onTargetR || offTargetR))
                        notify(leftTeamInfo, rightTeamInfo, url);
                    break;
                case AND:
                    if ((possessL && onTargetL && offTargetL) || (possessionR && onTargetR && offTargetR))
                        notify(leftTeamInfo, rightTeamInfo, url);
                    break;

            }
        }

        logger.debug("=============");
    }

    private String getID(String url) {
        Pattern pattern = Pattern.compile(".+/r/(\\d+).+");
        Matcher matcher = pattern.matcher(url);
        while (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private void notify(TeamInfo leftMatch, TeamInfo rightMatch, String url) {

        Hyperlink hyperlink = new Hyperlink(url);
        hyperlink.setOnAction(actionEvent -> {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                try {
                    Desktop.getDesktop().browse(new URI(hyperlink.getText()));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        FXMLController.hyperlinkObservableList.add(hyperlink);
        telegramBot.interestingMatch(url, leftMatch, rightMatch);
    }
}