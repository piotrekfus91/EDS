package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractSuccessFailureJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonResourceGroupListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.ResourceGroupJsonDto;

import java.util.LinkedList;
import java.util.List;

public class JsonResourceGroupListExporterImpl extends AbstractSuccessFailureJsonExporter
        implements JsonResourceGroupListExporter {
    @Override
    public String exportSuccess(List<ResourceGroup> resourceGroups) {
        List<ResourceGroupJsonDto> dtos = getDtos(resourceGroups);
        return success(dtos);
    }

    @Override
    public String exportFailure(String errorMessage, List<ResourceGroup> resourceGroups) {
        List<ResourceGroupJsonDto> dtos = getDtos(resourceGroups);
        return failure(errorMessage, dtos);
    }

    private List<ResourceGroupJsonDto> getDtos(List<ResourceGroup> resourceGroups) {
        List<ResourceGroupJsonDto> dtos = new LinkedList<>();
        if(resourceGroups != null) {
            for(ResourceGroup resourceGroup : resourceGroups) {
                dtos.add(ResourceGroupJsonDto.from(resourceGroup));
            }
        }
        return dtos;
    }
}
