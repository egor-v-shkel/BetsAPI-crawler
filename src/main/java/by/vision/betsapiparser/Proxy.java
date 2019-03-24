package by.vision.betsapiparser;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class Proxy {
    private String ip;
    private int port;
    private JSONArray ipArray;
    private static final Logger LOGGER = Logger.getLogger(Proxy.class.getName());
    private final String TOO_MUCH_REQUSTS = "We have to temporarily stop you. You're requesting proxies a little too fast (2+ requests per second). Get your API to remove this limit at http://pubproxy.com/#premium";
    private final String REQUEST_LIMIT = "You reached the maximum 60 requests for today. Get your API to make unlimited requests at http://pubproxy.com/#premium";

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
                LOGGER.debug("Get proxy list exception", e);
            }
        } else {
            setProxy();
        }
    }

    public void getProxyList() throws IOException {
        boolean useURL = true;
        final String defaultURL = "http://pubproxy.com/api/proxy?format=json&type=http&https=true&last_check=60&speed=25&limit=20&user_agent=true&referer=true&country=RU,PL,UA,BY,LT,LV";
        String response = getResponse(defaultURL);
        //LOGGER.debug(response);

        //check API request limit for free users
        if (response.endsWith("premium")) {
            useURL = false;
            LOGGER.debug("Reached request limit "+response);
            response = responseFormat(getResponse("file://localhost/c:/temp/proxy_list.JSON"));
            LOGGER.debug("Using local proxy list\n"+response);
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            LOGGER.debug("Not a JSON object:");
        }
        ipArray = jsonObject.getJSONArray("data");

        if (useURL)writeProxyList(ipArray);
        setProxy();
    }

    private String responseFormat(String resp) {
        String s = resp.replaceAll("[]][\\[]", ",");
        return "{\"data\":  " + s + "}";
    }

    public String getResponse(String defaultURL) throws IOException {
        URL url = new URL(defaultURL);//&country=US,RU
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()) {
            sb.append(scanner.nextLine());
        }
        return sb.toString();
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
