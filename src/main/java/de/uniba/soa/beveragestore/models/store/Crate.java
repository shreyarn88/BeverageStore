package de.uniba.soa.beveragestore.models.store;

public class Crate implements Beverage {
    private int id;
    private Bottle bottle;
    private int noOfBottles;
    private double price;
    private int inStock;
    private BeverageType beverageType;

    public Crate(int id, Bottle bottle, int noOfBottles, double price, int inStock) {
        this.id = id;
        this.bottle = bottle;
        this.noOfBottles = noOfBottles;
        this.price = price;
        this.inStock = inStock;
        this.beverageType = BeverageType.CRATE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bottle getBottle() {
        return bottle;
    }

    public void setBottle(Bottle bottle) {
        this.bottle = bottle;
    }

    public int getNoOfBottles() {
        return noOfBottles;
    }

    public void setNoOfBottles(int noOfBottles) {
        this.noOfBottles = noOfBottles;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public BeverageType getBeverageType() {
        return BeverageType.CRATE;
    }

    public void setBeverageType(BeverageType beverageType) {
        this.beverageType = beverageType;
    }

    @Override
    public String toString() {
        return "Crate{" +
                "id=" + id +
                ", bottle=" + bottle +
                ", noOfBottles=" + noOfBottles +
                ", price=" + price +
                ", inStock=" + inStock +
                ", beverageType=" + beverageType +
                '}';
    }
}
