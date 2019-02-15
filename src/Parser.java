import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static void parseMainPage() {

        //read from url
        final String MAIN_URL = "https://ru.betsapi.com/ci/soccer";

        //read from local file
        File input = new File("D:\\BetsAPI.html");

        /*try {
            doc = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Element tblInplayElement;
        String pageTitle = "";
        String matchURL = "";

        //Setting up connection
        Connection.Response response = null;
        try {
            response = Jsoup.connect(MAIN_URL)
                    .proxy("88.12.42.81", 47617)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36")
                    .referrer("http://www.google.com")
                    .timeout(10000)
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

        pageTitle = doc.title();
        System.out.println(pageTitle);

        assert doc != null;
        tblInplayElement = doc.getElementsByAttributeValue("id", "tbl_inplay").first();
        List<String> matchURLList = tblInplayElement.select("a[id]").eachAttr("href");
        final String HOST_SITE = "https://ru.betsapi.com";

        for (String urlAttributes : matchURLList
        ) {
            matchURL = HOST_SITE + urlAttributes;
            //parseMatchSite(matchURL);
            System.out.println(matchURL);
        }


    }

    public static void parseMatchSite() {

        //String matchSite = "";
        MatchInfo info = new MatchInfo();
        File input = new File("d:\\matchSite.html");
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //selecting elements that contain target information
        Elements infoInTr = doc.select("table.table-sm tr");
        //remove <span class="sr-only"> cause of repeating parameters with <div ... role="progressbar"...>
        infoInTr.select("span.sr-only").remove();
        ArrayList<ArrayList> paramArrList = new ArrayList<>();

        for (Element e : infoInTr
        ) {
            Elements tdTagElements = e.select("td");
            ArrayList<String> paramInfoBlock = new ArrayList<>();
            if (tdTagElements.hasText()) {
                for (Element tdElement : tdTagElements
                ) {
                    paramInfoBlock.add(tdElement.text());
                }
                paramArrList.add(paramInfoBlock);
            }

        }

        for (ArrayList param : paramArrList
        ) {
            String header =(String) param.get(1);
            Object leftTeamParam = param.get(0);
            Object rightTeamParam = param.get(2);

            switch (header)
            {
                case "":
                    info.setClubName((String) leftTeamParam);
                    info.setClubName((String) rightTeamParam);
                    break;

                case "Goals":
                    info.setGoals((int) leftTeamParam);
                    info.setGoals((int) rightTeamParam);
                    break;

                case "Corners":
                    info.setCorners((int) leftTeamParam);
                    info.setCorners((int) rightTeamParam);
                    break;

                case "Corners (Half)":
                    info.setCornersHalf((int) leftTeamParam);
                    info.setCornersHalf((int) rightTeamParam);
                    break;

                case "Желтые карточки":
                    info.setCardYellow((int) leftTeamParam);
                    info.setCardYellow((int) rightTeamParam);
                    break;

                case "Красные карточки":
                    info.setCardRed((int) leftTeamParam);
                    info.setCardRed((int) rightTeamParam);
                    break;

                case "Пенальти":
                    info.setPenalties((int) leftTeamParam);
                    info.setPenalties((int) rightTeamParam);
                    break;

                case "Замены":
                    info.setSubstitutions((int) leftTeamParam);
                    info.setSubstitutions((int) rightTeamParam);
                    break;

                case "Атака":
                    info.setAttacks((int) leftTeamParam);
                    info.setAttacks((int) rightTeamParam);
                    break;

                case "Опасная Атака":
                    info.setAttacksDangerous((int) leftTeamParam);
                    info.setAttacksDangerous((int) rightTeamParam);
                    break;


                case "Удары в створ ворот":
                    info.setTargetOn((int) leftTeamParam);
                    info.setTargetOn((int) rightTeamParam);
                    break;

                case "Удары мимо ворот":
                    info.setTargetOff((int) leftTeamParam);
                    info.setTargetOff((int) rightTeamParam);
                    break;

                case "Владение мячом":
                    info.setPossession((int) leftTeamParam);
                    info.setPossession((int) rightTeamParam);
                    break;

            }
            System.out.println("header"+header);
            System.out.println("List ==>" + param);
        }

    }

}
