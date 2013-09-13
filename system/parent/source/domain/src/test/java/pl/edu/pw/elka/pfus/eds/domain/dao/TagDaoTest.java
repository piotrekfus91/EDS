package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.EntityFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.TagFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.impl.HibernateTagDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;
import pl.edu.pw.elka.pfus.eds.domain.validator.MockEntityValidator;

public class TagDaoTest extends IdentifableDaoTest<Tag, TagDao> {
    private TagDao tagDao;
    private TagFactory factory = new TagFactory();

    @Override
    public TagDao getDao() {
        return tagDao;
    }

    @Override
    public void setDao(TagDao dao) {
        this.tagDao = dao;
    }

    @Override
    protected void prepareDao(SessionFactory sessionFactory, Context context) {
        tagDao = new HibernateTagDao(context, sessionFactory, new MockEntityValidator());
    }

    @Override
    protected EntityFactory<Tag> getFactory() {
        return factory;
    }

    @Override
    protected void updateEntity(Tag entity) {
        entity.setValue(entity.getValue() + entity.getValue());
    }
}
