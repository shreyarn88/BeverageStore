package de.uniba.soa.beveragestore.controller;

import de.uniba.soa.beveragestore.models.api.PaginatedOrders;
import de.uniba.soa.beveragestore.models.dto.OrderDTO;
import de.uniba.soa.beveragestore.models.errors.ErrorMessage;
import de.uniba.soa.beveragestore.models.errors.ErrorType;
import de.uniba.soa.beveragestore.models.store.Order;
import de.uniba.soa.beveragestore.models.store.OrderStatus;
import de.uniba.soa.beveragestore.service.OrderService;
import de.uniba.soa.beveragestore.service.PaginationHelper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.logging.Logger;

@Path("order")
public class OrderController {
    private static final Logger logger = Logger.getLogger(OrderController.class.getName());

    @GET
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrderById(@PathParam("orderId") final int orderId) {
        logger.info("getOrderById :: " + orderId);
        Order order = OrderService.instance.getOrder(orderId);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Cannot find order with ID " + orderId)).build();
        }
        return Response.ok(order).type(MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllOrders(@Context final UriInfo info,
                                  @QueryParam("pageLimit") @DefaultValue("10") final int pageLimit,
                                  @QueryParam("page") @DefaultValue("1") final int page) {
        logger.info("List Orders" + info.toString());
        if (pageLimit < 1 || page < 1) {
            final ErrorMessage errorMessage = new ErrorMessage(ErrorType.INVALID_PARAMETER, "PageLimit or page is less than 1. Read the documentation for a proper handling!");
            return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
        }
        final PaginationHelper<Order> helper = new PaginationHelper<>(OrderService.instance.listOrders());
        final PaginatedOrders response = new PaginatedOrders(helper.getPagination(info, page, pageLimit), OrderDTO.marshall(helper.getPaginatedList(), info.getBaseUri()), info.getRequestUri());
        return Response.ok(response).build();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNewOrder(final OrderDTO orderDTO) {
        logger.info(orderDTO.toString());
        Order order = OrderService.instance.createOrder(orderDTO.unmarshall());
        return Response.ok(order).build();
    }

    @PUT
    @Path("/update/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOrder(@PathParam("orderId") int orderId,
                                final OrderDTO orderDTO) {
        logger.info("Update Order " + orderDTO);
        if (orderDTO == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Order details cannot be null")).build();
        }
        Order order = orderDTO.unmarshall();
        Order updatedOrder = OrderService.instance.updateOrder(orderId, order);
        if (updatedOrder == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Order cannot be updated")).build();
        }
        return Response.ok(updatedOrder).build();
    }

    @GET
    @Path("/process/{orderId}/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response processOrder(@PathParam("orderId") final int orderId,
                                 @PathParam("status") final OrderStatus status) {
        logger.info("processOrder :: " + status);
        if (status == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "OrderStatus cannot be null")).build();
        }
        Order updatedOrder = OrderService.instance.updateStatus(orderId, status);
        if (updatedOrder == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "The status cannot be updated")).build();
        }
        return Response.ok(updatedOrder)
                .build();
    }

    @DELETE
    @Path("/delete/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cancelOrder(@PathParam("orderId") final int orderId) {
        boolean isDeleted = OrderService.instance.deleteOrder(orderId);
        if (isDeleted) {
            return Response.ok("Order deleted").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(ErrorType.INVALID_PARAMETER, "Order cannot be deleted")).build();
    }
}
