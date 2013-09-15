package pl.edu.pw.elka.pfus.eds.util.file.system.init;

import org.picocontainer.Startable;
import pl.edu.pw.elka.pfus.eds.util.file.system.PathCreator;

public class FileSystemInitializer implements Startable {
    private PathCreator pathCreator;

    public FileSystemInitializer(PathCreator pathCreator) {
        this.pathCreator = pathCreator;
    }

    @Override
    public void start() {
        pathCreator.createNecessaryDirectories();
    }

    @Override
    public void stop() {

    }
}
