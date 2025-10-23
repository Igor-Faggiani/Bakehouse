package br.bakeryudesc.view.tab.register;

import br.bakeryudesc.controller.CustomerController;
import br.bakeryudesc.utils.DialogUtil;
import br.bakeryudesc.utils.RefreshFlag;
import br.bakeryudesc.utils.ValidateInput;
import lombok.Getter;

import javax.swing.*;

public class RegisterCustomerTab {
    @Getter
    private JPanel mainPanel;
    private JLabel labelName;
    private JTextField textFieldCustomerName;
    private JLabel labelCpf;
    private JTextField textFieldCustomerCPF;
    private JLabel labelPhone;
    private JTextField textFieldCustomerPhoneNumber;
    private JButton buttonAdd;

    private final CustomerController customerController;

    public RegisterCustomerTab() {
        this.customerController = new CustomerController();
        initListeners();
    }

    private void initListeners() {
        buttonAdd.addActionListener(e -> {
            addCustomer();
        });
    }

    private void addCustomer() {
        String name = textFieldCustomerName.getText();
        String cpf = textFieldCustomerCPF.getText().replace(".","").replace("-","");
        String phone = textFieldCustomerPhoneNumber.getText();

        if (!ValidateInput.isValidName(name)) {
            DialogUtil.showInvalidInputDialog(mainPanel, "Name");
            return;
        }

        if (!ValidateInput.isValidCpf(cpf) || cpf.length() != 11) {
            DialogUtil.showInvalidInputDialog(mainPanel, "CPF");
            return;
        }

        if (!ValidateInput.isValidPhoneNumber(phone)) {
            DialogUtil.showInvalidInputDialog(mainPanel, "Phone");
            return;
        }

        customerController.createCustomer(name, cpf, phone);
        cleanFields();
        DialogUtil.showSuccessAdded(mainPanel, "Customer");
        RefreshFlag.refreshRegisterView = true;
        RefreshFlag.refreshCustomerView = true;
    }

    private void cleanFields() {
        textFieldCustomerName.setText("");
        textFieldCustomerCPF.setText("");
        textFieldCustomerPhoneNumber.setText("");
    }

}
