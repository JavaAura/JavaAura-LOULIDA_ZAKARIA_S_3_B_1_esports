package org.esport.presentation;

import org.esport.presentation.menu.MainMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Console {
    private static final Logger LOGGER = LoggerFactory.getLogger(Console.class);

    public static void main(String[] args) {
        LOGGER.info("Démarrage de l'application");
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        MainMenu mainMenu = context.getBean(MainMenu.class);
        mainMenu.displayMainMenu();
        LOGGER.info("Fermeture de l'application");
    }
}
