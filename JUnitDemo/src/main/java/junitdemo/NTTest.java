package junitdemo;

import java.util.InputMismatchException;

public class NTTest {
    public static boolean isNt(int n) {
        if (n < 0)
           throw new InputMismatchException();

        if (n >= 2) {
            for (int i = 2; i < Math.sqrt(n); i++)
                if (n % i == 0)
                    return false;

            return true;
        }

        return false;
    }
}
