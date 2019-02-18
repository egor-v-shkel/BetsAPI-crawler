import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.lang.management.MonitorInfo;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    static Settings settings = new Settings();

    public static void parseMainPage() {

        final String HOST_SITE = "https://ru.betsapi.com";
        //read from url
        final String MAIN_URL = "https://ru.betsapi.com/ci/soccer";

        Elements tblInplayElements;
        List<MainPageInfo> mainPageInfoList = new ArrayList<>();

        //Setting up connection
        Document doc = getDoc(MAIN_URL);

        //String pageTitle = doc.title();
        //System.out.println(pageTitle);

        tblInplayElements = doc.select("table[id=tbl_inplay] tr");
        //System.out.println("Element ==>"+tblInplayElements+"<==Element");

        //parse values from main page and write it to mainPageInfoList
        for (Element e:tblInplayElements
             ) {
            MainPageInfo mainPageInfo = new MainPageInfo();

            mainPageInfo.setLeague(e.selectFirst("td[class=league_n] a").ownText());
            mainPageInfo.setIdMatch(e.attr("id").replace("r_","" ));

            Element timeElement = e.selectFirst("span[class=race-time]");
            mainPageInfo.setTime(Integer.parseInt(timeElement.ownText().replace("\'", "")));

            Element timeSupElement = timeElement.selectFirst("sup");
            if (timeSupElement != null){
                timeSupElement.remove();
            }

            Element scoreElement = e.selectFirst(String.format("td[id=o_%s_0]", mainPageInfo.getIdMatch()));
            mainPageInfo.setScore(scoreElement.ownText());

            mainPageInfo.setUrlMatch(timeElement.attr("href"));
            mainPageInfo.setRateL(e.selectFirst(String.format("td[id=o_%s_0]", mainPageInfo.getIdMatch())).ownText());
            mainPageInfo.setRateC(e.selectFirst(String.format("td[id=o_%s_1]", mainPageInfo.getIdMatch())).ownText());
            mainPageInfo.setRateR(e.selectFirst(String.format("td[id=o_%s_2]", mainPageInfo.getIdMatch())).ownText());
            mainPageInfo.setClubL(e.selectFirst("td[class=text-right text-truncate] a").ownText());
            mainPageInfo.setClubR(e.selectFirst("td[class=text-truncate] a").ownText());

            mainPageInfoList.add(mainPageInfo);

        }

        mainPageInfoList.removeIf(s -> s.getTime() == settings.TimeSelect);
        if (mainPageInfoList.size() > 0){
            for (MainPageInfo info:mainPageInfoList
                 ) {
                String site = HOST_SITE+info.getUrlMatch();
                parseMatchSite(site);
            }
        }


    }

    public static void parseMatchSite(String site) {

        MatchInfo leftMatch = new MatchInfo();
        MatchInfo rightMatch = new MatchInfo();
        Document doc = getDoc(site);
        Elements infoInTr = doc.select("table.table-sm tr");
        //remove <span class="sr-only"> cause of repeating parameters with <div ... role="progressbar"...>
        infoInTr.select("span.sr-only").remove();

        List<String[]> paramList = new ArrayList<>();
        String[] paramInfoBlock = new String[3];

        for (Element trElement:infoInTr
             ) {
            Elements tdTagElements = trElement.select("td");
            int counter = 0;
            System.out.println("!!!New elements group!!!");
            for (Element elem:tdTagElements
                 ) {
                System.out.println("Element==>"+elem+"<==Element, at index = "+counter);
                paramInfoBlock[counter] = elem.text();
                counter++;
            }
            paramList.add(paramInfoBlock);
            System.out.println(paramInfoBlock);
        }


        paramList.forEach(param -> {
            String header = param[1];
            int leftTeamParam = Integer.parseInt(param[0]);
            int rightTeamParam = Integer.parseInt(param[2]);
            switch (header) {
                case "":
                    leftMatch.setClubName(param[0]);
                    rightMatch.setClubName(param[2]);
                    break;

                case "Goals":
                    leftMatch.setGoals(leftTeamParam);
                    rightMatch.setGoals(rightTeamParam);
                    break;

                case "Corners":
                    leftMatch.setCorners(leftTeamParam);
                    rightMatch.setCorners(rightTeamParam);
                    break;

                case "Corners (Half)":
                    leftMatch.setCornersHalf(leftTeamParam);
                    rightMatch.setCornersHalf(rightTeamParam);
                    break;

                case "Желтые карточки":
                    leftMatch.setCardYellow(leftTeamParam);
                    rightMatch.setCardYellow(rightTeamParam);
                    break;

                case "Красные карточки":
                    leftMatch.setCardRed(leftTeamParam);
                    rightMatch.setCardRed(rightTeamParam);
                    break;

                case "Пенальти":
                    leftMatch.setPenalties(leftTeamParam);
                    rightMatch.setPenalties(rightTeamParam);
                    break;

                case "Замены":
                    leftMatch.setSubstitutions(leftTeamParam);
                    rightMatch.setSubstitutions(rightTeamParam);
                    break;

                case "Атака":
                    leftMatch.setAttacks(leftTeamParam);
                    rightMatch.setAttacks(rightTeamParam);
                    break;

                case "Опасная Атака":
                    leftMatch.setAttacksDangerous(leftTeamParam);
                    rightMatch.setAttacksDangerous(rightTeamParam);
                    break;


                case "Удары в створ ворот":
                    leftMatch.setTargetOn(leftTeamParam);
                    rightMatch.setTargetOn(rightTeamParam);
                    break;

                case "Удары мимо ворот":
                    leftMatch.setTargetOff(leftTeamParam);
                    rightMatch.setTargetOff(rightTeamParam);
                    break;

                case "Владение мячом":
                    leftMatch.setPossession(leftTeamParam);
                    rightMatch.setPossession(rightTeamParam);
                    break;

            }
        });
        System.out.println("Goals r"+rightMatch.getGoals());
        System.out.println("Goals l"+leftMatch.getGoals());

    }

    public static Document getDoc(String MAIN_URL) {
        Connection.Response response = null;
        try {
            response = Jsoup.connect(MAIN_URL)
                    .proxy("189.51.96.114", 52858)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
                    .referrer("http://www.google.com")
                    .timeout(settings.timeout)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Document doc = null;
        try {
            doc = response.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

}
