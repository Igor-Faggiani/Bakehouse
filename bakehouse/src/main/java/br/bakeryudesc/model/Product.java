package br.bakeryudesc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private int id;
    private String name;
    private BigDecimal price;
    private int categoryId;
    private int stockQuantity;
    private boolean isRedeemable;
    private int pointsCost;

    public Product(String name, BigDecimal price, int categoryId, int stockQuantity, boolean isRedeemable, int pointsCost) {
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.stockQuantity = stockQuantity;
        this.isRedeemable = isRedeemable;
        this.pointsCost = pointsCost;
    }



}
