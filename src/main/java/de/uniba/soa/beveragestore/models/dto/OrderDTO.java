package de.uniba.soa.beveragestore.models.dto;

import de.uniba.soa.beveragestore.controller.OrderController;
import de.uniba.soa.beveragestore.models.store.Beverage;
import de.uniba.soa.beveragestore.models.store.Order;
import de.uniba.soa.beveragestore.models.store.OrderItem;
import de.uniba.soa.beveragestore.models.store.OrderStatus;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
public class OrderDTO {
    private int orderId;
    @XmlElement(required = true)
    private List<OrderItemDTO> orderItems;
    private double price;
    private OrderStatus status;
    private URI href;

    public OrderDTO() {
    }

    public OrderDTO(final Order order, final URI baseUri) {
        this.orderId = order.getOrderId();
        this.orderItems = OrderItemDTO.marshall(order.getOrderItems());
        this.status = order.getStatus();
        this.price = order.getPrice();
        this.href = UriBuilder.fromUri(baseUri).path(OrderController.class).path(OrderController.class, "listAllOrders").build(this.orderId);
    }

    public static List<OrderDTO> marshall(final List<Order> orderList, final URI baseUri) {
        final ArrayList<OrderDTO> orders = new ArrayList<>();
        for (final Order order : orderList) {
            orders.add(new OrderDTO(order, baseUri));
        }
        return orders;
    }

    public Order unmarshall() {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO ot : this.orderItems) {
            orderItems.add(new OrderItem(ot.getNumber(), (Beverage) ot.getBeverage(), ot.getQuantity()));
        }
        return new Order(0, orderItems, this.price, OrderStatus.SUBMITTED);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + orderId +
                ", orderItems=" + orderItems +
                ", price=" + price +
                ", status=" + status +
                ", href=" + href +
                '}';
    }
}
