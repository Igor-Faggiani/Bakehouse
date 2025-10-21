package br.bakeryudesc.view;

import lombok.Getter;

import javax.swing.*;

public class MainPanel {

    @Getter
    private JPanel mainPanel;
    private JButton buttonCustomer;
    private JButton button;
    private JPanel sideBarPanel;
    private JButton buttonRegister;
    private JPanel contentPanel;

    public MainPanel() {
        initListeners();
    }

    private void initListeners() {

        buttonCustomer.addActionListener(e -> {
            contentPanel.add(new CustomerForm().getMainPanel());
            contentPanel.revalidate();
            contentPanel.repaint();
        });
    }

}
