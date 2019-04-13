package by.vision.betsapiparser.Crawler;

import by.vision.betsapiparser.FXMLController;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrawlerThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(WebCrawler.class);
    private Thread thread;
    CrawlController controller;
    private CrawlerStarter starter;

    // Конструктор
    public CrawlerThread(String name) {
        // Создаём новый поток
        thread = new Thread(this, name);
        thread.start(); // Запускаем поток
    }

    // Обязательный метод для интерфейса Runnable
    public void run() {

        FXMLController.bStop = false;

        while (!FXMLController.bStop) {
            try {
                starter = new CrawlerStarter();
                starter.start();
            } catch (Exception e) {
                logger.debug("Exception while starting crawler: \n", e);
            }
        }
        System.out.println("Thread was stopped");
    }

    public void stop(){
        FXMLController.bStop = true;
        starter.stop();
        logger.debug("Crawler stopped");
    }


}
