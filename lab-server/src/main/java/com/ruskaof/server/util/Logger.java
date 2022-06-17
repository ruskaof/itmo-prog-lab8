package com.ruskaof.server.util;

import com.ruskaof.server.Server;
import org.slf4j.LoggerFactory;

public final class Logger {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Server.class);
    private Logger() {

    }

    public static void log(String message) {
        LOGGER.info(message);
    }
}
