package pl.edu.pw.elka.pfus.eds.security;

import org.objectledge.security.GroupNamingPolicy;
import org.objectledge.security.object.Group;
import org.objectledge.security.util.GroupSet;

public class DummyGroupNamingPolicy implements GroupNamingPolicy {
    @Override
    public GroupSet getAllGroups() {
        return GroupSet.emptySet();
    }

    @Override
    public GroupSet getSubGroups(Group root) {
        return GroupSet.emptySet();
    }
}
