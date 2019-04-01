package by.vision.betsapiparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

public class Connection {

    String request = "https://gimmeproxy.com/api/getProxy?ipPort=true&protocol=http";
    org.jsoup.Connection.Response response;
    Proxy proxy;
    boolean useProxy;
    private int NUM_TRIES = 5;
    Document doc;
    org.jsoup.Connection preset = Jsoup.connect(request)
            .timeout(Settings.proxyTimeout)
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36")
            .referrer("http://www.google.com");


    public Connection(String request) {
        this.request = request;
    }


    public void start() {
        while (!FXMLController.bStop) {
            try {
                response = preset
                        //.ignoreHttpErrors(true)
                        .execute();
                break;
            } catch (IOException e) {
                MyLogger.ROOT_LOGGER.info("Unsuccessful connection\n", e);
                checkTry(e);
            }
        }

    }

    public void start(Proxy proxy) {
        String proxyStr = null;
        int port = 0;
        getProxy();
        java.net.Proxy px = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("", port));
        while (!FXMLController.bStop) {
            try {
                response = preset
                        //.ignoreHttpErrors(true)
                        .proxy(proxyStr, port)
                        .execute();
                break;
            } catch (IOException e) {
                MyLogger.ROOT_LOGGER.info("Unsuccessful connection for proxy ", e);
                checkTry(e);
            }
        }


    }

    public void start(boolean autoProxy) {
        while (!FXMLController.bStop) {
            if (autoProxy) {
                start(getProxy());
            } else {
                start();
            }
        }
    }

    private void checkTry(IOException e) {
        if (--NUM_TRIES <= 0) try {
            throw e;
        } catch (IOException e1) {
            MyLogger.ROOT_LOGGER.info("Reconnection attempt №" + NUM_TRIES);
        }
    }

    private Proxy getProxy() {
        return null;
    }

    public String getResponse() {
        return Integer.toString(response.statusCode());
    }

    private void respToDoc() {

    }

    /*{
        try {
            doc = response.parse();
        } catch (IOException e) {
            e.printStackTrace();
            MyLogger.ROOT_LOGGER.info("Document was not created", e);
        }
    }*/

}
