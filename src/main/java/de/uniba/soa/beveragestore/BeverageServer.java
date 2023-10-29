package de.uniba.soa.beveragestore;

import com.sun.net.httpserver.HttpServer;
import de.uniba.soa.beveragestore.config.Configuration;
import jakarta.ws.rs.core.UriBuilder;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import java.util.logging.Logger;

public class BeverageServer {
    private static final Properties properties = Configuration.loadProperties();
    private static final Logger logger = Logger.getLogger(BeverageServer.class.getName());

    public static void main(String[] args) throws IOException {

        String serverUri = properties.getProperty("serverUri");

        URI baseUri = UriBuilder.fromUri(serverUri).build();
        ResourceConfig config = ResourceConfig.forApplicationClass(BeverageStoreAPI.class);
        HttpServer server = JdkHttpServerFactory.createHttpServer(baseUri, config);
        logger.info("Server started on : " + serverUri);
        logger.info("Go to http://localhost:9999/v1/swagger-ui/index.html to find more details");
        logger.info("Press any key to stop the server..");
        System.in.read();
        logger.info("Stopping server");
        server.stop(1);
    }
}
