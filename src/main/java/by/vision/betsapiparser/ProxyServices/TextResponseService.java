package by.vision.betsapiparser.ProxyServices;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public interface TextResponseService extends ProxyService {
    default ArrayList<Proxy> toList(String response){
        ArrayList<Proxy> proxyArrayList = new ArrayList<>();
        proxyArrayList = Pattern.compile("your regex here")
                .matcher(response)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());

        return proxyArrayList;
    }
}
