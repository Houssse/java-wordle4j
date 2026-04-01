package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordleGameTest {
    @Test
    void testProcessGuess() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter log = new PrintWriter(stringWriter);

        WordleDictionary dict = new WordleDictionary(log);
        dict.setWords(Set.of("абвгд", "бевса", "иииии"));

        WordleGame game = new WordleGame(dict, log);
        game.setAnswer("абвгд");

        assertEquals("+++++", game.processGuess("абвгд"));
        assertEquals("^-+-^", game.processGuess("бевса"));
        assertEquals("-----", game.processGuess("иииии"));
    }
}
