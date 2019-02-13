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

        File input = new File("d:\\matchSite.html");
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //creating elements that contain target information
        Elements infoInTr = doc.select("table.table-sm tr");
        for (Element e : infoInTr
        ) {
            String elementSpanL = "", elementSpanR = "";
            Elements listTdElements = e.getElementsByTag("td");
            String leftTeamVal = listTdElements.first().ownText();
            String rightTeamVal = listTdElements.last().ownText();
            Element leftTeamSpanElem = listTdElements.first();
            Element rightTeamSpanVal = listTdElements.tagName("span").attr("class", "sr-only").first();
            //String rightTeamSpanVal = leftTeamSpanElem.selectFirst("span.sr-only").ownText();
            //System.out.println("Left team span val " + leftTeamSpanVal);
            //System.out.println("Right team span val " + rightTeamSpanVal);
            System.out.println(rightTeamSpanVal);

        }

    }

}
