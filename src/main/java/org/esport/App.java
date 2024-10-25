package org.esport;

import org.esport.presentation.menu.MainMenu;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        MainMenu mainMenu = context.getBean(MainMenu.class);

        // Get the H2 web server and log its URL
        org.h2.tools.Server h2WebServer = context.getBean("h2WebServer", org.h2.tools.Server.class);
        String h2ConsoleUrl = h2WebServer.getURL();
        LOGGER.info("H2 Console URL: {}", h2ConsoleUrl);

        mainMenu.displayMainMenu();
    }
}
