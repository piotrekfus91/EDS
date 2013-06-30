package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.impl.HibernateTagDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

public class TagDaoTest extends IdentifableDaoTest<Tag, TagDao> {
    private TagDao tagDao;

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
        tagDao = new HibernateTagDao(context, sessionFactory);
    }

    @Override
    protected Tag getSampleEntity() {
        Tag tag = new Tag();
        tag.setValue("sample value");
        return tag;
    }

    @Override
    protected void updateEntity(Tag entity) {
        entity.setValue(entity.getValue() + entity.getValue());
    }
}
