package de.uniba.soa.beveragestore;

import de.uniba.soa.beveragestore.controller.BottleController;
import de.uniba.soa.beveragestore.controller.CrateController;
import de.uniba.soa.beveragestore.controller.OrderController;
import de.uniba.soa.beveragestore.controller.SwaggerController;
import de.uniba.soa.beveragestore.provider.BottleBodyWriter;
import de.uniba.soa.beveragestore.provider.CrateBodyWriter;
import de.uniba.soa.beveragestore.provider.OrderBodyWriter;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;

import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BeverageStoreAPI extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> resources = new HashSet<>();

        resources.add(BottleController.class);
        resources.add(CrateController.class);
        resources.add(OrderController.class);

        // Providers
        resources.add(BottleBodyWriter.class);
        resources.add(CrateBodyWriter.class);
        resources.add(OrderBodyWriter.class);

        resources.add(SwaggerController.class);
        return resources;
    }
}
