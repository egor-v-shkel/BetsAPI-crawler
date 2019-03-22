package by.vision.betsapiparser;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

class Proxy {
    private String ip;
    private int port;
    private JSONArray ipArray;
    private static final Logger LOGGER = Logger.getLogger(Proxy.class);

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public void refresh() {
        if (ipArray.isEmpty()) {
            try {
                getProxyList();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            setProxy();
        }
    }

    public void getProxyList() throws IOException {
        //TODO rewrite this part
        URL url = new URL("http://pubproxy.com/api/proxy?format=json&type=http&https=true&last_check=60&speed=25&limit=20&user_agent=true&referer=true&country=RU");//&country=US,RU
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()) {
            sb.append(scanner.nextLine());
        }
        String response = sb.toString();

        System.out.println("New JSON was taken");

        JSONObject jsonObject = new JSONObject(response);
        ipArray = jsonObject.getJSONArray("data");

        writeProxyList(ipArray);
        setProxy();
    }

    private void setProxy() {
        JSONObject proxyObj = ipArray.getJSONObject(0);
        ip = proxyObj.getString("ip");
        port = proxyObj.getInt("port");
        ipArray.remove(0);
    }

    private void writeProxyList(JSONArray ipArray) throws IOException {
        FileWriter fileWriter = new FileWriter("c:/temp/proxy_list.JSON", true);
        int indentFactor = 2;
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write(ipArray.toString(indentFactor));
        writer.close();
    }
}
