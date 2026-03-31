package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordleGameTest {
    @Test
    void testProcessGuess() {
        WordleDictionary dict = new WordleDictionary();
        dict.setWords(Set.of("абвгд", "бевса", "иииии"));

        WordleGame game = new WordleGame(dict);
        game.setAnswer("абвгд");

        assertEquals("+++++", game.processGuess("абвгд"));
        assertEquals("^-+-^", game.processGuess("бевса"));
        assertEquals("-----", game.processGuess("иииии"));
    }
}
