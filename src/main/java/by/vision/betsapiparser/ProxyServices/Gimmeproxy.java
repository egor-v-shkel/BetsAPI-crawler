package by.vision.betsapiparser.ProxyServices;

import by.vision.betsapiparser.Connection;
import java.util.ArrayList;

public class Gimmeproxy implements ProxyService {
    String ip;
    int port;
    private final String REQUEST = "https://gimmeproxy.com/api/getProxy?ipPort=true&protocol=http";

    @Override
    public ArrayList<String> list() {
        return null;
    }

    @Override
    public void serviceConnect() {
        Connection conn = new Connection(REQUEST);
        conn.start();
        System.out.println(conn.getResponse());

    }

    private String getIp(){
        return ip;
    }

    private int getPort(){
        return port;
    }
}
