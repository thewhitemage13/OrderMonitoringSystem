package org.thewhitemage13.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDTO implements Serializable {
    private Long userId;
    private String address;
    private Long productId;
    private Long countOfItems;

    @Override
    public String toString() {
        return "CreateOrderDTO{" +
                "userId=" + userId +
                ", address='" + address + '\'' +
                ", productId=" + productId +
                ", countOfItems=" + countOfItems +
                '}';
    }
}
