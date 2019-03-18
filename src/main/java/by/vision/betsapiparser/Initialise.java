package by.vision.betsapiparser;

import java.io.IOException;

public class Initialise {

    public static void start(Proxy proxy) {

        System.out.println("Star new parsing iteration");

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
        System.out.println("Thread was stopped");
    }

    public  void stop(){
        FXMLController.bStop = true;
    }



}
