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
}