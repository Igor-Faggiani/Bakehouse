package br.bakeryudesc.view.tab;

import br.bakeryudesc.controller.CustomerController;
import br.bakeryudesc.model.Customer;
import br.bakeryudesc.utils.DialogUtil;
import br.bakeryudesc.utils.RefreshFlag;
import br.bakeryudesc.utils.ValidateInput;
import br.bakeryudesc.view.tab.register.RegisterSale;
import lombok.Getter;
import lombok.Setter;

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
    private JButton buttonAddSale;
    private JPanel salePanel;

    @Getter
    @Setter
    private Customer customer;

    private final RegisterSale registerSale;


    public InfoCustomerTab(Customer customer) {
        this.customer = customer;
        this.registerSale = new RegisterSale(customer);
        initListeners();
        refreshData();
    }

    private void initListeners() {
        buttonSave.addActionListener(e -> updateCustomer());
        buttonAddSale.addActionListener(e -> addSale());
    }

    private void addSale() {
        salePanel.removeAll();

        registerSale.getMainPanel().setVisible(true);
        salePanel.add(registerSale.getMainPanel());
        salePanel.revalidate();
        salePanel.repaint();
    }

    private void updateCustomer() {
        String name = textField_name.getText();
        String cpf = textField_cpf.getText();
        String phone = textField_phoneNumber.getText();
        String redemptionPoints = textField_redemptionPoints.getText();

        if (!ValidateInput.isValidName(name)) {
            DialogUtil.showInvalidInputDialog(mainPanel, "Name");
            return;
        }

        if (!ValidateInput.isValidCpf(cpf)) {
            DialogUtil.showInvalidInputDialog(mainPanel, "CPF");
            return;
        }

        if (!ValidateInput.isValidPhoneNumber(phone)) {
            DialogUtil.showInvalidInputDialog(mainPanel, "Phone");
            return;
        }

        if (!ValidateInput.isNumeric(redemptionPoints)) {
            DialogUtil.showInvalidInputDialog(mainPanel, "Redemption Points");
            return;
        }

        customer.setName(name);
        customer.setCpf(cpf.replace(".","").replace("-",""));
        customer.setPhone(phone);
        customer.setTotalPoints(Integer.parseInt(redemptionPoints));

        CustomerController controller;
        try {
            controller = new CustomerController();

            controller.updateCustomer(customer);
            DialogUtil.showSuccessUpdated(mainPanel, "Customer");
            RefreshFlag.refreshCustomerView = true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void refreshData() {
        registerSale.setCustomer(customer);
        registerSale.refreshData();

        textField_name.setText(customer.getName());
        textField_cpf.setText(ValidateInput.formatCpf(customer.getCpf()));
        textField_phoneNumber.setText(customer.getPhone());
        textField_redemptionPoints.setText(customer.getTotalPoints() + "");
    }

}
