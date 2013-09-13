package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.dao.dto.DocumentsNumberInDaysDto;

import java.text.SimpleDateFormat;

public class DocumentsNumberInDaysJsonDto {
    private long documentsNumber;
    private String day;

    public DocumentsNumberInDaysJsonDto() {

    }

    public DocumentsNumberInDaysJsonDto(long documentsNumber, String day) {
        this.documentsNumber = documentsNumber;
        this.day = day;
    }

    public static DocumentsNumberInDaysJsonDto from(DocumentsNumberInDaysDto dto) {
        if(dto == null)
            return new DocumentsNumberInDaysJsonDto();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM");
        return new DocumentsNumberInDaysJsonDto(dto.getDocumentsNumber(), dateFormatter.format(dto.getDay()));
    }

    public long getDocumentsNumber() {
        return documentsNumber;
    }

    public void setDocumentsNumber(long documentsNumber) {
        this.documentsNumber = documentsNumber;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
