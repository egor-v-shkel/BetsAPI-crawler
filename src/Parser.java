import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static void parseMainPage(){

        //final String betsapiMainURL = "https://ru.betsapi.com/ci/soccer";
        File input = new File("D:\\BetsAPI.html");
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //List<ParseInfo> parsInfList = new List<ParseInfo>();
        //Document doc  = null;
        Element tblInplayElement;
        String pageTitle = "";
        String matchURL = "";
        ArrayList<Element> elementList = new ArrayList<>(); //list of parsed elements

        /*try {
            doc = Jsoup.connect(betsapiMainURL).get();
            pageTitle = doc.title();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        assert doc != null;
        tblInplayElement = doc.getElementsByAttributeValue("id", "tbl_inplay").first();
        System.out.println(tblInplayElement);
        System.out.println();
        System.out.println("!!!"+tblInplayElement.select("a[id]").eachAttr("href"));

    }

}
