package br.bakeryudesc.view.tab.register;

import br.bakeryudesc.controller.CategoryController;
import br.bakeryudesc.utils.DialogUtil;
import br.bakeryudesc.utils.RefreshFlag;
import lombok.Getter;

import javax.swing.*;

public class RegisterCategoryTab {
    @Getter
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField textFieldCategoryName;
    private JButton buttonAdd;

    private final CategoryController categoryController;

    public RegisterCategoryTab() {
        this.categoryController = new CategoryController();
        initListeners();
    }

    private void initListeners() {
        buttonAdd.addActionListener(e -> {
            addCategory();
        });
    }

    private void addCategory() {
        String categoryName = textFieldCategoryName.getText();

        if (categoryName.isEmpty()) {
            DialogUtil.showInvalidInputDialog(mainPanel, "Category");
            return;
        }

        categoryController.createCategory(categoryName);
        cleanFields();
        DialogUtil.showSuccessAdded(mainPanel, "Category");
        RefreshFlag.refreshRegisterView = true;
    }

    private void cleanFields() {
        textFieldCategoryName.setText("");
    }

}
