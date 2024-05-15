package com.github.tqspolloshermanos.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "OrderStatus")
public class OrderStatus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id")
    private Long orderStatusId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "status")
    private Status status;

    @Column(nullable = false, name = "status_date")
    private LocalDateTime statusDate;

    public OrderStatus(Order order, Status status, LocalDateTime statusDate) {
        this.order = order;
        this.status = status;
        this.statusDate = statusDate;
    }

    public OrderStatus() {
    }

    public Long getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Long orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderStatusId == null) ? 0 : orderStatusId.hashCode());
        result = prime * result + ((order == null) ? 0 : order.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((statusDate == null) ? 0 : statusDate.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderStatus that)) return false;
        return Objects.equals(getOrderStatusId(), that.getOrderStatusId()) &&
                Objects.equals(getOrder(), that.getOrder()) &&
                getStatus() == that.getStatus() &&
                Objects.equals(getStatusDate(), that.getStatusDate());
    }

    @Override
    public String toString() {
        return "OrderStatus [orderStatusId=" + orderStatusId + ", order=" + order + ", status=" + status
                + ", statusDate=" + statusDate + "]";
    }

}
