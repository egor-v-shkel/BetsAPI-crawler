package by.vision.betsapiparser;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

public class Initialise {

    public static void start(Proxy proxy) {

        System.out.println("Star new parsing iteration");

        Parser ps = new Parser(proxy);
        ps.parseMainPage();
    }
}

class MyThread implements Runnable {
    Thread thread;

    // Конструктор
    MyThread(String name) {
        // Создаём новый поток
        thread = new Thread(this, name);
        thread.start(); // Запускаем поток
    }

    // Обязательный метод для интерфейса Runnable
    public void run() {


        Proxy proxy = new Proxy();
        try {
            proxy.getNewProxyArray();
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
