import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

class Proxy {
    private String ip;
    private int port;
    private boolean firstTimeCall = true;
    private JSONArray ipArray;
    private JSONObject proxyObj;

    public String getIp() {
        if (firstTimeCall){
            try {
                getNewProxyArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
            firstTimeCall = false;
        } else {
            try {
                renew();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    private void renew() throws IOException {
        if(ipArray.isEmpty()){
            getNewProxyArray();
        } else {
            proxyObj = ipArray.getJSONObject(0);
            ip = proxyObj.getString("ip");
            port = proxyObj.getInt("port");
            ipArray.remove(0);
        }
    }

    private void getNewProxyArray() throws IOException {
        //TODO rewrite this part
        URL url = new URL("http://pubproxy.com/api/proxy?format=json&type=http&https=true&last_check=60&speed=25&limit=20&user_agent=true&referer=true&country=RU");
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()){
            sb.append(scanner.nextLine());
        }
        String response = sb.toString();
        System.out.println("New JSON was taken");

        JSONObject jsonObject = new JSONObject(response);
        ipArray = jsonObject.getJSONArray("data");
        proxyObj = ipArray.getJSONObject(0);
        ip = proxyObj.getString("ip");
        port = proxyObj.getInt("port");
        ipArray.remove(0);
    }
}
