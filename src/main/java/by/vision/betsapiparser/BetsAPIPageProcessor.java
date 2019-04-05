package by.vision.betsapiparser;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class BetsAPIPageProcessor implements PageProcessor {
    private Site site = Site.me()
            .setRetryTimes(5)
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36")
            .setSleepTime(1000);
    @Override
    public void process(Page page) {
        System.out.println("Hello from thread: "+Thread.currentThread().getName());
        MyLogger.ROOT_LOGGER.debug("Hi");
        //page.addTargetRequest();
        page.putField("Links", page.getHtml().links().all());
        if (page.getResultItems().get("Links")==null){
            //skip this page
            page.setSkip(true);
        }

    }

    @Override
    public Site getSite() {
        return site;
    }
}
