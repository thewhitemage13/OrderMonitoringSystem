package org.thewhitemage13.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "orders")
@Entity
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    private String items;
    private String address;
    private String status;
    private BigDecimal totalSum;
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "count_of_items", nullable = false)
    private Long countOfItems;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Order() {
    }

    public Order(Long userId, String items, String address, String status, BigDecimal totalSum, Long productId, Long countOfItems, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
        this.items = items;
        this.address = address;
        this.status = status;
        this.totalSum = totalSum;
        this.productId = productId;
        this.countOfItems = countOfItems;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Order(Long userId, String items, String address, String status, BigDecimal totalSum, Long productId, Long countOfItems, LocalDateTime createdAt) {
        this.userId = userId;
        this.items = items;
        this.address = address;
        this.status = status;
        this.totalSum = totalSum;
        this.productId = productId;
        this.countOfItems = countOfItems;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", items='" + items + '\'' +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                ", totalSum=" + totalSum +
                ", productId=" + productId +
                ", countOfItems=" + countOfItems +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
