package pl.edu.pw.elka.pfus.eds.logic.mime.type.detector;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.MimeTypeDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.MimeType;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidMimeTypeException;

import java.io.InputStream;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TikaMimeTypeDetectorTest {
    private TikaMimeTypeDetector detector;
    private MimeTypeDao mimeTypeDao;
    private MimeType mimeType;

    @BeforeMethod
    private void setUp() {
        mimeType = new MimeType();
        mimeTypeDao = mock(MimeTypeDao.class);
        detector = new TikaMimeTypeDetector(mimeTypeDao);
    }

    @Test
    public void testDetecting() throws Exception {
        InputStream stream = TikaMimeTypeDetectorTest.class.getClassLoader().getResourceAsStream("Gerrard20.jpeg");
        when(mimeTypeDao.findByType(anyString())).thenReturn(mimeType);

        assertThat(detector.detect(stream)).isEqualTo(mimeType);
    }

    @Test(expectedExceptions = InvalidMimeTypeException.class)
    public void testFindMimeTypeForException() throws Exception {
        when(mimeTypeDao.findByType(anyString())).thenThrow(new InvalidMimeTypeException(""));

        detector.findMimeTypeOrThrowException("sdjfj");
    }

    @Test
    public void testFindMimeTypeForSuccess() throws Exception {
        when(mimeTypeDao.findByType("type")).thenReturn(mimeType);

        assertThat(detector.findMimeTypeOrThrowException("type")).isEqualTo(mimeType);
    }
}
