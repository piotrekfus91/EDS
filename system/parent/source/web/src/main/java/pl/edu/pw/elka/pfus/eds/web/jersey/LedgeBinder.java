package pl.edu.pw.elka.pfus.eds.web.jersey;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.PicoContainer;

import java.util.Map;
import java.util.Map.Entry;

class LedgeBinder
    extends AbstractBinder
{

    private PicoContainer container;

    public LedgeBinder(MutablePicoContainer container)
    {
        super();
        this.container = container.getParent();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    protected void configure()
    {
        final Map<Class<?>, Object> proxies = ComponentProxyFactory.proxies(container, getClass().getClassLoader());
        for(Entry<Class<?>, Object> entry : proxies.entrySet())
        {
            final Class key = entry.getKey();
            bind(entry.getValue()).to(key);
        }
    }
}
