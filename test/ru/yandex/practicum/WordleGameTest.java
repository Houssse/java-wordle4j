package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {
    private WordleGame game;
    private GameLogger logger;

    @BeforeEach
    void setUp() {
        logger = new GameLogger("test_log.txt");
        WordleDictionary dict = new WordleDictionary(new ArrayList<>(List.of("котик", "книга", "мышка")));
        game = new WordleGame(logger, dict, "котик");
    }

    @Test
    void checkAnswer_correctWord_finishesGame() {
        String result = game.checkAnswer("котик");

        assertTrue(game.isFinished());
        assertTrue(result.contains("котик"));
    }

    @Test
    void checkAnswer_wrongLength_throwsException() {
        assertThrows(WordleGameException.class, () -> game.checkAnswer("кот"));
    }

    @Test
    void checkAnswer_wrongWord_returnsMask() {
        String result = game.checkAnswer("книга");

        assertEquals("+-^--", result);
        assertFalse(game.isFinished());
    }

    @Test
    void checkAnswer_sixWrongAttempts_finishesGame() {
        for (int i = 0; i < 6; i++) {
            try {
                game.checkAnswer("абвгд");
            } catch (WordleGameException ignored) {
            }
        }

        assertTrue(game.isFinished());
    }

    @Test
    void checkAnswer_normalizesInput() {
        String result = game.checkAnswer("КОТИК");

        assertTrue(game.isFinished());
        assertTrue(result.contains("котик"));
    }

    @Test
    void checkAnswer_yoReplacedWithE() {
        WordleDictionary dict = new WordleDictionary(new ArrayList<>(List.of("ёжика")));
        WordleGame g = new WordleGame(logger, dict, "ежика");

        String result = g.checkAnswer("ёжика");

        assertTrue(g.isFinished());
    }

    @Test
    void toChar_correctMask() {
        String mask = game.toChar("книга");

        assertEquals("+-^--", mask);
    }

    @Test
    void getSteps_increasesAfterEachAttempt() {
        try {
            game.checkAnswer("книга");
        } catch (WordleGameException ignored) {
        }
        try {
            game.checkAnswer("мышка");
        } catch (WordleGameException ignored) {
        }

        assertEquals(2, game.getSteps());
    }
}