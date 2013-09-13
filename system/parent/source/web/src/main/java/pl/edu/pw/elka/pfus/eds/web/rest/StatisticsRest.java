package pl.edu.pw.elka.pfus.eds.web.rest;

import pl.edu.pw.elka.pfus.eds.domain.dao.dto.DocumentsNumberInDaysDto;
import pl.edu.pw.elka.pfus.eds.logic.statistics.Statistics;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDocumentsNumberInDaysListExporter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/statistics")
public class StatisticsRest {
    public static final int WEEK_LENGTH = 7;
    private Statistics statistics;
    private JsonDocumentsNumberInDaysListExporter documentsNumberInDaysListExporter;

    @Inject
    public StatisticsRest(Statistics statistics,
                          JsonDocumentsNumberInDaysListExporter documentsNumberInDaysListExporter) {
        this.statistics = statistics;
        this.documentsNumberInDaysListExporter = documentsNumberInDaysListExporter;
    }

    @GET
    @Path("/upload/lastWeek")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLastWeekUploadStatistics() {
        List<DocumentsNumberInDaysDto> documentsNumberInDaysDtos = statistics.documentsUploadedInLastDays(WEEK_LENGTH);
        String exported = documentsNumberInDaysListExporter.export(documentsNumberInDaysDtos);
        return Response.status(Response.Status.OK).entity(exported).build();
    }
}
