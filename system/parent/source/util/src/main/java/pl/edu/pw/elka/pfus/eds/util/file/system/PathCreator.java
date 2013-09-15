package pl.edu.pw.elka.pfus.eds.util.file.system;

/**
 * Interfejs odpowiedzialny za utworzenie ścieżki potrzebnej dla pliku.
 */
public interface PathCreator {
    /**
     * Tworzy katalogi niezbędne do działania aplikacji
     * podstawie {@link pl.edu.pw.elka.pfus.eds.util.config.Config},
     * wpisów:
     * <ul>
     *     <li>{@code file_system_root}</li>
     *     <li>{@code index_dir}</li>
     * </ul>.
     */
    void createNecessaryDirectories();
    /**
     * Tworzy ścieżkę począwszy od podanego {@code rootPath}
     * z podfolderami zgodnymi z {@code hashedPath}.
     *
     * @param rootPath katalog główny.
     * @param hashedPath podkatalogi do utworzenia.
     */
    void createPath(String rootPath, String[] hashedPath);
}
