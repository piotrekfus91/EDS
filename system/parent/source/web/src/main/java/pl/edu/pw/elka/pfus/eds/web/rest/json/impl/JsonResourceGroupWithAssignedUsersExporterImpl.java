package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.logic.resource.group.dto.ResourceGroupWithAssignedUsers;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractSuccessFailureJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonResourceGroupWithAssignedUsersExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.ResourceGroupUsersJsonDto;

public class JsonResourceGroupWithAssignedUsersExporterImpl extends AbstractSuccessFailureJsonExporter
        implements JsonResourceGroupWithAssignedUsersExporter{
    @Override
    public String exportSuccess(ResourceGroupWithAssignedUsers object) {
        ResourceGroupUsersJsonDto dto = ResourceGroupUsersJsonDto.from(object);
        return success(dto);
    }

    @Override
    public String exportFailure(String errorMessage, ResourceGroupWithAssignedUsers object) {
        ResourceGroupUsersJsonDto dto = ResourceGroupUsersJsonDto.from(object);
        return failure(errorMessage, dto);
    }
}
