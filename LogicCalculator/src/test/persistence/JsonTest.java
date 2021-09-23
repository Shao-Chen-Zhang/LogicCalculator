package persistence;

import exceptions.EmptyListException;
import model.LogicSentences;
import model.Variables;

import java.util.function.LongFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// modelled code off sample application JsonSerializationDemo
public class JsonTest {
    //REQUIRES: Both Variables must have the same number of elements
    //EFFECTS: checks that both Variables have the same truth values (int) for each variable in the same order
    protected void checkSameTruthValuesEachVariable(Variables expected, Variables actual) {
        try {
            for (int i = 0; i < expected.size(); i++) {
                assertEquals(expected.getVariable(i).getTruthVals(), actual.getVariable(i).getTruthVals());
            }
        } catch (EmptyListException e) {
            fail(e.getMessage());
        }
    }

    //REQUIRES: Both LogicSentences must have the same number of elements
    //EFFECTS: checks that both LogicSentences have the same logic sentences (strings) in the same order
    protected void checkSameLogicSentences(LogicSentences expected, LogicSentences actual) {
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.getSentence(i), actual.getSentence(i));
        }
    }
}
