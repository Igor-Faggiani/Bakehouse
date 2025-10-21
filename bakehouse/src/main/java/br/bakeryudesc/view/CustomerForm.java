package br.bakeryudesc.view;

import br.bakeryudesc.controller.CustomerController;
import br.bakeryudesc.model.Customer;
import br.bakeryudesc.view.tab.InfoCustomerTab;
import lombok.Getter;

import javax.swing.*;
import java.util.List;

public class CustomerForm {
    @Getter
    private JPanel mainPanel;
    private JTextField searchCustomer;
    private JTabbedPane tabbedPaneCustomerInfo;
    private JLabel searchLabel;
    private JList listCustomer;

    private JPanel infoTabPanel;
    private JPanel buyHistoryTabPanel;

    private List<Customer> customerList;

    public CustomerForm() {
        initListeners();
        populateCustomerList();
    }

    private void initListeners() {
        listCustomer.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                infoTabPanel.add(new InfoCustomerTab(customerList.get(listCustomer.getSelectedIndex())).getMainPanel());
                infoTabPanel.revalidate();
                infoTabPanel.repaint();
            }
        });
    }

    private void populateCustomerList() {

        CustomerController customerController = new CustomerController();
        customerList = customerController.findAllCustomers();

        listCustomer.setListData(customerList.toArray());
        listCustomer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

}
