package pl.edu.pw.elka.pfus.eds.util.file.system;

public class PathHelper {
    /**
     * Zamienia początkową tildę na katalog domowy użytkownika.
     *
     * @param basePath ścieżka wejściowa.
     * @return obliczona ścieżka.
     */
    public static String countFileSystemRoot(String basePath) {
        if(basePath.startsWith("~"))
            return System.getProperty("user.home") + basePath.substring("~".length());
        return basePath;
    }
}
