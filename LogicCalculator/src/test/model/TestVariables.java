package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestVariables {
    private Variables testVariables1;
    private Variables testVariables2;
    private Variables testVariables3;
    private List<Integer> truthVal1;
    private List<Integer> truthVal2;
    private List<Integer> truthVal3;
    private List<Integer> testTwoTruthValues;

    @BeforeEach
    void setup() {
        testVariables1 = new Variables();
        testVariables2 = new Variables();
        testVariables3 = new Variables();
        testTwoTruthValues = new ArrayList<Integer>();
        testTwoTruthValues.add(1);
        testTwoTruthValues.add(0);
        truthVal1 = new ArrayList<Integer>();
        truthVal2 = new ArrayList<Integer>();
        truthVal3 = new ArrayList<Integer>();

    }

    @Test
    void Variables() {
        testVariables1.getVariables().isEmpty();
    }

    @Test
    void testSetVariablesInUse() {
        try {
            testVariables1.setVariablesInUse(1);
            testVariables2.setVariablesInUse(2);

            assertEquals(testTwoTruthValues, testVariables1.getVariable(0).getTruthVals());
            assertEquals(1, testVariables1.size());

            Variable A = testVariables2.getVariable(0);
            List<Integer> truthValA = new ArrayList<Integer>();
            truthValA.add(1);
            truthValA.add(1);
            truthValA.add(0);
            truthValA.add(0);
            assertEquals(truthValA, A.getTruthVals());
            Variable B = testVariables2.getVariable(1);
            List<Integer> truthValB = new ArrayList<Integer>();
            truthValB.add(1);
            truthValB.add(0);
            truthValB.add(1);
            truthValB.add(0);
            assertEquals(truthValB, B.getTruthVals());
            assertEquals(2, testVariables2.size());

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testNot() {
        try {
            testVariables1.setVariablesInUse(1);
            testVariables2.setVariablesInUse(2);
            testVariables3.setVariablesInUse(3);

            Variable v1 = testVariables1.not(testVariables1.getVariable(0));
            Variable v2 = testVariables2.not(testVariables2.getVariable(1));
            Variable v3 = testVariables3.not(testVariables3.getVariable(1));

            truthVal1.add(0);
            truthVal1.add(1);
            assertEquals(truthVal1, v1.getTruthVals());
            truthVal2.add(0);
            truthVal2.add(1);
            truthVal2.add(0);
            truthVal2.add(1);
            assertEquals(truthVal2, v2.getTruthVals());
            for (int i = 1; i <= 2; i++) {
                truthVal3.add(0);
                truthVal3.add(0);
                truthVal3.add(1);
                truthVal3.add(1);
            }
            assertEquals(truthVal3, v3.getTruthVals());

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testAnd() {
        try {
            testVariables1.setVariablesInUse(2);
            testVariables2.setVariablesInUse(3);

            Variable v1 = testVariables1.and(testVariables1.getVariable(0), testVariables1.getVariable(1));
            Variable v2 = testVariables2.and(testVariables2.getVariable(0), testVariables2.getVariable(1));
            Variable v3 = testVariables2.and(testVariables2.getVariable(1), testVariables2.getVariable(2));

            truthVal1.add(1);
            truthVal1.add(0);
            truthVal1.add(0);
            truthVal1.add(0);
            assertEquals(truthVal1, v1.getTruthVals());

            truthVal2.add(1);
            truthVal2.add(1);
            truthVal2.add(0);
            truthVal2.add(0);
            truthVal2.add(0);
            truthVal2.add(0);
            truthVal2.add(0);
            truthVal2.add(0);
            assertEquals(truthVal2, v2.getTruthVals());

            truthVal3.add(1);
            truthVal3.add(0);
            truthVal3.add(0);
            truthVal3.add(0);
            truthVal3.add(1);
            truthVal3.add(0);
            truthVal3.add(0);
            truthVal3.add(0);
            assertEquals(truthVal3, v3.getTruthVals());

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testOr() {
        try {
            testVariables1.setVariablesInUse(2);
            testVariables2.setVariablesInUse(3);

            Variable v1 = testVariables1.or(testVariables1.getVariable(0), testVariables1.getVariable(1));
            Variable v2 = testVariables2.or(testVariables2.getVariable(0), testVariables2.getVariable(1));
            Variable v3 = testVariables2.or(testVariables2.getVariable(0), testVariables2.getVariable(2));

            truthVal1.add(1);
            truthVal1.add(1);
            truthVal1.add(1);
            truthVal1.add(0);
            assertEquals(truthVal1, v1.getTruthVals());

            truthVal2.add(1);
            truthVal2.add(1);
            truthVal2.add(1);
            truthVal2.add(1);
            truthVal2.add(1);
            truthVal2.add(1);
            truthVal2.add(0);
            truthVal2.add(0);
            assertEquals(truthVal2, v2.getTruthVals());

            truthVal3.add(1);
            truthVal3.add(1);
            truthVal3.add(1);
            truthVal3.add(1);
            truthVal3.add(1);
            truthVal3.add(0);
            truthVal3.add(1);
            truthVal3.add(0);
            assertEquals(truthVal3, v3.getTruthVals());

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testIfThen() {
        try {
            testVariables1.setVariablesInUse(2);
            testVariables2.setVariablesInUse(3);

            Variable v1 = testVariables1.ifThen(testVariables1.getVariable(0), testVariables1.getVariable(1));
            Variable v2 = testVariables2.ifThen(testVariables2.getVariable(0), testVariables2.getVariable(1));
            Variable v3 = testVariables2.ifThen(testVariables2.getVariable(0), testVariables2.getVariable(2));

            truthVal1.add(1);
            truthVal1.add(0);
            truthVal1.add(1);
            truthVal1.add(1);
            assertEquals(truthVal1, v1.getTruthVals());

            truthVal2.add(1);
            truthVal2.add(1);
            truthVal2.add(0);
            truthVal2.add(0);
            truthVal2.add(1);
            truthVal2.add(1);
            truthVal2.add(1);
            truthVal2.add(1);
            assertEquals(truthVal2, v2.getTruthVals());

            truthVal3.add(1);
            truthVal3.add(0);
            truthVal3.add(1);
            truthVal3.add(0);
            truthVal3.add(1);
            truthVal3.add(1);
            truthVal3.add(1);
            truthVal3.add(1);
            assertEquals(truthVal3, v3.getTruthVals());

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testIfAndOnlyIf() {
        try {
            testVariables1.setVariablesInUse(2);
            testVariables2.setVariablesInUse(3);

            Variable v1 = testVariables1.ifAndOnlyIf(testVariables1.getVariable(0), testVariables1.getVariable(1));
            Variable v2 = testVariables2.ifAndOnlyIf(testVariables2.getVariable(0), testVariables2.getVariable(1));
            Variable v3 = testVariables2.ifAndOnlyIf(testVariables2.getVariable(1), testVariables2.getVariable(2));

            truthVal1.add(1);
            truthVal1.add(0);
            truthVal1.add(0);
            truthVal1.add(1);
            assertEquals(truthVal1, v1.getTruthVals());

            truthVal2.add(1);
            truthVal2.add(1);
            truthVal2.add(0);
            truthVal2.add(0);
            truthVal2.add(0);
            truthVal2.add(0);
            truthVal2.add(1);
            truthVal2.add(1);
            assertEquals(truthVal2, v2.getTruthVals());

            truthVal3.add(1);
            truthVal3.add(0);
            truthVal3.add(0);
            truthVal3.add(1);
            truthVal3.add(1);
            truthVal3.add(0);
            truthVal3.add(0);
            truthVal3.add(1);
            assertEquals(truthVal3, v3.getTruthVals());

        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }


    @Test
    void testAssignNewVariable() {
        try {
            testVariables1.setVariablesInUse(2);
            Variable v1 =
                    testVariables1.ifAndOnlyIf(testVariables1.getVariable(0), testVariables1.getVariable(1));
            testVariables2.setVariablesInUse(3);
            Variable v2 = new Variable();
            v1.setVariable(1, 1);
            v2.setVariable(2, 1);

            testVariables1.assignNewVariable(v1);
            testVariables2.assignNewVariable(v2);

            assertEquals(v1.getTruthVals(), testVariables1.getVariable(2).getTruthVals());
            assertEquals(v2.getTruthVals(), testVariables2.getVariable(3).getTruthVals());

        } catch (Exception e) {
            fail("Exception should not have been thrown" + e.getMessage());
        }
    }

    @Test
    void testAddVariable() {
        try {
            Variable v1 = new Variable();
            testVariables2.setVariablesInUse(3);
            assertEquals(3, testVariables2.size());

            testVariables1.addVariable(v1);
            testVariables2.addVariable(v1);

            assertEquals(1, testVariables1.size());
            assertEquals(4, testVariables2.size());

        } catch (Exception e) {
            fail("Exception should not have been thrown" + e.getMessage());
        }
    }


}
