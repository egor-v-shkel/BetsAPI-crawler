package by.vision.betsapicrawler.ProxyProvider;

import java.util.ArrayList;

public class PubProxy implements ProxyProvider {

    private final String REQUEST = "http://pubproxy.com/api/proxy?format=json&type=http&https=true&last_check=60&speed=25&limit=20&user_agent=true&referer=true&country=RU,PL,UA,BY,LT,LV";

    @Override
    public ArrayList<String> toList() {
        return null;
    }

    @Override
    public void refresh() {

    }

    @Override
    public String getIP() {
        return null;
    }

    @Override
    public int getPort() {
        return 0;
    }

}
