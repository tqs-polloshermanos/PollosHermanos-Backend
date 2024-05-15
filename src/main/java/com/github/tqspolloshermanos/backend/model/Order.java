package com.github.tqspolloshermanos.backend.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetail;

    @Column(nullable = false, name = "order_date")
    private LocalDateTime orderDate;

    @Column(nullable = false, name = "total_amount")
    private double totalAmount;
    
    @Column(name = "number_of_order")
    private int numberOfOrder;

    public Order(User user, Restaurant restaurant, List<OrderDetail> orderDetail, LocalDateTime orderDate) {
        this.user = user;
        this.restaurant = restaurant;
        this.orderDetail = orderDetail;
        this.orderDate = orderDate;
        this.totalAmount = calculateTotalAmount(orderDetail);
        this.numberOfOrder = restaurant.getNumberOfOrders();
    }

    public Order() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
        this.totalAmount = calculateTotalAmount(orderDetail);
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getNumberOfOrder() {
        return numberOfOrder;
    }

    public void setNumberOfOrder(int numberOfOrder) {
        this.numberOfOrder = numberOfOrder;
    }
    
    private double calculateTotalAmount(List<OrderDetail> orderDetails) {
        if(orderDetails == null){
            return 0;
        }
        return orderDetails.stream().mapToDouble(OrderDetail::getPrice).sum();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((restaurant == null) ? 0 : restaurant.hashCode());
        result = prime * result + ((orderDetail == null) ? 0 : orderDetail.hashCode());
        result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
        long temp;
        temp = Double.doubleToLongBits(totalAmount);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + numberOfOrder;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(getTotalAmount(), order.getTotalAmount()) == 0 &&
                getNumberOfOrder() == order.getNumberOfOrder() &&
                Objects.equals(getOrderId(), order.getOrderId()) &&
                Objects.equals(getUser(), order.getUser()) &&
                Objects.equals(getRestaurant(), order.getRestaurant()) &&
                Objects.equals(getOrderDetail(), order.getOrderDetail()) &&
                Objects.equals(getOrderDate(), order.getOrderDate());
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", user=" + user + ", restaurant=" + restaurant + ", orderDetail="
                + orderDetail + ", orderDate=" + orderDate + ", totalAmount=" + totalAmount + ", numberOfOrder="
                + numberOfOrder + "]";
    }
}
