package br.bakeryudesc.view.tab;

import br.bakeryudesc.controller.SaleController;
import br.bakeryudesc.model.Customer;
import br.bakeryudesc.model.Sale;
import br.bakeryudesc.utils.ValidateInput;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CustomerBuyHistory {
    @Getter
    private JPanel mainPanel;
    private JTable tableHistory;
    private JScrollPane scrollPane;
    @Getter
    @Setter
    private Customer customer;
    private DefaultTableModel model;

    private final String[] columns = {"Date", "Items", "Points", "Total price"};

    public CustomerBuyHistory(Customer customer) {
        this.customer = customer;
        populateTable();
    }

    private void populateTable() {
        SaleController controller;
        try {
            controller = new SaleController();

            List<Sale> sales = controller.findSalesByCustomerId(customer.getId());
            model = getDefaultTableModel(columns, sales);

            tableHistory.setModel(model);
            tableHistory.setAutoCreateRowSorter(true);
            tableHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            scrollPane.setViewportView(tableHistory);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void refreshTable() {
        SaleController controller;
        try {
            controller = new SaleController();

            model.setRowCount(0);
            List<Sale> sales = controller.findSalesByCustomerId(customer.getId());
            fillTable(sales, model);

            tableHistory.clearSelection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private DefaultTableModel getDefaultTableModel(String[] columns, List<Sale> sales) {
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return fillTable(sales, model);
    }

    private DefaultTableModel fillTable(List<Sale> sales, DefaultTableModel model) {
        for (Sale sale : sales) {
            Object[] rowData = {
                    ValidateInput.formatTimestamp(sale.getSaleDate().toString()),
                    sale.getItemString(),
                    sale.getPointsEarned(),
                    "R$" + sale.getTotalAmount()
            };
            model.addRow(rowData);
        }
        return model;
    }

}
