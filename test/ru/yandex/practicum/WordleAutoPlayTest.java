package ru.yandex.practicum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleAutoPlayTest {

    private static final String LOG_FILE = "test_auto_log.txt";
    private GameLogger logger;

    @BeforeEach
    void setUp() {
        logger = new GameLogger(LOG_FILE);
    }

    @AfterEach
    void tearDown() {
        logger.deleteLogFile();
    }

    @Test
    void autoPlay_winsOnSmallDictionary() {
        List<String> words = new ArrayList<>(List.of(
                "котик", "комик", "кролик", "книга", "мышка",
                "банан", "какао", "мама", "папа", "рама"
        ));
        WordleDictionary dict = new WordleDictionary(words, logger);
        WordleGame game = new WordleGame(dict, "банан", logger);

        boolean win = game.autoPlay();

        assertTrue(win, "Автопилот должен угадать слово");
        assertTrue(game.getSteps() <= 6);
    }

    @Test
    void autoPlay_doesNotLoseSolution() {
        List<String> words = new ArrayList<>(List.of(
                "банан", "какао", "мама", "папа", "рама", "каша", "саша"
        ));
        WordleDictionary dict = new WordleDictionary(words, logger);
        WordleGame game = new WordleGame(dict, "банан", logger);

        while (!game.isFinished()) {
            String hint = game.giveHint();
            game.makeMove(hint);
            if (!game.isFinished()) {
                assertTrue(dict.contains("банан"),
                        "Ответ не должен теряться из словаря до победы");
            }
        }
        assertTrue(game.isFinished());
    }

    @Test
    void autoPlay_neverExceedsSixSteps() {
        List<String> words = new ArrayList<>(List.of(
                "банан", "какао", "мама", "папа", "рама", "каша", "саша", "вата", "хата"
        ));
        WordleDictionary dict = new WordleDictionary(words, logger);
        WordleGame game = new WordleGame(dict, "какао", logger);

        game.autoPlay();

        assertTrue(game.getSteps() <= 6);
    }
}