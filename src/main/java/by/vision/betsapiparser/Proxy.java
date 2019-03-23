package by.vision.betsapiparser;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Proxy {
    private String ip;
    private int port;
    private JSONArray ipArray;
    private static final Logger LOGGER = Logger.getLogger(Proxy.class);
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
        //TODO rewrite this part
        final String SPEC = "http://pubproxy.com/api/proxy?format=json&type=http&https=true&last_check=60&speed=25&limit=20&user_agent=true&referer=true&country=RU";
        final String toolurProxyUS1 = "https://proxy-us1.toolur.com/browse.php?u=H64Fx1RqY45Zkf6CljJo3pVtDAqFk5MSAWyyyUaZJVhHeJtKYLI696OwPDw2D0%2BRH1ajFepmUUqu1dch7mwQILiBw4%2Bb5jbkhRlnnIRuFeMZ1sYcxdfym3E3wXSmbEEuJouvCWkawEeHlqfFS9VSRj7USK5GaymFR0RcNtLYEglwTg%3D%3D&b=1&f=norefer";
        final String toolurProxyUS2 = "https://proxy-us2.toolur.com/browse.php?u=dj9SykCb4%2FTDTQeZnkd39HsRNBbL9bZRfvKbntgdvM4k89SV%2BxNcVwGZJU8rBPQZIwVwSgBWWJouCd8KY6dOVCHK7cNskHkl0cwEvD7JoV%2ByuxZ2XjQHVlHGig%2B02fil2Z1Oo6%2FgexxFybZRlgBm%2FiuWFL1eAHkBHlipwjVhar4Ihg%3D%3D&b=1&f=norefer";
        final String toolurProxyUS3 = "https://proxy-us3.toolur.com/browse.php?u=2qZ3jFhHKf3iiHcYQ%2BuoKlkGK67DGAsFPsgHuIio3bbZ5g0Pg4D3aQmzvJhi8DBrXFEKlbrs3K8FQyTcbGPncoapfBjHtEpfbOXr5Dqr3ac6Y6PW%2FQPOdQIQ3qr%2BxidZPirQB7u13U4WJ3ui9BGgvWzHRe5ynkaTFmV59%2FrdOsNe5A%3D%3D&b=1&f=norefer";
        final String toolurProxyUS4 = "https://proxy-us4.toolur.com/browse.php?u=INLHElvGJDavFnpK8zHkxqC17c1BERQI01PgeV7LMJGZY%2BsvFl0USnBM8SjVRsS9%2BPpfbvqO%2BIAe61akNLrcS1k2jJkVBVN2ojGrjdBudkssm3mvtzYhrvG45YcG6GSBBfT3NBtIA53st4DbAKs0ivd%2BIPF6E79WTQCHpysGbiEC8w%3D%3D&b=1&f=norefer";
        final String toolurProxyUS5 = "https://proxy-us5.toolur.com/browse.php?u=1fU8WECWK5gpXK4%2BPwcKOp7Z556NJY4y3SoKT5GgMuyUFqVpXArbD0y%2B2mcJept9X%2BUCBCDzsmBR94%2BxtMzlBtxKfG%2F079gphyTpL37HETazJ5NIgNp7nOuUrz116tdOvz3q5eOQjvrM8BZURjemRAH7XGr2u03OrQeZjgeoOZCrwg%3D%3D&b=1&f=norefer";
        final String toolurProxyUS6 = "https://proxy-us6.toolur.com/browse.php?u=uH3jhQBM8ukM1E1SZyfFrtFP%2FDakEoiewE6nyJngW5Cda%2BPfF8iMYm5u8WUlOUxhXbcDb%2FcqTweexTFltwdf11BbCC7sfcpKLV669kwv6loxZXYd1GCwrPmKupeJuIkevr%2BPhz5ipzctPMYDF6d2RKCr%2FAYKjth8xbr2xVKLPaLfIA%3D%3D&b=1&f=norefer";
        final String toolurProxyUS7 = "https://proxy-us7.toolur.com/browse.php?u=JdnavUmlprVwizrJD8e1z9nbRItPYYDYAHVYn2wy60GCJef8iLWaBO1hlY4XNRmGYeRW2k8XFKsP3%2B6nyF6lfreVrhT%2Bw7n%2BkuUuWRFkgJnebwKrW0SAQU%2BZueURatiWimDlf0eEOwAQxNssGMJjDRr5d2IwhFN%2F1i7Qpv3PkjxyrA%3D%3D&b=1&f=norefer";
        final String toolurProxyEU1 = "https://proxy-de1.toolur.com/browse.php?u=0KzKqRocBfDthDz3bHFpzgJrHgyCJnT%2FqsRUzvzV49kTYMMWJsDUx%2FsAJfxDtVPtKYs%2BAJ%2BSAROOBxTd1jbPaMReLTZuU0oM2iIt0CPXJPf4i7g0IHvs5jvh1rqSsCl6ArElrYKJX3VAnEvk4TxTN2GHEip6Puv65Vr70trDeom9vg%3D%3D&b=1&f=norefer";
        final String toolurProxyEU2 = "https://proxy-fr1.toolur.com/browse.php?u=O8JiE8yNK78Pts4Xj4Yk46MHO5qSL80FS0T23Jotc8iaKnNlxLiZWEOARn6jUCCjg4rFoK0wwBaeSrUGgfBTfh0l2EbN9EvOBcLMCrsM6UUHlUpdDL1JX9As91mbhOmqK7elDvZyFYLA4heW9RoQ50qvXa%2FFgmZFQYqJ6ZHs4iYMHw%3D%3D&b=1&f=norefer";
        URL url = new URL(SPEC);//&country=US,RU
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNext()) {
            sb.append(scanner.nextLine());
        }
        String response = sb.toString();

        //check API request limit for free users
        assert response != null;
        if (response.endsWith("premium")) {
            useCachedJSON();
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            LOGGER.debug("Not a JSON object:" + response);
        }
        ipArray = jsonObject.getJSONArray("data");

        writeProxyList(ipArray);
        setProxy();
    }

    private void useCachedJSON() {

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
