package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLogicSentences {
    private LogicSentences testLogicSentences1;
    private LogicSentences testLogicSentences2;
    private LogicSentences testLogicSentences3;

    @BeforeEach
    void setup() {
        testLogicSentences1 = new LogicSentences();
        testLogicSentences2 = new LogicSentences();
        testLogicSentences3 = new LogicSentences();
    }

    @Test
    void testLogicSentences() {
        assertEquals(0, testLogicSentences1.size());
    }

    @Test
    void testAddSentence() {
        testLogicSentences1.addSentence("0");
        testLogicSentences2.addSentence("1");
        testLogicSentences2.addSentence("2");

        assertEquals(1, testLogicSentences1.size());
        assertEquals("0", testLogicSentences1.getSentence(0));
        assertEquals(2, testLogicSentences2.size());
        assertEquals("1", testLogicSentences2.getSentence(0));
        assertEquals("2", testLogicSentences2.getSentence(1));
    }

    @Test
    void testSize() {
        testLogicSentences2.addSentence("0");
        testLogicSentences3.addSentence("1");
        testLogicSentences3.addSentence("2");

        assertEquals(0, testLogicSentences1.size());
        assertEquals(1, testLogicSentences2.size());
        assertEquals(2, testLogicSentences3.size());
    }

    @Test
    void testGetSentence() {
        testLogicSentences1.addSentence("01");
        testLogicSentences1.addSentence("12");
        testLogicSentences1.addSentence("23");

        assertEquals("01", testLogicSentences1.getSentence(0));
        assertEquals("12", testLogicSentences1.getSentence(1));
        assertEquals("23", testLogicSentences1.getSentence(2));
    }
}
