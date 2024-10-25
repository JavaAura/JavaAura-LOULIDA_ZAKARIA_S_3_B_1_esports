package org.esport.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleLogger.class);

    public void displayMessage(String message) {
        LOGGER.info(message);

    }

    public void displayError(String message) {
        LOGGER.error(message);

    }
}
