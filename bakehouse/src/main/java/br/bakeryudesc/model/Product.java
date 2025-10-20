package br.bakeryudesc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Column(name = "redeemable")
    private boolean redeemable;

    @Column(name = "points_cost")
    private Integer pointsCost;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Product(String name, BigDecimal price, int stockQuantity, boolean redeemable, Integer pointsCost, String imageUrl, Category category) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.redeemable = redeemable;
        this.pointsCost = pointsCost;
        this.imageUrl = imageUrl;
        this.category = category;
    }

}
