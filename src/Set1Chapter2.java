// Garrett Scholtes
// 2017-07-17

import java.util.*;
import java.math.BigInteger;

public class Set1Chapter2 {

    public static void main(String[] args) {
        String givenString1 = new String("1c0111001f010100061a024b53535009181c");
        String givenString2 = new String("686974207468652062756c6c277320657965");
        String outputExpected = new String("746865206b696420646f6e277420706c6179");
        String outputActual = BasicCrypto.xor(givenString1, givenString2);

        System.out.println("Set 1 Chapter 2");

        System.out.println(outputActual);

        System.out.println(outputExpected.equals(outputActual) ? "Pass!" : "Fail!");
    }
}