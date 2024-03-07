/*package Domain;

public class Rental extends BaseEntity<Long> {
    private Long clientId;
    private Long movieId;

    private rentalDate;
    private returnDate;

    public Rental(Long id, Long clientId, Long movieId) {
        super(id);
        this.clientId = clientId;
        this.movieId = movieId;
    }

    // Getters and Setters
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    // Override toString for easy display
    @Override
    public String toString() {
        return "Rental{" +
                "id=" + getId() +
                ", clientId=" + clientId +
                ", movieId=" + movieId +
                '}';
    }
}*/
package Domain;


import java.time.LocalDateTime;

public class Rental extends BaseEntity<Long> {
    private Long clientId;
    private Long movieId;
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;

    public Rental(Long id, Long clientId, Long movieId, LocalDateTime rentalDate, LocalDateTime returnDate) {
        super(id);
        this.clientId = clientId;
        this.movieId = movieId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    // Getters and Setters
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public LocalDateTime getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    // Override toString for easy display
    @Override
    public String toString() {
        return "Rental{" +
                "id=" + getId() +
                ", clientId=" + clientId +
                ", movieId=" + movieId +
                ", rentalDate=" + rentalDate +
                ", returnDate=" + returnDate +
                '}';
    }
}

