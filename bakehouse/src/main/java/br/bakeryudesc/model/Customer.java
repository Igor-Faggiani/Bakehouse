package br.bakeryudesc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private int id;
    private String name;
    private String cpf;
    private String phoneNumber;
    private int totalPoints;

    public Customer(String name, String cpf, String phoneNumber, int totalPoints) {
        this.name = name;
        this.cpf = cpf;
        this.phoneNumber = phoneNumber;
        this.totalPoints = totalPoints;
    }

}
