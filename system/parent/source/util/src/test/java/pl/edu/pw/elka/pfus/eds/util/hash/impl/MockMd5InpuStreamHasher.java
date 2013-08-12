package pl.edu.pw.elka.pfus.eds.util.hash.impl;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockMd5InpuStreamHasher extends Md5InputStreamHasher {
    private static final Logger logger = Logger.getLogger(MockMd5InpuStreamHasher.class);

    @Override
    DigestInputStream getDigestInputStream(InputStream inputStream, MessageDigest digest) {
        DigestInputStream digestInputStream = mock(DigestInputStream.class);
        try {
            when(digestInputStream.read(any(byte[].class), anyInt(), anyInt())).thenThrow(new IOException());
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return digestInputStream;
    }
}
