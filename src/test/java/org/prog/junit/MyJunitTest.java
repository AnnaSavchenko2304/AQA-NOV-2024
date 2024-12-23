package org.prog.junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

public class MyJunitTest {

    @BeforeAll
    public static void setUp() {
        System.out.println("Start Browser");
    }

    @BeforeEach
    public void beforeTest() {
        System.out.println("=================");
    }

    @AfterEach
    public void afterTest() {
        System.out.println("****************");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("Close Browser");
    }

    @Test
    public void test1() {
        System.out.println("Hello Test 1!");
    }

    @Test
    public void test2() {
        //start web driver
        //do test
        //close web driver
        System.out.println("Hello Test 2!");
        throw new RuntimeException("!!");
    }

    @ParameterizedTest
    @MethodSource("sources")
    public void parametrizedTest(String s, int i) {
        System.out.println(s + i);
    }

    @ParameterizedTest
    @MethodSource("sources")
    public void parametrizedTest2(String s, int i, String s2) {
        System.out.println(">>>>> " + s + " ----------> " + i + " " + s2);
    }

    public static Stream<Arguments> sources() {
        return Stream.of(
                Arguments.of("Random int is: ", randomInt(), "oops!"),
                Arguments.of("Random int is: ", randomInt(), "oops!"),
                Arguments.of("Random int is: ", randomInt(), "oops!"),
                Arguments.of("Random int is: ", randomInt(), "oops!"),
                Arguments.of("Random int is NOT: ", randomInt(), "oops!")
        );
    }

    public static int randomInt() {
        Random random = new Random();
        return random.nextInt(100);
    }
}
