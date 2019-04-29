package by.vision.betsapicrawler.ProxyProvider;

import java.util.ArrayList;
import java.util.List;

public class MyProxyProvider implements ProxyProvider {
    private String currentIP;
    private int currentPort;
    private ArrayList<String> servicesList;

    public void addService(String service){
        servicesList.add(service);
    }

    String resp;
    public MyProxyProvider response(String resp){
        this.resp = resp;
        return this;
    }

    @Override
    public List<String> toList() {
        List<String> proxyArrayList = new ArrayList<>();/*
        proxyArrayList = Pattern.compile("((\\d{1,3}\\.){3}\\d{1,3}):(\\d{1,5})")
                .matcher(resp)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());*/

        return proxyArrayList;
    }

    @Override
    public void refresh() {

    }

    @Override
    public String getIP() {
        return currentIP;
    }

    @Override
    public int getPort() {
        return currentPort;
    }
}
