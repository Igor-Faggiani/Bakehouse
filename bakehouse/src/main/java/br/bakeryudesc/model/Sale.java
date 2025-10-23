package br.bakeryudesc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate;

    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "points_earned")
    private int pointsEarned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(
            mappedBy = "sale",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<SaleItem> items = new ArrayList<>();

    public Sale(LocalDateTime saleDate, BigDecimal totalAmount, int pointsEarned, Customer customer, List<SaleItem> items) {
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
        this.pointsEarned = pointsEarned;
        this.customer = customer;
        this.items = items;
    }

    public void addItem(SaleItem item) {
        items.add(item);
        item.setSale(this);
    }

    public void removeItem(SaleItem item) {
        items.remove(item);
        item.setSale(null);
    }

    public String getItemString() {
        StringBuilder itemString = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            itemString.append(items.get(i).getQuantity()).append(" x ");

            if (i == items.size() - 1) {
                itemString.append(items.get(i).toString());
            } else {
                itemString.append(items.get(i).toString()).append(", ");
            }
        }

        return itemString.toString();
    }

    @PrePersist
    protected void onPersist() {
        saleDate = LocalDateTime.now();
    }

}
