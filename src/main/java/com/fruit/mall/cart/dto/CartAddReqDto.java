package com.fruit.mall.cart.dto;

import com.fruit.mall.cart.Cart;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CartAddReqDto {
    private Long productId;
    private int productPrice;
    private int productCount;
    private int productDiscount;
    private List<Cart> localCart;

    @Builder
    public CartAddReqDto(Long productId, int productPrice, int productCount, int productDiscount) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.productCount = productCount;
        this.productDiscount = productDiscount;
    }
}
