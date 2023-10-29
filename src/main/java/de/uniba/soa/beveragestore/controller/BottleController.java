package de.uniba.soa.beveragestore.controller;

import de.uniba.soa.beveragestore.models.api.PaginatedBottles;
import de.uniba.soa.beveragestore.models.dto.BottleDTO;
import de.uniba.soa.beveragestore.models.errors.ErrorMessage;
import de.uniba.soa.beveragestore.models.errors.ErrorType;
import de.uniba.soa.beveragestore.models.store.Bottle;
import de.uniba.soa.beveragestore.service.BeverageStoreService;
import de.uniba.soa.beveragestore.service.PaginationHelper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.logging.Logger;
//BottleController class to expose Bottle endpoints for the API
//Endpoints to Create, Read, Update and Delete
@Path("bottle")
public class BottleController {

    private static final Logger logger = Logger.getLogger(BottleController.class.getName());

    //TODO Add request validation to all the requests before processing
    //TODO Add appropriate error messages in the response

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllBeverages(@Context final UriInfo info,
                                     @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                                     @QueryParam("page") @DefaultValue("1") final int page) {
        logger.info(info.toString());
        if (pageLimit < 1 || page < 1) {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
        final PaginationHelper<Bottle> helper = new PaginationHelper<>(BeverageStoreService.instance.listBeverages());
        final PaginatedBottles response = new PaginatedBottles(helper.getPagination(info, page, pageLimit), BottleDTO.marshall(helper.getPaginatedList(), info.getBaseUri()), info.getRequestUri());
        return Response.ok(response).build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchBeverage(@Context final UriInfo info,
                                   @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                                   @QueryParam("page") @DefaultValue("1") final int page,
                                   @QueryParam("minPrice") final double minPrice,
                                   @QueryParam("maxPrice") final double maxPrice) {
        logger.info("Search" + info.toString());
        logger.info("Price" + minPrice + " || " + maxPrice);
        if (pageLimit < 1 || page < 1) {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
        final PaginationHelper<Bottle> helper = new PaginationHelper<>(BeverageStoreService.instance.searchBeverages(minPrice, maxPrice));
        final PaginatedBottles response = new PaginatedBottles(helper.getPagination(info, page, pageLimit), BottleDTO.marshall(helper.getPaginatedList(), info.getBaseUri()), info.getRequestUri());
        return Response.ok(response).build();
    }

    @GET
    @Path("/{bottleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBeverageById(@Context final UriInfo uri, @PathParam("bottleId") final int id) {
        final Bottle bottle = BeverageStoreService.instance.getBottle(id);

        if (bottle == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Bottle with Id :: " + id + " not found")).build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(new BottleDTO(bottle, uri.getBaseUri()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBeverage(@Context final UriInfo uri, final BottleDTO bottle) {
        if (bottle == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Bottle cannot be empty")).build();
        }
        Bottle response = BeverageStoreService.instance.addBottle(bottle.unmarshall());
        return Response
                .status(Response.Status.OK)
                .entity(new BottleDTO(response, uri.getBaseUri()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @PUT
    @Path("/update/{bottleId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBeverage(@Context final UriInfo uriInfo, BottleDTO bottle, @PathParam("bottleId") final int id) {
        if (bottle == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Bottle cannot be empty")).build();
        }

        Bottle updatedBottle = BeverageStoreService.instance.updateBottle(id, bottle.unmarshall());
        if (updatedBottle == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Bottle not available.")).build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(new BottleDTO(updatedBottle, uriInfo.getBaseUri()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
