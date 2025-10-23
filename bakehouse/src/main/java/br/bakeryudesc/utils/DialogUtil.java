package br.bakeryudesc.utils;

import br.bakeryudesc.model.Product;

import javax.swing.*;
import java.awt.*;

public class DialogUtil {

    public static void showInvalidInputDialog(Component parent, String fieldName) {
        String title = "Invalid Input";
        String message = "The field '" + fieldName + "' is invalid or in the wrong format.\n"
                + "Please check and try again.";

        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public static void showEmptyInputDialog(Component parent, String fieldName) {
        String title = "Invalid Input";
        String message = "The field '" + fieldName + "' is empty.\n"
                + "Please check and try again.";

        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public static void showErrorMessage(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void showSuccessBuy(Component parent, String objectName) {
        String title = "Success";
        String message = objectName + " has been completed successfully.";

        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showSuccessAdded(Component parent, String objectName) {
        String title = "Success";
        String message = objectName + " has been added successfully.";

        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showSuccessDeleted(Component parent, String objectName) {
        String title = "Success";
        String message = objectName + " has been deleted successfully.";

        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showSuccessUpdated(Component parent, String objectName) {
        String title = "Success";
        String message = objectName + " has been updated successfully.";

        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean askToUsePoints(Component parent, int customerPoints) {
        String title = "Use Redeemable Points";
        String message = String.format("You have %d available points.\nWould you like to use them?", customerPoints);

        int result = JOptionPane.showConfirmDialog(
                parent,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        return result == JOptionPane.YES_OPTION;
    }

}
