package by.vision.betsapicrawler.Crawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.parser.TikaHtmlParser;
import edu.uci.ics.crawler4j.url.TLDList;

public class MyHtmlParser extends TikaHtmlParser {
    public MyHtmlParser(CrawlConfig config, TLDList tldList) throws InstantiationException, IllegalAccessException {
        super(config);
    }
    public HtmlParseData parse(){
    return null;
    }
}
