package persistence;

import exceptions.EmptyListException;
import exceptions.InvalidTruthValueException;
import model.LogicSentences;
import model.Variable;
import model.Variables;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// Heavily modelled code off sample application JsonSerializationDemo
// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads Variables from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Variables readVariables() throws IOException, EmptyListException, InvalidTruthValueException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseVariables(jsonObject);
    }

    // EFFECTS: reads LogicSentences from file and returns it;
    // throws IOException if an error occurs reading data from file
    public LogicSentences readLogicSentences() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLogicSentences(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses variables from JSON object and returns it
    private Variables parseVariables(JSONObject jsonObject) throws EmptyListException, InvalidTruthValueException {
        Variables variables = new Variables();
        jsonAddVariables(variables, jsonObject);
        return variables;
    }

    // MODIFIES: variables
    // EFFECTS: parses a list of variable from JSON object and adds them to variables
    private void jsonAddVariables(Variables variables, JSONObject jsonObject)
            throws EmptyListException, InvalidTruthValueException {
        JSONArray jsonVariables = jsonObject.getJSONArray("variables");
        for (Object jsArray : jsonVariables) {
            JSONArray jsonVariable = (JSONArray) jsArray;
            variables.assignNewVariable(jsonAddVariable(jsonVariable));
        }
    }

    // MODIFIES: variable
    // EFFECTS: parses integers from jsonVariable and adds them to variable
    //          variable is then returned
    private Variable jsonAddVariable(JSONArray jsonVariable)
            throws InvalidTruthValueException {
        int length = jsonVariable.length();
        Variable variable = new Variable();
        for (int i = 0; i < length; i++) {
            variable.addTruthVal(Integer.parseInt(jsonVariable.get(i).toString()));
        }
        return variable;
    }


    // EFFECTS: parses logicSentences from JSON object and returns it
    private LogicSentences parseLogicSentences(JSONObject jsonObject) {
        LogicSentences logicSentences = new LogicSentences();
        jsonAddLogicSentence(logicSentences, jsonObject);
        return logicSentences;
    }

    // MODIFIES: logicSentences
    // EFFECTS: parses a list of string from JSON object and adds them to logicSentences
    private void jsonAddLogicSentence(LogicSentences logicSentences, JSONObject jsonObject) {
        JSONArray jsonLogicSentences = jsonObject.getJSONArray("logicSentences");
        int length = jsonLogicSentences.length();
        for (int i = 0; i < length; i++) {
            logicSentences.addSentence(jsonLogicSentences.get(i).toString());
        }
    }
}
