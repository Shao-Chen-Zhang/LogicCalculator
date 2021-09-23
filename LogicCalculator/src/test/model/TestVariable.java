package model;

import exceptions.EmptyListException;
import exceptions.ImpossibleVariableSetupException;
import exceptions.InvalidTruthValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestVariable {
    private Variable testVariable1;
    private Variable testVariable2;
    private Variable testVariable3;
    private Variable testVariable4;
    private Variable testVariable5;
    private List<Integer> truthVal1;
    private List<Integer> truthVal2;
    private List<Integer> truthVal3;
    private List<Integer> truthVal4;
    private List<Integer> truthVal5;


    @BeforeEach
    void setup() {
        testVariable1 = new Variable();
        testVariable2 = new Variable();
        testVariable3 = new Variable();
        testVariable4 = new Variable();
        testVariable5 = new Variable();
        truthVal1 = new ArrayList<Integer>();
        truthVal2 = new ArrayList<Integer>();
        truthVal3 = new ArrayList<Integer>();
        truthVal4 = new ArrayList<Integer>();
        truthVal5 = new ArrayList<Integer>();
    }

    @Test
    void testVariable() {
        assertTrue(testVariable1.isVarEmpty());
        assertEquals(0, testVariable1.varSize());
    }

    @Test
    void testSetVariableWithExceptions() {
        try {
            testVariable1.setVariable(0, 1);
            fail("ImpossibleVariableSetupException should have been thrown");
        } catch (ImpossibleVariableSetupException e) {
            System.out.println(e.getMessage());
        }

        try {
            testVariable2.setVariable(3, 6);
            fail("ImpossibleVariableSetupException should have been thrown");
        } catch (ImpossibleVariableSetupException e) {
            System.out.println(e.getMessage());
        }

        assertTrue(testVariable1.isVarEmpty());
        assertTrue(testVariable2.isVarEmpty());
    }

    @Test
    void testSetVariableNoExceptions() {
        try {
            testVariable1.setVariable(1, 1);
            testVariable2.setVariable(2, 1);
            testVariable3.setVariable(2, 2);
            testVariable4.setVariable(4, 2);
            testVariable5.setVariable(4, 3);

            truthVal1.add(1);
            truthVal1.add(0);
            assertEquals(truthVal1, testVariable1.getTruthVals());

            truthVal2.add(1);
            truthVal2.add(0);
            truthVal2.add(1);
            truthVal2.add(0);
            assertEquals(truthVal2, testVariable2.getTruthVals());

            truthVal3.add(1);
            truthVal3.add(1);
            truthVal3.add(0);
            truthVal3.add(0);
            assertEquals(truthVal3, testVariable3.getTruthVals());

            for (int i = 1; i <= 4; i++) {
                truthVal4.add(1);
                truthVal4.add(1);
                truthVal4.add(0);
                truthVal4.add(0);
            }
            assertEquals(truthVal4, testVariable4.getTruthVals());

            for (int j = 1; j <= 2; j++) {
                for (int i = 1; i <= 4; i++) {
                    truthVal5.add(1);
                }
                for (int i = 1; i <= 4; i++) {
                    truthVal5.add(0);
                }
            }

            assertEquals(truthVal5, testVariable5.getTruthVals());

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testVarSize() {
        try {
            testVariable2.setVariable(2, 1);
        } catch (ImpossibleVariableSetupException e) {
            fail("Exception should not have been thrown");
        }

        assertEquals(0, testVariable1.varSize());
        assertEquals(4, testVariable2.varSize());
    }

    @Test
    void testisVarEmpty() {
        try {
            testVariable2.setVariable(2, 1);
            testVariable3.setVariable(3, 2);
        } catch (ImpossibleVariableSetupException e) {
            fail("Exception should not have been thrown");
        }

        assertTrue(testVariable1.isVarEmpty());
        assertFalse(testVariable2.isVarEmpty());
        assertFalse(testVariable3.isVarEmpty());
    }

    @Test
    void testGetTruthValsWithExceptions() {
        try {
            testVariable1.getTruthVals();
            fail("EmptyListException should have been thrown");
        } catch (EmptyListException e) {
            System.out.println(e.getMessage());
        }

        assertTrue(testVariable1.isVarEmpty());
    }

    @Test
    void testGetTruthValsNoExceptions() {
        try {
            testVariable2.setVariable(1, 1);
            testVariable3.setVariable(2, 1);


            truthVal2.add(1);
            truthVal2.add(0);
            assertEquals(truthVal2, testVariable2.getTruthVals());

            truthVal3.add(1);
            truthVal3.add(0);
            truthVal3.add(1);
            truthVal3.add(0);
            assertEquals(truthVal3, testVariable3.getTruthVals());

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testAddTruthValWithExceptions() {
        truthVal2.add(1);
        truthVal2.add(1);

        try {
            testVariable1.addTruthVal(2);
            fail("InvalidTruthValueException should have been thrown");
        } catch (InvalidTruthValueException e) {
            System.out.println(e.getMessage());
        }
        try {
            testVariable2.addTruthVal(1);
            testVariable2.addTruthVal(1);
            testVariable2.addTruthVal(-1);
            testVariable2.addTruthVal(0);
            fail("InvalidTruthValueException should have been thrown");
        } catch (InvalidTruthValueException e) {
            System.out.println(e.getMessage());
        }

        try {
            assertEquals(truthVal2, testVariable2.getTruthVals());
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testAddTruthValNoExceptions() {
        try {
            testVariable1.addTruthVal(1);
            testVariable2.addTruthVal(0);
            testVariable2.addTruthVal(1);
            testVariable2.addTruthVal(1);

            truthVal1.add(1);
            assertEquals(truthVal1, testVariable1.getTruthVals());
            truthVal2.add(0);
            truthVal2.add(1);
            truthVal2.add(1);
            assertEquals(truthVal2, testVariable2.getTruthVals());

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testSetTruthValsWithExceptions() {
        truthVal2.add(1);
        truthVal2.add(0);
        truthVal2.add(2);

        try {
            testVariable1.setTruthVals(truthVal1);
            fail("EmptyListException should have been thrown");
        } catch (EmptyListException e) {
            System.out.println(e.getMessage());
        } catch (InvalidTruthValueException e) {
            fail("InvalidTruthValueException should not have been thrown");
        }

        try {
            testVariable2.setTruthVals(truthVal2);
            fail("InvalidTruthValueException should have been thrown");
        } catch (EmptyListException e) {
            fail("EmptyListException should not have been thrown");
        } catch (InvalidTruthValueException e) {
            System.out.println(e.getMessage());
        }

        assertTrue(testVariable1.isVarEmpty());
        assertTrue(testVariable2.isVarEmpty());
    }

    @Test
    void testSetTruthValsNoExceptions() {
        try {
            truthVal1.add(1);
            truthVal1.add(1);
            truthVal1.add(1);
            testVariable2.setVariable(1, 1);
            truthVal2.add(1);
            truthVal2.add(0);
            truthVal2.add(1);
            testVariable3.setVariable(2, 1);
            truthVal3.add(0);
            truthVal3.add(0);
            truthVal3.add(0);
            truthVal3.add(1);

            testVariable1.setTruthVals(truthVal1);
            testVariable2.setTruthVals(truthVal2);
            testVariable3.setTruthVals(truthVal3);

            assertEquals(truthVal1, testVariable1.getTruthVals());
            assertEquals(truthVal2, testVariable2.getTruthVals());
            assertEquals(truthVal3, testVariable3.getTruthVals());

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testGetTruthValsStringWithExceptions() {
        try {
            testVariable1.getTruthValsString();
            fail("EmptyListException should have been thrown");
        } catch (EmptyListException e) {
            System.out.println(e.getMessage());
        }

        assertTrue(testVariable1.isVarEmpty());
    }

    @Test
    void testGetTruthValsStringNoExceptions() {
        try {
            testVariable1.setVariable(2, 1);
            testVariable2.setVariable(3, 2);

            String truthValsString1 = testVariable1.getTruthValsString();
            String truthValsString2 = testVariable2.getTruthValsString();

            assertEquals("1 0 1 0", truthValsString1);
            assertEquals("1 1 0 0 1 1 0 0", truthValsString2);

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }


}