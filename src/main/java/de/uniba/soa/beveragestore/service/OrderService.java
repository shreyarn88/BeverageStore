package de.uniba.soa.beveragestore.service;

import de.uniba.soa.beveragestore.db.BeverageStoreDB;
import de.uniba.soa.beveragestore.models.store.*;

import java.util.List;

public class OrderService {

    public static final OrderService instance = new OrderService();

    private final BeverageStoreDB db;

    public OrderService() {
        this.db = new BeverageStoreDB();
    }

    public List<Order> listOrders() {
        return this.db.getOrders();
    }

    public Order createOrder(final Order order) {
        if (order == null) {
            return null;
        }
        if (isOrderValid(order)) {
            double price = 0;
            for (OrderItem ot : order.getOrderItems()) {
                Beverage beverageOrder = ot.getBeverage();
                int beverageId = beverageOrder.getId();
                if (beverageOrder.getBeverageType().equals(BeverageType.BOTTLE)) {
                    Bottle bottle = this.db.getBottle(beverageId);
                    double itemPrice = (bottle.getPrice() * ot.getQuantity());
                    price += itemPrice;
                    ot.setBeverage(bottle);
                    int inStock = bottle.getInStock();
                    bottle.setInStock(inStock - ot.getQuantity());
                } else {
                    Crate crate = this.db.getCrate(beverageId);
                    double itemPrice = (crate.getPrice() * ot.getQuantity());
                    price += itemPrice;
                    ot.setBeverage(crate);
                    int inStock = crate.getInStock();
                    crate.setInStock(inStock - ot.getQuantity());
                }
            }
            order.setPrice((price * 100) / 100);
            this.db.addOrder(order);
            return order;
        }
        return null;
    }

    public Order getOrder(final int id) {
        return this.db.getOrder(id);
    }

    public Order updateOrder(final int id, final Order updateRequest) {
        final Order order = this.db.getOrder(id);
        if (order == null || updateRequest == null) {
            return null;
        }
        if (order.getStatus() == OrderStatus.PROCESSED || order.getStatus() == OrderStatus.CANCELLED) {
            return null;
        }
        if (isUpdateValid(updateRequest, order)) {

        }
        return order;
    }


    public boolean deleteOrder(final int id) {
        return this.db.deleteOrder(id);
    }

    public Order updateStatus(int orderId, OrderStatus status) {
        Order order = this.db.getOrders().stream().filter(or -> or.getOrderId() == orderId).findFirst().orElse(null);
        if (order == null) return null;
        if (order.getStatus() == OrderStatus.PROCESSED || order.getStatus() == OrderStatus.CANCELLED) {
            return null;
        }
        if (status == OrderStatus.SUBMITTED) return null;
        order.setStatus(status);
        return order;
    }

    public boolean isOrderValid(final Order order) {
        for (final OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getQuantity() <= 0) {
                return false;
            }
            Beverage beverage = orderItem.getBeverage();
            if (beverage.getBeverageType() == BeverageType.BOTTLE) {
                Bottle bottle = this.db.getBottle(beverage.getId());
                int inStock = bottle.getInStock();
                if (inStock - orderItem.getQuantity() < 0) {
                    return false;
                }
            }
            if (beverage.getBeverageType() == BeverageType.CRATE) {
                Crate crate = this.db.getCrate(beverage.getId());
                int inStock = crate.getInStock();
                if (inStock - orderItem.getQuantity() < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isUpdateValid(Order updateRequest, Order order) {
        for (final OrderItem orderItem : updateRequest.getOrderItems()) {
            if (orderItem.getQuantity() <= 0) {
                return false;
            }
            Beverage beverage = orderItem.getBeverage();
            if (beverage.getBeverageType() == BeverageType.BOTTLE) {
                Bottle bottle = this.db.getBottle(beverage.getId());
                int inStock = bottle.getInStock();
                boolean isOld = false;
                for (final OrderItem dbOrderItem : order.getOrderItems()) {
                    if (dbOrderItem.getBeverage().getBeverageType() == BeverageType.BOTTLE && dbOrderItem.getBeverage().getId() == beverage.getId()) {
                        isOld = true;
                        int oldQuantity = dbOrderItem.getQuantity();
                        if (oldQuantity < orderItem.getQuantity()) {
                            int newItemsRequired = orderItem.getQuantity() - oldQuantity;
                            if (inStock < newItemsRequired) {
                                return false;
                            }
                        }
                    }
                }
                if (!isOld && (inStock - orderItem.getQuantity() < 0)) {
                    return false;
                }
            }
            if (beverage.getBeverageType() == BeverageType.CRATE) {
                Crate crate = this.db.getCrate(beverage.getId());
                int inStock = crate.getInStock();
                boolean isOld = false;
                for (final OrderItem dbOrderItem : order.getOrderItems()) {
                    isOld = true;
                    if (dbOrderItem.getBeverage().getBeverageType() == BeverageType.CRATE && dbOrderItem.getBeverage().getId() == beverage.getId()) {
                        int oldQuantity = dbOrderItem.getQuantity();
                        if (oldQuantity < orderItem.getQuantity()) {
                            int newItemsRequired = orderItem.getQuantity() - oldQuantity;
                            if (inStock < newItemsRequired) {
                                return false;
                            }
                        }
                    }
                }
                if (!isOld && (inStock - orderItem.getQuantity() < 0)) {
                    return false;
                }
            }
        }
        return true;
    }
}
