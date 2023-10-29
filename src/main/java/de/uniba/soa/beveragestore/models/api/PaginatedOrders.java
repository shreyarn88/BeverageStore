package de.uniba.soa.beveragestore.models.api;

import de.uniba.soa.beveragestore.models.dto.CrateDTO;
import de.uniba.soa.beveragestore.models.dto.OrderDTO;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

import java.net.URI;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"pagination", "orders", "href"})
public class PaginatedOrders {

    private Pagination pagination;
    private List<OrderDTO> orders;
    private URI href;

    public PaginatedOrders() {
    }

    public PaginatedOrders(Pagination pagination, List<OrderDTO> orders, URI href) {
        this.pagination = pagination;
        this.orders = orders;
        this.href = href;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public URI getHref() {
        return href;
    }

    public void setHref(URI href) {
        this.href = href;
    }
}
