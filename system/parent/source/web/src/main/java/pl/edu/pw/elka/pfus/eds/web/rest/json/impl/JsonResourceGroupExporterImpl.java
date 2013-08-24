package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractSuccessFailureJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonResourceGroupExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.ResourceGroupJsonDto;

public class JsonResourceGroupExporterImpl extends AbstractSuccessFailureJsonExporter
        implements JsonResourceGroupExporter {
    @Override
    public String exportSuccess(ResourceGroup resourceGroup) {
        ResourceGroupJsonDto dto = ResourceGroupJsonDto.from(resourceGroup);
        return success(dto);
    }

    @Override
    public String exportFailure(String errorMessage, ResourceGroup object) {
        ResourceGroupJsonDto dto = ResourceGroupJsonDto.from(object);
        return failure(errorMessage, dto);
    }
}
