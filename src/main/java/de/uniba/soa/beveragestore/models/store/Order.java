package de.uniba.soa.beveragestore.models.store;

import java.util.List;

public class Order {
    private int orderId;
    private List<OrderItem> orderItems;
    private double price;
    private OrderStatus status;

    public Order() {
    }

    public Order(int orderId, List<OrderItem> orderItems, double price, OrderStatus status) {
        this.orderId = orderId;
        this.orderItems = orderItems;
        this.price = price;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
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

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderItems=" + orderItems +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
