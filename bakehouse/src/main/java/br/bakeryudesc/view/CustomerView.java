package br.bakeryudesc.view;

import br.bakeryudesc.controller.CustomerController;
import br.bakeryudesc.model.Customer;
import br.bakeryudesc.utils.DialogUtil;
import br.bakeryudesc.utils.RefreshFlag;
import br.bakeryudesc.view.tab.CustomerBuyHistory;
import br.bakeryudesc.view.tab.InfoCustomerTab;
import lombok.Getter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;
import java.util.Objects;

public class CustomerView {
    @Getter
    private JPanel mainPanel;
    private JTextField searchCustomer;
    private JTabbedPane tabbedPaneCustomerInfo;
    private JLabel searchLabel;
    private JList jListCustomer;

    private JPanel infoTabPanel;
    private JPanel buyHistoryTabPanel;
    private JButton buttonDelete;
    private JScrollPane customerScrollPane;

    private List<Customer> customerList;

    private InfoCustomerTab infoCustomerTab;
    private CustomerBuyHistory customerBuyHistory;

    private Customer currentCustomer;

    public CustomerView() {
        initListeners();
        populateCustomerList();
    }

    private void initListeners() {

        searchCustomer.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterCustomerList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterCustomerList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterCustomerList();
            }
        });

        jListCustomer.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }

            Customer customer = (Customer) jListCustomer.getSelectedValue();

            if (customer == null) {
                return;
            }

            currentCustomer = customer;

            System.out.println("CustomerView: " + currentCustomer);
            if (tabbedPaneCustomerInfo.getSelectedIndex() == 0) {
                refreshInfo();
            } else if (tabbedPaneCustomerInfo.getSelectedIndex() == 1) {
                refreshHistory();
            }
        });

        tabbedPaneCustomerInfo.addChangeListener(e -> {
            int index = jListCustomer.getSelectedIndex();

            if (index > 0) {
                index = 0;
            }

            Customer customer = customerList.get(index);
            if (tabbedPaneCustomerInfo.getSelectedIndex() == 0) {
                refreshInfo();
            } else if (tabbedPaneCustomerInfo.getSelectedIndex() == 1) {
                refreshHistory();
            }
        });

        buttonDelete.addActionListener(e -> {
            deleteCustomer();
        });

    }

    private void deleteCustomer() {
        CustomerController controller;
        try {
            controller = new CustomerController();

            controller.deleteCustomer(currentCustomer);
            DialogUtil.showSuccessDeleted(mainPanel, "Customer");
            populateCustomerList();
            RefreshFlag.refreshCustomerView = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void refreshInfo() {
        if (infoCustomerTab == null) {
            infoCustomerTab = new InfoCustomerTab(currentCustomer);
            infoTabPanel.add(infoCustomerTab.getMainPanel());
        } else {
            infoCustomerTab.setCustomer(currentCustomer);
            infoCustomerTab.refreshData();
        }

        infoTabPanel.revalidate();
        infoTabPanel.repaint();
    }

    private void refreshHistory() {
        if (customerBuyHistory == null) {
            customerBuyHistory = new CustomerBuyHistory(currentCustomer);
            buyHistoryTabPanel.add(customerBuyHistory.getMainPanel());
        } else {
            customerBuyHistory.setCustomer(currentCustomer);
            customerBuyHistory.refreshTable();
        }

        buyHistoryTabPanel.revalidate();
        buyHistoryTabPanel.repaint();
    }

    private void populateCustomerList() {
        CustomerController customerController = new CustomerController();
        customerList = customerController.findAllCustomers();

        jListCustomer.setListData(customerList.toArray());
        jListCustomer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void filterCustomerList() {
        String searchText = searchCustomer.getText().toLowerCase();

        if (searchText.isEmpty()) {
            jListCustomer.setListData(customerList.toArray());
            return;
        }

        List<Customer> filteredList = customerList.stream()
                .filter(customer -> {
                    boolean nameMatch = customer.getName() != null && customer.getName().toLowerCase().contains(searchText);
                    boolean cpfMatch = customer.getCpf() != null && customer.getCpf().contains(searchText);
                    return nameMatch || cpfMatch;
                })
                .toList();

        jListCustomer.setListData(filteredList.toArray());
    }

    public void refreshData() {
        populateCustomerList();


        if (currentCustomer != null) {
            currentCustomer = customerList.stream().filter(customer -> Objects.equals(customer.getId(), currentCustomer.getId())).toList().getFirst();

            refreshInfo();
            refreshHistory();
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

}
