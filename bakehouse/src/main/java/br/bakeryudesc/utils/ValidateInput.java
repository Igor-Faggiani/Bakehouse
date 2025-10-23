package br.bakeryudesc.utils;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidateInput {

    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static String formatTimestamp(String isoTimestamp) {
        if (isoTimestamp == null || isoTimestamp.isEmpty()) {
            return null;
        }

        try {
            LocalDateTime dateTime = LocalDateTime.parse(isoTimestamp);

            return dateTime.format(OUTPUT_FORMATTER);

        } catch (DateTimeParseException e) {
            System.err.println("Error while trying to format the data: " + e.getMessage());
            return isoTimestamp;
        }
    }

    public static String formatCpf(String cpf) {
        if (cpf == null) {
            return null;
        }

        String cleanCpf = cpf.replaceAll("[^0-9]", "");

        if (cleanCpf.length() != 11) {
            return cpf;
        }

        try {
            MaskFormatter mask = new MaskFormatter("###.###.###-##");
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(cleanCpf);
        } catch (ParseException e) {
            return cpf;
        }
    }

    public static boolean isNumeric(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }

        String cleanStr = input.replace(",", ".");

        try {
            double number = Double.parseDouble(cleanStr);
            return number >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidName(String name) {
        if (name == null || name.trim().length() < 2 || name.length() > 255) {
            return false;
        }

        return name.matches("^[a-zA-ZÀ-ÿ '.-]+$");
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }

        String cleanNumber = phoneNumber.replaceAll("[^0-9]", "");

        int length = cleanNumber.length();
        return length == 10 || length == 11;
    }

    public static boolean isValidCpf(String cpf) {
        String cleanCpf = cpf.replaceAll("[^0-9]", "");

        if (cleanCpf.length() != 11) {
            return false;
        }

        if (cleanCpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += (cleanCpf.charAt(i) - '0') * (10 - i);
            }

            int remainder = (sum * 10) % 11;
            int digit1 = remainder == 10 ? 0 : remainder;

            if (digit1 != (cleanCpf.charAt(9) - '0')) {
                return false;
            }

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += (cleanCpf.charAt(i) - '0') * (11 - i);
            }

            remainder = (sum * 10) % 11;
            int digit2 = remainder == 10 ? 0 : remainder;

            return digit2 == (cleanCpf.charAt(10) - '0');

        } catch (NumberFormatException e) {
            return false;
        }
    }


}
