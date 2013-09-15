package pl.edu.pw.elka.pfus.eds.logic.search.dto;

import com.google.common.base.Objects;

public class DocumentSearchDto {
    private int id;
    private String title;

    public DocumentSearchDto(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(DocumentSearchDto.class).add("id", id).add("title", title).toString();
    }
}
