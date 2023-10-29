package de.uniba.soa.beveragestore.models.dto;

import de.uniba.soa.beveragestore.models.store.Beverage;
import de.uniba.soa.beveragestore.models.store.BeverageType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "beverage")
public class BeverageDTO implements Beverage {

    int id;
    double price;
    BeverageType beverageType;

    public BeverageDTO() {
    }

    public BeverageDTO(int id, double price, BeverageType beverageType) {
        this.id = id;
        this.price = price;
        this.beverageType = beverageType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BeverageType getBeverageType() {
        return beverageType;
    }

    public void setBeverageType(BeverageType beverageType) {
        this.beverageType = beverageType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BeverageDTO{" +
                "id=" + id +
                ", price=" + price +
                ", beverageType=" + beverageType +
                '}';
    }
}
