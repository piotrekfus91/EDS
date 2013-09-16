package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.logic.search.dto.DocumentSearchDto;

import static pl.edu.pw.elka.pfus.eds.web.rest.Rest.downloadUrl;

public class DownloadableDocumentSearchDto {
    private int id;
    private String title;
    private String url;

    public DownloadableDocumentSearchDto() {

    }

    public DownloadableDocumentSearchDto(int id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    public static DownloadableDocumentSearchDto from(DocumentSearchDto documentSearchDto) {
        if(documentSearchDto == null)
            return new DownloadableDocumentSearchDto();
        return new DownloadableDocumentSearchDto(documentSearchDto.getId(), documentSearchDto.getTitle(),
                downloadUrl(documentSearchDto.getId()));
    }
}
