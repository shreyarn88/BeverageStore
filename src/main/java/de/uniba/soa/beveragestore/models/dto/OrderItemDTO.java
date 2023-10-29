package de.uniba.soa.beveragestore.models.dto;

import de.uniba.soa.beveragestore.models.store.Beverage;
import de.uniba.soa.beveragestore.models.store.OrderItem;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderItem")
public class OrderItemDTO {

    @XmlElement(required = true)
    private int number;

    @XmlElement(required = true)
    private BeverageDTO beverage;

    @XmlElement(required = true)
    private int quantity;

    public OrderItemDTO() {
    }

    public OrderItemDTO(final OrderItem orderItem) {
        this.number = orderItem.getNumber();
        Beverage beverage = orderItem.getBeverage();
        this.beverage = new BeverageDTO(beverage.getId(), beverage.getPrice(), beverage.getBeverageType());
        this.quantity = orderItem.getQuantity();
    }

    public static List<OrderItemDTO> marshall(final List<OrderItem> orderItemList) {
        final ArrayList<OrderItemDTO> orderItems = new ArrayList<>();
        for (final OrderItem orderItem : orderItemList) {
            orderItems.add(new OrderItemDTO(orderItem));
        }
        return orderItems;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BeverageDTO getBeverage() {
        return beverage;
    }

    public void setBeverage(BeverageDTO beverage) {
        this.beverage = beverage;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "number=" + number +
                ", beverage=" + beverage +
                ", quantity=" + quantity +
                '}';
    }
}
