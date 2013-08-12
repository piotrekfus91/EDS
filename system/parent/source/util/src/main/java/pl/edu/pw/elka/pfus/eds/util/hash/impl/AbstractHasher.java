package pl.edu.pw.elka.pfus.eds.util.hash.impl;

import com.google.common.base.Strings;
import pl.edu.pw.elka.pfus.eds.util.hash.Hasher;

import java.math.BigInteger;

public abstract class AbstractHasher<T> implements Hasher<T> {
    protected String addStartingPaddingOfZeros(String hash) {
        return Strings.padStart(hash, getHashStringLength(), '0');
    }

    protected String getStringFromBytes(byte[] hashedBytes) {
        BigInteger hashNumber = new BigInteger(AbstractInpuStreamHasher.POSITIVE_SIGNUM, hashedBytes);
        return addStartingPaddingOfZeros(hashNumber.toString(getBytesNumber()));
    }
}
