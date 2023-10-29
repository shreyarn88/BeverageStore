package de.uniba.soa.beveragestore.models.dto;

import de.uniba.soa.beveragestore.controller.BottleController;
import de.uniba.soa.beveragestore.controller.CrateController;
import de.uniba.soa.beveragestore.models.store.Bottle;
import de.uniba.soa.beveragestore.models.store.Crate;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "crate")
public class CrateDTO {

    private int id;
    private Bottle bottle;
    @XmlElement(required = true)
    private int noOfBottles;
    @XmlElement(required = true)
    private double price;
    @XmlElement(required = true)
    private int inStock;
    private URI href;

    public CrateDTO() {
    }

    public CrateDTO(final Crate crate, final URI baseUri) {
        this.id = crate.getId();
        this.bottle = crate.getBottle();
        this.noOfBottles = crate.getNoOfBottles();
        this.inStock = crate.getInStock();
        this.price = crate.getPrice();
        this.href = UriBuilder.fromUri(baseUri).path(CrateController.class).path(CrateController.class, "listAllCrates").build(this.id);
    }

    public static List<CrateDTO> marshall(final List<Crate> cratesList, final URI baseUri) {
        final ArrayList<CrateDTO> crates = new ArrayList<>();
        for (final Crate crate : cratesList) {
            crates.add(new CrateDTO(crate, baseUri));
        }
        return crates;
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

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }

    public Crate unmarshall() {
        return new Crate(0, this.bottle, this.noOfBottles, this.price, this.inStock);
    }
}
