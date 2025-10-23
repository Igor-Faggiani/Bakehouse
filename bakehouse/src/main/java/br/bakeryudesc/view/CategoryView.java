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

    private List<Category> categoryList;

    private Category currentCategory;

    public CategoryView() {
        populateList();
        initListeners();
    }

    private void populateList() {
        CategoryController controller;
        try {
            controller = new CategoryController();

            categoryList = controller.findAllCategories();
            System.out.println(categoryList);
            jListCategory.setListData(categoryList.toArray());
            jListCategory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
        CategoryController controller;
        try {
            controller = new CategoryController();

            currentCategory.setName(textField_nameCategory.getText());
            controller.updateCategory(currentCategory);
            DialogUtil.showSuccessUpdated(mainPanel, "Category");
            RefreshFlag.refreshCategoryView = true;
            RefreshFlag.refreshProductView = true;
            RefreshFlag.refreshRegisterView = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteCategory() {
        CategoryController controller;
        try {
            controller = new CategoryController();

            controller.deleteCategory(currentCategory);
            DialogUtil.showSuccessDeleted(mainPanel, "Category");
            populateList();
            RefreshFlag.refreshCategoryView = true;
            RefreshFlag.refreshProductView = true;
            RefreshFlag.refreshRegisterView = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
        if (currentCategory != null) {
            textField_nameCategory.setText(currentCategory.getName());
        }
    }

    public void refreshData() {
        populateList();
        refreshInfo();
    }

}
