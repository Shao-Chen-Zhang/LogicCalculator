package model;

import exceptions.EmptyListException;
import exceptions.ImpossibleVariableSetupException;
import exceptions.InvalidTruthValueException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a list of variable that will store their truth values (1's and 0's)
public class Variables {
    private List<Variable> variables;

    //REQUIRES: numOfVariable >= 1
    //EFFECTS: Creates a list of variables from A-Z and an empty list of variables
    public Variables() {
        variables = new ArrayList<Variable>();
    }

    //REQUIRES: 1 <= numTotalVariables <= 26
    //MODIFIES: this
    //EFFECTS: Adds numTotalVariables variables to "variables"
    // creates and sets the first variable in variables using setVariable(numTotalVariables, numTotalVariables)
    // creates and sets the second variable in variables using setVariable(numTotalVariables, numTotalVariables - 1)
    // creates and sets the third variable in variables using setVariable(numTotalVariables, numTotalVariables - 2)
    // until (numTotalVariables - n) = 1
    // Now the variable that have been set will cover every combination of 1's and 0's that are
    // numTotalVariables long by matching the 1's and 0's of each set variable according to their index
    public void setVariablesInUse(int numTotalVariables) throws ImpossibleVariableSetupException {
        if (!(1 <= numTotalVariables && numTotalVariables <= 26)) {
            throw new ImpossibleVariableSetupException("The number of variables in the logic statement "
                    + "must be within 1 and 26");
        }
        for (int i = 0; i <= numTotalVariables - 1; i++) {
            Variable v = new Variable();
            variables.add(v);
            getVariable(i).setVariable(numTotalVariables, numTotalVariables - i);
        }
    }

    //getters
    //EFFECTS: returns variables as a list of variables
    public List<Variable> getVariables() {
        List<Variable> varsList = new ArrayList<Variable>();
        for (Variable v : variables) {
            varsList.add(v);
        }
        return varsList;
    }

    public Variable getVariable(int index) {
        return variables.get(index);
    }


    //EFFECTS: returns the size of variables
    public int size() {
        return variables.size();
    }


    //MODIFIES: this
    //EFFECTS: returns a new variable that reverses all the 1's to 0 and 0's to 1 in variable x
    public Variable not(Variable x) throws EmptyListException, InvalidTruthValueException {
        Variable v = new Variable();
        for (int i : x.getTruthVals()) {
            if (i == 1) {
                v.addTruthVal(0);
            } else {
                v.addTruthVal(1);
            }
        }
        return v;
    }

    //REQUIRES: x and y must have the same number of elements
    //MODIFIES: this
    //EFFECTS: returns a new variable with the same number of elements as x and y
    // that has an element of the value 1 if at the same index x and y are also 1
    // otherwise 0 at that index
    public Variable and(Variable x, Variable y) throws EmptyListException, InvalidTruthValueException {
        Variable v = new Variable();
        List<Integer> truthValsX = x.getTruthVals();
        List<Integer> truthValsY = y.getTruthVals();
        for (int i = 0; i < x.varSize(); i++) {
            if ((truthValsX.get(i) == 1) && (truthValsY.get(i) == 1)) {
                v.addTruthVal(1);
            } else {
                v.addTruthVal(0);
            }
        }
        return v;
    }

    //REQUIRES: x and y must have the same number of elements
    //MODIFIES: this
    //EFFECTS: returns a new variable with the same number of elements as x and y
    // that has an element of the value 1 if at the same index x or y (or both) are also 1
    // otherwise 0 at that index
    public Variable or(Variable x, Variable y) throws EmptyListException, InvalidTruthValueException {
        Variable v = new Variable();
        List<Integer> truthValsX = x.getTruthVals();
        List<Integer> truthValsY = y.getTruthVals();
        for (int i = 0; i < x.varSize(); i++) {
            if ((truthValsX.get(i) == 1) || (truthValsY.get(i) == 1)) {
                v.addTruthVal(1);
            } else {
                v.addTruthVal(0);
            }
        }
        return v;
    }


    //REQUIRES: x and y must have the same number of elements
    //MODIFIES: this
    //EFFECTS: returns a new variable with the same number of elements as x and y
    // that has an element of the value 0 if at the same index x is 1 and y is 0
    // otherwise 1 at that index
    public Variable ifThen(Variable x, Variable y) throws EmptyListException, InvalidTruthValueException {
        Variable v = new Variable();
        List<Integer> truthValsX = x.getTruthVals();
        List<Integer> truthValsY = y.getTruthVals();
        for (int i = 0; i < x.varSize(); i++) {
            if ((truthValsX.get(i) == 1) && (truthValsY.get(i) == 0)) {
                v.addTruthVal(0);
            } else {
                v.addTruthVal(1);
            }
        }
        return v;
    }

    //REQUIRES: x and y must have the same number of elements
    //MODIFIES: this
    //EFFECTS: returns a new variable with the same number of elements as x and y
    // that has an element of the value 1 if at the same index x and y have the same value
    // (either x and y are both 1 or 0), otherwise 0 at that index
    public Variable ifAndOnlyIf(Variable x, Variable y) throws EmptyListException, InvalidTruthValueException {
        Variable v = new Variable();
        List<Integer> truthValsX = x.getTruthVals();
        List<Integer> truthValsY = y.getTruthVals();
        for (int i = 0; i < x.varSize(); i++) {
            if ((truthValsX.get(i) == truthValsY.get(i))) {
                v.addTruthVal(1);
            } else {
                v.addTruthVal(0);
            }
        }
        return v;
    }

    //MODIFIES: this
    //EFFECTS: adds a new variable to variables
    // and assigns the new variable to have the truth values of variable v
    public void assignNewVariable(Variable v) throws EmptyListException, InvalidTruthValueException {
        Variable v2 = new Variable();
        variables.add(v2);
        variables.get(variables.size() - 1).setTruthVals(v.getTruthVals());
    }

    //MODIFIES: this
    //EFFECTS: adds a variable to variables
    public void addVariable(Variable v) {
        variables.add(v);
    }

    //EFFECTS: returns true if variables if empty, false otherwise
    public boolean isEmpty() {
        return variables.isEmpty();
    }

    //EFFECTS: returns variables as a json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("variables", variablesToJson());
        return json;
    }

    //EFFECTS: returns all the elements of variables
    //         as a JSON array
    private JSONArray variablesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Variable v : variables) {
            jsonArray.put(v.variableToJson());
        }

        return jsonArray;
    }

}
