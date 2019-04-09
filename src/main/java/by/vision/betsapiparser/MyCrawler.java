package by.vision.betsapiparser;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyCrawler extends WebCrawler {

    /**
     * You should implement this function to specify whether the given url
     * should be crawled or not (based on your crawling logic).
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {

        String href = url.getURL().toLowerCase();
        return href.startsWith("https://ru.betsapi.com/r/");
    }

    /**
     * This function is called when a page is fetched and ready to be processed
     * by your program.
     */
    @Override
    public void visit(Page page) {
        /*int docid = page.getWebURL().getDocid();
        String url = page.getWebURL().getURL();
        String domain = page.getWebURL().getDomain();
        String path = page.getWebURL().getPath();
        String subDomain = page.getWebURL().getSubDomain();
        String parentUrl = page.getWebURL().getParentUrl();
        String anchor = page.getWebURL().getAnchor();
        String tag = page.getWebURL().getTag();

        logger.debug("Docid: {}", docid);
        logger.info("URL: {}", url);
        logger.debug("Domain: '{}'", domain);
        logger.debug("Sub-domain: '{}'", subDomain);
        logger.debug("Path: '{}'", path);
        logger.debug("Parent page: {}", parentUrl);
        logger.debug("Anchor text: {}", anchor);
        logger.debug("Tag: {}", tag);


        if (page.getParseData() instanceof TikaHtmlParser) {
            System.out.println();
        }
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            logger.debug("Text length: {}", text.length());
            logger.debug("Html length: {}", html.length());
            logger.debug("Number of outgoing links: {}", links.size());
        }

        Header[] responseHeaders = page.getFetchResponseHeaders();
        if (responseHeaders != null) {
            logger.debug("Response headers:");
            for (Header header : responseHeaders) {
                logger.debug("\t{}: {}", header.getName(), header.getValue());
            }
        }*/

        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
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

                MatchInfo leftMatch = MainPageInfo.getMatchInfoL();
                MatchInfo rightMatch = MainPageInfo.getMatchInfoR();

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

            MatchInfo leftMatch = MainPageInfo.getMatchInfoL();
            MatchInfo rightMatch = MainPageInfo.getMatchInfoR();

            boolean bPossess = leftMatch.getPossession() >= Settings.possessionMin;
            boolean bTargetOn = leftMatch.getTargetOn() >= Settings.targetOnMin;
            boolean bTargetOff = leftMatch.getTargetOff() >= Settings.targetOffMin;
            boolean bRightPossess = rightMatch.getPossession() >= Settings.possessionMin;
            boolean bRightTargetOn = rightMatch.getTargetOn() >= Settings.targetOnMin;
            boolean bRightTargetOff = rightMatch.getTargetOff() >= Settings.targetOffMin;

            switch (Settings.logic) {

                case OR:
                    if (bPossess || bTargetOn || bTargetOff) break;
                    if (bRightPossess || bRightTargetOn || bRightTargetOff) break;
                    break;
                case AND:
                    if (bPossess && bTargetOn && bTargetOff) break;
                    if (bRightPossess && bRightTargetOn && bRightTargetOff) break;
                    break;

            }

            //add link to GUI/Telegram
            String url = page.getWebURL().toString();
            new Parser().sendTelegramMessage(url, leftMatch, rightMatch);
        }

        logger.debug("=============");
    }
}