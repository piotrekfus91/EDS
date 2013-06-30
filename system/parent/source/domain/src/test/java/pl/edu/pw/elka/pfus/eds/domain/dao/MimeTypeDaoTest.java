package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.objectledge.context.Context;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.impl.HibernateMimeTypeDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.MimeType;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

import static org.fest.assertions.Assertions.assertThat;

public class MimeTypeDaoTest extends IdentifableDaoTest<MimeType, MimeTypeDao> {
    private MimeTypeDao mimeTypeDao;

    @Test
    public void testDefaultEnabled() throws Exception {
        MimeType mimeType = getSampleEntity();
        getDao().persist(mimeType);
        assertThat(mimeType.isEnabled()).isTrue();
    }

    @Test
    public void testDisable() throws Exception {
        MimeType mimeType = getSampleEntity();
        getDao().persist(mimeType);
        mimeType.setEnabled(false);
        getDao().persist(mimeType);
        assertThat(mimeType.isEnabled()).isFalse();
    }

    @Override
    public MimeTypeDao getDao() {
        return mimeTypeDao;
    }

    @Override
    public void setDao(MimeTypeDao dao) {
        this.mimeTypeDao = dao;
    }

    @Override
    protected void prepareDao(SessionFactory sessionFactory, Context context) {
        mimeTypeDao = new HibernateMimeTypeDao(context, sessionFactory);
    }

    @Override
    protected MimeType getSampleEntity() {
        MimeType mimeType = new MimeType();
        mimeType.setType("type");
        mimeType.setDefaultExtension("ext");
        mimeType.setDescription("");
        return mimeType;
    }

    @Override
    protected void updateEntity(MimeType entity) {
        entity.setType(entity.getType() + entity.getType());
    }
}
