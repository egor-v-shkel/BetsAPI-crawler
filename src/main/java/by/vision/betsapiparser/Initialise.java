package by.vision.betsapiparser;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;

public class Initialise {

    private static final Logger LOGGER = LogManager.getLogger(Initialise.class.getName());

    public static void start(Proxy proxy) {

        LOGGER.debug("Start parsing");

        Parser ps = new Parser(proxy);
        ps.parseMainPage();
    }
}

class ParserThread implements Runnable {
    Thread thread;

    // Конструктор
    ParserThread(String name) {
        // Создаём новый поток
        thread = new Thread(this, name);
        thread.start(); // Запускаем поток
    }

    // Обязательный метод для интерфейса Runnable
    public void run() {


        Proxy proxy = new Proxy();
        try {
            proxy.getProxyList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!FXMLController.bStop) {
            Initialise.start(proxy);
        }

    }

    public  void stop(){
        FXMLController.bStop = true;
    }



}
