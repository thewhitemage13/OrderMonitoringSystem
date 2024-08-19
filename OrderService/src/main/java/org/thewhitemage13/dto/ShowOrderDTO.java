package org.thewhitemage13.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowOrderDTO implements Serializable {
    private Long userId;
    private String items;
    private String address;
    private String status;
    private BigDecimal totalSum;
    private Long productId;
    private Long countOfItems;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "ShowOrderDTO{" +
                "userId=" + userId +
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
