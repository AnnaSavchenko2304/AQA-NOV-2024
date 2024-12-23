package org.prog.testng;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class MyTestNGTest {

    @BeforeSuite
    public void setUp() {
        System.out.println("===========RUN BEFORE ALL TESTS=============");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("before MyTestNGTest");
    }

    @BeforeMethod
    public void beforeEach(){
        System.out.println(".................");
    }

    @Test
    public void testNg11() {
        System.out.println("Test # 1.1");
    }

    @Test
    public void testNg12() {
        int i = 1;
        int j = 2;
        Assert.assertTrue(i > j, "Check for numbers i and j has failed, expected i > j");
    }

    @Test
    public void testNg13() {
        System.out.println("Test # 1.3");
    }
}
