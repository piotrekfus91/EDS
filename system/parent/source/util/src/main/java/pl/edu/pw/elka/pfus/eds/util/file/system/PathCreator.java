package pl.edu.pw.elka.pfus.eds.util.file.system;

/**
 * Interfejs odpowiedzialny za utworzenie ścieżki potrzebnej dla pliku.
 */
public interface PathCreator {
    /**
     * Tworzy katalog główny aplikacji na podstawie {@link pl.edu.pw.elka.pfus.eds.util.config.Config},
     * wpisu {@code file_system_root}.
     */
    void createFileSystemRoot();
    /**
     * Tworzy ścieżkę począwszy od podanego {@code rootPath}
     * z podfolderami zgodnymi z {@code hashedPath}.
     *
     * @param rootPath katalog główny.
     * @param hashedPath podkatalogi do utworzenia.
     */
    void createPath(String rootPath, String[] hashedPath);
}
