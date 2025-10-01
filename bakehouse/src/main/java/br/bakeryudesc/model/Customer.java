package br.bakeryudesc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "total_points", nullable = false)
    private int totalPoints;

    public Customer(int totalPoints, String phone, String cpf, String name) {
        this.totalPoints = totalPoints;
        this.phone = phone;
        this.cpf = cpf;
        this.name = name;
    }

}
