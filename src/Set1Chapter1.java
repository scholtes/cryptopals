// Garrett Scholtes
// 2017-07-17

import java.util.*;
import java.math.BigInteger;

public class Set1Chapter1 {

    public static void main(String[] args) {
        BigInteger givenBigInt = new BigInteger("49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d", 0x10);
        String base64expected = new String("SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t");
        String base64actual = BasicCrypto.bigInt2base64(givenBigInt);
        BigInteger bigIntActual = BasicCrypto.base642bigInt(base64expected);

        System.out.println(base64actual);
        System.out.println(bigIntActual.toString(0x10));

        System.out.println(base64expected.equals(base64actual) ? "Pass!" : "Fail!");
        System.out.println(givenBigInt.equals(bigIntActual) ? "Pass!" : "Fail!");
    }
}