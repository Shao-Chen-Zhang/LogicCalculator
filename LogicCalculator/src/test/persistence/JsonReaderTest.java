package persistence;

import exceptions.EmptyListException;
import exceptions.ImpossibleVariableSetupException;
import exceptions.InvalidTruthValueException;
import model.LogicSentences;
import model.Variables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// modelled code off sample application JsonSerializationDemo
public class JsonReaderTest extends JsonTest {
    Variables testVariables;
    LogicSentences testLogicSentences;

    @BeforeEach
    void setup() {
        try {
            testVariables = new Variables();
            testVariables.setVariablesInUse(3);
            testLogicSentences = new LogicSentences();
            testLogicSentences.addSentence("0");
            testLogicSentences.addSentence("1");
            testLogicSentences.addSentence("2");

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testReaderUnableToReadFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Variables variables = reader.readVariables();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (EmptyListException e) {
            fail("IOException expected");
        } catch (InvalidTruthValueException e) {
            fail("IOException expected");
        }

        try {
            LogicSentences logicSentences = reader.readLogicSentences();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader variablesReader = new JsonReader("./data/testReaderEmptyVariables.json");
        try {
            Variables variables = variablesReader.readVariables();
            fail("EmptyListException should have been thrown");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JsonReader logicSentencesReader = new JsonReader("./data/testReaderEmptyLogicSentences.json");
        try {
            LogicSentences logicSentences = logicSentencesReader.readLogicSentences();
            assertEquals(0, logicSentences.size());
        } catch (Exception e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader variablesReader = new JsonReader("./data/testReaderGeneralVariables.json");
        try {
            Variables variables = variablesReader.readVariables();
            checkSameTruthValuesEachVariable(testVariables, variables);
        } catch (Exception e) {
            fail("Couldn't read from file");
        }

        JsonReader logicSentencesReader = new JsonReader("./data/testReaderGeneralLogicSentences.json");
        try {
            LogicSentences logicSentences = logicSentencesReader.readLogicSentences();
            checkSameLogicSentences(testLogicSentences, logicSentences);
        } catch (Exception e) {
            fail("Couldn't read from file");
        }
    }
}

