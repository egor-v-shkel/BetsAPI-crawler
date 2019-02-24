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
        URL url = new URL("http://pubproxy.com/api/proxy?format=json&type=http&https=true&last_check=60&speed=25&limit=20&user_agent=true&referer=true");
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
    }

    public String getIp() throws IOException {
        if (firstTimeCall){
            getNewProxyArray();
            ipArray.remove(0);
            firstTimeCall = false;
        } else {
            renew();
        }
        return ip;
    }

    public int getPort() {
        return port;
    }
}
