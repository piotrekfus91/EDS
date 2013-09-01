package pl.edu.pw.elka.pfus.eds.web.rest;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.logic.exception.LogicException;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.ResourceGroupService;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.dto.ResourceGroupWithAssignedUsers;
import pl.edu.pw.elka.pfus.eds.security.dto.RolesGrantedDto;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonResourceGroupExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonResourceGroupListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonResourceGroupWithAssignedUsersExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonRolesGrantedListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.SimpleResourceGroupJsonDto;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/resourceGroups")
public class ResourceGroupRest {
    private static final Logger logger = Logger.getLogger(ResourceGroupRest.class);

    private ResourceGroupService resourceGroupService;
    private JsonResourceGroupExporter resourceGroupExporter;
    private JsonResourceGroupListExporter resourceGroupListExporter;
    private JsonResourceGroupWithAssignedUsersExporter resourceGroupWithAssignedUsersExporter;
    private JsonRolesGrantedListExporter rolesGrantedExporter;

    @Inject
    public ResourceGroupRest(ResourceGroupService resourceGroupService,
                             JsonResourceGroupExporter resourceGroupExporter,
                             JsonResourceGroupListExporter resourceGroupListExporter,
                             JsonResourceGroupWithAssignedUsersExporter resourceGroupWithAssignedUsersExporter,
                             JsonRolesGrantedListExporter rolesGrantedExporter) {
        this.resourceGroupService = resourceGroupService;
        this.resourceGroupExporter = resourceGroupExporter;
        this.resourceGroupListExporter = resourceGroupListExporter;
        this.resourceGroupWithAssignedUsersExporter = resourceGroupWithAssignedUsersExporter;
        this.rolesGrantedExporter = rolesGrantedExporter;
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
            ResourceGroupWithAssignedUsers resourceGroupDto = resourceGroupService.getByNameWithUsers(name);
            exported = resourceGroupWithAssignedUsersExporter.exportSuccess(resourceGroupDto);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            exported = resourceGroupExporter.exportFailure(e.getMessage(), null);
        }
        return Response.status(Response.Status.OK).entity(exported).build();
    }

    @GET
    @Path("/roles/group/{groupName}/user/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserRoles(@PathParam("groupName") String groupName, @PathParam("userName") String userName) {
        List<RolesGrantedDto> grantedRoles = resourceGroupService.getUserRolesOverResourceGroups(userName, groupName);
        String exported = rolesGrantedExporter.export(grantedRoles);
        return Response.status(Response.Status.OK).entity(exported).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createResourceGroup(SimpleResourceGroupJsonDto resourceGroupJsonDto) {
        String exported;
        logger.info("creating resource group: " + resourceGroupJsonDto.getName());
        try {
            ResourceGroup resourceGroup = resourceGroupService.create(
                    resourceGroupJsonDto.getName(), resourceGroupJsonDto.getDescription());
            exported = resourceGroupExporter.exportSuccess(resourceGroup);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            exported = resourceGroupExporter.exportFailure(e.getMessage(), null);
        }
        return Response.status(Response.Status.OK).entity(exported).build();
    }

    @PUT
    @Path("/update/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateResourceGroup(@PathParam("name") String name,
                                        SimpleResourceGroupJsonDto resourceGroupJsonDto) {
        logger.info("updating group " + name + " to " + resourceGroupJsonDto.getName());
        String exported;
        try {
            ResourceGroup resourceGroup = resourceGroupService.updateNameAndDescription(name,
                    resourceGroupJsonDto.getName(), resourceGroupJsonDto.getDescription());
            exported = resourceGroupExporter.exportSuccess(resourceGroup);
        } catch (LogicException e) {
            logger.error(e.getMessage(), e);
            exported = resourceGroupExporter.exportFailure(e.getMessage(), null);
        }
        return Response.status(Response.Status.OK).entity(exported).build();
    }

    @PUT
    @Path("/roles/group/{groupName}/user/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRoles(@PathParam("groupName") String groupName, @PathParam("userName") String userName,
                                List<RolesGrantedDto> rolesGranted) {
        logger.info("updating roles for user " + userName + " over group " + groupName);
        logger.info("new roles: " + rolesGranted);
        resourceGroupService.updateRoles(groupName, userName, rolesGranted);
        return getResourceGroupByName(groupName);
    }

    @DELETE
    @Path("/delete/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteResourceGroup(@PathParam("name") String name) {
        String exported;
        try {
            resourceGroupService.delete(name);
            exported = resourceGroupExporter.exportSuccess(null);
        } catch (LogicException e) {
            exported = resourceGroupExporter.exportFailure(e.getMessage(), null);
        }
        return Response.status(Response.Status.OK).entity(exported).build();
    }
}
