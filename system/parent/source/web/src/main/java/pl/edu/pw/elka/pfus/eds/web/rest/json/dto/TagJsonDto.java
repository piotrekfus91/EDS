package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

public class TagJsonDto {
    private int id;
    private String label;
    private String value;

    public TagJsonDto() {

    }

    public TagJsonDto(int id, String label, String value) {
        this.id = id;
        this.label = label;
        this.value = value;
    }

    public static TagJsonDto from(Tag tag) {
        if(tag == null)
            return new TagJsonDto();
        return new TagJsonDto(tag.getId(), tag.getValue(), tag.getValue());
    }
}
