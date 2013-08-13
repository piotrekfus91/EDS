package pl.edu.pw.elka.pfus.eds.logic.mime.type.detector;

import pl.edu.pw.elka.pfus.eds.domain.entity.MimeType;

import java.io.File;
import java.io.InputStream;

/**
 * Interfejs odpowiedzialny za sprawdzanie typu MIME.
 */
public interface MimeTypeDetector {
    /**
     * Sprawdza typ MIME dla podanego pliku.
     *
     * @param file plik do sprawdzenia.
     * @return typ mime.
     */
    MimeType detect(File file);

    /**
     * Sprawdza typ MIME dla podanego strumienia.
     *
     * @param inputStream strumień do sprawdzenia.
     * @return typ mime.
     */
    MimeType detect(InputStream inputStream);

    /**
     * Sprawdza typ MIME podanych bajtów wejściowych.
     *
     * @param input dane wejściowe.
     * @return typ mime.
     */
    MimeType detect(byte[] input);
}
