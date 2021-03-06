// Garrett Scholtes
// 2017-07-17

import java.math.BigInteger;
import java.util.Base64;
import java.util.Collections;
import java.util.Arrays;
import java.io.UnsupportedEncodingException;
import java.lang.Math;

public class BasicCrypto {

    // Data found from Googling around various sources
    static final int ALPHA_SIZE = 26;
    static final int LETTER_A = (int)('A');
    static final double AVG_WORD_SIZE = 5.1; // Number of letters per avg english word
    static final double[] FREQUENCIES_EN = {8.167,1.492,2.782,4.253,12.702,2.228,2.015,6.094,6.966,0.153,0.772,4.025,2.406,6.749,7.507,1.929,0.095,5.987,6.327,9.056,2.758,0.978,2.360,0.150,1.974,0.074};

    // These are rather arbitrary
    // Smallest key size to guess and test
    static final int MIN_KEY_SIZE = 1;
    // Largest key size to guess and test
    static final int MAX_KEY_SIZE = 50;
    // Number of sample block pairs to compare
    static final int BLOCK_SAMPLES = 20;


    public static String bigInt2base64(BigInteger bigInt) {
        return new String(Base64.getEncoder().encode(bigInt.toByteArray()));
    }

    public static BigInteger base642bigInt(String base64) {
        return new BigInteger(Base64.getDecoder().decode(base64));
    }

    // Probably better to just work directly with big integers in some cases,
    // but the challenge asks for this so it's here and might prove useful anyways
    public static String xor(String str1, String str2) {
        assert str1.length() == str2.length();
        String notZeroPad = (new BigInteger(str1, 0x10)).xor(new BigInteger(str2, 0x10)).toString(0x10);
        if (notZeroPad.length() < str1.length()) {
            notZeroPad = String.join("", Collections.nCopies(str1.length() - notZeroPad.length(), "0")) + notZeroPad;
        }
        return notZeroPad;
    }

    // Takes a hex string, outputs an ASCII string
    public static String hex2ascii(String hexString) throws Exception {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hexString.length(); i += 2) {
            String str = hexString.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    // Takes an ASCII string, outputs hex string
    public static String ascii2hex(String asciiString) {
        char[] chars = asciiString.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(String.format("%02X", (int) chars[i]));
        }
        return hex.toString();
    }

    // Takes a hex string cipher text and a single character key, and creates a
    // hex string key whose length is equal to the cipher text
    public static String repeatSingleByteKey(char key, String hexCipher) throws Exception {
        String asciiKey = String.join("", Collections.nCopies(hexCipher.length()/2, Character.toString(key)));
        String hexKey = ascii2hex(asciiKey);
        return hexKey;
    }

    // Decrypt a string with a repeating single byte key, with the input string in the hex encoded format, and the
    // output string in the ascii format.  This is symmetric, with the exception of the ascii/hex conversion.
    public static String decryptSingleByteXor(String hexCipher, char key) throws Exception {
        String repeatedKey = repeatSingleByteKey(key, hexCipher);
        return hex2ascii(xor(repeatedKey, hexCipher));
    }

    // Encrypt a string with a repeating single byte key, with the input string in ASCII format, and the
    // output string in the hex encoded format.
    public static String encryptSingleByteXor(String asciiPlaintext, char key) throws Exception {
        String hexPlaintext = ascii2hex(asciiPlaintext);
        String repeatedKey = repeatSingleByteKey(key, hexPlaintext);
        return xor(repeatedKey, hexPlaintext);
    }

    // Using a repeating key 'key', xor it with the plaintext and return the hex encoded representation
    public static String encryptRepeatingXor(String asciiPlaintext, String keyString) throws Exception {
        char[] plaintext = asciiPlaintext.toCharArray();
        char[] key = keyString.toCharArray();
        char[] ciphertext = new char[plaintext.length];
        for(int i = 0; i < plaintext.length; i++) { 
            ciphertext[i] = (char)(plaintext[i] ^ key[i % key.length]);
        }
        return ascii2hex(new String(ciphertext));
    }

    // Using a repeating key 'key', xor it with the plaintext and return the ascii encoded representation
    // (basically the same as encryptSingleByteXor, with the exception of the hex/ascii business... in fact
    // this is just a wrapper function of that one.  Probably less efficient that way, but more readable
    // and less repetitive).
    public static String decryptRepeatingXor(String hexCipher, String keystring) throws Exception {
        return hex2ascii(encryptRepeatingXor(hex2ascii(hexCipher), keystring));
    }

