package de.uniba.soa.beveragestore.service;

import de.uniba.soa.beveragestore.db.BeverageStoreDB;
import de.uniba.soa.beveragestore.models.store.Bottle;
import de.uniba.soa.beveragestore.models.store.Crate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BeverageStoreService {
    public static final BeverageStoreService instance = new BeverageStoreService();

    private final BeverageStoreDB db;

    public BeverageStoreService() {
        this.db = new BeverageStoreDB();
    }

    public List<Bottle> listBeverages() {
        return this.db.getBottles();
    }

    public List<Crate> listCrates() {
        return this.db.getCrates();
    }

    public List<Bottle> searchBeverages(double minPrice, double maxPrice) {
        return this.db.getBottles().stream().filter(bottle ->
                        bottle.getPrice() >= minPrice &&
                                bottle.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public List<Crate> searchCrates(double minPrice, double maxPrice) {
        return this.db.getCrates().stream().filter(crate ->
                        crate.getPrice() >= minPrice &&
                                crate.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public Bottle addBottle(final Bottle bottle) {
        if (bottle == null) {
            return null;
        }
        return this.db.addBottle(bottle);
    }

    public Crate addCrate(final Crate crate) {
        if (crate == null) {
            return null;
        }
        return this.db.addCrate(crate);
    }


    public Bottle getBottle(final int id) {
        return this.db.getBottle(id);
    }

    public Crate getCrate(final int id) {
        return this.db.getCrate(id);
    }


    public Bottle updateBottle(final int id, final Bottle updatedBottle) {
        final Bottle bottle = this.getBottle(id);

        if (bottle == null || updatedBottle == null) {
            return null;
        }
        Optional.ofNullable(updatedBottle.getName()).ifPresent(bottle::setName);
        Optional.of(updatedBottle.getVolume()).ifPresent(bottle::setVolume);
        Optional.of(updatedBottle.isAlcoholic()).ifPresent(bottle::setAlcoholic);

        Optional.of(updatedBottle.getVolumePercent()).ifPresent(bottle::setVolumePercent);
        Optional.of(updatedBottle.getPrice()).ifPresent(bottle::setPrice);
        Optional.ofNullable(updatedBottle.getSupplier()).ifPresent(bottle::setSupplier);
        Optional.of(updatedBottle.getInStock()).ifPresent(bottle::setInStock);

        return updatedBottle;
    }

    public Crate updateCrate(final int id, final Crate updatedCrate) {
        final Crate crate = this.getCrate(id);
        if (crate == null || updatedCrate == null) {
            return null;
        }
        Optional.ofNullable(updatedCrate.getBottle()).ifPresent(crate::setBottle);
        Optional.of(updatedCrate.getNoOfBottles()).ifPresent(crate::setNoOfBottles);
        Optional.of(updatedCrate.getPrice()).ifPresent(crate::setPrice);
        Optional.of(updatedCrate.getInStock()).ifPresent(crate::setInStock);

        return updatedCrate;
    }

    public boolean deleteBeverage(final int id) {
        return this.db.deleteBottle(id);
    }

    public boolean deleteCrate(final int id) {
        return this.db.deleteCrate(id);
    }
}
