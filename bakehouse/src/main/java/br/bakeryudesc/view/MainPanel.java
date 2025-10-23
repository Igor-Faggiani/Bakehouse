package br.bakeryudesc.view;

import br.bakeryudesc.utils.PersistenceInstance;
import br.bakeryudesc.utils.RefreshFlag;
import lombok.Getter;

import javax.persistence.EntityManager;
import javax.swing.*;

public class MainPanel {

    @Getter
    private JPanel mainPanel;
    private JButton buttonCustomer;
    private JPanel sideBarPanel;
    private JButton buttonRegister;
    private JPanel contentPanel;
    private JButton buttonProduct;
    private JButton buttonCategory;

    private final CustomerView customerView;
    private final ProductView productView;
    private final CategoryView categoryView;
    private final RegisterView registerView;

    public MainPanel() {
        this.customerView = new CustomerView();
        this.productView = new ProductView();
        this.categoryView = new CategoryView();
        this.registerView = new RegisterView();
        initListeners();
        initRefreshThread();
    }

    private void initListeners() {
        buttonCustomer.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(customerView.getMainPanel());
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        buttonProduct.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(productView.getMainPanel());
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        buttonCategory.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(categoryView.getMainPanel());
            contentPanel.revalidate();
            contentPanel.repaint();
        });

        buttonRegister.addActionListener(e -> {
            contentPanel.removeAll();
            contentPanel.add(registerView.getMainPanel());
            contentPanel.revalidate();
            contentPanel.repaint();
        });
    }

    private void initRefreshThread() {
        new Thread(() -> {
            while (true) {
                PersistenceInstance.getEntityManager();

                if (RefreshFlag.refreshRegisterView) {
                    registerView.refreshData();
                    RefreshFlag.refreshRegisterView = false;
                }

                if (RefreshFlag.refreshCustomerView) {
                    customerView.refreshData();
                    RefreshFlag.refreshCustomerView = false;
                }

                if (RefreshFlag.refreshProductView) {
                    productView.refreshData();
                    RefreshFlag.refreshProductView = false;
                }

                if (RefreshFlag.refreshCategoryView) {
                    categoryView.refreshData();
                    RefreshFlag.refreshCategoryView = false;
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

}
