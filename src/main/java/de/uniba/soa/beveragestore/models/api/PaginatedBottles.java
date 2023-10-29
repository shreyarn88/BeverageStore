package de.uniba.soa.beveragestore.models.api;

import de.uniba.soa.beveragestore.models.dto.BottleDTO;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.net.URI;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"pagination", "bottles", "href"})
public class PaginatedBottles {
    private Pagination pagination;
    private List<BottleDTO> bottles;
    private URI href;

    public PaginatedBottles() {
    }

    public PaginatedBottles(final Pagination pagination, final List<BottleDTO> bottles, final URI href) {
        this.pagination = pagination;
        this.bottles = bottles;
        this.href = href;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<BottleDTO> getBottles() {
        return bottles;
    }

    public void setBottles(List<BottleDTO> bottles) {
        this.bottles = bottles;
    }

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }
}
