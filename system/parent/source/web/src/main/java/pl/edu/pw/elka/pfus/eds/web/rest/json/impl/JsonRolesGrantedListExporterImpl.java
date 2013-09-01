package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.security.dto.RolesGrantedDto;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonRolesGrantedListExporter;

import java.util.List;

public class JsonRolesGrantedListExporterImpl extends AbstractJsonExporter implements JsonRolesGrantedListExporter {
    @Override
    public String export(List<RolesGrantedDto> dto) {
        return getGson().toJson(dto);
    }
}
