package de.uniba.soa.beveragestore.controller;

import de.uniba.soa.beveragestore.models.api.PaginatedCrates;
import de.uniba.soa.beveragestore.models.dto.CrateDTO;
import de.uniba.soa.beveragestore.models.errors.ErrorMessage;
import de.uniba.soa.beveragestore.models.errors.ErrorType;
import de.uniba.soa.beveragestore.models.store.Crate;
import de.uniba.soa.beveragestore.service.BeverageStoreService;
import de.uniba.soa.beveragestore.service.PaginationHelper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.logging.Logger;

@Path("crate")
public class CrateController {

    private static final Logger logger = Logger.getLogger(BottleController.class.getName());

    //TODO Add request validation to all the requests before processing
    //TODO Add appropriate error messages in the response

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllCrates(@Context final UriInfo info,
                                  @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                                  @QueryParam("page") @DefaultValue("1") final int page) {
        logger.info("List All Crates : " + info.toString());
        if (pageLimit < 1 || page < 1) {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
        final PaginationHelper<Crate> helper = new PaginationHelper<>(BeverageStoreService.instance.listCrates());
        final PaginatedCrates response = new PaginatedCrates(helper.getPagination(info, page, pageLimit), CrateDTO.marshall(helper.getPaginatedList(), info.getBaseUri()), info.getRequestUri());
        return Response.ok(response).build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchCrates(@Context final UriInfo info,
                                 @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                                 @QueryParam("page") @DefaultValue("1") final int page,
                                 @QueryParam("minPrice") final double minPrice,
                                 @QueryParam("maxPrice") final double maxPrice) {
        logger.info("Search Crates" + info.toString());
        if (pageLimit < 1 || page < 1) {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
        final PaginationHelper<Crate> helper = new PaginationHelper<>(BeverageStoreService.instance.searchCrates(minPrice, maxPrice));
        final PaginatedCrates response = new PaginatedCrates(helper.getPagination(info, page, pageLimit), CrateDTO.marshall(helper.getPaginatedList(), info.getBaseUri()), info.getRequestUri());
        return Response.ok(response).build();
    }

    @GET
    @Path("/{crateId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCrateById(@Context final UriInfo uri, @PathParam("crateId") final int id) {
        final Crate crate = BeverageStoreService.instance.getCrate(id);

        if (crate == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "No crate found for ID :: " + id)).build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(new CrateDTO(crate, uri.getBaseUri()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @POST
    @Path(("/add"))
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewCrate(@Context final UriInfo uri, final CrateDTO crate) {
        if (crate == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Request body cannot be empty")).build();
        }
        Crate response = BeverageStoreService.instance.addCrate(crate.unmarshall());
        return Response
                .status(Response.Status.OK)
                .entity(new CrateDTO(response, uri.getBaseUri()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @Path("/update/{crateId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCrate(@Context final UriInfo uri, CrateDTO crate, @PathParam("crateId") final int id) {
        if (crate == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Body was empty")).build();
        }

        Crate updatedCrate = BeverageStoreService.instance.updateCrate(id, crate.unmarshall());
        if (updatedCrate == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Crate not available.")).build();
        }

        return Response
                .status(Response.Status.OK)
                .entity(new CrateDTO(updatedCrate, uri.getBaseUri()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
