package model;

import exceptions.EmptyListException;
import exceptions.ImpossibleVariableSetupException;
import exceptions.InvalidTruthValueException;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

// Represents a variable that will be used in the logic statement
public class Variable {
    private List<Integer> variable;

    //EFFECTS: Creates a new empty list of integers that will be used to represent
    //the truth values of that variable (1's and 0's)
    public Variable() {
        variable = new ArrayList<Integer>();

    }

    //MODIFIES: this
    //EFFECTS: sets the Variable to have 2^numTotalVariables elements of 1's and 0's,
    // 2^(numThisVariable - 1) indicates how many of the same elements in a row (either 1 or 0)
    // before switching to the other element (first element in list is always 1)
    // eg. if numThisVariable is 3: 1,1,1,1,0,0,0,0,1,1,1,1,... (until it reaches the size of 2^numTotalVariables)
    // if numTotalVariable >= 1, 1 <= numThisVariable <= numTotalVariables is not satisfied
    // throw ImpossibleVariableSetupException
    public void setVariable(int numTotalVariables, int numThisVariable) throws ImpossibleVariableSetupException {
        if (!((numTotalVariables >= 1) && (1 <= numThisVariable) && (numThisVariable <= numTotalVariables))) {
            throw new ImpossibleVariableSetupException("The setup of this Variable did not satisfy:"
                    + " numTotalVariable >= 1, 1 <= numThisVariable <= numTotalVariables");
        } else {
            for (int j = 1; j <= Math.pow(2, numTotalVariables - numThisVariable); j++) {
                for (int i = 1; i <= Math.pow(2, numThisVariable - 1); i++) {
                    variable.add(1);
                }
                for (int i = 1; i <= Math.pow(2, numThisVariable - 1); i++) {
                    variable.add(0);
                }
            }
        }
    }

    //EFFECTS: returns the elements of variable as a string with a space between each element
    // getTruthValsString() modelled from https://www.javacodeexamples.com/convert-list-to-string-java-example/529
    // if variable is empty throw EmptyListException
    public String getTruthValsString() throws EmptyListException {
        if (variable.isEmpty()) {
            throw new EmptyListException("Variable is empty");
        } else {
            String truthValues = variable.toString();
            truthValues = truthValues.replaceAll(", ", " ").replaceAll("\\[|\\]", "");
            return truthValues;
        }
    }

    //EFFECTS: returns the size of variable
    public int varSize() {
        return variable.size();
    }

    //EFFECTS: returns true if the variable is empty
    public boolean isVarEmpty() {
        return variable.isEmpty();
    }

    //EFFECTS: returns variable as a list with all its truth values
    // if variable is empty throw EmptyListException
    public List<Integer> getTruthVals() throws EmptyListException {
        if (variable.isEmpty()) {
            throw new EmptyListException("Variable is empty");
        } else {
            List<Integer> varList = new ArrayList<Integer>();
            for (int i : variable) {
                varList.add(i);
            }
            return varList;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds a truth value to variable
    // if i is neither 1 nor 0 throw InvalidTruthValueException
    public void addTruthVal(int i) throws InvalidTruthValueException {
        if (!((i == 1) || (i == 0))) {
            throw new InvalidTruthValueException("A truth value must be either 1 or 0");
        } else {
            variable.add(i);
        }
    }

    //MODIFIES: this
    //EFFECTS: sets variable's truth values to be the same as truthVals
    // if truthVals is empty throw EmptyListException
    // if truthVals contains anything other than 1's or 0's throw InvalidTruthValueException
    public void setTruthVals(List<Integer> truthVals) throws EmptyListException, InvalidTruthValueException {
        if (truthVals.isEmpty()) {
            throw new EmptyListException("An empty list of truth values were given");
        } else {
            for (int i : truthVals) {
                if (!((i == 1) || (i == 0))) {
                    throw new InvalidTruthValueException("A truth value must be either 1 or 0");
                }
            }
            variable.clear();
            for (int i = 0; i < truthVals.size(); i++) {
                variable.add(truthVals.get(i));
            }
        }
    }


    //EFFECTS: returns all the truth values of a variable as a json array
    public JSONArray variableToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < variable.size(); i++) {
            int truthVal = variable.get(i);
            jsonArray.put(truthVal);
        }
        return jsonArray;
    }


}

