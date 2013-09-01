package pl.edu.pw.elka.pfus.eds.web.rest.json;

import pl.edu.pw.elka.pfus.eds.security.dto.RolesGrantedDto;

import java.util.List;

public interface JsonRolesGrantedListExporter {
    String export(List<RolesGrantedDto> dto);
}
