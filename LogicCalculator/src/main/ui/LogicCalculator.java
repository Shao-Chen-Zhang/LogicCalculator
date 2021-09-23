package ui;

import exceptions.EmptyListException;
import exceptions.ImpossibleVariableSetupException;
import exceptions.InvalidTruthValueException;
import model.Variable;
import model.Variables;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

//Writing and Reading to and from Json modelled off JsonSerializationDemo from Phase 2
//A logic calculator
public class LogicCalculator {
    private static final String JSON_STORE = "./data/variables.json";
    private Variables variables;
    private boolean keepRunning;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs the logic calculator and give the user an introduction to the program
    public LogicCalculator() throws FileNotFoundException {
        System.out.println("This is a logical calculator.");
        System.out.println("The maximum number of variables that can be held is 26.");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runLogicCalculator();
    }

    //MODIFIES: this
    //EFFECTS: based in the user's input:
    // processes how many variables will be used and conducts the operation(s) on those variables
    public void runLogicCalculator() {
        keepRunning = true;
        selectNumberOfVariablesInEquation();
        while (keepRunning) {
            conductOperation();
        }
    }

    //MODIFIES: this
    //EFFECTS: creates a number of variables in "variables" based on the user's input
    private void selectNumberOfVariablesInEquation() {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("\nEnter the number of variables in the logic statement:");
            int numOfVariables = input.nextInt();
            variables = new Variables();
            variables.setVariablesInUse(numOfVariables);
        } catch (ImpossibleVariableSetupException e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again:");
            selectNumberOfVariablesInEquation();
        } catch (Exception e) {
            System.out.println("Please try again:");
            selectNumberOfVariablesInEquation();
        }
    }

    //MODIFIES: this
    //EFFECTS: conducts the logic operation of the the user's choice with the selected variable
    private void conductOperation() {
        selectOperation();
        Scanner input = new Scanner(System.in);
        String operation = input.next();
        selectOperationVariables(operation);
    }

    //EFFECTS: asks the user to input a command
    private void selectOperation() {
        System.out.println("\n Input a Command or Show Options:");
        System.out.println("\t* -> Options");
    }

    //MODIFIES: this
    //EFFECTS: processes the user's input and conducts the correct operation
    public void selectOperationVariables(String command) {
//        try {
        ifOptions(command);
        ifNegation(command);
        ifConjunction(command);
        ifDisjunction(command);
        ifConditional(command);
        ifBiconditional(command);
        ifPrint(command);
        ifSave(command);
        ifLoad(command);
        ifQuit(command);
//        } catch (EmptyListException e) {
//            System.out.println(e.getMessage());
//            System.out.println("Please try again:");
//        } catch (InvalidTruthValueException e) {
//            System.out.println(e.getMessage());
//            System.out.println("Please try again:");
//        } catch (Exception e) {
//            System.out.println("Please try again:");
//        }
    }

    //MODIFIES: this
    //EFFECTS: prints the different valid inputs
    public void ifOptions(String operation) {
        if (operation.equals("*")) {
            System.out.println("\t~ -> NEGATION");
            System.out.println("\t& -> CONJUNCTION");
            System.out.println("\tv -> DISJUNCTION");
            System.out.println("\t> -> CONDITIONAL");
            System.out.println("\t= -> BICONDITIONAL");
            System.out.println("\tp -> Print All Variables in Use");
            System.out.println("\ts -> Save Variables");
            System.out.println("\tl -> Load Saved Variables");
            System.out.println("\tq -> Quit and Display Truth Values of the Last Variable");
        }
    }

    //REQUIRES: the variable selected must exist
    //MODIFIES: this
    //EFFECTS: if correct input was entered, conducts the negation operation on a variable and outputs its truth values
    // the result of the operation is saved as a variable and added as the last variable
    public void ifNegation(String operation) {
        try {
            if (operation.equals("~")) {
                Scanner input = new Scanner(System.in);
                promptSelectOneVariable();
                int variableIndex = input.nextInt() - 1;
                Variable notResult = variables.not(variables.getVariable(variableIndex));
                variables.assignNewVariable(notResult);
                System.out.println(
                        "\nThe following result has been added as the last variable (" + variables.size() + ").");
                for (int truthVal : notResult.getTruthVals()) {
                    System.out.print(truthVal + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Please try again:");
            ifNegation(operation);
        }
    }

    //REQUIRES: the variables selected must exist
    //MODIFIES: this
    //EFFECTS: if correct input was entered, conducts the conjunction operation on two variables and
    // outputs its truth values the result of the operation is saved as a variable and added as the last variable
    public void ifConjunction(String operation) {
        try {
            if (operation.equals("&")) {
                Scanner input1 = new Scanner(System.in);
                Scanner input2 = new Scanner(System.in);
                promptSelectTwoVariables();
                int variableIndex1 = input1.nextInt() - 1;
                int variableIndex2 = input2.nextInt() - 1;
                Variable andResult = variables.and(variables.getVariable(variableIndex1),
                        variables.getVariable(variableIndex2));
                variables.assignNewVariable(andResult);
                System.out.println(
                        "\nThe following result has been added as the last variable (" + variables.size() + ").");
                for (int truthVal : andResult.getTruthVals()) {
                    System.out.print(truthVal + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Please try again:");
            ifConjunction(operation);
        }
    }

    //REQUIRES: the variables selected must exist
    //MODIFIES: this
    //EFFECTS: if correct input was entered, conducts the disjunction operation on two variables
    // and outputs its truth values the result of the operation is saved as a variable and added as the last variable
    public void ifDisjunction(String operation) {
        try {
            if (operation.equals("v")) {
                Scanner input1 = new Scanner(System.in);
                Scanner input2 = new Scanner(System.in);
                promptSelectTwoVariables();
                int variableIndex1 = input1.nextInt() - 1;
                int variableIndex2 = input2.nextInt() - 1;
                Variable orResult = variables.or(variables.getVariable(variableIndex1),
                        variables.getVariable(variableIndex2));
                variables.assignNewVariable(orResult);
                System.out.println(
                        "\nThe following result has been added as the last variable (" + variables.size() + ").");
                for (int truthVal : orResult.getTruthVals()) {
                    System.out.print(truthVal + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Please try again:");
            ifDisjunction(operation);
        }
    }

    //REQUIRES: the variables selected must exist
    //MODIFIES: this
    //EFFECTS: if correct input was entered, conducts the conditional operation on two variables
    // and outputs its truth values the result of the operation is saved as a variable and added as the last variable
    public void ifConditional(String operation) {
        try {
            if (operation.equals(">")) {
                Scanner input1 = new Scanner(System.in);
                Scanner input2 = new Scanner(System.in);
                promptSelectTwoVariables();
                int variableIndex1 = input1.nextInt() - 1;
                int variableIndex2 = input2.nextInt() - 1;
                Variable ifResult = variables.ifThen(variables.getVariable(variableIndex1),
                        variables.getVariable(variableIndex2));
                variables.assignNewVariable(ifResult);
                System.out.println(
                        "\nThe following result has been added as the last variable (" + variables.size() + ").");
                for (int truthVal : ifResult.getTruthVals()) {
                    System.out.print(truthVal + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Please try again:");
            ifConditional(operation);
        }
    }

    //REQUIRES: the variables selected must exist
    //MODIFIES: this
    //EFFECTS: if correct input was entered, conducts the biconditional operation on two variables and
    // outputs its truth values the result of the operation is saved as a variable and added as the last variable
    public void ifBiconditional(String operation) {
        try {
            if (operation.equals("=")) {
                Scanner input1 = new Scanner(System.in);
                Scanner input2 = new Scanner(System.in);
                promptSelectTwoVariables();
                int variableIndex1 = input1.nextInt() - 1;
                int variableIndex2 = input2.nextInt() - 1;
                Variable ifOnlyIfResult = variables.ifAndOnlyIf(variables.getVariable(variableIndex1),
                        variables.getVariable(variableIndex2));
                variables.assignNewVariable(ifOnlyIfResult);
                System.out.println(
                        "\nThe following result has been added as the last variable (" + variables.size() + ").");
                for (int truthVal : ifOnlyIfResult.getTruthVals()) {
                    System.out.print(truthVal + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Please try again:");
            ifBiconditional(operation);
        }
    }

    //EFFECTS: prints all variables in use with their respective truth values under a letter denoting that variable
    public void ifPrint(String operation) {
        try {
            if (operation.equals("p")) {
                //modelled code involving char off https://stackoverflow.com/questions/15590675/converting-char-array-to-list-in-java
                char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
                List<Character> variableNames = new ArrayList<Character>();
                for (char c : alphabet) {
                    variableNames.add(c);
                }
                for (int i = 0; i < variables.size(); i++) {
                    System.out.print(variableNames.get(i) + " ");
                }
                System.out.println();
                for (int i = 0; i < variables.getVariable(0).varSize(); i++) {
                    for (int j = 0; j < variables.size(); j++) {
                        System.out.print(variables.getVariable(j).getTruthVals().get(i) + " ");
                    }
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("Please try again:");
            ifPrint(operation);
        }
    }

    //EFFECTS: saves the variables in use to file
    public void ifSave(String operation) {
        if (operation.equals("s")) {
            try {
                jsonWriter.open();
                jsonWriter.write(variables);
                jsonWriter.close();
                System.out.println("Saved variables to file" + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: loads the variables from the file
    public void ifLoad(String operation) {
        if (operation.equals("l")) {
            try {
                variables = jsonReader.readVariables();
                System.out.println("Loaded variables from file" + JSON_STORE);
            } catch (Exception e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: if correct input was entered, ends the program and
    // outputs the truth values of the most recent calculation
    public void ifQuit(String operation) {
        try {
            if (operation.equals("q")) {
                Variable finalVariable = variables.getVariable(variables.size() - 1);
                for (int truthVal : finalVariable.getTruthVals()) {
                    System.out.print(truthVal + " ");
                }
                keepRunning = false;
            }
        } catch (Exception e) {
            System.out.println("Please try again:");
            ifQuit(operation);
        }
    }


    //EFFECTS: prints the prompt to select one variable to conduct the operation on
    public void promptSelectOneVariable() {
        System.out.println("\nSelect one variable to conduct the operation on:");
        System.out.println("\t1 -> 1st variable");
        System.out.println("\t2 -> 2nd variable");
        System.out.println("\t etc ...");
    }

    //EFFECTS: prints the prompt to select two variables to conduct the operation on
    public void promptSelectTwoVariables() {
        System.out.println("\nSelect two variables to conduct the operation on:");
        System.out.println("\t1 -> 1st variable");
        System.out.println("\t2 -> 2nd variable");
        System.out.println("\t etc ...");
    }

}
