package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

public class TagJsonDto {
    private int id;
    private String label;
    private String value;
    private String tag;
    private int count;

    public TagJsonDto() {

    }

    public TagJsonDto(int id, String label, String value, String tag, int count) {
        this.id = id;
        this.label = label;
        this.value = value;
        this.tag = tag;
        this.count = count;
    }

    public static TagJsonDto from(Tag tag) {
        if(tag == null)
            return new TagJsonDto();
        return new TagJsonDto(tag.getId(), tag.getValue(), tag.getValue(), tag.getValue(), tag.getDocuments().size());
    }
}
