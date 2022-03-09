
import junitdemo.NTTest;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.InputMismatchException;

public class TestCase {
    @BeforeAll
    public static void beforeAll() {
        System.out.println("Before All");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("After All");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("Before Each");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("After Each");
    }

    @Test
    @DisplayName("Kiem thu cho so chan nguyen to")
    public void test01() {
        int input = 2;
        boolean expected = true;
        boolean actual = NTTest.isNt(input);

        Assertions.assertEquals(expected, actual);

    }

    @Test
    @DisplayName("Kiem thu cho so le nguyen to")
    public void test02() {
        int input = 3;
        boolean expected = true;
        boolean actual = NTTest.isNt(input);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void test03() {
        boolean actual = NTTest.isNt(4);
        Assertions.assertFalse(actual);
    }

    @Test
    @DisplayName("Kiem tra ngoai le")
    public void test04() {
        int n = -1;

        Assertions.assertThrows(InputMismatchException.class, () -> {
            NTTest.isNt(n);
        });
    }

    @Test
    @DisplayName("Kiem tra timeout")
    public void test05() {
        Assertions.assertTimeout(Duration.ofMillis(100), () -> {
            NTTest.isNt(100);
        });
    }

    @ParameterizedTest
    @CsvSource({"3, true", "143, false"})
    public void testOddNumbers(int n, boolean expected){
        boolean actual = NTTest.isNt(n);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/datatest.csv", numLinesToSkip = 1)
    public void testNumbers(int n, boolean expected){
        boolean actual = NTTest.isNt(n);
        Assertions.assertEquals(expected, actual);
    }

}
