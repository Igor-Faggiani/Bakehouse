package br.bakeryudesc;

import br.bakeryudesc.view.Dashboard;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bakery Management System");

            frame.setContentPane(new Dashboard().getDashboardPanel());

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            frame.setVisible(true);
        });
    }
}