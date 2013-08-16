package pl.edu.pw.elka.pfus.eds.logic.tag.cache;

import org.hibernate.Session;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

import java.util.List;

public interface TagCache {
    Tag get(Integer key);

    List<Tag> getAll();

    void rebuild();

    void setSession(Session session);
}
