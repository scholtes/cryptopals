// Garrett Scholtes
// 2017-07-17

import java.math.BigInteger;
import java.util.Base64;
import java.util.Collections;
import java.io.UnsupportedEncodingException;

public class BasicCrypto {

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
}