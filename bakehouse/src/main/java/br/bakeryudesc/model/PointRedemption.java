package br.bakeryudesc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "point_redemptions")
public class PointRedemption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_redemption_id")
    private Long id;

    @Column(name = "redemption_date", nullable = false)
    private LocalDateTime redemptionDate;

    @Column(name = "points_used", nullable = false)
    private int pointsUsed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    public PointRedemption(LocalDateTime redemptionDate, int pointsUsed, Customer customer, Product product) {
        this.redemptionDate = redemptionDate;
        this.pointsUsed = pointsUsed;
        this.customer = customer;
        this.product = product;
    }

    @PrePersist
    protected void onPersist() {
        redemptionDate = LocalDateTime.now();
    }
}
