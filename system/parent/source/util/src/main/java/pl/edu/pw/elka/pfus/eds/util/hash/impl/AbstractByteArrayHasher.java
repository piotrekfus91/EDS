package pl.edu.pw.elka.pfus.eds.util.hash.impl;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.util.hash.ByteArrayHasher;

import java.security.MessageDigest;

public abstract class AbstractByteArrayHasher extends AbstractHasher<byte[]> implements ByteArrayHasher {
    private static final Logger logger = Logger.getLogger(AbstractByteArrayHasher.class);

    @Override
    public String getString(byte[] input) {
        MessageDigest digest = getMessageDigest();
        byte[] hashedBytes = digest.digest(input);
        String hash = getStringFromBytes(hashedBytes);
        logger.debug("hash: " + hash);
        return hash;
    }

    protected abstract MessageDigest getMessageDigest();
}
