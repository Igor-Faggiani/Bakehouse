package br.bakeryudesc.view.popup;

import br.bakeryudesc.controller.SaleController;
import br.bakeryudesc.model.Customer;
import br.bakeryudesc.model.Product;
import br.bakeryudesc.utils.DialogUtil;
import br.bakeryudesc.utils.RefreshFlag;
import lombok.Getter;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FinishToPay {
    @Getter
    private JPanel mainPanel;
    private JList jListProductList;
    private JLabel amountLabel;
    private JTextField textFieldAmountToPay;
    private JButton buttonCompleted;
    private JLabel pointsGainedLabel;
    private JTextField textFieldPointsGained;
    private JScrollPane scrollPane;

    private final List<Product> products;
    private final List<Product> redeemableProducts;
    private final Customer customer;

    private final SaleController saleController;

    private int pointsToGain;

    private JFrame frame;

    public FinishToPay(List<Product> products, List<Product> redeemableProducts, Customer customer) {
        this.products = products;
        this.redeemableProducts = redeemableProducts;
        this.customer = customer;
        this.saleController = new SaleController();
        this.frame = new JFrame();

        initComponents();
        populateComponents();
        addListeners();
    }

    private void addListeners() {
        buttonCompleted.addActionListener(e -> {
            completeBuy();
        });
    }

    private void initComponents() {
        frame.setTitle("Pay");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void populateComponents() {
        BigDecimal amountTotal = new BigDecimal(0);
        List<String> productToPrint = new ArrayList<>();

        for (Product product : products) {
            BigDecimal value = product.getPrice();
            amountTotal = amountTotal.add(value);
            productToPrint.add("+R$" + product.getPrice() + " - " + product.getName());
        }

        productToPrint.add("---------------");

        for (Product product : redeemableProducts) {
            BigDecimal value = product.getPrice();
            amountTotal = amountTotal.subtract(value);
            productToPrint.add("-R$" + value + " - " + product.getName());
        }

        pointsToGain = amountTotal.divideToIntegralValue(new BigDecimal(10)).intValue();

        jListProductList.setListData(productToPrint.toArray());
        textFieldAmountToPay.setText("R$" + amountTotal);
        textFieldPointsGained.setText(pointsToGain + "");
    }

    private void completeBuy() {
        saleController.createSale(customer.getId(), products);
        DialogUtil.showSuccessBuy(mainPanel, "Buy");
        RefreshFlag.refreshCustomerView = true;
        RefreshFlag.refreshProductView = true;
        this.frame.dispose();
    }

}
