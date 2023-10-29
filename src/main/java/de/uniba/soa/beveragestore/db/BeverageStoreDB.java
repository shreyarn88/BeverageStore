package de.uniba.soa.beveragestore.db;

import de.uniba.soa.beveragestore.models.store.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class BeverageStoreDB {
    private final List<Bottle> bottles;
    private final List<Crate> crates;
    private final List<Order> orders;

    public BeverageStoreDB() {
        this.bottles = initBottles();
        this.crates = initCases();
        this.orders = initOrders();
    }

    private List<Bottle> initBottles() {
        return new ArrayList<>(Arrays.asList(
                new Bottle(1, "Pils", 0.5, true, 4.8, 0.79, "Keesmann", 34),
                        new Bottle(2, "Helles", 0.5, true, 4.9, 0.89, "Mahr", 17),
                        new Bottle(3, "Boxbeutel", 0.75, true, 12.5, 5.79, "Divino", 11),
                        new Bottle(4, "Tequila", 0.7, true, 40.0, 13.79, "Tequila Inc.", 5),
                        new Bottle(5, "Gin", 0.5, true, 42.00, 11.79, "Hopfengarten", 3),
                        new Bottle(6, "Export Edel", 0.5, true, 4.8, 0.59, "Oettinger", 66),
                        new Bottle(7, "Premium Tafelwasser", 0.7, false, 0.0, 4.29, "Franken Brunnen", 12),
                        new Bottle(8, "Wasser", 0.5, false, 0.0, 0.29, "Franken Brunnen", 57),
                        new Bottle(9, "Spezi", 0.7, false, 0.0, 0.69, "Franken Brunnen", 42),
                        new Bottle(10, "Grape Mix", 0.5, false, 0.0, 0.59, "Franken Brunnen", 12),
                        new Bottle(11, "Still", 1.0, false, 0.0, 0.66, "Franken Brunnen", 34),
                        new Bottle(12, "Cola", 1.5, false, 0.0, 1.79, "CCC", 69),
                        new Bottle(13, "Cola Zero", 2.0, false, 0.0, 2.19, "CCC", 12),
                        new Bottle(14, "Apple", 0.5, false, 0.0, 1.99, "Juice Factory", 25),
                        new Bottle(15, "Orange", 0.5, false, 0.0, 1.99, "Juice Factory", 55),
                        new Bottle(16, "Lime", 0.5, false, 0.0, 2.99, "Juice Factory", 8)
        ));
    }

    private List<Crate> initCases() {
        return new ArrayList<>(Arrays.asList(
                new Crate(1, this.bottles.get(0), 20, 14.99, 3),
                new Crate(2, this.bottles.get(1), 20, 15.99, 5),
                new Crate(3, this.bottles.get(2), 6, 30.00, 7),
                new Crate(4, this.bottles.get(7), 12, 1.99, 11),
                new Crate(5, this.bottles.get(8), 20, 11.99, 13),
                new Crate(6, this.bottles.get(11), 6, 10.99, 4),
                new Crate(7, this.bottles.get(12), 6, 11.99, 5),
                new Crate(8, this.bottles.get(13), 20, 35.00, 7),
                new Crate(9, this.bottles.get(14), 12, 20.00, 9)
        ));
    }

    private List<Order> initOrders() {
        return new ArrayList<>(Arrays.asList(
                new Order(1, new ArrayList<>(Arrays.asList(
                        new OrderItem(10, this.bottles.get(3), 2),
                        new OrderItem(20, this.crates.get(3), 1),
                        new OrderItem(30, this.bottles.get(15), 1)
                )), 32.56, OrderStatus.SUBMITTED),
                new Order(2, new ArrayList<>(Arrays.asList(
                        new OrderItem(10, this.bottles.get(13), 2),
                        new OrderItem(20, this.bottles.get(14), 2),
                        new OrderItem(30, this.crates.get(0), 1)
                )), 22.95, OrderStatus.PROCESSED),
                new Order(3, new ArrayList<>(Arrays.asList(
                        new OrderItem(10, this.bottles.get(2), 1)
                )), 5.79, OrderStatus.SUBMITTED)
        ));
    }

    public List<Bottle> getBottles() {
        return bottles;
    }

    public List<Crate> getCrates() {
        return crates;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Bottle getBottle(final int id) {
        return this.bottles.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public Crate getCrate(final int id) {
        return this.crates.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    public Order getOrder(final int id) {
        return this.orders.stream().filter(c -> c.getOrderId() == id).findFirst().orElse(null);
    }

    public Bottle addBottle(final Bottle bottle) {
        bottle.setId(this.bottles.stream().map(Bottle::getId).max(Comparator.naturalOrder()).orElse(0) + 1);
        this.bottles.add(bottle);
        return bottle;
    }

    public Crate addCrate(final Crate crate) {
        crate.setId(this.crates.stream().map(Crate::getId).max(Comparator.naturalOrder()).orElse(0) + 1);
        this.crates.add(crate);
        return crate;
    }

    public Order addOrder(final Order order) {
        order.setOrderId(this.orders.stream().map(Order::getOrderId).max(Comparator.naturalOrder()).orElse(0) + 1);
        this.orders.add(order);
        return order;
    }

    public boolean deleteBottle(final int id) {
        final Bottle bottle = this.getBottle(id);
        return this.bottles.remove(bottle);
    }

    public boolean deleteCrate(final int id) {
        final Crate crate = this.getCrate(id);
        return this.crates.remove(crate);
    }

    public boolean deleteOrder(final int id) {
        final Order order = this.getOrder(id);
        return this.orders.remove(order);
    }
}
