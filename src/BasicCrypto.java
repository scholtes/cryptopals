// Garrett Scholtes
// 2017-07-17

import java.math.BigInteger;
import java.util.Base64;

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
        return (new BigInteger(str1, 0x10)).xor(new BigInteger(str2, 0x10)).toString(0x10);
    }
}