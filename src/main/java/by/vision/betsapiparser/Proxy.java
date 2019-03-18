package by.vision.betsapiparser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

class Proxy {
    private String ip;
    private int port;
    private JSONArray ipArray;

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
        setProxy();
    }

    private void setProxy() {
        JSONObject proxyObj = ipArray.getJSONObject(0);
        ip = proxyObj.getString("ip");
        port = proxyObj.getInt("port");

        try {
            writeProxy();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ipArray.remove(0);
    }

    private void writeProxy() throws IOException {
        String proxy = String.format("%s:%d\n", ip, port);
        BufferedWriter writer = new BufferedWriter(App.fileWriter);
        writer.write(proxy);
        writer.close();
    }
}
