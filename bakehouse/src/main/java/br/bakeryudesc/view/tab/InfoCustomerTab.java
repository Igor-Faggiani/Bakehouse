package br.bakeryudesc.view.tab;

import br.bakeryudesc.model.Customer;
import lombok.Getter;

import javax.swing.*;

public class InfoCustomerTab {
    private JLabel nameLabel;
    private JTextField textField_name;
    private JLabel pointsLabel;
    private JTextField textField_redemptionPoints;
    private JLabel cpfLabel;
    private JTextField textField_cpf;
    private JLabel phoneLabel;
    private JTextField textField_phoneNumber;
    private JButton buttonSave;

    @Getter
    private JPanel mainPanel;

    private Customer customer;

    public InfoCustomerTab(Customer customer) {
        this.customer = customer;
        init();
    }

    private void init() {
        textField_name.setText(customer.getName());
        textField_cpf.setText(customer.getCpf());
        textField_phoneNumber.setText(customer.getPhone());
        textField_redemptionPoints.setText(customer.getTotalPoints() + "");
    }

}
