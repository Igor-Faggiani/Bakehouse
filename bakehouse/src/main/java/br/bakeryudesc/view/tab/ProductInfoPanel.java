package br.bakeryudesc.view.tab;

import br.bakeryudesc.controller.CategoryController;
import br.bakeryudesc.controller.ProductController;
import br.bakeryudesc.model.Category;
import br.bakeryudesc.model.Product;
import br.bakeryudesc.utils.DialogUtil;
import br.bakeryudesc.utils.ValidateInput;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.List;

public class ProductInfoPanel {
    @Getter
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField textField_productName;
    private JLabel priceLabel;
    private JTextField textField_productPrice;
    private JLabel stockLabel;
    private JTextField textField_productStock;
    private JLabel pointLabel;
    private JTextField textField_redemptionPoints;
    private JLabel isRedeemableLabel;
    private JCheckBox checkBox_isRedeemable;
    private JLabel categoryLabel;
    private JComboBox comboBox_productCategory;
    private JButton buttonSave;

    @Getter
    @Setter
    private Product product;
    private final CategoryController categoryController;
    private final ProductController  productController;

    public ProductInfoPanel(Product product) {
        this.product = product;
        this.categoryController = new CategoryController();
        this.productController = new ProductController();
        refreshData();
        initListeners();
    }

    private void initListeners() {
        checkBox_isRedeemable.addActionListener(e -> textField_redemptionPoints.setEnabled(checkBox_isRedeemable.isSelected()));
        buttonSave.addActionListener(e -> updateProduct());
    }

    private void updateProduct() {
        String price = textField_productPrice.getText();
        String stock = textField_productStock.getText();
        String redemptionPoints = textField_productStock.getText();

        if (!ValidateInput.isNumeric(price)) {
            DialogUtil.showInvalidInputDialog(mainPanel, "Price");
            return;
        }

        if (!ValidateInput.isNumeric(stock)) {
            DialogUtil.showInvalidInputDialog(mainPanel, "Stock");
            return;
        }

        if (!ValidateInput.isNumeric(redemptionPoints)) {
            DialogUtil.showInvalidInputDialog(mainPanel, "Redemption Points");
            return;
        }

        product.setName(textField_productName.getText());
        product.setPrice(new BigDecimal(price));
        product.setStockQuantity(Integer.parseInt(stock));
        product.setRedeemable(checkBox_isRedeemable.isSelected());
        product.setPointsCost(Integer.valueOf(redemptionPoints));
        product.setCategory((Category) comboBox_productCategory.getSelectedItem());

        productController.updateProduct(product);
        DialogUtil.showSuccessUpdated(mainPanel, "Product");
    }

    public void refreshData() {
        List<Category> categoryList = categoryController.findAllCategories();
        comboBox_productCategory.removeAllItems();
        categoryList.forEach(category -> comboBox_productCategory.addItem(category));

        if (product == null) {
            return;
        }

        textField_productName.setText(product.getName());
        textField_productPrice.setText(product.getPrice().toString());
        textField_productStock.setText(product.getStockQuantity() + "");
        textField_redemptionPoints.setText(product.getPointsCost() == null ? "0" : product.getPointsCost() + "");
        checkBox_isRedeemable.setSelected(product.isRedeemable());
        comboBox_productCategory.setSelectedItem(product.getCategory());

        textField_redemptionPoints.setEnabled(product.isRedeemable());
    }

}
