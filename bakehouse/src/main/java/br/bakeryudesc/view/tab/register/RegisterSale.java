package br.bakeryudesc.view.tab.register;

import br.bakeryudesc.controller.ProductController;
import br.bakeryudesc.model.Customer;
import br.bakeryudesc.model.Product;
import br.bakeryudesc.utils.DialogUtil;
import br.bakeryudesc.view.popup.SelectRedeemableProduct;
import br.bakeryudesc.view.popup.FinishToPay;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterSale {
    @Getter
    private JPanel mainPanel;
    private JList listAllProduct;
    private JTextField searchProduct;
    private JList listSelectedProduct;
    private JButton buttonAddToSale;
    private JButton buttonRemoveFromSale;
    private JButton buttonFinish;
    private JLabel searchProductLabel;
    private JButton buttonCancel;
    private JScrollPane selectedProductScrollPane;
    private JScrollPane allProductScrollPane;

    private final ProductController productController;

    @Getter
    @Setter
    private Customer customer;

    private Product selectedProduct;

    private List<Product> productList;
    private Map<Product, Integer> selectProductList;

    public RegisterSale(Customer customer) {
        this.customer = customer;
        this.productController = new ProductController();
        this.selectProductList = new HashMap<>();
        populateComponents();
        initListeners();
    }

    private void initListeners() {
        buttonAddToSale.addActionListener(e -> {
            if (selectedProduct != null) {
                addProductToSale();
            }
        });

        buttonRemoveFromSale.addActionListener(e -> {
            if (selectedProduct != null) {
                removeProductFromSale();
            }
        });

        buttonFinish.addActionListener(e -> finishSale());

        listAllProduct.addListSelectionListener(e -> {
            listSelectedProduct.clearSelection();
            selectedProduct = (Product) listAllProduct.getSelectedValue();
        });

        listSelectedProduct.addListSelectionListener(e -> {
            listAllProduct.clearSelection();
            selectedProduct = (Product) listSelectedProduct.getSelectedValue();
        });

        searchProduct.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterList();
            }
        });

        buttonCancel.addActionListener(e -> refreshList());

    }

    private void finishSale() {
        if (selectProductList.isEmpty() && !productList.isEmpty()) {
            DialogUtil.showEmptyInputDialog(mainPanel, "Product list");
            return;
        }

        int points = customer.getTotalPoints();
        List<Product> productThatCanBeBoughtByPointsList = calculateProductAndPoints(points);

        List<Product> selectedProductsList = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : selectProductList.entrySet()) {
            for (int i = entry.getValue(); i > 0; i--) {
                selectedProductsList.add(entry.getKey());
            }
        }

        if (!productThatCanBeBoughtByPointsList.isEmpty() && DialogUtil.askToUsePoints(mainPanel, points)) {
            new SelectRedeemableProduct(selectedProductsList, productThatCanBeBoughtByPointsList, points, customer);
        } else {
            new FinishToPay(selectedProductsList, List.of(), customer);
        }

        refreshList();
    }

    private List<Product> calculateProductAndPoints(int points) {
        List<Product> productsRedeemable = new ArrayList<>();

        for (Map.Entry<Product, Integer> entry : selectProductList.entrySet()) {
            if (entry.getKey().isRedeemable() && entry.getKey().getPointsCost() <= points) {
                for (int i = entry.getValue(); i > 0; i--) {
                    productsRedeemable.add(entry.getKey());
                }
            }
        }

        return productsRedeemable;
    }

    private void populateComponents() {
        productList = productController.findAllProductsInStock();
        listAllProduct.setListData(productList.toArray());
        listAllProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void addProductToSale() {
        if (selectProductList.containsKey(selectedProduct) && selectedProduct.getStockQuantity() - selectProductList.get(selectedProduct) == 0) {
            DialogUtil.showErrorMessage(mainPanel, "Stock", "No more product in stock");
            return;
        }

        selectProductList.put(selectedProduct, selectProductList.getOrDefault(selectedProduct, 0) + 1);
        refreshDataOnList();
    }

    private void removeProductFromSale() {
        if (selectProductList.get(selectedProduct) > 1) {
            selectProductList.put(selectedProduct, selectProductList.getOrDefault(selectedProduct, 0) - 1);
        } else {
            selectProductList.remove(selectedProduct);
        }

        refreshDataOnList();
    }

    private void filterList() {
        String searchText = searchProduct.getText().toLowerCase();

        if (searchText.isEmpty()) {
            listAllProduct.setListData(productList.toArray());
            return;
        }

        List<Product> filteredList = productList.stream()
                .filter(product -> product.getName() != null && product.getName().toLowerCase().contains(searchText))
                .toList();

        listAllProduct.setListData(filteredList.toArray());
    }

    private void refreshDataOnList() {
        List<String> products = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : selectProductList.entrySet()) {
            products.add(entry.getValue() + " x " + entry.getKey().getName());
        }

        listSelectedProduct.setListData(products.toArray());
    }

    private void refreshList() {
        mainPanel.setVisible(false);
    }

    public void refreshData() {
        if (selectedProduct != null) {
            selectProductList.clear();
        }

        searchProduct.setText("");
        selectedProduct = null;

        listSelectedProduct.setListData(List.of().toArray());

        populateComponents();
    }

}
