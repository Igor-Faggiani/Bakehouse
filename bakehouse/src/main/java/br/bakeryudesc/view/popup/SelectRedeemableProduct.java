package br.bakeryudesc.view.popup;

import br.bakeryudesc.model.Customer;
import br.bakeryudesc.model.Product;
import br.bakeryudesc.utils.DialogUtil;
import lombok.Getter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SelectRedeemableProduct {
    @Getter
    private JPanel mainPanel;
    private JLabel amountLabel;
    private JLabel remainingLabel;
    private JTextField amountPointsTextField;
    private JTextField remainingPointsTextField;
    private JList listProductRedeemable;
    private JScrollPane scrollPane;
    private JButton buttonConfirm;
    private JButton buttonCancel;

    private final List<Product> products;
    private final List<Product> allSelectedProducts;
    private List<Product> selectedProductsToRedeemble;
    private final Customer customer;
    private final int points;
    private int selectedPoints = 0;

    private JFrame frame;

    public SelectRedeemableProduct(List<Product> allSelectedProducts, List<Product> products, int points, Customer customer) {
        this.products = products;
        this.points = points;
        this.frame = new JFrame();
        this.selectedProductsToRedeemble = new ArrayList<>();
        this.allSelectedProducts = allSelectedProducts;
        this.customer = customer;

        initComponents();
        populateComponents();
        addListeners();
    }

    private void addListeners() {
        listProductRedeemable.addListSelectionListener(e -> {
            calculateSelectedProducts();
        });

        buttonConfirm.addActionListener(e -> {
            if (selectedPoints > points) {
                DialogUtil.showErrorMessage(mainPanel, "Insufficient points", "You cannot select more items than can be redeemed with points.");
                return;
            }
           new FinishToPay(allSelectedProducts, selectedProductsToRedeemble, customer);
           this.frame.dispose();
        });

        buttonCancel.addActionListener(e -> {
            new FinishToPay(allSelectedProducts, List.of(), customer);
            this.frame.dispose();
        });
    }

    private void calculateSelectedProducts() {
        int[] selectedIndices = listProductRedeemable.getSelectedIndices();

        selectedProductsToRedeemble.clear();
        for (int selectedIndex : selectedIndices) {
            selectedProductsToRedeemble.add(products.get(selectedIndex));
        }

        selectedPoints = 0;

        for (Product product : selectedProductsToRedeemble) {
            selectedPoints += product.getPointsCost();
        }

        remainingPointsTextField.setText(points - selectedPoints + "");
    }

    private void populateComponents() {
        amountPointsTextField.setText(points + "");
        remainingPointsTextField.setText(points + "");

        List<String> productToPrint = new ArrayList<>();
        for (Product product : products) {
            productToPrint.add(product.getName() + " | " + product.getPointsCost());
        }

        listProductRedeemable.setListData(productToPrint.toArray());
    }

    private void initComponents() {
        frame.setTitle("Product Redeemable");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
