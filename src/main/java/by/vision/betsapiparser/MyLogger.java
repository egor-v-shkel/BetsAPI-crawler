package by.vision.betsapiparser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyLogger {

    public static final Logger ROOT_LOGGER = LogManager.getRootLogger();
    public static final Logger RESPONSE_LOGGER = LogManager.getLogger(Proxy.class.getName());

}
