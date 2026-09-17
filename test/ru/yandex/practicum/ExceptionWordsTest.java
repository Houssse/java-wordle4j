package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionWordsTest {

    @Test
    void constructor_allLettersCorrect_charPositionFilled() {
        ExceptionWords ew = new ExceptionWords("котик", "котик");

        assertEquals(0, ew.getChars().length());
        assertEquals(0, ew.getMisplacements().size());
        assertEquals(4, ew.getCharPosition().size());

        assertEquals(List.of(0, 4), ew.getCharPosition().get('к'));
        assertEquals(List.of(1), ew.getCharPosition().get('о'));
        assertEquals(List.of(2), ew.getCharPosition().get('т'));
        assertEquals(List.of(3), ew.getCharPosition().get('и'));
    }

    @Test
    void constructor_noMatchingLetters_charsFilled() {
        ExceptionWords ew = new ExceptionWords("абвгд", "котик");

        assertEquals(0, ew.getCharPosition().size());
        assertEquals(0, ew.getMisplacements().size());
        assertEquals("абвгд", ew.getChars());
    }

    @Test
    void constructor_letterInWrongPlace_misplacementsFilled() {
        ExceptionWords ew = new ExceptionWords("книга", "котик");


        assertTrue(ew.getChars().contains("н"));
        assertTrue(ew.getChars().contains("г"));
        assertTrue(ew.getChars().contains("а"));
        assertTrue(ew.getCharPosition().containsKey('к'));
        assertTrue(ew.getMisplacements().containsKey('и'));
    }

    @Test
    void getWord_returnsOriginalWord() {
        ExceptionWords ew = new ExceptionWords("книга", "котик");
        assertEquals("книга", ew.getWord());
    }
}