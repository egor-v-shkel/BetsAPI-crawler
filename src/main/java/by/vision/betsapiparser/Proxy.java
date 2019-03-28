package by.vision.betsapiparser;

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
                MyLogger.RESPONSE_LOGGER.info("Get proxy list exception", e);
            }
        } else {
            setProxy();
        }
    }

    public void getProxyList() throws IOException {
        boolean useURL = true;
        final String defaultURL = "http://pubproxy.com/api/proxy?format=json&type=http&https=true&last_check=60&speed=25&limit=20&user_agent=true&referer=true&country=RU,PL,UA,BY,LT,LV";
        String response = getResponse(defaultURL);
        MyLogger.RESPONSE_LOGGER.debug(response);

        //check API request limit for free users
        if (response.endsWith("premium")) {
            useURL = false;
            MyLogger.RESPONSE_LOGGER.debug("Reached request limit\n"+response);
            response = responseFormat(getResponse("file://localhost/c:/temp/proxy_list.JSON"));
            MyLogger.RESPONSE_LOGGER.debug("Using local proxy list\n"+response);
        } else MyLogger.RESPONSE_LOGGER.debug("Using parsed proxy list\n"+response);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            MyLogger.RESPONSE_LOGGER.warn("Not a JSON object:");
        }
        ipArray = jsonObject.getJSONArray("data");

        if (useURL)writeProxyList(ipArray);
        setProxy();
    }

    /**
     * This method concatenate all parsed arrays into single one
     * @param resp
     * @return
     */
    private String responseFormat(String resp) {
        String s = resp.replaceAll("[]][\\[]", ",");
        return "{\"data\":  " + s + "}";
    }

    public String getResponse(String defaultURL) throws IOException {
        URL url = new URL(defaultURL);
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
        File file = new File("c:/temp/proxy_list.JSON");
        try {
            if(file.createNewFile()) {
                MyLogger.ROOT_LOGGER.debug("JSON file was created");
            }
        } catch (IOException e) {
            MyLogger.ROOT_LOGGER.warn("Creating new file exception", e);
        }
        JSONArray jsArr = new JSONArray(file);
        /*for (JSONObject jObj:jsArr
             ) {

        }*/


        //ipArray.put();
        FileWriter fileWriter = new FileWriter("c:/temp/proxy_list.JSON", true);
        int indentFactor = 2;
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write(ipArray.toString(indentFactor));
        writer.close();
    }
}
