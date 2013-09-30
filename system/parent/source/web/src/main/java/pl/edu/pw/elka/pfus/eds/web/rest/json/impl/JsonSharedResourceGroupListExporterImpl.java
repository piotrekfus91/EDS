package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.domain.dao.dto.SharedResourceGroupDto;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractSuccessFailureJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonSharedResourceGroupListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.SharedResourceGroupJsonDto;

import java.util.LinkedList;
import java.util.List;

public class JsonSharedResourceGroupListExporterImpl extends AbstractSuccessFailureJsonExporter
        implements JsonSharedResourceGroupListExporter {
    @Override
    public String exportSuccess(List<SharedResourceGroupDto> sharedResourceGroupDtos) {
        List<SharedResourceGroupJsonDto> dtos = getDtos(sharedResourceGroupDtos);
        return success(dtos);
    }

    @Override
    public String exportFailure(String errorMessage, List<SharedResourceGroupDto> sharedResourceGroupDtos) {
        List<SharedResourceGroupJsonDto> dtos = getDtos(sharedResourceGroupDtos);
        return failure(errorMessage, dtos);
    }

    private List<SharedResourceGroupJsonDto> getDtos(List<SharedResourceGroupDto> sharedResourceGroupDtos) {
        List<SharedResourceGroupJsonDto> dtos = new LinkedList<>();
        if(sharedResourceGroupDtos != null) {
            for (SharedResourceGroupDto sharedResourceGroupDto : sharedResourceGroupDtos) {
                dtos.add(SharedResourceGroupJsonDto.from(sharedResourceGroupDto));
            }
        }
        return dtos;
    }
}
