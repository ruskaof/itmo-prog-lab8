package com.ruskaof.server.util;

import com.ruskaof.server.Server;
import org.slf4j.LoggerFactory;

public class Logger {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Server.class);

    public static void log(String message) {
        logger.info(message);
    }
}
