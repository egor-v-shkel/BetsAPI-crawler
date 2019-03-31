package by.vision.betsapiparser.ProxyServices;

import java.util.ArrayList;

public class PubProxy implements ProxyService {

    private final String REQUEST = "http://pubproxy.com/api/proxy?format=json&type=http&https=true&last_check=60&speed=25&limit=20&user_agent=true&referer=true&country=RU,PL,UA,BY,LT,LV";

    @Override
    public ArrayList<String> list() {
        return null;
    }

    @Override
    public void request() {

    }

}
