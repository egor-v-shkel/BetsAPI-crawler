import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    MatchInfo leftMatch = new MatchInfo();
    MatchInfo rightMatch = new MatchInfo();
    static Settings settings = new Settings();

    public void parseMainPage() {

        final String HOST_SITE = "https://ru.betsapi.com";
        //read from url
        final String MAIN_URL = "https://ru.betsapi.com/ci/soccer";

        Elements tblInplayElements;
        List<MainPageInfo> mainPageInfoList = new ArrayList<>();

        //Setting up connection
        Document doc = getDoc(MAIN_URL);

        tblInplayElements = doc.select("table[id=tbl_inplay] tr");

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

        //remove matches from list , that don't meet time value
        mainPageInfoList.removeIf(s -> s.getTime() != settings.TimeSelect);

        //parse all compatible matches sites
        if (mainPageInfoList.size() > 0){
            for (MainPageInfo info:mainPageInfoList
                 ) {
                /*//this look pretty bad
                String site = HOST_SITE+info.getUrlMatch();
                MatchInfo[] matchParam = parseMatchSite(site);
                MatchInfo leftMatch = matchParam[0];
                MatchInfo rightMatch = matchParam[1];*/

                switch (settings.logic){
                    case OR:
                        if (leftMatch.getPossession() >= settings.possessionMin)
                }
            }
        }


    }

    public void parseMatchSite(String site) {

        Document doc = getDoc(site);

        Elements infoInTr = doc.select("table.table-sm tr");
        //remove <span class="sr-only"> cause of repeating parameters with <div ... role="progressbar"...>
        infoInTr.select("span.sr-only").remove();

        List<String[]> paramList = new ArrayList<>();

        for (Element trElement:infoInTr
             ) {
            Elements tdTagElements = trElement.select("td");
            int counter = 0;
            //System.out.println("!!!New elements group!!!");
            String[] paramInfoBlock = new String[3];
            for (Element elem:tdTagElements
                 ) {
                //System.out.println("Element==>"+elem+"<==Element, at index = "+counter);
                paramInfoBlock[counter] = elem.text();
                counter++;
                if (counter == 2) paramList.add(paramInfoBlock);
            }
            System.out.println(Arrays.toString(paramInfoBlock));
        }


        for (String[] param:paramList
             ) {
            String header = param[1];
            String leftTeamParam = param[0];
            String rightTeamParam = param[2];
            switch (header) {
                case "":
                    leftMatch.setClubName(param[0]);
                    rightMatch.setClubName(param[2]);
                    break;

                case "Goals":
                    leftMatch.setGoals(Integer.parseInt(leftTeamParam));
                    rightMatch.setGoals(Integer.parseInt(rightTeamParam));
                    break;

                case "Corners":
                    leftMatch.setCorners(Integer.parseInt(leftTeamParam));
                    rightMatch.setCorners(Integer.parseInt(rightTeamParam));
                    break;

                case "Corners (Half)":
                    leftMatch.setCornersHalf(Integer.parseInt(leftTeamParam));
                    rightMatch.setCornersHalf(Integer.parseInt(rightTeamParam));
                    break;

                case "Желтые карточки":
                    leftMatch.setCardYellow(Integer.parseInt(leftTeamParam));
                    rightMatch.setCardYellow(Integer.parseInt(rightTeamParam));
                    break;

                case "Красные карточки":
                    leftMatch.setCardRed(Integer.parseInt(leftTeamParam));
                    rightMatch.setCardRed(Integer.parseInt(rightTeamParam));
                    break;

                case "Пенальти":
                    leftMatch.setPenalties(Integer.parseInt(leftTeamParam));
                    rightMatch.setPenalties(Integer.parseInt(rightTeamParam));
                    break;

                case "Замены":
                    leftMatch.setSubstitutions(Integer.parseInt(leftTeamParam));
                    rightMatch.setSubstitutions(Integer.parseInt(rightTeamParam));
                    break;

                case "Атака":
                    leftMatch.setAttacks(Integer.parseInt(leftTeamParam));
                    rightMatch.setAttacks(Integer.parseInt(rightTeamParam));
                    break;

                case "Опасная Атака":
                    leftMatch.setAttacksDangerous(Integer.parseInt(leftTeamParam));
                    rightMatch.setAttacksDangerous(Integer.parseInt(rightTeamParam));
                    break;


                case "Удары в створ ворот":
                    leftMatch.setTargetOn(Integer.parseInt(leftTeamParam));
                    rightMatch.setTargetOn(Integer.parseInt(rightTeamParam));
                    break;

                case "Удары мимо ворот":
                    leftMatch.setTargetOff(Integer.parseInt(leftTeamParam));
                    rightMatch.setTargetOff(Integer.parseInt(rightTeamParam));
                    break;

                case "Владение мячом":
                    leftMatch.setPossession(Integer.parseInt(leftTeamParam));
                    rightMatch.setPossession(Integer.parseInt(rightTeamParam));
                    break;

            }
        }

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
