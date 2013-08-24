package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.logic.exception.LogicException;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.ResourceGroupService;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonResourceGroupExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonResourceGroupListExporter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/resourceGroups")
public class ResourceGroupRest {
    private static final Logger logger = Logger.getLogger(ResourceGroupRest.class);

    private ResourceGroupService resourceGroupService;
    private JsonResourceGroupExporter resourceGroupExporter;
    private JsonResourceGroupListExporter resourceGroupListExporter;

    @Inject
    public ResourceGroupRest(ResourceGroupService resourceGroupService,
                             JsonResourceGroupExporter resourceGroupExporter,
                             JsonResourceGroupListExporter resourceGroupListExporter) {
        this.resourceGroupService = resourceGroupService;
        this.resourceGroupExporter = resourceGroupExporter;
        this.resourceGroupListExporter = resourceGroupListExporter;
    }

    @GET
    @Path("/founded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResourceGroupsFoundedByCurrentUser() {
        List<ResourceGroup> resourceGroups = resourceGroupService.getCurrentUserResourceGroups();
        String exported = resourceGroupListExporter.exportSuccess(resourceGroups);
        return Response.status(Response.Status.OK).entity(exported).build();
    }

    @GET
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResourceGroupByName(@PathParam("name") String name) {
        String exported;
        try {
            ResourceGroup resourceGroup = resourceGroupService.getByNameWithDocuments(name);
            exported = resourceGroupExporter.exportSuccess(resourceGroup);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            exported = resourceGroupExporter.exportFailure(e.getMessage(), null);
        }
        return Response.status(Response.Status.OK).entity(exported).build();
    }
}
