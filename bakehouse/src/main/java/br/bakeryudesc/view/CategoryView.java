package br.bakeryudesc.view;

import br.bakeryudesc.controller.CategoryController;
import br.bakeryudesc.model.Category;
import br.bakeryudesc.utils.DialogUtil;
import br.bakeryudesc.utils.RefreshFlag;
import lombok.Getter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public class CategoryView {
    @Getter
    private JPanel mainPanel;
    private JLabel searchLabel;
    private JTextField searchCategory;
    private JList jListCategory;
    private JTextField textField_nameCategory;
    private JLabel nameLabel;
    private JButton buttonSave;
    private JButton buttonDelete;
    private JScrollPane categoryScrollPane;

    private CategoryController categoryController;
    private List<Category> categoryList;

    private Category currentCategory;

    public CategoryView() {
        this.categoryController = new CategoryController();
        populateList();
        initListeners();
    }

    private void populateList() {
        categoryList = categoryController.findAllCategories();
        jListCategory.setListData(categoryList.toArray());
        jListCategory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initListeners() {
        searchCategory.getDocument().addDocumentListener(new DocumentListener() {
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

        jListCategory.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }

            Category category = (Category) jListCategory.getSelectedValue();

            if (category == null) {
                return;
            }

            currentCategory = category;
            refreshInfo();
        });

        buttonSave.addActionListener(e -> {
            updateCategory();
        });

        buttonDelete.addActionListener(e -> {
           deleteCategory();
        });

    }

    private void updateCategory() {
        currentCategory.setName(textField_nameCategory.getText());
        categoryController.updateCategory(currentCategory);
        DialogUtil.showSuccessUpdated(mainPanel, "Category");
        RefreshFlag.refreshCategoryView = true;
    }

    private void deleteCategory() {
        categoryController.deleteCategory(currentCategory);
        DialogUtil.showSuccessDeleted(mainPanel, "Category");
        populateList();
    }

    private void filterList() {
        String searchText = searchCategory.getText().toLowerCase();

        if (searchText.isEmpty()) {
            jListCategory.setListData(categoryList.toArray());
            return;
        }

        List<Category> filteredList = categoryList.stream()
                .filter(category -> category.getName() != null && category.getName().toLowerCase().contains(searchText))
                .toList();

        jListCategory.setListData(filteredList.toArray());
    }

    private void refreshInfo() {
        textField_nameCategory.setText(currentCategory.getName());
    }

    public void refreshData() {
        this.categoryController = new CategoryController();
        populateList();
        refreshInfo();
    }

}
