package br.bakeryudesc;

import br.bakeryudesc.view.MainPanel;

import javax.swing.*;
import java.net.URL;

public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String iconPath = "/icons/logo.png";

        URL imgUrl = Main.class.getResource(iconPath);

        ImageIcon icon;

        if (imgUrl != null) {
            icon = new ImageIcon(imgUrl);
        } else {
            icon = null;
            System.err.println("Failure while trying to find the application icon: " + iconPath);
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bakery Management System");
            if (icon != null) {
                frame.setIconImage(icon.getImage());
            }
            frame.setContentPane(new MainPanel().getMainPanel());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        });
    }
}