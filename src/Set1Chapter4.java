// Garrett Scholtes
// 2017-07-20

import java.util.*;
import java.io.File;
import java.math.BigInteger;

public class Set1Chapter4 {

    public static void main(String[] args) throws Exception {
        System.out.println("Scanning file...");

        // Read the text file
        Scanner scn = new Scanner(new File("./dat/4.txt"));
        ArrayList<String> cipherTexts = new ArrayList<String>();
        while(scn.hasNextLine()) {
            cipherTexts.add(scn.nextLine());
        }
        scn.close();

        // Each iteration's information
        char key;
        String plaintext;
        double score;
        // Keep track of best score (and the interesting stuff)
        char bestKey = 'A';
        String bestPlaintext = "";
        double bestScore = 100.0;
        String bestCiphertext = "";
        int bestLineNumber = 1;
        int lineNo = 1;

        for(String ciphertext : cipherTexts) {
            key = BasicCrypto.autoGetKey(ciphertext);
            plaintext = BasicCrypto.decryptSingleByteXor(ciphertext, key);
            score = BasicCrypto.getEnglishMetric(plaintext);
            if (bestScore > score) {
                bestKey = key;
                bestPlaintext = plaintext;
                bestScore = score;
                bestCiphertext = ciphertext;
                bestLineNumber = lineNo;
            }
            //////// Quality control ////////
            //System.out.println(ciphertext.length());
            /////////////////////////////////
            lineNo++;
        }

        // Print out the best result
        System.out.println();
        System.out.println("Found it on ---> line " + bestLineNumber + " <-- !!!");
        System.out.println("Key = '" + bestKey + "' (0x" + String.format("%02X", (int)(bestKey)) + ")");
        System.out.println("Ciphertext : " + bestCiphertext);
        System.out.println("Plaintext :  " + bestPlaintext);
    }
}