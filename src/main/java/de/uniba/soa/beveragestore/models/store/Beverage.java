package de.uniba.soa.beveragestore.models.store;

public interface Beverage {
    int getId();

    void setId(int id);

    double getPrice();

    void setPrice(double price);

    BeverageType getBeverageType();

    void setBeverageType(BeverageType beverageType);

}
