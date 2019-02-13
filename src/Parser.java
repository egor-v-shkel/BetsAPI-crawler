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

        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element tblInplayElement;
        String pageTitle = "";
        String matchURL = "";
        ArrayList<Element> elementList = new ArrayList<>(); //list of parsed elements

        try {
            doc = Jsoup.connect(MAIN_URL).get();
            pageTitle = doc.title();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert doc != null;
        tblInplayElement = doc.getElementsByAttributeValue("id", "tbl_inplay").first();
        List<String> matchURLList = tblInplayElement.select("a[id]").eachAttr("href");
        final String HOST_SITE = "https://ru.betsapi.com";

        for (String urlAttributes : matchURLList
        ) {
            matchURL = HOST_SITE + urlAttributes;
            //parseMatchSite(matchURL);
        }


    }

    public static void parseMatchSite() {

        //String matchSite = "";

        File input = new File("e:\\zeno\\matchSite.html");
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //creating elements with main information
        Elements infoInTr = doc.select("table.table-sm tr");
        for (Element e:infoInTr
             ) {
            System.out.println("!!!Element!!!"+e);
        }

    }

}
