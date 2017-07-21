// Garrett Scholtes
// 2017-07-20

import java.util.*;
import java.io.File;
import java.math.BigInteger;

public class Set1Chapter5 {

    public static void main(String[] args) throws Exception {
        String input = new String("Burning 'em, if you ain't quick and nimble\nI go crazy when I hear a cymbal");
        String expectedCipher = new String("0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f");
        String key = new String("ICE");

        String cipherActual = BasicCrypto.encryptRepeatingXor(input, key);
        System.out.println(cipherActual);
        if (cipherActual.toLowerCase().equals(expectedCipher)) {
            System.out.println("Pass");
        } else {
            System.out.println("Fail");
        }

        String decryptedPlaintext = BasicCrypto.decryptRepeatingXor(cipherActual, key);
        System.out.println(decryptedPlaintext);
        if (decryptedPlaintext.equals(input)) {
            System.out.println("Pass");
        } else {
            System.out.println("Fail");
        }
    }
}