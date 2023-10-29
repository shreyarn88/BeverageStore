package de.uniba.soa.beveragestore.models.dto;

import de.uniba.soa.beveragestore.controller.BottleController;
import de.uniba.soa.beveragestore.models.store.Bottle;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bottle")
public class BottleDTO {

    private int id;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private double volume;
    @XmlElement(required = true)
    private boolean isAlcoholic;
    @XmlElement(required = true)
    private double volumePercent;
    @XmlElement(required = true)
    private double price;
    private String supplier;
    @XmlElement(required = true)
    private int inStock;
    private URI href;

    public BottleDTO() {
    }

    public BottleDTO(final Bottle bottle, final URI baseUri) {
        this.id = bottle.getId();
        this.name = bottle.getName();
        this.volume = bottle.getVolume();
        this.isAlcoholic = bottle.isAlcoholic();
        this.volumePercent = bottle.getVolumePercent();
        this.price = bottle.getPrice();
        this.supplier = bottle.getSupplier();
        this.inStock = bottle.getInStock();
        this.href = UriBuilder.fromUri(baseUri).path(BottleController.class).path(BottleController.class, "listAllBeverages").build(this.id);
    }

    public static List<BottleDTO> marshall(final List<Bottle> bottlesList, final URI baseUri) {
        final ArrayList<BottleDTO> bottles = new ArrayList<>();
        for (final Bottle bottle : bottlesList) {
            bottles.add(new BottleDTO(bottle, baseUri));
        }
        return bottles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public boolean isAlcoholic() {
        return isAlcoholic;
    }

    public void setAlcoholic(boolean alcoholic) {
        isAlcoholic = alcoholic;
    }

    public double getVolumePercent() {
        return volumePercent;
    }

    public void setVolumePercent(double volumePercent) {
        this.volumePercent = volumePercent;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public Bottle unmarshall() {
        return new Bottle(0, this.name, this.volume, this.isAlcoholic, this.volumePercent, this.price, this.supplier, this.inStock);
    }

    @Override
    public String toString() {
        return "BottleDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", volume=" + volume +
                ", isAlcoholic=" + isAlcoholic +
                ", volumePercent=" + volumePercent +
                ", price=" + price +
                ", supplier='" + supplier + '\'' +
                ", inStock=" + inStock +
                '}';
    }
}
