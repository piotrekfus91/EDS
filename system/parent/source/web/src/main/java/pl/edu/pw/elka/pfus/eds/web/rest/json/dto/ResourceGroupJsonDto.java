package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;

import java.util.LinkedList;
import java.util.List;

public class ResourceGroupJsonDto {
    private String name;
    private String description;
    private String founder;
    private List<DocumentJsonDto> documents;

    public ResourceGroupJsonDto() {

    }

    public ResourceGroupJsonDto(String name, String description, String founder, List<DocumentJsonDto> documents) {
        this.name = name;
        this.description = description;
        this.founder = founder;
        this.documents = documents;
    }

    public static ResourceGroupJsonDto from(ResourceGroup resourceGroup) {
        if(resourceGroup == null)
            return new ResourceGroupJsonDto();
        List<DocumentJsonDto> documents = new LinkedList<>();
        for(Document document : resourceGroup.getAllDocuments()) {
            documents.add(DocumentJsonDto.from(document));
        }
        return new ResourceGroupJsonDto(resourceGroup.getName(), resourceGroup.getDescription(),
                resourceGroup.getFounder().getFriendlyName(), documents);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }

    public List<DocumentJsonDto> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentJsonDto> documents) {
        this.documents = documents;
    }
}
