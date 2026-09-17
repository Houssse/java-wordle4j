package ru.yandex.practicum;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exception.InvalidWordLengthException;
import ru.yandex.practicum.exception.WordNotFoundInDictionary;
import ru.yandex.practicum.exception.WordleGameException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {

    private static final String LOG_FILE = "test_game_log.txt";
    private GameLogger logger;

    @BeforeEach
    void setUp() {
        logger = new GameLogger(LOG_FILE);
    }

    @AfterEach
    void tearDown() {
        logger.deleteLogFile();
    }

    private WordleGame newGame(String answer, String... words) {
        WordleDictionary dict = new WordleDictionary(new ArrayList<>(List.of(words)), logger);
        return new WordleGame(dict, answer, logger);
    }

    @Test
    void makeMove_correctWord_finishesGame() {
        WordleGame game = newGame("банан", "банан", "котик", "книга", "мышка");

        String result = game.makeMove("банан");

        assertTrue(game.isFinished());
        assertTrue(result.contains("банан"));
    }

    @Test
    void makeMove_wrongLength_throws() {
        WordleGame game = newGame("банан", "банан", "котик");

        assertThrows(InvalidWordLengthException.class, () -> game.makeMove("кот"));
    }

    @Test
    void makeMove_wordNotInDictionary_throws() {
        WordleGame game = newGame("банан", "банан", "котик");

        assertThrows(WordNotFoundInDictionary.class, () -> game.makeMove("абвгд"));
    }

    @Test
    void makeMove_duplicateLetters_maskIsCorrect() {
        WordleGame game = newGame("банан", "банан", "котик");

        assertEquals("-+-+-", game.toChar("ааааа"));
    }

    @Test
    void makeMove_normalizesInput() {
        WordleGame game = newGame("банан", "банан", "котик");

        String result = game.makeMove("БАНАН");

        assertTrue(game.isFinished());
        assertTrue(result.contains("банан"));
    }

    @Test
    void makeMove_yoReplacedWithE() {
        WordleGame game = newGame("ежика", "ежика", "банан");

        String result = game.makeMove("ёжика");

        assertTrue(game.isFinished());
        assertTrue(result.contains("ежика"));
    }

    @Test
    void makeMove_repeatedInput_throws() {
        WordleGame game = newGame("банан", "банан", "котик", "книга");

        game.makeMove("котик");

        WordleGameException e = assertThrows(WordleGameException.class,
                () -> game.makeMove("котик"));

        assertTrue(e.getMessage().contains("уже вводили"),
                "Ожидалось сообщение о повторе, а получили: " + e.getMessage());
    }

    @Test
    void makeMove_afterFinish_throws() {
        WordleGame game = newGame("банан", "банан", "котик");

        game.makeMove("банан");

        assertThrows(WordleGameException.class, () -> game.makeMove("котик"));
    }

    @Test
    void steps_increaseAfterEachMove() {
        WordleGame game = newGame("банан",
                "банан", "батон", "барин", "какао", "мама");

        game.makeMove("батон");
        game.makeMove("барин");

        assertEquals(2, game.getSteps());
    }

    @Test
    void sixWrongAttempts_finishesGame() {

        WordleGame game = newGame("банан",
                "банан", "ванан", "ганан", "данан", "жанан", "занан", "канан");

        String[] guesses = {"ванан", "ганан", "данан", "жанан", "занан", "канан"};
        for (String guess : guesses) {
            if (game.isFinished()) break;
            game.makeMove(guess);
        }

        assertTrue(game.isFinished(), "Игра должна завершиться после 6 ходов");
        assertEquals(6, game.getSteps());
    }

    @Test
    void hint_isNotAMove() {
        WordleGame game = newGame("банан", "банан", "котик", "книга");

        int before = game.getSteps();
        game.giveHint();

        assertEquals(before, game.getSteps());
    }

    @Test
    void hint_doesNotRepeat() {
        WordleGame game = newGame("банан", "банан", "котик", "книга", "мышка");

        String h1 = game.giveHint();
        String h2 = game.giveHint();

        assertNotEquals(h1, h2);
    }
}