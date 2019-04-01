package by.vision.betsapiparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.Proxy;

public class Connection {

    String request;
    org.jsoup.Connection.Response response;
    Proxy proxy;
    boolean useProxy;
    private int NUM_TRIES = 5;
    Document doc;
    org.jsoup.Connection preset = Jsoup.connect(request)
            .timeout(Settings.proxyTimeout)
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36")
            .referrer("http://www.google.com");


    Connection(String request){
        this.request = request;
    }


    private org.jsoup.Connection.Response connect(){
        while (!FXMLController.bStop) {
            try {
                response = preset
                        //.ignoreHttpErrors(true)
                        .execute();
                break;
            } catch (IOException e) {
                MyLogger.ROOT_LOGGER.info("Unsuccessful connection for proxy "+ip+":"+port+"\n", e);
                if (--NUM_TRIES <= 0) try {
                    throw e;
                } catch (IOException e1) {
                    MyLogger.ROOT_LOGGER.info("Reconnection attempt №"+ NUM_TRIES);
                }
            }
        }




    }

    private org.jsoup.Connection.Response connect(Proxy proxy){
        while (!FXMLController.bStop) {
            try {
                response = preset
                        //.ignoreHttpErrors(true)
                        .proxy(ip, port)
                        .execute();

                break;
            } catch (IOException e) {
                MyLogger.ROOT_LOGGER.info("Unsuccessful connection for proxy "+ip+":"+port+"\n", e);
                if (--NUM_TRIES <= 0) try {
                    throw e;
                } catch (IOException e1) {
                    MyLogger.ROOT_LOGGER.info("Reconnection attempt №"+ NUM_TRIES);
                    proxy.refresh();
                    ip = proxy.getIp();
                    port = proxy.getPort();
                }
            }
        }


    }

    private void connect(boolean autoProxy){
        while (!FXMLController.bStop) {
            response = autoProxy ? connect(getProxy()) : connect();
        }
    }

    private Proxy getProxy() {

    }

    private String getResponse(){

    }

    private void respToDoc{
        try {
            doc = response.parse();
        } catch (IOException e) {
            e.printStackTrace();
            MyLogger.ROOT_LOGGER.info("Document was not created", e);
        } finally {
            return doc;
        }
    }

}
