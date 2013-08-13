package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.objectledge.context.Context;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.EntityFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.MimeTypeFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.impl.HibernateMimeTypeDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.MimeType;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

import static org.fest.assertions.Assertions.assertThat;

public class MimeTypeDaoTest extends IdentifableDaoTest<MimeType, MimeTypeDao> {
    private MimeTypeDao mimeTypeDao;
    private MimeTypeFactory factory = new MimeTypeFactory();

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

    @Test
    public void testByNameForNotFound() throws Exception {
        assertThat(getDao().findByType("type")).isNull();
    }

    @Test
    public void testByNameForFound() throws Exception {
        MimeType mimeType = getSampleEntity();
        mimeType.setType("type");
        getDao().persist(mimeType);

        assertThat(getDao().findByType("type")).isEqualTo(mimeType);
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
    protected EntityFactory<MimeType> getFactory() {
        return factory;
    }

    @Override
    protected void updateEntity(MimeType entity) {
        entity.setType(entity.getType() + entity.getType());
    }
}
