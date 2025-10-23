package br.bakeryudesc.view;

import br.bakeryudesc.view.tab.register.RegisterCategoryTab;
import br.bakeryudesc.view.tab.register.RegisterCustomerTab;
import br.bakeryudesc.view.tab.register.RegisterProductTab;
import lombok.Getter;

import javax.swing.*;

public class RegisterView {
    @Getter
    private JPanel mainPanel;
    private JTabbedPane registerPanelTabbed;
    private JPanel registerCustomerTab;
    private JPanel registerProductTab;
    private JPanel registerCategoryTab;

    private RegisterCustomerTab customerTab;
    private RegisterProductTab productTab;
    private RegisterCategoryTab categoryTab;

    public RegisterView() {
        refreshData();
    }

    public void refreshData() {
        this.customerTab = new RegisterCustomerTab();
        this.productTab = new RegisterProductTab();
        this.categoryTab = new RegisterCategoryTab();
        initComponents();
    }

    private void initComponents() {
        registerCustomerTab.removeAll();
        registerProductTab.removeAll();
        registerCategoryTab.removeAll();

        registerCustomerTab.add(customerTab.getMainPanel());
        registerProductTab.add(productTab.getMainPanel());
        registerCategoryTab.add(categoryTab.getMainPanel());

        mainPanel.revalidate();
        mainPanel.repaint();
    }

}
