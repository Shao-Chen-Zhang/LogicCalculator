package persistence;

import model.LogicSentences;
import model.Variables;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// modelled code off sample application JsonSerializationDemo
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Variables v1 = new Variables();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyVariables.json");
            writer.open();
            writer.write(v1);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyVariables.json");
            Variables v2 = reader.readVariables();
            checkSameTruthValuesEachVariable(v1, v2);

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }

        try {
            LogicSentences ls1 = new LogicSentences();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyLogicSentences.json");
            writer.open();
            writer.write(ls1);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLogicSentences.json");
            LogicSentences ls2 = reader.readLogicSentences();
            checkSameLogicSentences(ls1, ls2);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Variables v1 = new Variables();
            v1.setVariablesInUse(5);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralVariables.json");
            writer.open();
            writer.write(v1);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralVariables.json");
            Variables v2 = reader.readVariables();
            checkSameTruthValuesEachVariable(v1, v2);

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }

        try {
            LogicSentences ls1 = new LogicSentences();
            for (int i = 0; i < 6; i++) {
                ls1.addSentence(Integer.toString(i));
            }
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLogicSentences.json");
            writer.open();
            writer.write(ls1);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLogicSentences.json");
            LogicSentences ls2 = reader.readLogicSentences();
            checkSameLogicSentences(ls1, ls2);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
