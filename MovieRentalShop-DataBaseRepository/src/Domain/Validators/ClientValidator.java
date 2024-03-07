package Domain.Validators;

import Domain.Client;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ClientValidator implements Validator<Client> {

    // Define your email regular expression pattern here
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Override
    public void validate(Client client) throws ValidatorException {
        // TODO: validate client
        if (client == null) {
            throw new ValidatorException("Client is null.");
        }

        if (client.getFirstName() == null || client.getFirstName().isEmpty()) {
            throw new ValidatorException("First name is required.");
        }

        if (client.getLastName() == null || client.getLastName().isEmpty()) {
            throw new ValidatorException("Last name is required.");
        }

        // E-mail validation
        if (client.getEmail() == null || !isValidEmail(client.getEmail())) {
            throw new ValidatorException("A valid e-mail is required.");
        }

        // Phone number validation
        if (client.getPhoneNumber() == null || !isValidPhoneNumber(client.getPhoneNumber())) {
            throw new ValidatorException("A valid phone number is required.");
        }
    }

    private static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    // Define your phone number validation logic here
    private static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        // Remove any non-digit characters (e.g., spaces, dashes, parentheses)
        String digitsOnly = phoneNumber.replaceAll("[^0-9]", "");

        // Check if the cleaned phone number has a valid length

        int length = digitsOnly.length();

        return length == 10; // 10 digits for a valid phone number
    }

}
