package pl.edu.pw.elka.pfus.eds.logic.statistics;

import pl.edu.pw.elka.pfus.eds.domain.dao.dto.DocumentsNumberInDaysDto;

import java.util.List;

/**
 * Interfejs dostarczający dane dla statystyk.
 */
public interface Statistics {
    /**
     * Zwraca listę ilości ostatnio uploadowanych plików
     * przez bieżącego użytkownika.
     *
     * @param days ilość dni.
     * @return statystyka uploadu.
     */
    List<DocumentsNumberInDaysDto> documentsUploadedInLastDays(int days);
}
