package by.vision.betsapiparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Connection {

    String request;
    org.jsoup.Connection.Response response;
    String proxy;
    boolean useProxy;
    int numTries = 5;

    Connection(String request){
        this.request = request;
    }
    Connection(String request, boolean useProxy){
        this.request = request;
        getProxy();

    }

    Connection(String request, String proxy){
        this.request = request;
        this.proxy = proxy;

    }

    private void getProxy() {

    }

    private void connect(String request){
        while (!FXMLController.bStop) {
            try {
                response = Jsoup.connect(request)
                        .timeout(Settings.proxyTimeout)
                        .proxy(ip, port)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.119 Safari/537.36")
                        .referrer("http://www.google.com")
                        //.ignoreHttpErrors(true)
                        .execute();

                break;
            } catch (IOException e) {
                MyLogger.ROOT_LOGGER.info("Unsuccessful connection for proxy "+ip+":"+port+"\n", e);
                if (--numTries <= 0) try {
                    throw e;
                } catch (IOException e1) {
                    MyLogger.ROOT_LOGGER.info("Reconnection attempt №"+numTries);
                    proxy.refresh();
                    ip = proxy.getIp();
                    port = proxy.getPort();
                }
            }
        }

        Document doc = null;
        try {
            doc = response.parse();
        } catch (IOException e) {
            e.printStackTrace();
            MyLogger.ROOT_LOGGER.info("Document was not created", e);
        } finally {
            return doc;
        }


    }

    private String getResponse(){

    }


}
