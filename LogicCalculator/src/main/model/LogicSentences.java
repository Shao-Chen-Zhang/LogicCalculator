package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//Represents a list of logic sentence which are derived from one another
// examples: (1 v 2), 1, ~(1 & (7 > 3))
public class LogicSentences {
    List<String> logicSentences;

    public LogicSentences() {
        logicSentences = new ArrayList<String>();
    }

    //REQUIRES: The string must be a well formed formula of sentential logic
    //MODIFIES: this
    //EFFECTS: adds a string to logicSentences
    public void addSentence(String s) {
        logicSentences.add(s);
    }

    //EFFECTS: returns the size of logicSentences
    public int size() {
        return logicSentences.size();
    }

    //REQUIRES: int must be an index for a string in logicSentences
    //EFFECTS: returns the String with the index
    public String getSentence(int index) {
        return logicSentences.get(index);
    }


    //EFFECTS: returns logicSentences as a json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("logicSentences", logicSentencesToJson());
        return json;
    }

    //EFFECTS: returns logicSentences as a json array
    public JSONArray logicSentencesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < logicSentences.size(); i++) {
            String logicSentence = logicSentences.get(i);
            jsonArray.put(logicSentence);
        }
        return jsonArray;
    }
}
