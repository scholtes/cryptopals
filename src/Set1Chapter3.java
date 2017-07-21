// Garrett Scholtes
// 2017-07-17

import java.util.*;
import java.math.BigInteger;

public class Set1Chapter3 {

    public static void main(String[] args) throws Exception {
        challengeTest();
    }

    public static void challengeTest() throws Exception {
        String input = new String("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736");
        char guessedKey = BasicCrypto.autoGetKey(input);
        String plaintext = BasicCrypto.decryptSingleByteXor(input, guessedKey);
        System.out.println(input);
        System.out.println(plaintext);
    }

    public static void hobbitTest() throws Exception {
        // Here is a test plaintext
        String input = new String("It had a perfectly round door like a porthole, painted green, with a shiny yellow brass knob in the exact middle. The door opened on to a tube-shaped hall like a tunnel: a very comfortable tunnel without smoke, with panelled walls, and floors tiled and carpeted, provided with polished chairs, and lots and lots of pegs for hats and coats - the hobbit was fond of visitors. The tunnel wound on and on, going fairly but not quite straight into the side of the hill - The Hill, as all the people for many miles round called it - and many little round doors opened out of it, first on one side and then on another. No going upstairs for the hobbit: bedrooms, bathrooms, cellars, pantries (lots of these), wardrobes (he had whole rooms devoted to clothes), kitchens, dining-rooms, all were on the same floor, and indeed on the same passage. The best rooms were all on the left-hand side (going in), for these were the only ones to have windows, deep-set round windows looking over his garden, and meadows beyond, sloping down to the river. ");
        // And here is a test cipher text, encrypted with some arbitrary key (some char between 0 and FF... in this case, '[' == 5b)
        String encryptedInput = BasicCrypto.encryptSingleByteXor(input, '[');

        // Attempt to guess the key from the cipher text alone
        char guessedKey = BasicCrypto.autoGetKey(encryptedInput);
        // Guess the plaintext using the guessed key
        String guessedPlaintext = BasicCrypto.decryptSingleByteXor(encryptedInput, guessedKey);

        if (guessedPlaintext.equals(input)) {
            System.out.println("Hobbit test passed!");
        } else {
            System.out.println("Hobit test failed!");
        }
    }

    public static void randomStuffTest() throws Exception {
        String input = new String("1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736");
        String hexKey;
        for (int i = 0; i < 0x100; i++) {
            // Very messy output.... visualize all possible plaintexts, just for some validation
            System.out.println(Integer.toString(i, 16) + " : " + BasicCrypto.decryptSingleByteXor(input, (char)(i)));

            // This is just some validation that the encryption and decryption are workign properly... they should always exactly undo each other
            if (input.equals(BasicCrypto.encryptSingleByteXor(BasicCrypto.decryptSingleByteXor(input, (char)(i)), (char)(i)))) {
                System.out.println("pass");
            } else {
                System.out.println("FAIL!!!!!!!!!!!!!!!!!");
            }
        }
        System.out.println((int)BasicCrypto.autoGetKey(input));
    }
}