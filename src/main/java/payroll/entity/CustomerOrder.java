package payroll.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import payroll.enums.OrderStatus;

import java.util.Objects;

@SuppressWarnings("unused")
@Entity
public class CustomerOrder {

    private @Id
    @GeneratedValue Long id;
    private String description;
    private OrderStatus status;

    protected CustomerOrder() {
    }

    public CustomerOrder(String description, OrderStatus status) {
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrderStatus getStatus() {
        return this.status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CustomerOrder order)) return false;
        return Objects.equals(this.id, order.id)
            && Objects.equals(this.description, order.description)
            && this.status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.description, this.status);
    }

    @Override
    public String toString() {
        return "CustomerOrder{id=" + this.id + ", description='" + this.description + "', status=" + this.status + "}";
    }
}
