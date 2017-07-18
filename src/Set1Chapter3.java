// Garrett Scholtes
// 2017-07-17

import java.util.*;
import java.math.BigInteger;

public class Set1Chapter3 {

    public static void main(String[] args) throws Exception {
        String input = new String("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736");
        String hexKey;
        String asciiKey;
        for (int i = 0; i < 0x100; i++) {
            asciiKey = String.join("", Collections.nCopies(input.length()/2, Character.toString((char)(i))));
            hexKey = BasicCrypto.ascii2hex(asciiKey);
            //System.out.println((new Integer(i)).toString() + " : " + hexKey.equals(BasicCrypto.ascii2hex(BasicCrypto.hex2ascii(hexKey))));
            System.out.println(BasicCrypto.hex2ascii(BasicCrypto.xor(hexKey, input)));
        }
    }
}