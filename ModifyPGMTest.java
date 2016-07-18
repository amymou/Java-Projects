import java.util.Arrays;

/**
 * Program to test TabConverter
 * 
 * @author
 */
public class ModifyPGMTest {

    private static final int[][] IMG_NORMAL = { { 1, 2, 3 }, { 4, 5, 6 },
            { 7, 8, 9 } };

    private static final int[][] IMG_DARKER = { { 0, 0, 0 }, { 0, 1, 2 },
            { 3, 4, 5 } };
            
    private static final int[][] IMG_LIGHTER = { { 3, 4, 5 }, { 6, 7, 8 },
            { 9, 10, 11 } };
    
    private static final int[][] IMG_NORMAL2 = { { 4, 4, 4 }, { 4, 5, 6 },
        { 7, 8, 9 } };

    private static final int[][] IMG_LIGHTER2 = { { 16, 16, 16 }, { 16, 16, 16 },
        { 16, 16, 16 } };

    private static final int[][] IMG_DARKER2 = { { 0, 0, 0 }, { 0, 0, 0 },
        { 0, 0, 0 } };
        
    private static final int[][] IMG_DARKER4 = { { 0, 1, 0 }, { 0, 0, 0 },
        { 0, 1, 0 } };

    private static final int[][]  IMG_LIGHTER4= { { 8, 9, 8 }, { 8, 8, 8 },
        { 8, 9, 8 } };
        
   /**
     * Starts the test program
     * 
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {
        String id = "Darken";
        String description = "ModifyPGM.modifyPGM(IMG_NORMAL, 16, -4)";
        int[][] expected = IMG_DARKER;
        int[][] actual = ModifyPGM.modifyPGM(IMG_NORMAL, 16, -4);
        testResult(id, description, expected, actual);

        id = "Lighten";
        description = "ModifyPGM.modifyPGM(IMG_NORMAL, 16, 2)";
        expected = IMG_LIGHTER;
        actual = ModifyPGM.modifyPGM(IMG_NORMAL, 16, 2);
        testResult(id, description, expected, actual);

        id = "Ragged array";
        int[][] ragged = new int[3][];
        ragged[0] = new int[3];
        ragged[1] = new int[5];
        ragged[2] = new int[4];
        description = "ModifyPGM.modifyPGM(ragged, 16, 3)";
        String exp = "class java.lang.IllegalArgumentException";
        String act = "";
        try {
            act = Arrays.deepToString(ModifyPGM.modifyPGM(ragged, 16, 3));
        } catch (IllegalArgumentException e) {
            act = "" + e.getClass();
        }
        testResultString(id, description, exp, act);

        //test case added
        
        id = "Darken2";
        description = "ModifyPGM.modifyPGM(IMG_DARKER, 16, 4)";
        expected = IMG_NORMAL2;
        actual = ModifyPGM.modifyPGM(IMG_DARKER, 16, 4);
        testResult(id, description, expected, actual);

        id = "Lighten2";
        description = "ModifyPGM.modifyPGM(IMG_LIGHTER, 16, -2)";
        expected = IMG_NORMAL;
        actual = ModifyPGM.modifyPGM(IMG_LIGHTER, 16, -2);
        testResult(id, description, expected, actual);

        id = "Darken3";
        description = "ModifyPGM.modifyPGM(IMG_NORMAL2, 16, 16)";
        expected = IMG_LIGHTER2;
        actual = ModifyPGM.modifyPGM(IMG_NORMAL2, 16, 16);
        testResult(id, description, expected, actual);

        id = "Lighten3";
        description = "ModifyPGM.modifyPGM(IMG_NORMAL2, 16, -16)";
        expected = IMG_DARKER2;
        actual = ModifyPGM.modifyPGM(IMG_NORMAL2, 16, -16);
        testResult(id, description, expected, actual);

        id = "Darken4";
        description = "ModifyPGM.modifyPGM(IMG_DARKER4, 16, 16)";
        expected = IMG_LIGHTER4;
        actual = ModifyPGM.modifyPGM(IMG_DARKER4, 16, 8);
        testResult(id, description, expected, actual);

        id = "Lighten4";
        description = "ModifyPGM.modifyPGM(IMG_LIGHTER4, 16, -16)";
        expected = IMG_DARKER4;
        actual = ModifyPGM.modifyPGM(IMG_LIGHTER4, 16, -8);
        testResult(id, description, expected, actual);

        id = "Ragged array";
        ragged = new int[3][];
        ragged[0] = new int[3];
        ragged[1] = new int[3];
        ragged[2] = new int[4];
        description = "ModifyPGM.modifyPGM(ragged, 16, 3)";
        exp = "class java.lang.IllegalArgumentException";
        act = "";
        try {
            act = Arrays.deepToString(ModifyPGM.modifyPGM(ragged, 16, 3));
        } catch (IllegalArgumentException e) {
            act = "" + e.getClass();
        }
        testResultString(id, description, exp, act);
        
        id = "Null array";
        int[][]nullArray = new int[3][];
        nullArray[0] = new int[3];
        nullArray[1] = new int[3];
        nullArray[2] = null;
        description = "ModifyPGM.modifyPGM(nullArray, 16, 3)";
        exp = "class java.lang.NullPointerException";
        act = "";
        try {
            act = Arrays.deepToString(ModifyPGM.modifyPGM(nullArray, 16, 3));
        } catch (NullPointerException e) {
            act = "" + e.getClass();
        }
        testResultString(id, description, exp, act);
        
    }

    /**
     * Prints the test information.
     * 
     * @param id
     *            id of the test
     * @param desc
     *            description of the test (e.g., method call)
     * @param exp
     *            expected result of the test
     * @param act
     *            actual result of the test
     */
    private static void testResult(String id, String desc, int[][] exp,
            int[][] act) {
        System.out.println();
        System.out.println("ID:          " + id);
        System.out.println("Description: " + desc);
        System.out.println("Expected:    " + Arrays.deepToString(exp));
        System.out.println("Actual:      " + Arrays.deepToString(act));
        System.out.println("Pass test?   " + Arrays.deepEquals(exp, act));
        System.out.println();
    }

    /**
     * Prints the test information when testing Strings.
     * 
     * @param id
     *            id of the test
     * @param desc
     *            description of the test (e.g., method call)
     * @param exp
     *            expected result of the test
     * @param act
     *            actual result of the test
     */
    private static void testResultString(String id, String desc, String exp,
            String act) {
        System.out.println();
        System.out.println("ID:          " + id);
        System.out.println("Description: " + desc);
        System.out.println("Expected:    " + exp);
        System.out.println("Actual:      " + act);
        System.out.println("Pass test?   " + exp.equals(act));
        System.out.println();
    }
}