    // Attempts to find the most likely key based on character frequency analysis on the English alphabet
    public static char autoGetKey(String hexCipher) throws Exception {
        double best_score = (double) ALPHA_SIZE;
        char best_key = (char) 0;
        double score;
        double norm;
        for(int i = 0; i < 0x100; i++) {
            score = getEnglishMetric(decryptSingleByteXor(hexCipher, (char)(i)));
            if (best_score > score) {
                best_score = score;
                best_key = (char)(i);
            }
            //////// QC ////////
            //System.out.println("" + String.format("%02X", i) + " : " + score);
            ////////////////////
        }
        return best_key;
    }

    // Given ascii text, return a metric that estimates the distance of that text 
    // from the English language.
    //
    // Smaller number means more like English.
    //
    // In it's current implementation, this is simply based on letter frequencies.
    // There are additional penalties to the score for things like not the correct
    // number of expected whitespaces or alphabet characters for English.
    public static double getEnglishMetric(String asciiText) {
        double[] frequencies = new double[ALPHA_SIZE];
        double score = 0;
        // Used for penalties... see below
        int whitespaceCount = 0;
        int alphaCount = 0;
        // A botched way to avoid dividing by zero.
        // This is okay because any strings containing no english letters will have a large (poorer) score
        double norm = 1e-5;
        char[] plaintext = asciiText.toUpperCase().toCharArray();
        for(int k = 0; k < plaintext.length; k++) {
            if ((LETTER_A <= (int)plaintext[k]) && ((int)plaintext[k] < LETTER_A + ALPHA_SIZE)) {
                frequencies[(int)plaintext[k] - LETTER_A]++;
                alphaCount++;
            } else if (plaintext[k] == ' ') {
                whitespaceCount++;
            }
        }
        for(int k = 0; k < ALPHA_SIZE; k++) {
            norm += frequencies[k];
        }
        for(int k = 0; k < ALPHA_SIZE; k++) {
            score += Math.abs(frequencies[k]/norm - FREQUENCIES_EN[k]/100);
        }

        // Penalty for having not the expected number of spaces
        double expectedWhitespace = (plaintext.length+1)/(AVG_WORD_SIZE+1);
        score += Math.abs((whitespaceCount - expectedWhitespace)/(plaintext.length + 1e-5));

        // Penalty for having not the expected number of alphabet characters
        double expectedAlpha = plaintext.length * AVG_WORD_SIZE/(AVG_WORD_SIZE+1);
        score += Math.abs((alphaCount - expectedAlpha)/(plaintext.length + 1e-5));


        return score;
    }

    // Compute number of differing bits between two char arrays
    // They are assumed to be equal length
    public static int hamming(char[] string1, char[] string2) {
        assert string1.length == string2.length;
        int bitCount = 0;
        for(int i = 0; i < string1.length; i++) {
            bitCount += Integer.bitCount(string1[i] ^ string2[i]);
        }
        return bitCount;
    }

    // Use hamming distance to guess what the probable key size is
    // for a given cihpertext that was known to be made with a repeated key
    public static int autoGetKeySize(char[] ascii) {
        assert MAX_KEY_SIZE*BLOCK_SAMPLES <= ascii.length;

        double best_norm_hamming = 8.0;
        int best_key_length = 0;

        double norm_hamming;
        for (int key_length = MIN_KEY_SIZE; key_length <= MAX_KEY_SIZE; key_length++) {
            norm_hamming = 0;
            for (int i = 0; i < BLOCK_SAMPLES; i++) {
                norm_hamming += hamming(
                        Arrays.copyOfRange(ascii, 2*i*key_length, (2*i+1)*key_length),
                        Arrays.copyOfRange(ascii, (2*i+1)*key_length, 2*(i+1)*key_length)
                        );
            }
            norm_hamming /= (BLOCK_SAMPLES * key_length);
            if (best_norm_hamming > norm_hamming) { 
                best_norm_hamming = norm_hamming;
                best_key_length = key_length;
            }
        }

        return best_key_length;
    }

    // Given a probable key size and cipher text, break the ciphertext using frequency analysis (using
    // the same function created above)
    public static String autoGuessRepeatedKey(char[] ascii, int keySize) throws Exception {
        char[] keystring = new char[keySize];
        for (int offset = 0; offset < keySize; offset++) {
            char[] transpose = new char[(ascii.length-offset)/keySize];
            // Copy every keSize'th character into a new string
            for(int i = 0; i < transpose.length; i++) {
                transpose[i] = ascii[keySize*i + offset];
            }
            // Guess another key character
            keystring[offset] = autoGetKey(ascii2hex(new String(transpose)));
        }
        return new String(keystring);
    }
}