package pl.edu.pw.elka.pfus.eds.logic.mime.type.detector;

import com.google.common.annotations.VisibleForTesting;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.hibernate.Session;
import pl.edu.pw.elka.pfus.eds.domain.dao.MimeTypeDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.MimeType;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidMimeTypeException;
import pl.edu.pw.elka.pfus.eds.logic.exception.MimeTypeDetectionException;

import java.io.*;

public class TikaMimeTypeDetector implements MimeTypeDetector {
    private static final Logger logger = Logger.getLogger(TikaMimeTypeDetector.class);

    private MimeTypeDao mimeTypeDao;

    private Tika tika;

    public TikaMimeTypeDetector(MimeTypeDao mimeTypeDao) {
        this.mimeTypeDao = mimeTypeDao;
        tika = new Tika();
    }

    @Override
    public MimeType detect(File file) {
        try(FileInputStream fileInputStream = new FileInputStream(file)) {
            return detect(fileInputStream);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new MimeTypeDetectionException(e);
        }
    }

    @Override
    public MimeType detect(InputStream inputStream) {
        try {
            String detected = tika.detect(inputStream);
            logger.info(detected);
            return findMimeTypeOrThrowException(detected);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new MimeTypeDetectionException();
        }
    }

    @Override
    public MimeType detect(byte[] input) {
        try(ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(input)) {
            return detect(byteArrayInputStream);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new MimeTypeDetectionException(e);
        }
    }

    @Override
    public void setSession(Session session) {
        mimeTypeDao.setSession(session);
    }

    @VisibleForTesting
    MimeType findMimeTypeOrThrowException(String mimeTypeAsString) {
        mimeTypeDao.clear();
        MimeType mimeType = mimeTypeDao.findByType(mimeTypeAsString);
        if(mimeType == null)
            throw new InvalidMimeTypeException(mimeTypeAsString);
        return mimeType;
    }
}
