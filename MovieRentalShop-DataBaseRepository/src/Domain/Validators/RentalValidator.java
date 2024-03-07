
package Domain.Validators;

import Domain.Rental;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RentalValidator implements Validator<Rental> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public void validate(Rental rental) throws ValidatorException {
        if (rental == null) {
            throw new ValidatorException("Rental cannot be null.");
        }

        if (rental.getClientId() == null) {
            throw new ValidatorException("Client ID cannot be null.");
        }

        if (rental.getMovieId() == null) {
            throw new ValidatorException("Movie ID cannot be null.");
        }

        validateDateTime(rental.getRentalDate(), "Rental date");
        validateDateTime(rental.getReturnDate(), "Return date");
    }

    private void validateDateTime(LocalDateTime dateTime, String fieldName) throws ValidatorException {
        if (dateTime == null) {
            throw new ValidatorException(fieldName + " cannot be null.");
        }

        try {
            FORMATTER.format(dateTime);
        } catch (DateTimeParseException e) {
            throw new ValidatorException(fieldName + " is not in the format dd.MM.yyyy HH:mm.");
        }
    }

    // Additional validation rules can be added here
    // For example, checking if client ID and movie ID exist in their respective tables
    // This might require database access, which should be handled separately
}


