package ru.yandex.practicum;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionWordsTest {

    @Test
    void allCorrect_maskIsAllPlus() {
        ExceptionWords ew = new ExceptionWords("котик", "котик");
        assertEquals("+++++", ew.getMask());
        assertEquals("", ew.getAbsentChars());
        assertEquals(4, ew.getCorrectPositions().size());
        assertEquals(List.of(0, 4), ew.getCorrectPositions().get('к'));
    }

    @Test
    void allAbsent_maskIsAllMinus() {
        ExceptionWords ew = new ExceptionWords("абвгд", "котик");
        assertEquals("-----", ew.getMask());
        assertEquals("абвгд", ew.getAbsentChars());
        assertEquals(0, ew.getCorrectPositions().size());
        assertEquals(0, ew.getMisplacedPositions().size());
    }

    @Test
    void misplaced_maskHasCaret() {
        ExceptionWords ew = new ExceptionWords("книга", "котик");
        assertTrue(ew.getCorrectPositions().containsKey('к'));
        assertTrue(ew.getMisplacedPositions().containsKey('и'));
    }

    @Test
    void duplicateLetters_extraAreAbsent() {
        ExceptionWords ew = new ExceptionWords("ааааа", "банан");
        assertEquals("-+-+-", ew.getMask());
    }

    @Test
    void duplicateLetters_mixedMask() {
        ExceptionWords ew = new ExceptionWords("ааааб", "банан");
        assertEquals("-+-+^", ew.getMask());
    }

        @Test
        void getWord_returnsOriginal () {
            ExceptionWords ew = new ExceptionWords("книга", "котик");
            assertEquals("книга", ew.getWord());
        }
    }