package br.bakeryudesc.view;

import br.bakeryudesc.controller.ProductController;
import br.bakeryudesc.model.Product;
import br.bakeryudesc.utils.DialogUtil;
import br.bakeryudesc.utils.RefreshFlag;
import br.bakeryudesc.view.tab.ProductInfoPanel;
import lombok.Getter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public class ProductView {
    @Getter
    private JPanel mainPanel;
    private JTextField searchProduct;
    private JLabel searchLabel;
    private JPanel contentPanel;
    private JList jListProduct;
    private JButton buttonDelete;
    private JScrollPane productScrollPane;
    private List<Product> productList;

    private ProductInfoPanel productInfoPanel;
    private Product currentProduct;

    public ProductView() {
        initListeners();
        populateProductList();
    }

    private void populateProductList() {
        ProductController controller;
        try {
            controller = new ProductController();

            jListProduct.removeAll();
            productList = controller.findAllProducts();

            jListProduct.setListData(productList.toArray());
            jListProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void initListeners() {

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

        jListProduct.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }

            Product product = (Product) jListProduct.getSelectedValue();

            if (product == null) {
                return;
            }

            currentProduct = product;

            refreshInfo();
        });

        buttonDelete.addActionListener(e -> {
            deleteProduct();
        });
    }

    private void deleteProduct() {
        ProductController controller;
        try {
            controller = new ProductController();

            controller.deleteProduct(currentProduct);
            DialogUtil.showSuccessDeleted(mainPanel, "Product");
            RefreshFlag.refreshCustomerView = true;
            RefreshFlag.refreshProductView = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void refreshInfo() {
        if (productInfoPanel == null) {
            productInfoPanel = new ProductInfoPanel(currentProduct);
            contentPanel.add(productInfoPanel.getMainPanel());
        } else {
            productInfoPanel.setProduct(currentProduct);
            productInfoPanel.refreshData();
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void filterList() {
        String searchText = searchProduct.getText().toLowerCase();

        if (searchText.isEmpty()) {
            jListProduct.setListData(productList.toArray());
            return;
        }

        List<Product> filteredList = productList.stream()
                .filter(product -> product.getName() != null && product.getName().toLowerCase().contains(searchText))
                .toList();

        jListProduct.setListData(filteredList.toArray());
    }

    public void refreshData() {
        populateProductList();
        refreshInfo();
    }

}
