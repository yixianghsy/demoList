package com.hsy;

import com.hsy.iterator.FilmMenu;
import com.hsy.iterator.MainMenu;
import com.hsy.iterator.TVChanneMenu;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IteratorTest {


    @Test
    void  contextLoads() {
        TVChanneMenu tvMenu = new TVChanneMenu();
        FilmMenu filmMenu = new FilmMenu();

        MainMenu mainMenu = new MainMenu(tvMenu, filmMenu);
        mainMenu.printMenu();
    }
}
