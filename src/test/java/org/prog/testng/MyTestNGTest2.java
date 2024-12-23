package org.prog.testng;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyTestNGTest2 {

    @BeforeTest
    public void beforeTest() {
        System.out.println("----------------------------------");
    }

    @BeforeClass
    public void beforeClass() {
        System.out.println("before MyTestNGTest2");
    }

    @BeforeMethod
    public void beforeEach(){
        System.out.println("!!!!!!!!!!!!!!!!!!");
    }

    @Test
    public void testNg21() {
        System.out.println("test # 2.1");
    }

    @Test
    public void testNg22() {
        System.out.println("test # 2.2");
    }

    @Test
    public void testNg23() {
        System.out.println("test # 2.3");
    }
}
