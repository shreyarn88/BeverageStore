package de.uniba.soa.beveragestore.provider;

import de.uniba.soa.beveragestore.models.dto.BottleDTO;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * This is only for demonstration purposes.
 * In reality, when designing your own content type, you have to also write a message body
 * writer for each generic class like lists, maps, sets etc.
 */
@Produces(MediaType.TEXT_PLAIN)
public class BottleBodyWriter implements MessageBodyWriter<BottleDTO> {
    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        return type == BottleDTO.class;
    }

    @Override
    public long getSize(final BottleDTO bottle, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
        // deprecated by JAX-RS 2.0 and ignored by Jersey runtime
        return -1;
    }

    @Override
    public void writeTo(final BottleDTO bottle, final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String, Object> httpHeaders, final OutputStream entityStream) throws IOException, WebApplicationException {
        entityStream.write(bottle.toString().getBytes());
        entityStream.flush();
    }
}
