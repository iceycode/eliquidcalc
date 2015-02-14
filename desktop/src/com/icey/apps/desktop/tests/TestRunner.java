package com.icey.apps.desktop.tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Scanner;

/** Runs tests
 *
 * Created by Allen on 2/4/15.
 */
public class TestRunner {

    public static void main(String[] args){

        System.out.print("Choose test to run: \n(1) CalcUtilsTest1 \n" +
                "(2) SaveManagerTest \n" +
                "ENTER: ");
        Scanner reader = new Scanner(System.in);
        int num = reader.nextInt();

        runTest(num);
    }


    public static void runTest(int test){

        Result result;

        if (test == 1)
            result = JUnitCore.runClasses(CalcUtilsTest.class);
        else
            result = JUnitCore.runClasses(SaveManagerTest.class);

        //prints any failures
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
    }

}
