package pl.edu.pw.elka.pfus.eds.util.hash.impl;

import com.google.common.annotations.VisibleForTesting;
import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.util.hash.InputStreamHasher;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

/**
 * Implementacja {@link pl.edu.pw.elka.pfus.eds.util.hash.InputStreamHasher} działająca ze skrótem MD5.
 */
public abstract class AbstractInpuStreamHasher extends AbstractHasher<InputStream> implements InputStreamHasher {
    private static final Logger logger = Logger.getLogger(AbstractInpuStreamHasher.class);

    public static final int POSITIVE_SIGNUM = 1;

    @Override
    public String getString(InputStream inputStream) {
        MessageDigest digest = getMessageDigest();
        updateDigestByInputStream(inputStream, digest);
        byte[] hashedBytes = digest.digest();
        String hash = getStringFromBytes(hashedBytes);
        logger.debug("hash: " + hash);
        return hash;
    }

    protected abstract MessageDigest getMessageDigest();

    private void updateDigestByInputStream(InputStream inputStream, MessageDigest digest) {
        DigestInputStream digestInputStream = getDigestInputStream(inputStream, digest);
        byte[] bytes = new byte[2048];
        try {
            while(digestInputStream.read(bytes, 0, bytes.length) > 0) { }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @VisibleForTesting
    DigestInputStream getDigestInputStream(InputStream inputStream, MessageDigest digest) {
        return new DigestInputStream(inputStream, digest);
    }
}
