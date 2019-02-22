
import java.net.MalformedURLException;
import java.net.URL;

public class Proxy {

    String ip;
    int port;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    static void getProxyList(){

        try {
            URL url = new URL("http://pubproxy.com/api/proxy?format=json&type=http&https=true&last_check=60&speed=25&limit=20&user_agent=true&referer=true&country=US,RU");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
