package by.vision.betsapicrawler.ProxyProvider;

import java.util.List;

public interface ProxyProvider {
    List<String> toList();
    void refresh();
    String getIP();
    int getPort();
}
