package pl.edu.pw.elka.pfus.eds.util.hash.impl;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.util.hash.ByteArrayHasher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5ByteArrayHasher extends AbstractByteArrayHasher implements ByteArrayHasher {
    private static final Logger logger = Logger.getLogger(Md5ByteArrayHasher.class);

    @Override
    public int getHashStringLength() {
        return 32;
    }

    @Override
    public int getBytesNumber() {
        return 16;
    }

    @Override
    protected MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.fatal(e.getMessage(), e);
            throw new ExceptionInInitializerError(e);
        }
    }
}
