package pl.edu.pw.elka.pfus.eds.util.hash;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Implementacja {@link InputStreamHasher} działająca ze skrótem MD5.
 */
public class Md5InpuStreamHasher implements InputStreamHasher {
    private static final Logger logger = Logger.getLogger(Md5InpuStreamHasher.class);
    public static final int POSITIVE_SIGNUM = 1;
    public static final int NUMBER_OF_BYTES = 16;
    public static final int NUMBER_OF_CHARS_IN_STRING_REPRESNTATION = 32;

    @Override
    public String getString(InputStream inputStream) {
        MessageDigest digest = getMessageDigest();
        updateDigestByInputStream(inputStream, digest);
        byte[] hashBytes = digest.digest();
        BigInteger hashNumber = new BigInteger(POSITIVE_SIGNUM, hashBytes);
        return addStartingPaddingOfZeros(hashNumber.toString(NUMBER_OF_BYTES));
    }

    @VisibleForTesting
    String addStartingPaddingOfZeros(String hash) {
        return Strings.padStart(hash, NUMBER_OF_CHARS_IN_STRING_REPRESNTATION, '0');
    }

    private void updateDigestByInputStream(InputStream inputStream, MessageDigest digest) {
        DigestInputStream digestInputStream = new DigestInputStream(inputStream, digest);
        byte[] bytes = new byte[2048];
        try {
            while(digestInputStream.read(bytes, 0, bytes.length) > 0) { }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.fatal(e.getMessage(), e);
            throw new ExceptionInInitializerError(e);
        }
    }
}
