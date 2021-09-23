package ui;

import exceptions.EmptyListException;
import exceptions.ImpossibleVariableSetupException;
import exceptions.InvalidTruthValueException;
import model.LogicSentences;
import model.Variable;
import model.Variables;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// A logic calculator Gui
class Gui extends JFrame implements ActionListener, ListSelectionListener {
    private static final String JSON_STORE_VARIABLES = "./data/variables.json";
    private JsonWriter jsonWriterVariables;
    private JsonReader jsonReaderVariables;
    private static final String JSON_STORE_LOGIC_SENTENCES = "./data/logicSentences.json";
    private JsonWriter jsonWriterLogicSentences;
    private JsonReader jsonReaderLogicSentences;

    private Variables variables;
    LogicSentences logicSentences;
    private JTextField operationField1;
    private JTextField operationField2;
    private JTextField setVariablesField;
    private JButton negationButton;
    private JButton conjunctionButton;
    private JButton disjunctionButton;
    private JButton conditionalButton;
    private JButton biconditionalButton;
    private JButton setVariablesButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton showButton;
    private JButton clearButton;
    private JButton resetButton;
    private JList logicSentencesJList;
    private JList truthVals;
    private DefaultListModel logicSentencesModel;
    private DefaultListModel truthValsModel;
    private static int JFRAME_HEIGHT = 370;
    private static int JFRAME_WIDTH = 840;

    int variableIndex1;
    int variableIndex2;
    Variable variable1;
    Variable variable2;

