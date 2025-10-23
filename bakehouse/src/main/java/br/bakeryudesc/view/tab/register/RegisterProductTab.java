package br.bakeryudesc.view.tab.register;

import br.bakeryudesc.controller.CategoryController;
import br.bakeryudesc.controller.ProductController;
import br.bakeryudesc.model.Category;
import br.bakeryudesc.utils.DialogUtil;
import br.bakeryudesc.utils.RefreshFlag;
import br.bakeryudesc.utils.ValidateInput;
import jdk.jfr.StackTrace;
import lombok.Getter;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.List;

public class RegisterProductTab {
    @Getter
    private JPanel mainPanel;
    private JLabel nameProductLabel;
    private JTextField textFieldProductName;
    private JLabel categoryLabel;
    private JComboBox comboBoxProductCategory;
    private JLabel priceLabel;
    private JTextField textFieldProductPrice;
    private JLabel redeemableLabel;
    private JCheckBox redeemableCheckBox;
    private JLabel pointsLabel;
    private JTextField textFieldRedeemablePoints;
    private JButton buttonAdd;
    private JLabel stockLabel;
    private JTextField textFieldProductStock;

    public RegisterProductTab() {
        initListeners();
        populateComponents();
    }

    private void populateComponents() {

        CategoryController controller;
        try {
            controller = new CategoryController();

            List<Category> categoryList = controller.findAllCategories();

            comboBoxProductCategory.removeAllItems();
            categoryList.forEach(category -> comboBoxProductCategory.addItem(category));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void initListeners() {
        buttonAdd.addActionListener(e -> addProduct());
        redeemableCheckBox.addActionListener(e -> textFieldRedeemablePoints.setEnabled(redeemableCheckBox.isSelected()));
    }

    private void addProduct() {
        String name = textFieldProductName.getText();
        Category category = (Category) comboBoxProductCategory.getSelectedItem();
        String price = textFieldProductPrice.getText();
        String redeemablePoints = textFieldRedeemablePoints.getText();
        String stock = textFieldProductStock.getText();
        boolean redeemable = redeemableCheckBox.isSelected();

        if (name.isBlank()) {
            DialogUtil.showInvalidInputDialog(mainPanel, "Name");
            return;
        }

        if (!ValidateInput.isNumeric(price)) {
            DialogUtil.showInvalidInputDialog(mainPanel, "Price");
            return;
        }

        if (!redeemable) {
            redeemablePoints = "0";
        } else if (!ValidateInput.isNumeric(redeemablePoints)) {
            DialogUtil.showInvalidInputDialog(mainPanel, "Redeemable Points");
            return;
        }

        if (!ValidateInput.isNumeric(stock)) {
            DialogUtil.showInvalidInputDialog(mainPanel, "Stock");
            return;
        }

        if (category == null) {
            DialogUtil.showInvalidInputDialog(mainPanel, "Category");
            return;
        }

        ProductController controller;
        try {
            controller = new ProductController();

            controller.createProduct(name, new BigDecimal(price), redeemable, Integer.parseInt(redeemablePoints), Integer.parseInt(stock), category.getId());
            cleanFields();
            DialogUtil.showSuccessAdded(mainPanel, "Product");
            RefreshFlag.refreshRegisterView = true;
            RefreshFlag.refreshProductView = true;
            RefreshFlag.refreshCustomerView = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void cleanFields() {
        textFieldProductName.setText("");
        textFieldProductPrice.setText("");
        textFieldRedeemablePoints.setText("");
        textFieldProductStock.setText("");
        comboBoxProductCategory.setSelectedIndex(-1);
    }

}
