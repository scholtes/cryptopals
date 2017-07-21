// Garrett Scholtes
// 2017-07-20

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;

public class Set1Chapter6 {

    public static void main(String[] args) throws Exception {
        String base64input = readFile6();
        String asciiInput = new String(Base64.getDecoder().decode(base64input));

        int keySize = BasicCrypto.autoGetKeySize(asciiInput.toCharArray());
        System.out.println("GOT KEY SIZE! keysize = " + keySize);

        String guessedKey = BasicCrypto.autoGuessRepeatedKey(asciiInput.toCharArray(), keySize);
        System.out.println("GOT KEY! key = '" + guessedKey + "'");

        System.out.println("---------------------------");

        System.out.println(BasicCrypto.decryptRepeatingXor(BasicCrypto.ascii2hex(asciiInput), guessedKey));
    }

    public static void hammingTest() {
        String test1 = new String("this is a test");
        String test2 = new String("wokka wokka!!!");

        System.out.println(BasicCrypto.hamming(test1.toCharArray(), test2.toCharArray()));
    }

    public static String readFile6() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        Scanner scn = new Scanner(new File("./dat/6.txt"));
        while(scn.hasNextLine()) {
            sb.append(scn.nextLine());
        }
        scn.close();

        return sb.toString();
    }
}