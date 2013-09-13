package pl.edu.pw.elka.pfus.eds.domain.dao.dto;

import java.util.Calendar;
import java.util.Date;

public class DocumentsNumberInDaysDto implements Comparable {
    private Date day;
    private long documentsNumber;

    public DocumentsNumberInDaysDto(Date day) {
        this.day = day;
        this.documentsNumber = 0;
    }

    public DocumentsNumberInDaysDto(int day, int month, int year, long documentsNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month + 1); // calendar liczy od 0
        calendar.set(Calendar.YEAR, year);
        this.day = calendar.getTime();
        this.documentsNumber = documentsNumber;
    }

    @Override
    public int compareTo(Object o) {
        DocumentsNumberInDaysDto other = (DocumentsNumberInDaysDto) o;
        return day.compareTo(other.getDay());
    }

    public Date getDay() {
        return day;
    }

    public long getDocumentsNumber() {
        return documentsNumber;
    }
}
