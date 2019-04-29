package by.vision.betsapicrawler;

import by.vision.betsapicrawler.ProxyProvider.Proxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyLogger {

    public static final Logger ROOT_LOGGER = LogManager.getRootLogger();
    public static final Logger RESPONSE_LOGGER = LogManager.getLogger(Proxy.class.getName());
    public static final Logger STDOUT_LOGGER = LogManager.getLogger("STDOUT");

}