    // runs the Gui
    public Gui() {
        super("Gui");
        ImageIcon icon = new ImageIcon("data/icon.png");
        setIconImage(icon.getImage());

        jsonWriterVariables = new JsonWriter(JSON_STORE_VARIABLES);
        jsonWriterLogicSentences = new JsonWriter(JSON_STORE_LOGIC_SENTENCES);
        jsonReaderVariables = new JsonReader(JSON_STORE_VARIABLES);
        jsonReaderLogicSentences = new JsonReader(JSON_STORE_LOGIC_SENTENCES);

        createLogicOperationButtons();
        createOtherButtons();

        JScrollPane lsScrollPane = createLogicSentencesScrollPane();
        JScrollPane tvScrollPane = createTruthValuesScrollPane();

        JPanel commandsPanel = createCommandsPanel();
        addButtonsAndFieldsToCommandsPanel(commandsPanel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setSize(JFRAME_WIDTH, JFRAME_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        add(commandsPanel);
        add(lsScrollPane);
        add(tvScrollPane);
    }

    private void addButtonsAndFieldsToCommandsPanel(JPanel commandsPanel) {
        commandsPanel.add(operationField1);
        commandsPanel.add(operationField2);
        commandsPanel.add(negationButton);
        commandsPanel.add(conjunctionButton);
        commandsPanel.add(disjunctionButton);
        commandsPanel.add(conditionalButton);
        commandsPanel.add(biconditionalButton);
        commandsPanel.add(setVariablesField);
        commandsPanel.add(setVariablesButton);
        commandsPanel.add(saveButton);
        commandsPanel.add(loadButton);
        commandsPanel.add(showButton);
        commandsPanel.add(clearButton);
        commandsPanel.add(resetButton);
    }

    private JPanel createCommandsPanel() {
        JPanel commandsPanel = new JPanel();
        commandsPanel.setBackground(Color.LIGHT_GRAY);
        commandsPanel.setBorder(BorderFactory.createEtchedBorder());
        commandsPanel.setBounds(0, 0, JFRAME_WIDTH / 4, JFRAME_HEIGHT);
        return commandsPanel;
    }

    private JScrollPane createTruthValuesScrollPane() {
        truthValsModel = new DefaultListModel();
        truthVals = new JList(truthValsModel);
        truthVals.addListSelectionListener(this);
        JScrollPane tvScrollPane = new JScrollPane(truthVals);
        tvScrollPane.setBounds(JFRAME_WIDTH / 4, JFRAME_HEIGHT / 2,
                JFRAME_WIDTH * 3 / 4 - 6, JFRAME_HEIGHT / 2 - 35);
        return tvScrollPane;
    }

    private JScrollPane createLogicSentencesScrollPane() {
        logicSentencesModel = new DefaultListModel();
        logicSentencesJList = new JList(logicSentencesModel);
        logicSentencesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        logicSentencesJList.addListSelectionListener(this);
        JScrollPane lsScrollPane = new JScrollPane(logicSentencesJList);
        lsScrollPane.setBounds(JFRAME_WIDTH / 4, 0, JFRAME_WIDTH * 3 / 4 - 6, JFRAME_HEIGHT / 2);
        return lsScrollPane;
    }

    //MODIFIES: this
    //EFFECTS: creates all the non-logical operator buttons and fields needed
    private void createOtherButtons() {
        setVariablesButton = new JButton("Set Variables");
        buttonSetUp(setVariablesButton, "setVariablesInUse");
        setVariablesField = new JTextField(6);

        saveButton = new JButton("Save", UIManager.getIcon("FileView.floppyDriveIcon"));
        buttonSetUp(saveButton, "save");

        loadButton = new JButton("Load", UIManager.getIcon("FileChooser.upFolderIcon"));
        buttonSetUp(loadButton, "load");

        showButton = new JButton("   Show   ");
        buttonSetUp(showButton, "show");
        showButton.setEnabled(false);

        clearButton = new JButton("   Clear   ");
        buttonSetUp(clearButton, "clear");

        resetButton = new JButton("RESET", UIManager.getIcon("OptionPane.errorIcon"));
        buttonSetUp(resetButton, "reset");
    }

    //MODIFIES: this
    //EFFECTS: creates all the logical operator buttons and fields
    private void createLogicOperationButtons() {
        operationField1 = new JTextField(8);
        operationField2 = new JTextField(8);

        negationButton = new JButton("  ~    (Negation)   ");
        buttonSetUp(negationButton, "~");
        negationButton.setEnabled(false);

        conjunctionButton = new JButton("&   (Conjunction)");
        buttonSetUp(conjunctionButton, "&");
        conjunctionButton.setEnabled(false);

        disjunctionButton = new JButton(" v   (Disjunction) ");
        buttonSetUp(disjunctionButton, "v");
        disjunctionButton.setEnabled(false);

        conditionalButton = new JButton(" >   (Conditional) ");
        buttonSetUp(conditionalButton, ">");
        conditionalButton.setEnabled(false);

        biconditionalButton = new JButton("=  (Biconditional)");
        buttonSetUp(biconditionalButton, "=");
        biconditionalButton.setEnabled(false);
    }

    //This is the method that is called when a JButton is clicked
    //The logic operations are done on "variables" and
    // "logicSentenceModel" displays the logic sentence of the operation that was done
    public void actionPerformed(ActionEvent e) {
        try {
            ifSetVariablesButtonPressed(e);
            ifNegationButtonPressed(e);
            ifConjunctionButtonPressed(e);
            ifDisjunctionButtonPressed(e);
            ifConditionalButtonPressed(e);
            ifBiconditionalButtonPressed(e);
            ifShowButtonPressed(e);
            ifClearButtonPressed(e);
            ifSaveButtonPressed(e);
            ifLoadButtonPressed(e);
            ifResetButtonPressed(e);
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,
                    "Error",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //MODIFIES: this
    //EFFECTS: if the setVariablesButton is pressed setVariablesInUse to the number of variables
    // in the setVariablesField, then add those variables as their indexes in logicSentencesModel
    // finally, disable the setVariablesButton and enable all the other buttons
    private void ifSetVariablesButtonPressed(ActionEvent e) throws ImpossibleVariableSetupException {
        if (e.getSource() == setVariablesButton) {
            variables = new Variables();
            variables.setVariablesInUse(Integer.parseInt(setVariablesField.getText()));
            for (int i = 0; i < variables.size(); i++) {
                logicSentencesModel.add(i, Integer.toString(i));
            }
            setVariablesButton.setEnabled(false);
            enableButtons();
        }
    }

    //MODIFIES: this
    //EFFECTS: enables all buttons except loadButton, setVariablesButton, and resetButton
    private void enableButtons() {
        negationButton.setEnabled(true);
        conjunctionButton.setEnabled(true);
        disjunctionButton.setEnabled(true);
        conditionalButton.setEnabled(true);
        biconditionalButton.setEnabled(true);
        showButton.setEnabled(true);
    }


    //MODIFIES: this
    //EFFECTS: if the negationButton is pressed,
    // negate the variable in variables with index entered in operationField1
    // and add the logicSentenceModel indicated by operationField1 as the index to logicSentencesModel
    // but with a ~ in front of it
    private void ifNegationButtonPressed(ActionEvent e) throws EmptyListException, InvalidTruthValueException {
        if (e.getSource() == negationButton) {
            variableIndex1 = Integer.parseInt(operationField1.getText());
            variable1 = variables.getVariable(variableIndex1);

            Variable notResult = variables.not(variable1);
            variables.assignNewVariable(notResult);
            logicSentencesModel.add(logicSentencesModel.size(),
                    "~" + logicSentencesModel.get(variableIndex1));
        }
    }


    //MODIFIES: this
    //EFFECTS: if the conjunctionButton is pressed,
    // "and" the 2 respective variable in variables
    // using index entered in operationField1 and operationField1
    // and add the conjuncted logicSentenceModel
    // indicated by operationField1 and operationField1 to logicSentencesModel
    private void ifConjunctionButtonPressed(ActionEvent e) throws EmptyListException, InvalidTruthValueException {
        if (e.getSource() == conjunctionButton) {
            assignIndexesAndVariablesFromOperationFields();

            Variable andResult = variables.and(variable1, variable2);
            variables.assignNewVariable(andResult);
            logicSentencesModel.add(logicSentencesModel.size(),
                    "(" + logicSentencesModel.get(variableIndex1)
                            + " & " + logicSentencesModel.get(variableIndex2) + ")");
        }
    }

    //MODIFIES: this
    //EFFECTS: if the disjunctionButton is pressed,
    // "or" the 2 respective variable in variables
    // using index entered in operationField1 and operationField1
    // and add the disjuncted logicSentenceModel
    // indicated by operationField1 and operationField1 to logicSentencesModel
    private void ifDisjunctionButtonPressed(ActionEvent e) throws EmptyListException, InvalidTruthValueException {
        if (e.getSource() == disjunctionButton) {
            assignIndexesAndVariablesFromOperationFields();

            Variable orResult = variables.or(variable1, variable2);
            variables.assignNewVariable(orResult);
            logicSentencesModel.add(logicSentencesModel.size(),
                    "(" + logicSentencesModel.get(variableIndex1)
                            + " v " + logicSentencesModel.get(variableIndex2) + ")");
        }
    }

    //MODIFIES: this
    //EFFECTS: if the conditionalButton is pressed,
    // "ifThen" the 2 respective variable in variables
    // using index entered in operationField1 and operationField1
    // and add the conditional logicSentenceModel
    // indicated by operationField1 and operationField1 to logicSentencesModel
    private void ifConditionalButtonPressed(ActionEvent e) throws EmptyListException, InvalidTruthValueException {
        if (e.getSource() == conditionalButton) {
            assignIndexesAndVariablesFromOperationFields();

            Variable ifResult = variables.ifThen(variable1, variable2);
            variables.assignNewVariable(ifResult);
            logicSentencesModel.add(logicSentencesModel.size(),
                    "(" + logicSentencesModel.get(variableIndex1)
                            + " > " + logicSentencesModel.get(variableIndex2) + ")");
        }
    }

    //MODIFIES: this
    //EFFECTS: if the bicondtionalButton is pressed,
    // "ifAndOnlyIf" the 2 respective variable in variables
    // using index entered in operationField1 and operationField1
    // and add the biconditional logicSentenceModel
    // indicated by operationField1 and operationField1 to logicSentencesModel
    private void ifBiconditionalButtonPressed(ActionEvent e) throws EmptyListException, InvalidTruthValueException {
        if (e.getSource() == biconditionalButton) {
            assignIndexesAndVariablesFromOperationFields();
            Variable iffResult = variables.ifAndOnlyIf(variable1, variable2);
            variables.assignNewVariable(iffResult);
            logicSentencesModel.add(logicSentencesModel.size(),
                    "(" + logicSentencesModel.get(variableIndex1)
                            + " = " + logicSentencesModel.get(variableIndex2) + ")");
        }
    }

    //MODIFIES: this
    //EFFECTS: if the showButton is pressed,
    // add a string that has the index and binary truth values
    // of the logic sentence selected in logicSentencesJList to truthValsModel
    private void ifShowButtonPressed(ActionEvent e) throws EmptyListException {
        if (e.getSource() == showButton) {
            int index = logicSentencesJList.getSelectedIndex();
            truthValsModel.add(truthValsModel.size(), index + ":   "
                    + variables.getVariable(index).getTruthValsString());
        }
    }

    //MODIFIES: this
    //EFFECTS: if clearButton is pressed removeAllElements from truthValsModel
    private void ifClearButtonPressed(ActionEvent e) {
        if (e.getSource() == clearButton) {
            truthValsModel.removeAllElements();
        }
    }

    //EFFECTS: if saveButton is pressed,
    // writes logicSentencesModel and Variables to their own files separate files
    private void ifSaveButtonPressed(ActionEvent e) {
        if (e.getSource() == saveButton && !variables.isEmpty()) {
            logicSentences = new LogicSentences();

            for (int i = 0; i < logicSentencesModel.getSize(); i++) {
                logicSentences.addSentence(logicSentencesModel.get(i).toString());
            }

            try {
                jsonWriterVariables.open();
                jsonWriterVariables.write(variables);
                jsonWriterVariables.close();

                jsonWriterLogicSentences.open();
                jsonWriterLogicSentences.write(logicSentences);
                jsonWriterLogicSentences.close();
                JOptionPane.showMessageDialog(null,
                        "Files saved to: \n" + JSON_STORE_VARIABLES + "\n" + JSON_STORE_LOGIC_SENTENCES,
                        "Saved", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException error) {
                JOptionPane.showMessageDialog(null,
                        "Unable to write to files: \n" + JSON_STORE_VARIABLES + "\n" + JSON_STORE_LOGIC_SENTENCES,
                        "Save Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //EFFECTS: if loadButton is pressed,
    // reads logicSentencesModel and Variables from their own files separate files
    private void ifLoadButtonPressed(ActionEvent e) throws EmptyListException, InvalidTruthValueException {
        if (e.getSource() == loadButton) {
            logicSentences = new LogicSentences();
            logicSentencesModel.removeAllElements();
            truthValsModel.removeAllElements();

            setVariablesButton.setEnabled(false);
            enableButtons();
            try {
                variables = jsonReaderVariables.readVariables();
                logicSentences = jsonReaderLogicSentences.readLogicSentences();
                for (int i = 0; i < logicSentences.size(); i++) {
                    logicSentencesModel.addElement(logicSentences.getSentence(i));
                }
                JOptionPane.showMessageDialog(null, "Loaded variables from file: \n"
                        + JSON_STORE_LOGIC_SENTENCES + "\n" + JSON_STORE_VARIABLES);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "Unable to read from file: \n"
                        + JSON_STORE_LOGIC_SENTENCES + "\n" + JSON_STORE_VARIABLES);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: Exits the current Gui and reruns Gui
    public void ifResetButtonPressed(ActionEvent e) {
        if (e.getSource() == resetButton) {
            try {
                dispose();
                new Gui();
                this.getDefaultCloseOperation();
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null,
                        "Unable to run application",
                        "Unable to Run Application", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: assigns the variableIndex1 to the index entered in operationField1
    //         assigns the variableIndex2 to the index entered in operationField2
    //         assigns variable1 to be the variable in variables with the index entered in operationField1
    //         assigns variable2 to be the variable in variables with the index entered in operationField2
    private void assignIndexesAndVariablesFromOperationFields() {
        variableIndex1 = Integer.parseInt(operationField1.getText());
        variableIndex2 = Integer.parseInt(operationField2.getText());
        variable1 = variables.getVariable(variableIndex1);
        variable2 = variables.getVariable(variableIndex2);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }


    //MODIFIES: this
    //EFFECTS: sets a button's action command to the given command, adds "this" as an action listener,
    // and sets focusable to false
    private void buttonSetUp(JButton btn, String command) {
        btn.setActionCommand(command);
        btn.addActionListener(this);
        btn.setFocusable(false);
    }

    public static void main(String[] args) {
        new SplashScreen();
        try {
            new Gui();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(null,
                    "Unable to run application",
                    "Unable to Run Application", JOptionPane.ERROR_MESSAGE);
        }
    }
}


