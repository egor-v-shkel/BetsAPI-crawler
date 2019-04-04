package by.vision.betsapiparser.ProxyServices;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextResponseService implements ProxyService {
    String resp;
    public TextResponseService response(String resp){
        this.resp = resp;
        return this;
    }

    @Override
    public List<String> toList() {
        List<String> proxyArrayList = new ArrayList<>();
        proxyArrayList = Pattern.compile("((\\\\d+\\\\.){3}\\\\d+):(\\\\d+)")
                .matcher(resp)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());

        return proxyArrayList;
    }
}
