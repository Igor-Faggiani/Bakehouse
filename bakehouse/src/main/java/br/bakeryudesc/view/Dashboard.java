package br.bakeryudesc.view;

import javax.swing.*;
import java.awt.*;

public class Dashboard {
    private JPanel dashboardPanel;
    private JButton dashboardButton;
    private JButton clientesButton;
    private JButton produtosButton;
    private JPanel sideBarPanel;
    private JPanel mainContentPanel;
    private JButton backButton;

    private final Client clientView;
    private final Product productView;
    private final JPanel dashboardHomePanel;

    public Dashboard() {
        this.clientView = new Client();
        this.productView = new Product();
        this.dashboardHomePanel = new JPanel(new GridBagLayout());
        dashboardHomePanel.add(new JLabel("Bem-vindo ao Sistema!"));

        showPanel(dashboardHomePanel);

        clientesButton.addActionListener(e -> {
            showPanel(clientView.getClientPanel());
        });

        produtosButton.addActionListener(e -> {
            showPanel(productView.getProductPanel());
        });

        dashboardButton.addActionListener(e -> {
            showPanel(dashboardHomePanel);
        });

        backButton.addActionListener(e -> {
            System.exit(0);
        });
    }

    private void showPanel(JPanel panel) {
        mainContentPanel.removeAll();
        mainContentPanel.setLayout(new BorderLayout());
        mainContentPanel.add(panel, BorderLayout.CENTER);
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }

    public JPanel getDashboardPanel() {
        return dashboardPanel;
    }
}