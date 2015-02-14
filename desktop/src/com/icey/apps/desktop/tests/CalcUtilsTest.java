package com.icey.apps.desktop.tests;

import org.junit.runners.Suite;

/** Tests for CalcUtils class
 * - tests for changing percentages
 * - tests for adding flavors
 * - tests for calculating final amounts
 *
 * NOTES:
 * - These test only tests the 1st kind of assumption: flavor "other" differs from liquid "other"
 *      so does not have an effect on it
 * - seems like most eliquid calculators go with 1st assumption (described above)
 *
 * Created by Allen (02/03/15)
 *
 * PASSED (02/05/15)
 */
@Suite.SuiteClasses(Suite.class)
public class CalcUtilsTest {

    //    CalcUtils calcUtils; //for top tests
//
//    //==========test to see if percents & flavors added correctly==============
//    @Before //tests to see changes in percentage amounts working & adding flavors works
//    public void setUpTestPercents() throws Exception {
//        calcUtils = new CalcUtils(Constants.Tests.DESIRED_AMT, Constants.Tests.ZERO_STRENGTH,
//                Constants.Tests.TEST_BASE, Constants.Tests.DESIRED_PERCS);
//    }

    /** - Calculation Tests
     *    - checks length & whether values equal
     *    - check to see whether individual values fall within .001 to expected
     *    - check to see whether total value falls within .001 of expected (desired value)
     */
    //==============Calc test 1===============  PASSED
    //test calculations, 1 flavor, Regular Desired Percents
//    @Test
//    public void testFinalAmounts1() throws Exception {
//
//
//        CalcUtils calcUtils = new CalcUtils(Constants.Tests.DESIRED_AMT, Constants.Tests.ZERO_STRENGTH,
//                Constants.Tests.TEST_BASE, Constants.Tests.DESIRED_PERCS);
//
//        calcUtils.addFlavor(Constants.Tests.FLAV1);
//
//        calcUtils.calcAmounts(); //calculate amounts
//        Double[] finalMls = calcUtils.getFinalMills().toArray();
//
//        assertArrayEquals("Calc test 1: ", Constants.Tests.EXPECTED_FAs[0], finalMls);
//
//        double finalTotal = 0;
//        for (int i = 0; i < finalMls.length; i++){
//            double d = finalMls[i];
//
//            assertEquals(Constants.Tests.EXPECTED_FAs[0][i], d, .001);
//            finalTotal += d;
//        }
//
//
//        assertEquals(Constants.Tests.DESIRED_AMT, finalTotal, .001); //+- .001
//
//    }
//
//
//    //===============Calc test 2=============== PASSED
//    //2 flavors, regular percents
//    @Test
//    public void testFinalAmounts2() throws Exception{
//        CalcUtils calcUtils = new CalcUtils(Constants.Tests.DESIRED_AMT, Constants.Tests.ZERO_STRENGTH,
//                Constants.Tests.TEST_BASE, Constants.Tests.DESIRED_PERCS);
//
//        calcUtils.addFlavor(Constants.Tests.FLAV1);
//        calcUtils.addFlavor(Constants.Tests.FLAV2);
//
//        calcUtils.calcAmounts(); //calculate amounts
//
//        Double[] finalMls = calcUtils.getFinalMills().toArray();
//        assertArrayEquals("Calc test 2: ", Constants.Tests.EXPECTED_FAs[1], finalMls);
//
//        double finalTotal = 0;
//        for (int i = 0; i < finalMls.length; i++){
//            double d = finalMls[i];
//            finalTotal += d;
//
//            assertEquals(Constants.Tests.EXPECTED_FAs[1][i], d, .001);
//        }
//
//        assertEquals(Constants.Tests.DESIRED_AMT, finalTotal, .001); //+- .001
//    }
//
//    //===============Calc test 3=============== PASSED
//    //3 flavors, regular percents
//    @Test
//    public void testFinalAmounts3() throws AssertionError{
//        CalcUtils calcUtils = new CalcUtils(Constants.Tests.DESIRED_AMT, Constants.Tests.ZERO_STRENGTH,
//                Constants.Tests.TEST_BASE, Constants.Tests.DESIRED_PERCS);
//
//
//        calcUtils.setFlavors(Constants.Tests.TEST_FLAVORS);
//
//        calcUtils.calcAmounts(); //calculate amounts
//        Double[] finalMls = calcUtils.getFinalMills().toArray();
//
//
//        double finalTotal = 0;
//        for (int i = 0; i < finalMls.length; i++){
//            double d = finalMls[i];
//            finalTotal += d;
//            assertEquals(Constants.Tests.EXPECTED_FAs[2][i], d, .001);
//        }
//
//        assertEquals(Constants.Tests.DESIRED_AMT, finalTotal, .001); //+- .001
//        //assertArrayEquals("Calc test 3: ", Constants.Tests.EXPECTED_FAs[2], finalMls);
//    }
//
//
//    //===============Calc test 4=============== PASSED
//    //1 flavor added, alt percents
//    //PASSED when within .1 of expected value
//    @Test
//    public void testFinalAmounts4() throws Exception{
//        CalcUtils calcUtils = new CalcUtils(Constants.Tests.DESIRED_AMT, Constants.Tests.ZERO_STRENGTH,
//                Constants.Tests.TEST_BASE, Constants.Tests.ALT_DESIRED_PERCS);
//
//        calcUtils.addFlavor(Constants.Tests.FLAV1);
//        calcUtils.calcAmounts(); //calculate amounts
//        Double[] finalMls = calcUtils.getFinalMills().toArray();
//
//        double finalTotal = 0;
//        for (int i = 0; i < finalMls.length; i++){
//            double d = finalMls[i];
//            finalTotal += d;
//            assertEquals(Constants.Tests.EXPECTED_FAs[3][i], d, .01);
//        }
//
//        //assertArrayEquals("Calc test 4: ", Constants.Tests.EXPECTED_FAs[3], finalMls);
//        assertEquals(Constants.Tests.DESIRED_AMT, finalTotal, .1); //+- .1
//
//    }
//
//
//    //===============Calc test 5=============== PASSED
//    //along with alt percents, 2nd flavor is also other
//    @Test
//    public void testFinalAmounts5() throws Exception{
//        CalcUtils calcUtils = new CalcUtils(Constants.Tests.DESIRED_AMT, Constants.Tests.ZERO_STRENGTH,
//                Constants.Tests.TEST_BASE, Constants.Tests.ALT_DESIRED_PERCS);
//
//        calcUtils.addFlavor(Constants.Tests.FLAV1);
//        calcUtils.addFlavor(Constants.Tests.FLAV3);
//
//        calcUtils.calcAmounts(); //calculate amounts
//        assertArrayEquals("Calc test 5: ", Constants.Tests.EXPECTED_FAs[4], calcUtils.getFinalMills().toArray());
//
//    }
//
//
//
//    //===============Calc test 6=============== PASSED
//    //alt percents + 3 flavors
//    @Test
//    public void testFinalAmounts6() throws Exception{
//        CalcUtils calcUtils = new CalcUtils(Constants.Tests.DESIRED_AMT, Constants.Tests.ZERO_STRENGTH,
//                Constants.Tests.TEST_BASE, Constants.Tests.ALT_DESIRED_PERCS);
//
//        calcUtils.setFlavors(Constants.Tests.TEST_FLAVORS);
//
//        calcUtils.calcAmounts(); //calculate amounts
//        assertArrayEquals("Calc test 6: ", Constants.Tests.EXPECTED_FAs[5], calcUtils.getFinalMills().toArray());
//    }




//    /** Tests for adding flavors & adding/updating percentages
//     *  =======PASSED=====uncommented
//     */

//
//
//    //================test to see if percents updating correclty===================== PASSED
//    @Test
//    public void testDesiredPGChange() throws Exception {
//        calcUtils.setPercent("10", Constants.DESIRED_PERC_LABELS[0]);
//        assertSame("Check to see PG value changed", 10, calcUtils.getDesiredPercents().get(0));
//        assertSame("Check to see that changing PG changed VG.", 90, calcUtils.getDesiredPercents().get(1));
//    }
//
//    @Test //PASSED
//    public void testDesiredVGChange() throws Exception{
//        calcUtils.setPercent("10", Constants.DESIRED_PERC_LABELS[1]);
//        assertSame("Check to see VG value changed", 10, calcUtils.getDesiredPercents().get(1));
//        assertSame("Check to see that changing VG changed PG.", 90, calcUtils.getDesiredPercents().get(0));
//    }
//
//    @Test //PASSED
//    public void testBasePGChange() throws Exception{
//        calcUtils.setBasePercent("10", Constants.BASE_PERC_LABELS[0]);
//        assertSame("Base percent PG just changed.", 10, calcUtils.getBasePercents().get(0));
//        assertSame("Did changing base PG change base VG.", 90, calcUtils.getBasePercents().get(1));
//    }
//
//    @Test //PASSED
//    public void testBaseVGChange() throws Exception{
//        calcUtils.setBasePercent("10", Constants.BASE_PERC_LABELS[1]);
//        assertSame("Base percent VG just changed.", 10, calcUtils.getBasePercents().get(1));
//        assertSame("Did changing base VG change base VG.", 90, calcUtils.getBasePercents().get(0));
//    }
//
//    @Test //PASSED
//    public void testAddingFlavors() throws Exception{
//
//        Array<Flavor> flavors = Constants.Tests.TEST_FLAVORS;;
//
//        calcUtils.addFlavor(Constants.Tests.FLAV1);
//        calcUtils.addFlavor(Constants.Tests.FLAV2);
//        calcUtils.addFlavor(Constants.Tests.FLAV3);
//
//        assertEquals("Flavor1 in array", flavors.get(0), Constants.Tests.FLAV1);
//        assertEquals("Flavor2 in array", flavors.get(1), Constants.Tests.FLAV2);
//        assertEquals("Flavor3 in array", flavors.get(2), Constants.Tests.FLAV3);
//
//    }
}