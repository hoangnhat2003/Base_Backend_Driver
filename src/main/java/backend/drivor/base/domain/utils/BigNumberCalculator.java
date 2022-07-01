package backend.drivor.base.domain.utils;

import java.math.BigInteger;

public class BigNumberCalculator {

    public static long multiply(long val1, long val2) {
        return new BigInteger(String.valueOf(val1)).multiply(new BigInteger(String.valueOf(val2))).longValueExact();
    }
}
