package pl.edu.pw.elka.pfus.eds.web.container;

import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Startable;

public class ContainerFactory implements Startable {
    private static MutablePicoContainer container;

    public ContainerFactory(MutablePicoContainer container) {
        this.container = container;
    }

    public static MutablePicoContainer getContainer() {
        return container;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }
}
