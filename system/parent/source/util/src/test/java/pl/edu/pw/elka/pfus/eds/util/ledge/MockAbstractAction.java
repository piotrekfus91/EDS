package pl.edu.pw.elka.pfus.eds.util.ledge;

import org.objectledge.context.Context;
import org.objectledge.pipeline.ProcessingException;

public class MockAbstractAction extends AbstractAction {

    @Override
    public void process(Context context) throws ProcessingException {
        // implementacja mock - ta metoda nas nie interesuje
        // mock na potrzeby testow innych metod
    }
}
