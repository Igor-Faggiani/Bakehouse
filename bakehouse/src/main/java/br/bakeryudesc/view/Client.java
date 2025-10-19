package br.bakeryudesc.view;

import javax.swing.*;
import java.awt.*;

public class Client {
    private JPanel clientPanel;
    private JTabbedPane tabbedPane1;
    private JTable customerTable;
    private JTextField searchCustomer;
    private JButton registerButton;
    private JButton editarButton;

    private final registerClient registerClientView;

    public JPanel getClientPanel() {
        return clientPanel;
    }

    public Client() {
        this.registerClientView = new registerClient();

        registerButton.addActionListener(e -> {
            Window owner = SwingUtilities.getWindowAncestor(registerButton);

            JDialog dialog = new JDialog(owner);

            dialog.setTitle("Cadastrar Novo Cliente");
            dialog.setModal(true);
            dialog.setContentPane(registerClientView.getRegisterClient());
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.pack();
            dialog.setLocationRelativeTo(owner);

            dialog.setVisible(true);
        });
    }
}
